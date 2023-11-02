package com.rmnnorbert.InquireNet.dto.loginHistory;

import java.time.LocalDateTime;

public record LoginHistoryDTO(LocalDateTime time,
                             long user_id,
                             String ip_address,
                             String user_agent) {
}

