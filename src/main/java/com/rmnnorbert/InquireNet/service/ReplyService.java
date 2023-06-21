package com.rmnnorbert.InquireNet.service;

import com.rmnnorbert.InquireNet.dao.model.reply.ReplyDAOJdbc;
import com.rmnnorbert.InquireNet.dto.reply.NewReplyDTO;
import com.rmnnorbert.InquireNet.dto.reply.ReplyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ReplyService {
    private final ReplyDAOJdbc replyDAOJdbc;
    @Autowired
    public ReplyService(ReplyDAOJdbc replyDAOJdbc) {
        this.replyDAOJdbc = replyDAOJdbc;
    }

    public List<ReplyDTO> getAllReply() {
        return replyDAOJdbc.getAllReply()
                .stream()
                .map(ReplyDTO::of)
                .toList();
    }
    public Optional<ReplyDTO> getReplyById(long id) {
        return replyDAOJdbc.findReplyById(id).map(ReplyDTO::of);
    }
    public List<ReplyDTO> getAllReplyByAnswerId(long id){
        return replyDAOJdbc.getAllReplyByAnswerId(id)
                .stream()
                .map(ReplyDTO::of)
                .toList();
    }
    public boolean deleteReplyById(long id) {
        return replyDAOJdbc.deleteReplyById(id);
    }

    public int addNewReply(NewReplyDTO replyDTO) {
        return replyDAOJdbc.addReply(replyDTO);
    }

}
