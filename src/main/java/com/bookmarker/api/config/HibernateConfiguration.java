package com.bookmarker.api.config;

import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfiguration {
    
    @Bean
    HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
        return props -> props.put("integrator_provider", ClassImportIntegratorProvider.class.getName()); 
    }
}