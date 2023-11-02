package com.rmnnorbert.InquireNet.dto.forum.reply;

public record NewReplyDTO (String description,
                           long answerId,
                           long userId)
{
}
