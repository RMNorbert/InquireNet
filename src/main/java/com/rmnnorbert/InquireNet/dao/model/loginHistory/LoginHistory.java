package com.rmnnorbert.InquireNet.dao.model.loginHistory;

import java.time.LocalDateTime;

public record LoginHistory(long history_id,
                           LocalDateTime time,
                           long user_id,
                           String ip_address,
                           String user_agent) {
}
