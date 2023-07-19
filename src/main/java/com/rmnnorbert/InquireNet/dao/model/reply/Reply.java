package com.rmnnorbert.InquireNet.dao.model.reply;


import lombok.With;

import java.time.LocalDateTime;

public record Reply(long reply_id,
                    long user_id,
                    long answer_id,
                    @With String description,
                    LocalDateTime created) {

}
