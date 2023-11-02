package com.rmnnorbert.InquireNet.service.user.security.loginHistory;

import com.rmnnorbert.InquireNet.dao.model.loginHistory.LoginHistoryDaoJdbc;
import com.rmnnorbert.InquireNet.dto.loginHistory.LoginHistoryDTO;
import com.rmnnorbert.InquireNet.service.user.security.loginHistory.loginDetailsHandler.IpAddressValidator;
import com.rmnnorbert.InquireNet.service.user.security.loginHistory.loginDetailsHandler.UserAgentSanitizer;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
@Service
public class LoginHistoryService {
    private final Counter loginSuccessCounter;
    private final Counter loginFailureCounter;
    private final IpAddressValidator validatorService;
    private final UserAgentSanitizer sanitizerService;
    private final LoginHistoryDaoJdbc loginHistoryDaoJdbc;
    @Autowired
    public LoginHistoryService(IpAddressValidator validatorService, UserAgentSanitizer sanitizerService, LoginHistoryDaoJdbc loginHistoryDaoJdbc) {
        this.validatorService = validatorService;
        this.sanitizerService = sanitizerService;
        this.loginHistoryDaoJdbc = loginHistoryDaoJdbc;
        loginSuccessCounter = Metrics.counter("counter.login.success");
        loginFailureCounter = Metrics.counter("counter.login.failure");
    }

    public void successfulLogin(long userId) {
        storeLoginAttempt(userId);
        loginSuccessCounter.increment();
    }

    public void unSuccessfulLogin(long userId) {
        storeLoginAttempt(userId);
        loginFailureCounter.increment();
    }

    private void storeLoginAttempt(long userId) {
        String[] loginDetails = getLoginDetails();
        String ipAddress = loginDetails[0];
        String userAgent = loginDetails[1];
        LoginHistoryDTO dto = new LoginHistoryDTO(
                LocalDateTime.now(),
                userId,
                ipAddress,
                userAgent
                );
        loginHistoryDaoJdbc.addLoginHistory(dto);
    }
    private String[] getLoginDetails(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                                                                .getRequest();

        if (request != null) {
            String clientIpAddress = request.getRemoteAddr();
            String userAgent = request.getHeader("User-Agent");

            if (!validatorService.isValidIPAddress(clientIpAddress)) {
                clientIpAddress = "Unknown";
            }
            userAgent = sanitizerService.sanitizeUserAgent(userAgent);

            return new String[]{clientIpAddress, userAgent};
        } else {
            return new String[] {"Unknown", "Unknown"};
        }
    }
}
