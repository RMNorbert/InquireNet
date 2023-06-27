package com.rmnnorbert.InquireNet.dto.question;

public record UpdateQuestionDTO (long question_id,
                                 long user_id,
                                 String title,
                                 String description)
{
}
