package com.rmnnorbert.InquireNet.service;

import com.rmnnorbert.InquireNet.dto.answer.VoteDTO;
import com.rmnnorbert.InquireNet.dto.question.QuestionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

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
        VoteDTO voteDTO = new VoteDTO("upvote",1,1,1);
        QuestionDTO question = new QuestionDTO(1,1,"Title","Desc", LocalDateTime.now(),0);

        when(questionService.getQuestionById(voteDTO.questionId())).thenReturn(question);

        boolean actual = voteService.vote(voteDTO);
        assertTrue(actual);
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
