package com.rmnnorbert.InquireNet.service;

import com.rmnnorbert.InquireNet.dao.model.answer.AnswerDAOJdbc;
import com.rmnnorbert.InquireNet.dao.model.reply.ReplyDAOJdbc;
import com.rmnnorbert.InquireNet.dto.answer.AnswerDTO;
import com.rmnnorbert.InquireNet.dto.answer.AnswerRequestDTO;
import com.rmnnorbert.InquireNet.dto.answer.VoteDTO;
import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AnswerService {
    private final AnswerDAOJdbc answerDAO;
    private final ReplyDAOJdbc replyDAOJdbc;
    @Autowired
    public AnswerService(AnswerDAOJdbc answerDAO, ReplyDAOJdbc replyDAOJdbc) {
        this.answerDAO = answerDAO;
        this.replyDAOJdbc = replyDAOJdbc;
    }
    public List<AnswerDTO> getAllAnswers() {
        return answerDAO.getAllAnswers()
                .stream()
                .map(AnswerDTO::of)
                .toList();
    }
    public AnswerDTO getAnswerById(long id) {
        return AnswerDTO.of(answerDAO.findAnswerById(id));
    }
    public List<AnswerDTO> getAllAnswersByQuestionId(long id){
        return answerDAO.getAllAnswersByQuestionId(id)
                .stream()
                .map(AnswerDTO::of)
                .toList();
    }
    public boolean deleteAnswerById(DeleteRequestDTO dto) {
        deleteRepliesOfAnswer(dto.targetId());
        return answerDAO.deleteAnswerById(dto.targetId());
    }
    public int addNewAnswer(AnswerRequestDTO answer) {
        return answerDAO.addAnswer(answer);
    }
    public boolean update(AnswerRequestDTO answerRequestDTO){ return answerDAO.update(answerRequestDTO.description(),answerRequestDTO.id());}
    public void updateVote(VoteDTO voteDTO) { answerDAO.changeVote(voteDTO.vote(), voteDTO.id());}
    private boolean deleteRepliesOfAnswer(long id){
        return replyDAOJdbc.deleteReplyByAnswerId(id);
    }
}
