package com.example.blood_donation.config;

import com.example.blood_donation.entity.Account;
import com.example.blood_donation.entity.Role;
import com.example.blood_donation.enumType.PreDefinedRole;
import com.example.blood_donation.repository.AccountRepository;
import com.example.blood_donation.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ApplicationInit {

    PasswordEncoder passwordEncoder;
    AccountRepository accountRepository;
    RoleRepository roleRepository;

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            if (accountRepository.findByEmail("admin@gmail.com").isEmpty()) {
                Account account = new Account();
                Set<Role> roles = new HashSet<>();
                Role role = roleRepository.findByName(PreDefinedRole.ADMIN.name());
                roles.add(role);
                account.setRoles(roles);
                account.setPassword(passwordEncoder.encode("admin"));
                account.setEmail("admin@gmail.com");
                accountRepository.save(account);
            }
        };
    }
}
