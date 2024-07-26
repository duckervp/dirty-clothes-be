package com.dirty.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider", auditorAwareRef = "auditorProvider", modifyOnCreate = false)
public class AuditorPersistenceConfig {
    @Bean(name = "auditorProvider")
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication == null) {
//                return Optional.of("ANONYMOUS");
//            } else {
//                try {
//                    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
//                    return Optional.of(userPrincipal.getUuid().toString());
//                } catch (Exception e) {
//                    return Optional.of("ANONYMOUS");
//                }
//            }
            return Optional.of("ANONYMOUS");
        };
    }

    @Bean(name = "auditingDateTimeProvider")
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(Instant.now());
    }
}
