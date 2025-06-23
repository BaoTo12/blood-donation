package com.example.blood_donation.config;

import com.example.blood_donation.entity.Account;
import com.example.blood_donation.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ApplicationInit {

    PasswordEncoder passwordEncoder;
    AccountRepository accountRepository;

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            if (accountRepository.findByEmail("admin@gmail.com").isEmpty()) {
                Account account = new Account();
                account.setPassword(passwordEncoder.encode("admin"));
                account.setEmail("admin@gmail.com");
                accountRepository.save(account);
            }
        };
    }
}
