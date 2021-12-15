package com.tcs.ins.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.tcs.ins.support.SpringSecurityAuditorAware;

@Configuration
@EnableJpaAuditing
@EnableEnversRepositories(basePackages = { "com.tcs.ins.job.repository" })
class RepositoryConfig {

	@Bean
	AuditorAware<String> auditorProvider() {
		return new SpringSecurityAuditorAware();
	}
}