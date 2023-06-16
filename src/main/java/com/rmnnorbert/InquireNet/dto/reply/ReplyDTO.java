package com.rmnnorbert.InquireNet.dto.reply;

import com.rmnnorbert.InquireNet.dao.model.reply.Reply;

import java.time.LocalDateTime;

public class ReplyDTO {
    private final int reply_id;
    private final int answer_id;
    private final String description;
    private final LocalDateTime created;

    private ReplyDTO(int answer_id,
                      int reply_id,
                      String description,
                      LocalDateTime created) {
        this.reply_id = reply_id;
        this.answer_id = answer_id;
        this.description = description;
        this.created = created;
    }
    public static ReplyDTO of(Reply reply){
        return new ReplyDTO(
                reply.getReply_id(),
                reply.getAnswer_id(),
                reply.getDescription(),
                reply.getCreated()
        );
    }

    public int getReply_id() {
        return reply_id;
    }

    public int getAnswer_id() {
        return answer_id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreated() {
        return created;
    }
}
