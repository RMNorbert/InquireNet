package com.rmnnorbert.InquireNet.dto.answer;

public record VoteDTO (String vote,
                       long id ,
                       long userId,
                       long questionId)
{
}
