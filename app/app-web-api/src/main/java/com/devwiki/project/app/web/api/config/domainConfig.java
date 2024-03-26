package com.devwiki.project.app.web.api.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.devwiki.project.core.domain_service", "com.devwiki.project.core.domain"})
public class domainConfig {
}
