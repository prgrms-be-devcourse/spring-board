package com.programmers.board.config;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@TestConfiguration
@EnableJpaAuditing
public class JpaAuditConfig {

	@Bean
	public AuditorAware<String> autAuditorProvider() {
		return () -> Optional.of(UUID.randomUUID().toString());
	}
}
