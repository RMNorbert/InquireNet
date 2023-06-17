package com.rmnnorbert.InquireNet.service;

import com.rmnnorbert.InquireNet.dao.model.answer.Answer;
import com.rmnnorbert.InquireNet.dao.model.answer.AnswerDAOJdbc;
import com.rmnnorbert.InquireNet.dao.model.question.Question;
import com.rmnnorbert.InquireNet.dao.model.question.QuestionsDaoJdbc;
import com.rmnnorbert.InquireNet.dao.model.reply.ReplyDAOJdbc;
import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.question.NewQuestionDTO;
import com.rmnnorbert.InquireNet.dto.question.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class QuestionService {
    private final QuestionsDaoJdbc questionsDAO;
    private final AnswerDAOJdbc answerDAOJdbc;
    private final ReplyDAOJdbc replyDAOJdbc;

    @Autowired
    public QuestionService(QuestionsDaoJdbc questionsDAO, AnswerDAOJdbc answerDAOJdbc, ReplyDAOJdbc replyDAOJdbc) {
        this.questionsDAO = questionsDAO;
        this.answerDAOJdbc = answerDAOJdbc;
        this.replyDAOJdbc = replyDAOJdbc;
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

    public boolean deleteQuestionById(DeleteRequestDTO dto) {
        Optional<Question> question = questionsDAO.findQuestionById(dto.targetId());
        if(question.isPresent()) {
            if (question.get().getUserID() == dto.userId()) {
                deleteAnswers(dto.targetId());
                return questionsDAO.deleteQuestionById(dto.targetId());
            }
        }
        return false;
    }

    public int addNewQuestion(NewQuestionDTO question) {
        return questionsDAO.addQuestion(question);
    }
    private boolean deleteAnswers(int id){
        List<Answer> answersOfQuestion = answerDAOJdbc.getAllAnswersByQuestionId(id);
        for (Answer answer: answersOfQuestion) {
            deleteAllReply(answer.getAnswer_id());
        }
        return answerDAOJdbc.deleteAnswerByQuestionId(id);
    }
    private boolean deleteAllReply(int id){
        return replyDAOJdbc.deleteReplyByAnswerId(id);
    }
}
