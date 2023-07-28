package com.rmnnorbert.InquireNet.service;

import com.rmnnorbert.InquireNet.dto.answer.VoteDTO;
import com.rmnnorbert.InquireNet.dto.question.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {
    private final QuestionService questionService;
    private final AnswerService answerService;
    @Autowired
    public VoteService(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    public boolean vote(VoteDTO voteDTO) {
        QuestionDTO question = questionService.getQuestionById(voteDTO.questionId());
        if(question.user_id() == voteDTO.userId()) {
            return answerService.updateVote(voteDTO);
        }
        return false;
    }
}
