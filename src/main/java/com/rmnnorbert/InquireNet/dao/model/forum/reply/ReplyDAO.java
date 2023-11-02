package com.rmnnorbert.InquireNet.dao.model.forum.reply;

import com.rmnnorbert.InquireNet.dto.forum.reply.NewReplyDTO;

import java.util.List;

public interface ReplyDAO {
    List<Reply> getAllReply();
    Reply findReplyById(long id);
    List<Reply> getAllReplyByAnswerId(long id);
    boolean addReply(NewReplyDTO replyDTO);
    boolean deleteReplyById(long theId);
    boolean deleteReplyByAnswerId(long theId);
    boolean update(Reply replyUpdate);
}
