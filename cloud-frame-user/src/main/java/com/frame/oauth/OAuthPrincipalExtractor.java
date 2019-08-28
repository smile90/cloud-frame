package com.frame.oauth;

import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;

import java.util.Map;

public class OAuthPrincipalExtractor implements PrincipalExtractor {

    private static final String[] PRINCIPAL_KEYS = new String[]{"user", "username",
            "userid", "user_id", "login", "id", "name", "uid"};

    @Override
    public Object extractPrincipal(Map<String, Object> map) {
        for (String key : PRINCIPAL_KEYS) {
            if (map.containsKey(key)) {
                return map.get(key);
            }
        }
        return null;
    }
}