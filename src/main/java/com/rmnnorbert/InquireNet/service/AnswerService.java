package com.rmnnorbert.InquireNet.service;

import com.rmnnorbert.InquireNet.dao.model.answer.Answer;
import com.rmnnorbert.InquireNet.dao.model.answer.AnswerDAOJdbc;
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
    private final ReplyService replyService;
    @Autowired
    public AnswerService(AnswerDAOJdbc answerDAO, ReplyService replyService) {
        this.answerDAO = answerDAO;
        this.replyService = replyService;
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
        AnswerDTO answer = getAnswerById(dto.targetId());
        if(answer.user_id() == dto.userId()) {
            deleteRepliesOfAnswer(dto.targetId());
            return answerDAO.deleteAnswerById(dto.targetId());
        }
        return false;
    }
    public void deleteAnswers(List<AnswerDTO> answerDTOS) {
        for (AnswerDTO answer: answerDTOS) {
        deleteRepliesOfAnswer(answer.answer_id());
        answerDAO.deleteAnswerById(answer.answer_id());
        }
    }
    public boolean addNewAnswer(AnswerRequestDTO answer) {
        return answerDAO.addAnswer(answer);
    }
    public boolean update(AnswerRequestDTO answerRequestDTO){
        Answer answer = answerDAO.findAnswerById(answerRequestDTO.id());
        if(answerRequestDTO.userId() == answer.user_id()) {
            return answerDAO.update(answerRequestDTO.description(), answerRequestDTO.id());
        }
        return false;
    }
    private void deleteRepliesOfAnswer(long id){
        replyService.deleteAllReplyOfAnswer(id);
    }
    public void updateVote(VoteDTO voteDTO) {
            answerDAO.changeVote(voteDTO.vote(), voteDTO.id());
    }
    public int getNumberOfAnswersByUserId(long id) {
        return answerDAO.getNumberOfUserAnswers(id);
    }
}
