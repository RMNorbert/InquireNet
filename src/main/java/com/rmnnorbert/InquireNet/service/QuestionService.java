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
            if (question.getQuestion_id() == dto.userId()) {
                deleteAnswers(dto.targetId());
                return questionsDAO.deleteQuestionById(dto.targetId());
            }
        return false;
    }

    public int addNewQuestion(NewQuestionDTO question) {
        return questionsDAO.addQuestion(question);
    }
    private boolean deleteAnswers(long id){
        List<Answer> answersOfQuestion = answerDAOJdbc.getAllAnswersByQuestionId(id);
        for (Answer answer: answersOfQuestion) {
            deleteAllReply(answer.getAnswer_id());
        }
        return answerDAOJdbc.deleteAnswerByQuestionId(id);
    }
    private boolean deleteAllReply(long id){
        return replyDAOJdbc.deleteReplyByAnswerId(id);
    }
}
