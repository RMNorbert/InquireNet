package com.rmnnorbert.InquireNet.dao.model.user.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    ANSWER_READ("answer:read"),
    ANSWER_CREATE("answer:create"),
    ANSWER_UPDATE("answer:update"),
    ANSWER_DELETE("answer:delete"),
    QUESTION_READ("question:read"),
    QUESTION_CREATE("question:create"),
    QUESTION_UPDATE("question:update"),
    QUESTION_DELETE("question:delete"),
    USER_READ("user:read"),
    USER_UPDATE("user:update"),
    USER_CREATE("user:create"),
    USER_DELETE("user:delete"),
    REPLY_READ("reply:read"),
    REPLY_UPDATE("reply:update"),
    REPLY_CREATE("reply:create"),
    REPLY_DELETE("reply:delete");
    @Getter
    private final String permission;
}
