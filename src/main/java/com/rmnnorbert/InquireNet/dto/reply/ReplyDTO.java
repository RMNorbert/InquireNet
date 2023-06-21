package com.rmnnorbert.InquireNet.dto.reply;

import com.rmnnorbert.InquireNet.dao.model.reply.Reply;

import java.time.LocalDateTime;

public record ReplyDTO(long reply_id, long answer_id, String description, LocalDateTime created) {
    public static ReplyDTO of(Reply reply){
        return new ReplyDTO(
                reply.getReply_id(),
                reply.getAnswer_id(),
                reply.getDescription(),
                reply.getCreated()
        );
    }
}
