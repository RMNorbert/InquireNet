package com.rmnnorbert.InquireNet.dao.model.answer;


import java.time.LocalDateTime;


public record Answer(long answer_id,
                     long user_id,
                     long question_id,
                     String description,
                     LocalDateTime created,
                     int numberOfReply,
                     String vote) {
}
