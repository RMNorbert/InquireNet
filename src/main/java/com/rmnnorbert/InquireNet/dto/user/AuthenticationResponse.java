package com.rmnnorbert.InquireNet.dto.user;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record AuthenticationResponse(String token,
                                     LocalDateTime time)
{
}
