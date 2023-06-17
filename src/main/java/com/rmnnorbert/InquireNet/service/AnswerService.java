package com.rmnnorbert.InquireNet.service;

import com.rmnnorbert.InquireNet.dao.model.answer.AnswerDAOJdbc;
import com.rmnnorbert.InquireNet.dao.model.reply.ReplyDAOJdbc;
import com.rmnnorbert.InquireNet.dto.answer.AnswerDTO;
import com.rmnnorbert.InquireNet.dto.answer.NewAnswerDTO;
import com.rmnnorbert.InquireNet.dto.answer.VoteDTO;
import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public Optional<AnswerDTO> getAnswerById(int id) {
        return answerDAO.findAnswerById(id).map(AnswerDTO::of);
    }
    public List<AnswerDTO> getAllAnswersByQuestionId(int id){
        return answerDAO.getAllAnswersByQuestionId(id)
                .stream()
                .map(AnswerDTO::of)
                .toList();
    }


    public boolean deleteAnswerById(DeleteRequestDTO dto) {
        deleteRepliesOfAnswer(dto.targetId());
        return answerDAO.deleteAnswerById(dto.targetId());
    }

    public int addNewAnswer(NewAnswerDTO answer) {
        return answerDAO.addAnswer(answer);
    }
    public void updateVote(VoteDTO voteDTO) { answerDAO.changeVote(voteDTO.vote(), voteDTO.id());}
    private boolean deleteRepliesOfAnswer(int id){
        return replyDAOJdbc.deleteReplyByAnswerId(id);
    }
}
