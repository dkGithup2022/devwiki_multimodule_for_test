package com.devwiki.project.core.domain_service;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.devwiki.project.core.infra.rdms"})
public class DomainServiceConfig {
}
