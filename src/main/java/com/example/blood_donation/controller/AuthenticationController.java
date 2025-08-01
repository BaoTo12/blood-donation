package com.example.blood_donation.controller;


import com.example.blood_donation.dto.requests.LogoutRequest;
import com.example.blood_donation.dto.requests.RefreshRequest;
import com.example.blood_donation.dto.requests.account.AccountCreationRequest;
import com.example.blood_donation.dto.requests.auth.AuthenticationRequest;
import com.example.blood_donation.dto.requests.auth.IntrospectRequest;
import com.example.blood_donation.dto.responses.ApiResponse;
import com.example.blood_donation.dto.responses.auth.AuthenticationResponse;
import com.example.blood_donation.dto.responses.auth.IntrospectResponse;
import com.example.blood_donation.service.AccountService;
import com.example.blood_donation.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("${api.base-path}/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationController {
    AuthenticationService authenticationService;
    AccountService accountService;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        return ApiResponse.<IntrospectResponse>builder()
                .result(authenticationService.introspect(request))
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request)
            throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);

        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();

    }

    @PostMapping("/register")
    public ApiResponse<Long> register(@Valid @RequestBody AccountCreationRequest request) {
        var result = accountService.createAccount(request);
        return ApiResponse.<Long>builder().result(result).build();
    }
}
