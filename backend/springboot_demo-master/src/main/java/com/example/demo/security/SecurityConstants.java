package com.example.demo.security;

import org.springframework.core.env.Environment;

import com.example.demo.SpringApplicationContext;

public class SecurityConstants {

//    public static final long EXPIRATION_TIME = 600000; // 10 minute
    public static final long EXPIRATION_TIME = 86400000; // 1 day
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_ULR = "/users";
    public static final String JWT_LOGIN = "/users/login";
    public static final String TOKEN_SECRET = "3KmORmVkH91G6OMF1ZCqLRswgRznz0vV5aHUgOpNlIqsRpoosBCQNGgVlNNMk3EvDl";
    public static final String VERIFICATION_EMAIL_URL = "/users/email-verification";
    public static final String PASSWORD_RESET_REQUEST_URL = "/users/password-reset-request";
    public static final String PASSWORD_RESET_URL = "/users/password-reset";
    public static final String H2_CONSOLE = "/h2-console/**";

    public static String getTockenSecrect() {
        Environment environment = (Environment) SpringApplicationContext.getBean("environment");
        return environment.getProperty("tokenSecrect");
    }

    public static String getJwtRefreshExpirationInMs() {
        Environment environment = (Environment) SpringApplicationContext.getBean("environment");
        return environment.getProperty("jwtRefreshExpirationInMs");
    }
}
