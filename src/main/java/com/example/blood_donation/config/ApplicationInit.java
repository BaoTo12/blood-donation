package com.example.blood_donation.config;

import com.example.blood_donation.entity.Account;
import com.example.blood_donation.entity.Role;
import com.example.blood_donation.repository.AccountRepository;
import com.example.blood_donation.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ApplicationInit {

    PasswordEncoder passwordEncoder;
    AccountRepository accountRepository;
    RoleRepository roleRepository;

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            if (accountRepository.findByEmail("admin@gmail.com").isEmpty()) {
                Account account = new Account();
                List<Role> roles = roleRepository.findAll();
                account.setRoles(new HashSet<>(roles));
                account.setPassword(passwordEncoder.encode("admin"));
                account.setEmail("admin@gmail.com");
                accountRepository.save(account);
            }
        };
    }
}
