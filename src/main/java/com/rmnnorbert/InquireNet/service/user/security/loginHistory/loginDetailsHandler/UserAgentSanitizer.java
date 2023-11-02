package com.rmnnorbert.InquireNet.service.user.security.loginHistory.loginDetailsHandler;

import org.springframework.stereotype.Service;

@Service
public class UserAgentSanitizer {
    public String sanitizeUserAgent(String userAgent) {
        if (userAgent == null) {
            return "";
        }
        userAgent = userAgent.replaceAll("[^a-zA-Z0-9 .-]", "").trim().toLowerCase();
        return userAgent;
    }
}
