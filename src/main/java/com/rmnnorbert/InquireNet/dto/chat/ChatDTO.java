package com.rmnnorbert.InquireNet.dto.chat;

import com.rmnnorbert.InquireNet.dao.model.chat.Chat;


public record ChatDTO(long chatId,
                      long userId,
                      String title,
                      String role,
                      String content)
{
    public static ChatDTO of(Chat chat){
        return new ChatDTO(
                chat.chat_id(),
                chat.user_id(),
                chat.title(),
                chat.role(),
                chat.content()
        );
    }
}
