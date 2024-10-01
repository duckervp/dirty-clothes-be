package com.dirty.shop.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "auth")
@Getter
@Setter
public class AuthProperty {
    private String tokenSecret;
    private long accessTokenExpirationMils;
    private long refreshTokenExpirationMils;
    private long verifyEmailTokenExpirationMils;
    private long resetPasswordMinutes;
    private String resetPasswordUrl;
    private String recaptchaSecretToken;
    private String recaptchaGoogleUrl;
    private String supportEmail;
    private String twoFaIssuer;
    private String resetPasswordRedirectPath;
}
