package com.rmnnorbert.InquireNet.service.forum.vote;

import com.rmnnorbert.InquireNet.dto.forum.answer.VoteDTO;
import com.rmnnorbert.InquireNet.dto.forum.question.QuestionDTO;
import com.rmnnorbert.InquireNet.service.forum.answer.AnswerService;
import com.rmnnorbert.InquireNet.service.forum.question.QuestionService;
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
