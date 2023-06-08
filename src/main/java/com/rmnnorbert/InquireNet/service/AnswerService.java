package com.rmnnorbert.InquireNet.service;

import com.rmnnorbert.InquireNet.dao.model.answer.AnswerDAOJdbc;
import com.rmnnorbert.InquireNet.dto.answer.AnswerDTO;
import com.rmnnorbert.InquireNet.dto.answer.NewAnswerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AnswerService {
    private final AnswerDAOJdbc answerDAO;

    @Autowired
    public AnswerService(AnswerDAOJdbc answerDAO) {
        this.answerDAO = answerDAO;
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


    public boolean deleteAnswerById(int id) {
        return answerDAO.deleteAnswerById(id);
    }

    public int addNewAnswer(NewAnswerDTO answer) {
        return answerDAO.addAnswer(answer);
    }
}
