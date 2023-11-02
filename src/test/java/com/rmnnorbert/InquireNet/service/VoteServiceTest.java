package com.rmnnorbert.InquireNet.service;

import annotations.UnitTest;
import com.rmnnorbert.InquireNet.dto.forum.answer.VoteDTO;
import com.rmnnorbert.InquireNet.dto.forum.question.QuestionDTO;
import com.rmnnorbert.InquireNet.service.forum.answer.AnswerService;
import com.rmnnorbert.InquireNet.service.forum.question.QuestionService;
import com.rmnnorbert.InquireNet.service.forum.vote.VoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
@UnitTest
class VoteServiceTest {
    @Mock
    private QuestionService questionService;
    @Mock
    private AnswerService answerService;
    private VoteService voteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        voteService = new VoteService(questionService,answerService);
    }

    @Test
    void voteSuccessfullyShouldReturnTrue() {
        boolean expected = true;
        VoteDTO voteDTO = new VoteDTO("upvote",1,1,1);
        QuestionDTO question = new QuestionDTO(1,1,"Title","Desc", LocalDateTime.now(),0);

        when(questionService.getQuestionById(voteDTO.questionId())).thenReturn(question);
        when(answerService.updateVote(voteDTO)).thenReturn(expected);

        boolean actual = voteService.vote(voteDTO);

        assertTrue(actual);
        verify(questionService,times(1)).getQuestionById(voteDTO.questionId());
        verify(answerService,times(1)).updateVote(voteDTO);
    }

    @Test
    void voteUnSuccessfullyShouldReturnFalse() {
        VoteDTO voteDTO = new VoteDTO("upvote",1,2,1);
        QuestionDTO question = new QuestionDTO(1,1,"Title","Desc", LocalDateTime.now(),0);

        when(questionService.getQuestionById(voteDTO.questionId())).thenReturn(question);

        boolean actual = voteService.vote(voteDTO);
        assertFalse(actual);
    }
}
