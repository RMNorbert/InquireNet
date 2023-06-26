package com.rmnnorbert.InquireNet.service;

import com.rmnnorbert.InquireNet.dao.model.question.Question;
import com.rmnnorbert.InquireNet.dao.model.question.QuestionsDaoJdbc;
import com.rmnnorbert.InquireNet.dto.answer.AnswerDTO;
import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.question.NewQuestionDTO;
import com.rmnnorbert.InquireNet.dto.question.QuestionDTO;
import com.rmnnorbert.InquireNet.dto.question.UpdateQuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class QuestionService {
    private final QuestionsDaoJdbc questionsDAO;
    private final AnswerService answerService;


    @Autowired
    public QuestionService(QuestionsDaoJdbc questionsDAO, AnswerService answerService) {
        this.questionsDAO = questionsDAO;
        this.answerService = answerService;
    }

    public List<QuestionDTO> getAllQuestions() {
        return questionsDAO.getAllQuestion()
                .stream()
                .map(QuestionDTO::of)
                .toList();
    }
    public List<QuestionDTO> getAllQuestionOfUser(long userId){
        return questionsDAO.getAllQuestionByUserID(userId)
                .stream()
                .map(QuestionDTO::of)
                .toList();
    }
    public long getLastQuestion() {
        return questionsDAO.findLastQuestionId();
    }

    public QuestionDTO getQuestionById(long id) {
        Question question = questionsDAO.findQuestionById(id);
        return QuestionDTO.of(question);
    }

    public boolean deleteQuestionById(DeleteRequestDTO dto) {
        Question question = questionsDAO.findQuestionById(dto.targetId());
            if (question.user_id() == dto.userId()) {
                deleteAnswers(dto.targetId());
                return questionsDAO.deleteQuestionById(dto.targetId());
            }
        return false;
    }
    public boolean updateQuestion(UpdateQuestionDTO questionDTO){
        QuestionDTO question = getQuestionById(questionDTO.question_id());
        if (question.user_id() == questionDTO.user_id()) {
            return questionsDAO.update(questionDTO);
        }
        return false;
    }
    public boolean addNewQuestion(NewQuestionDTO question) {
        return questionsDAO.addQuestion(question);
    }
    private void deleteAnswers(long id){
        List<AnswerDTO> answersOfQuestion = answerService.getAllAnswersByQuestionId(id);
        answerService.deleteAnswers(answersOfQuestion);
    }

}
