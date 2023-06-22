package com.rmnnorbert.InquireNet.dao.model.question;

import java.time.LocalDateTime;

public record Question(long question_id,
                       long user_id,
                       String title,
                       String description,
                       LocalDateTime created,
                       int numberOfAnswers) {

}
