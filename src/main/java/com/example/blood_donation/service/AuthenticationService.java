package com.example.blood_donation.service;


import com.example.blood_donation.dto.requests.LogoutRequest;
import com.example.blood_donation.dto.requests.RefreshRequest;
import com.example.blood_donation.dto.requests.auth.AuthenticationRequest;
import com.example.blood_donation.dto.requests.auth.IntrospectRequest;
import com.example.blood_donation.dto.responses.auth.AuthenticationResponse;
import com.example.blood_donation.dto.responses.auth.IntrospectResponse;
import com.example.blood_donation.entity.Account;
import com.example.blood_donation.entity.InvalidatedToken;
import com.example.blood_donation.exception.AppException;
import com.example.blood_donation.exception.ErrorCode;
import com.example.blood_donation.repository.AccountRepository;
import com.example.blood_donation.repository.InValidatedTokenRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    PasswordEncoder passwordEncoder;
    AccountRepository accountRepository;
    InValidatedTokenRepository inValidatedTokenRepository;
    @NonFinal
    @Value("${jwt.signer-key}")
    // this field is still final but being treated as nonfinal --> RequiredArgsConstructor won't create constructor for this
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected Long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected Long REFRESHABLE_DURATION;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Account user = accountRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new AppException(ErrorCode.EMAIL_INVALID, "Email is invalid")
        );

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated)
            throw new AppException(ErrorCode.INVALID_PASSWORD, "Password is invalid");

        String token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean valid = true;
        try {
            verifyToken(token, false);
        } catch (AppException e) {
            valid = false;
        }
        return IntrospectResponse.builder().valid(valid).build();
    }

    public AuthenticationResponse refreshToken(RefreshRequest request)
            throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getToken(), true);
        // check if user doesn't belong to our system --> throw error
        var email = signedJWT.getJWTClaimsSet().getSubject();

        var user = accountRepository.findByEmail(email).orElseThrow(
                () -> new AppException(ErrorCode.EMAIL_INVALID, "Email is invalid")
        );
        // if user exists then we invalidate that token
        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();

        inValidatedTokenRepository.save(invalidatedToken);

        // create new token
        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try {
            var signedToken = verifyToken(request.getToken(), true);
            String jit = signedToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signedToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken inValidatedToken = new InvalidatedToken(jit, expiryTime);

            inValidatedTokenRepository.save(inValidatedToken);
        } catch (AppException e) {
            throw new AppException(ErrorCode.INVALID_TOKEN, "Token is expired");
        }
    }

    private String generateToken(Account account) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(account.getEmail()) // for user information
                .issuer("chibaosan.com") // to define who issues the token
                .issueTime(new Date()) // the time issue the token
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(account)) // -- > custom claim
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            // sign the token
            // this example uses symmetric key that means one key is used for encrypting and decrypting
            // there is also asymmetric key --> go deeper
            jwsObject.sign(new MACSigner(SIGNER_KEY));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token ", e);
            throw new RuntimeException(e);
        }
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        // check invalidatedToken
        if (inValidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED, "Invalid token");
        }

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);
        if (!(verified && expiryTime.after(new Date()))) {
            throw new AppException(ErrorCode.UNAUTHENTICATED, "Invalid token");
        }
        return signedJWT;
    }

    private String buildScope(Account account) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(account.getRoles())) {
            account.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission -> {
                        stringJoiner.add(permission.getName());
                    });
                }
            });
        }
        return stringJoiner.toString();
    }

}
