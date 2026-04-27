package com.bna.habil.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class LdapConfig {

    @Value("${spring.ldap.urls}")
    private String ldapUrl;

    @Value("${spring.ldap.base}")
    private String ldapBase;

    @Value("${spring.ldap.username}")
    private String ldapUsername;

    @Value("${spring.ldap.password}")
    private String ldapPassword;

    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(ldapUrl);
        contextSource.setBase(ldapBase);
        contextSource.setUserDn(ldapUsername);
        contextSource.setPassword(ldapPassword);
        contextSource.setPooled(false);

        // Configure base environment to ignore referrals (fixes PartialResultException)
        Map<String, Object> baseEnvironment = new HashMap<>();
        baseEnvironment.put("java.naming.referral", "ignore");
        baseEnvironment.put("java.naming.ldap.referral.limit", "0");
        contextSource.setBaseEnvironmentProperties(baseEnvironment);

        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate(LdapContextSource contextSource) {
        LdapTemplate ldapTemplate = new LdapTemplate(contextSource);

        // Ignore partial result exceptions (common in AD environments)
        ldapTemplate.setIgnorePartialResultException(true);
        ldapTemplate.setIgnoreNameNotFoundException(true);

        return ldapTemplate;
    }
}