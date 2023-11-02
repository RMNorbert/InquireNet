package com.rmnnorbert.InquireNet.dao.model.loginHistory;

import com.rmnnorbert.InquireNet.dto.loginHistory.LoginHistoryDTO;

import java.util.List;

public interface LoginHistoryDAO {
    List<LoginHistory> getAllLoginHistory();
    LoginHistory findLoginHistoryByLoginHistoryId(long id);
    List<LoginHistory> getAllLoginHistoryByUserId(long id);
    boolean addLoginHistory(LoginHistoryDTO dto);
    boolean deleteLoginHistoryById(long theId);
    boolean deleteLoginHistoryByUserId(long theId);
}
