package com.example.blood_donation.config;

import com.example.blood_donation.dto.request.auth.IntrospectRequest;
import com.example.blood_donation.repository.InValidatedTokenRepository;
import com.example.blood_donation.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class CustomJwtDecoder implements JwtDecoder {
    final InValidatedTokenRepository invalidatedTokenRepository;

    NimbusJwtDecoder nimbusJwtDecoder = null;

    @Value("${jwt.signer-key}")
    String signerKey;

    @Override
    public Jwt decode(String token) throws JwtException {

        try {
            validateTokenDirectly(token);

        } catch (JOSEException | ParseException e) {
            throw new JwtException(e.getMessage());
        }
        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder
                    .withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }


        return nimbusJwtDecoder.decode(token);
    }
    private void validateTokenDirectly(String token) throws JOSEException, ParseException {
        // Create a verifier using our signing key
        JWSVerifier verifier = new MACVerifier(signerKey.getBytes());

        // Parse the token
        SignedJWT signedJWT = SignedJWT.parse(token);

        // Check if this token has been invalidated (logged out)
        String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
        if (invalidatedTokenRepository.existsById(jwtId)) {
            throw new JwtException("Token has been invalidated");
        }

        // Verify the signature and check expiration
        boolean isSignatureValid = signedJWT.verify(verifier);
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean isNotExpired = expirationTime.after(new Date());

        if (!isSignatureValid || !isNotExpired) {
            throw new JwtException("Token is invalid or expired");
        }
    }
}
