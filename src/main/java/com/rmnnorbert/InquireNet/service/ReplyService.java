package com.rmnnorbert.InquireNet.service;

import com.rmnnorbert.InquireNet.dao.model.reply.Reply;
import com.rmnnorbert.InquireNet.dao.model.reply.ReplyDAOJdbc;
import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.reply.NewReplyDTO;
import com.rmnnorbert.InquireNet.dto.reply.ReplyDTO;
import com.rmnnorbert.InquireNet.dto.reply.ReplyUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public ReplyDTO getReplyById(long id) {
        return ReplyDTO.of(replyDAOJdbc.findReplyById(id));
    }
    public List<ReplyDTO> getAllReplyByAnswerId(long id){
        return replyDAOJdbc.getAllReplyByAnswerId(id)
                .stream()
                .map(ReplyDTO::of)
                .toList();
    }
    public boolean deleteReplyById(DeleteRequestDTO dto) {
        ReplyDTO replyDTO = getReplyById(dto.targetId());
        if(replyDTO.user_id() == dto.userId()) {
            return replyDAOJdbc.deleteReplyById(dto.targetId());
        }
        return false;
    }
    public boolean updateReply(ReplyUpdateDTO updateDTO) {
        Reply foundReply = replyDAOJdbc.findReplyById(updateDTO.id());
        if(updateDTO.userId() == foundReply.user_id() &&
           !updateDTO.description().equals(foundReply.description())) {
            Reply updatingReply = foundReply.withDescription(updateDTO.description());
            return replyDAOJdbc.update(updatingReply);
        }
        return false;
    }
    public boolean addNewReply(NewReplyDTO replyDTO) {
        return replyDAOJdbc.addReply(replyDTO);
    }
    public boolean deleteAllReplyOfAnswer(long id){
        return replyDAOJdbc.deleteReplyByAnswerId(id);
    }

}
