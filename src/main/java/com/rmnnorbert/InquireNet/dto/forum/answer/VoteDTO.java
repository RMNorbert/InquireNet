package com.rmnnorbert.InquireNet.dto.forum.answer;

public record VoteDTO (String vote,
                       long id ,
                       long userId,
                       long questionId)
{
}
