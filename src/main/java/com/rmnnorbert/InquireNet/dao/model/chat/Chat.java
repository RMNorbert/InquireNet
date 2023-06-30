package com.rmnnorbert.InquireNet.dao.model.chat;

public record Chat(long chat_id,
                   long user_id,
                   String title,
                   String role,
                   String content
                  ) {
}
