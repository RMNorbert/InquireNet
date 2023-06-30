package com.rmnnorbert.InquireNet.dto.chat;

public record ChatRegisterDTO(long userId,
                              String title,
                              String role,
                              String content)
{
}
