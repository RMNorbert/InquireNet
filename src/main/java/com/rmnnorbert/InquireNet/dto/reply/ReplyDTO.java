package com.rmnnorbert.InquireNet.dto.reply;

import com.rmnnorbert.InquireNet.dao.model.reply.Reply;

import java.time.LocalDateTime;

public record ReplyDTO(long reply_id, long user_id, long answer_id, String description, LocalDateTime created) {
    public static ReplyDTO of(Reply reply){
        return new ReplyDTO(
                reply.reply_id(),
                reply.user_id(),
                reply.answer_id(),
                reply.description(),
                reply.created()
        );
    }
}
