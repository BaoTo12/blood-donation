package com.example.blood_donation.config;

import com.example.blood_donation.enumType.PreDefinedRole;
import com.nimbusds.jose.JWSAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${api.base-path}")
    private String apiVersion;

    @Autowired
    CustomJwtDecoder customJwtDecoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        InMemoryUserDetailsManager uds = new InMemoryUserDetailsManager();
        uds.createUser(User.withUsername("user")
                .password(passwordEncoder.encode("123"))
                .roles("USER")
                .build());
        return uds;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        String[] baseEndpoints = {"/users", "/auth/token", "/auth/introspect", "/auth/logout"};

        // Build versioned endpoints dynamically
        String[] publicEndpoints = java.util.Arrays.stream(baseEndpoints)
                .map(endpoint -> apiVersion + endpoint)
                .toArray(String[]::new);
        httpSecurity
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(HttpMethod.POST, publicEndpoints).permitAll()
//                                .requestMatchers(HttpMethod.GET, apiVersion + "/account")
//                                    .hasRole(PreDefinedRole.ADMIN.name())
                                .anyRequest().authenticated()
                );

        //Tells Spring Security “when you see a Bearer token, hand it to this JwtDecoder
        // → give me an Authentication or fail.”
        // default flow
        /*
         *  AuthenticationFilter --> AuthenticationManager --> AuthenticationProvider
         * --> DaoAuthenticationProvider uses UserDetailsService and PasswordEncoder
         * */
        // JWT flow
        /*
        * BearerTokenAuthenticationFilter --> AuthenticationManager --> JwtAuthenticationProvider
        * --> uses NimbusJwtDecoder and JWS verification
        * JWT Parsing: Your token is decoded and verified
            Claims Extraction: Spring extracts the "scope" claim containing "ADMIN"
            Authority Conversion: Each scope value gets prefixed with "SCOPE_"
            Authentication Object Creation: A 'JwtAuthenticationToken' is created with:
            Name: JWT subject (admin@gmail.com)
            Authorities: Collection of SimpleGrantedAuthority objects
            * JwtAuthenticationToken is stored in SecurityContextHolder
            * Controller Access: Your controller retrieves the authentication object
         * */
        httpSecurity.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwtConfigurer -> jwtConfigurer
                        .decoder(customJwtDecoder)
                        .jwtAuthenticationConverter(jwtAuthenticationConverter())
                ));

        return httpSecurity.build();
    }

    //BearerTokenAuthenticationFilter → JwtAuthenticationProvider →
    // NimbusJwtDecoder → JwtAuthenticationConverter → SecurityContextHolder
    // JwtAuthenticationConverter serves as a crucial bridge between JWT token claims and Spring Security's authorization system.
    // it takes raw JWT payload and converts it into format that Spring Security expects
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        // This converter examines your JWT's claims and extracts authorities based on specific patterns.
        // By default, it looks for claims named "scope" or "scp" and treats them as space-separated lists of authorities.
        // change the prefix from "SCOPE_" to "ROLE_",
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }
}
