package com.rmnnorbert.InquireNet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dto.answer.AnswerDTO;
import com.rmnnorbert.InquireNet.dto.answer.AnswerRequestDTO;
import com.rmnnorbert.InquireNet.dto.answer.VoteDTO;
import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.update.UpdateDTO;
import com.rmnnorbert.InquireNet.service.AnswerService;
import com.rmnnorbert.InquireNet.service.VoteService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AnswerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AnswerService answerService;
    @MockBean
    private VoteService voteService;
    final static String MOCK_USERNAME = "testUser";

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("provideExpectedList")
    @WithMockUser(username = MOCK_USERNAME)
    void getAllAnswersShouldReturnOkStatusAndAnswerDTOList(List<AnswerDTO> expected) throws Exception {
        when(answerService.getAllAnswers()).thenReturn(expected);

        mockMvc.perform(get("/answers/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(expected.size())));

        verify(answerService,times(1)).getAllAnswers();
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME)
    void getAnswerByIdShouldReturnOkStatusAndExpectedValue() throws Exception {
        long searchedId = 1;
        AnswerDTO expectedAnswerDTO = new AnswerDTO(1,
                                                    1,
                                                    1,
                                                    "desc",
                                                    LocalDateTime.now(),
                                                    1,
                                                    "vote");

        when(answerService.getAnswerById(searchedId)).thenReturn(expectedAnswerDTO);

        mockMvc.perform(get("/answers/" + searchedId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer_id", Matchers.is((int)searchedId)))
                .andExpect(jsonPath("$.description", is("desc")));

        verify(answerService,times(1)).getAnswerById(searchedId);
    }


    @Test
    @WithMockUser(username = MOCK_USERNAME)
    void getAnswerByIdWhenServiceThrowsNotFoundExceptionShouldReturnNotFoundStatus() throws Exception {
        long searchedId = 1;

        when(answerService.getAnswerById(searchedId)).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/answers/" + searchedId))
                .andExpect(status().isNotFound());

        verify(answerService,times(1)).getAnswerById(searchedId);
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME)
    void getNumberOfAnswersByUserIdShouldReturnOkStatusAndIntegerNumber() throws Exception {
        long id = 1;
        int numberOfAnswers = 2;

        when(answerService.getNumberOfAnswersByUserId(id)).thenReturn(numberOfAnswers);

        mockMvc.perform(get("/answers/user/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(String.valueOf(numberOfAnswers))));

        verify(answerService,times(1)).getNumberOfAnswersByUserId(id);
    }

    @ParameterizedTest
    @MethodSource("provideIdAndExpectedList")
    @WithMockUser(username = MOCK_USERNAME)
    void getAllAnswersByQuestionIdShouldReturnOkStatusAndExpectedValue(long searchedId, List<AnswerDTO> expected) throws Exception {
        when(answerService.getAllAnswersByQuestionId(searchedId)).thenReturn(expected);

        mockMvc.perform(get("/answers/q/" + searchedId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(expected.size())));

        verify(answerService,times(1)).getAllAnswersByQuestionId(searchedId);
    }

    @ParameterizedTest
    @MethodSource("provideExpectedValue")
    @WithMockUser(username = MOCK_USERNAME)
    void addNewAnswerShouldReturnOkStatusAndExpectedValue(boolean expected) throws Exception {
        AnswerRequestDTO dto = new AnswerRequestDTO(1L,"desc",1L);

        when(answerService.addNewAnswer(dto)).thenReturn(expected);

        String jsonRequest = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(post("/answers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",is(expected)));

        verify(answerService,times(1)).addNewAnswer(dto);
    }

    @ParameterizedTest
    @MethodSource("provideExpectedValue")
    @WithMockUser(username = MOCK_USERNAME)
    public void deleteAnswerByIdShouldReturnOkStatusAndExpectedValue(boolean expected) throws Exception {
        DeleteRequestDTO dto = new DeleteRequestDTO(1,1);

        when(answerService.deleteAnswerById(dto)).thenReturn(expected);

        String jsonRequest = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(delete("/answers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",is(expected)));

        verify(answerService,times(1)).deleteAnswerById(dto);
    }

    @ParameterizedTest
    @MethodSource("provideExpectedValue")
    @WithMockUser(username = MOCK_USERNAME)
    void voteOnAnswerByIdShouldReturnOkStatusAndExpectedValue(boolean expected) throws Exception {
        VoteDTO dto = new VoteDTO("vote",1,1,1);

        when(voteService.vote(dto)).thenReturn(expected);

        String jsonRequest = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(put("/answers/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",is(expected)));

        verify(voteService,times(1)).vote(dto);
    }
    @ParameterizedTest
    @MethodSource("provideUpdateDTOAndExpectedValue")
    @WithMockUser(username = MOCK_USERNAME)
    void updateAnswerShouldReturnOkStatusAndExpectedValue(UpdateDTO value, boolean expected) throws Exception {
        when(answerService.update(value)).thenReturn(expected);

        String jsonRequest = new ObjectMapper().writeValueAsString(value);

        mockMvc.perform(put("/answers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",is(expected)));

        verify(answerService,times(1)).update(value);
    }
    @Test
    @WithMockUser(username = MOCK_USERNAME)
    void updateAnswerShouldReturnNotFoundStatusAndEmptyBody() throws Exception {
        UpdateDTO dto = new UpdateDTO(1,1,"title","desc");

        when(answerService.update(dto)).thenThrow(NotFoundException.class);

        String jsonRequest = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(put("/answers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isNotFound())
                .andExpect(content().string(emptyString()));
    }
    private static Stream<Arguments> provideIdAndExpectedList() {
        return Stream.of(
                Arguments.of( 1,List.of(
                        new AnswerDTO(1,1,1,"desc",LocalDateTime.now(),1,"vote"),
                        new AnswerDTO(2,2,1,"description",LocalDateTime.now(),1,"vote")
                )),
                Arguments.of( 2,List.of())
                );
    }
    private static Stream<Arguments> provideExpectedValue() {
        return Stream.of(
                Arguments.of( true),
                Arguments.of( false)
        );
    }
    private static Stream<Arguments> provideUpdateDTOAndExpectedValue() {
        return Stream.of(
                Arguments.of(new UpdateDTO(1,1,"title","desc"), true),
                Arguments.of( new UpdateDTO(1,2,"title","desc"),false)
        );
    }
    private static Stream<Arguments> provideExpectedList() {
        return Stream.of(
                Arguments.of(List.of(
                        new AnswerDTO(1L,
                                1,
                                1,
                                "desc",
                                LocalDateTime.now(),
                                0,
                                "voted")
                )),
                Arguments.of(List.of())
        );
    }
}
