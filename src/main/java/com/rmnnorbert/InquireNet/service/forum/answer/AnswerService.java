package com.rmnnorbert.InquireNet.service.forum.answer;

import com.rmnnorbert.InquireNet.dao.model.forum.answer.Answer;
import com.rmnnorbert.InquireNet.dao.model.forum.answer.AnswerDAOJdbc;
import com.rmnnorbert.InquireNet.dto.forum.answer.AnswerDTO;
import com.rmnnorbert.InquireNet.dto.forum.answer.AnswerRequestDTO;
import com.rmnnorbert.InquireNet.dto.forum.answer.VoteDTO;
import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.update.UpdateDTO;
import com.rmnnorbert.InquireNet.service.forum.reply.ReplyService;
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
    public List<Long> getAllVotesByUserId(long id){
        String upvote = "upVoted";
        String downvote = "downVoted";

        long upvotes = answerDAO.getAllAnswersByUserId(id)
                .stream()
                .filter(answer -> answer.vote().equals(upvote))
                .count();

        long downvotes = answerDAO.getAllAnswersByUserId(id)
                .stream()
                .filter(answer -> answer.vote().equals(downvote))
                .count();

        return List.of(upvotes,downvotes);
    }
    public boolean deleteAnswerById(DeleteRequestDTO dto) {
        AnswerDTO answer = getAnswerById(dto.targetId());
        if(answer.user_id() == dto.userId()) {
            deleteRepliesOfAnswer(dto.targetId());
            return answerDAO.deleteAnswerById(dto.targetId());
        }
        return false;
    }
    public boolean deleteAnswers(List<AnswerDTO> answerDTOS) {
        for (AnswerDTO answer: answerDTOS) {
        deleteRepliesOfAnswer(answer.answer_id());
        answerDAO.deleteAnswerById(answer.answer_id());
        }
        return true;
    }
    public boolean addNewAnswer(AnswerRequestDTO answer) {
        return answerDAO.addAnswer(answer);
    }
    public boolean update(UpdateDTO updateDTO){
        Answer answer = answerDAO.findAnswerById(updateDTO.id());
        if(updateDTO.userId() == answer.user_id()) {
            return answerDAO.update(updateDTO);
        }
        return false;
    }
    private void deleteRepliesOfAnswer(long id){
        replyService.deleteAllReplyOfAnswer(id);
    }
    public boolean updateVote(VoteDTO voteDTO) {
            return answerDAO.changeVote(voteDTO.vote(), voteDTO.id());
    }
    public int getNumberOfAnswersByUserId(long id) {
        return answerDAO.getNumberOfUserAnswers(id);
    }
}
