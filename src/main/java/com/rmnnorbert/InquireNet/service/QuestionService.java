package com.rmnnorbert.InquireNet.service;

import com.rmnnorbert.InquireNet.dao.model.question.Question;
import com.rmnnorbert.InquireNet.dao.model.question.QuestionsDaoJdbc;
import com.rmnnorbert.InquireNet.dto.question.NewQuestionDTO;
import com.rmnnorbert.InquireNet.dto.question.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class QuestionService {
    private final QuestionsDaoJdbc questionsDAO;

    @Autowired
    public QuestionService(QuestionsDaoJdbc questionsDAO) {
        this.questionsDAO = questionsDAO;
    }

    public List<QuestionDTO> getAllQuestions() {
        return questionsDAO.getAllQuestion().stream().map(QuestionDTO::of).toList();
    }
    public Optional<QuestionDTO> getLastQuestion() {
        return questionsDAO.findLastQuestion().map(QuestionDTO::of);
    }

    public Optional<QuestionDTO> getQuestionById(int id) {
        Optional<Question> question = questionsDAO.findQuestionById(id);
        return question.map(QuestionDTO::of);
    }

    public boolean deleteQuestionById(int id) {
        return questionsDAO.deleteQuestionById(id);
    }

    public int addNewQuestion(NewQuestionDTO question) {
        return questionsDAO.addQuestion(question);
    }
}
