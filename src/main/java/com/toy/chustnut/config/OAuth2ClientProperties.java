package com.toy.chustnut.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@ConfigurationProperties(prefix = "spring.security.oauth2.client")
public class OAuth2ClientProperties {

    public static class Registration {

        private String provider;

        private String clientId;

        private String clientSecret;

        private String clientAuthenticationMethod;

        private String authorizationGrantType;

        private String redirectUri;

        private Set<String> scope;
    }
}
