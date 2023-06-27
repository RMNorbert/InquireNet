package com.rmnnorbert.InquireNet.dao.model.reply;

import com.rmnnorbert.InquireNet.dto.reply.NewReplyDTO;
import com.rmnnorbert.InquireNet.dto.reply.ReplyDTO;

import java.util.List;
import java.util.Optional;

public interface ReplyDAO {
    List<Reply> getAllReply();
    Optional<Reply> findReplyById(long id);
    List<Reply> getAllReplyByAnswerId(long id);
    boolean addReply(NewReplyDTO replyDTO);
    boolean deleteReplyById(long theId);
    boolean deleteReplyByAnswerId(long theId);
    boolean update(ReplyDTO replyDTO);
}
