package com.rmnnorbert.InquireNet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.question.NewQuestionDTO;
import com.rmnnorbert.InquireNet.dto.question.QuestionDTO;
import com.rmnnorbert.InquireNet.dto.update.UpdateDTO;
import com.rmnnorbert.InquireNet.service.QuestionService;
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

import static com.rmnnorbert.InquireNet.controller.AnswerControllerTest.MOCK_USERNAME;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class QuestionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QuestionService service;
    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("provideExpectedList")
    @WithMockUser(username = MOCK_USERNAME)
    void getAllQuestionsShouldReturnOkStatusAndQuestionDTOList(List<QuestionDTO> expected) throws Exception {
        when(service.getAllQuestions()).thenReturn(expected);

        mockMvc.perform(get("/questions/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(expected.size())));

        verify(service,times(1)).getAllQuestions();
    }

    @ParameterizedTest
    @MethodSource("provideIdAndExpectedList")
    @WithMockUser(username = MOCK_USERNAME)
    void getAllQuestionsOfUserShouldReturnOkStatusAndExpectedQuestionDTOList(long searchedId,
                                                                             List<QuestionDTO> expected
                                                                            ) throws Exception {

        when(service.getAllQuestionOfUser(searchedId)).thenReturn(expected);

        mockMvc.perform(get("/questions/all/" + searchedId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",Matchers.hasSize(expected.size())));

        verify(service,times(1)).getAllQuestionOfUser(searchedId);
    }

    @Test
    void getLastQuestionsShouldReturnOkStatusAndExpectedValue() throws Exception {
        long expected = 1;

        when(service.getLastQuestion()).thenReturn(expected);

        mockMvc.perform(get("/questions/last")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(String.valueOf(expected))));

        verify(service,times(1)).getLastQuestion();
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME)
    void getQuestionByIdShouldReturnOkStatusAndQuestionDTO() throws Exception {
        long searchedId = 1;
        QuestionDTO dto = new QuestionDTO(1,
                                             1,
                                         "title",
                                     "desc",
                                  LocalDateTime.now(),
                                    0);

        when(service.getQuestionById(searchedId)).thenReturn(dto);

        mockMvc.perform(get("/questions/" + searchedId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question_id", Matchers.is((int)searchedId)))
                .andExpect(jsonPath("$.title", is("title")));

        verify(service,times(1)).getQuestionById(searchedId);
    }
    @Test
    @WithMockUser(username = MOCK_USERNAME)
    void getQuestionByIdShouldReturnOkStatusAndNotFoundException() throws Exception {
        long searchedId = 1L;

        when(service.getQuestionById(searchedId)).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/question/" + searchedId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(emptyString()));

        verify(service,times(0)).getQuestionById(searchedId);
    }
    @ParameterizedTest
    @MethodSource("provideExpectedValue")
    @WithMockUser(username = MOCK_USERNAME)
    void addNewQuestion(boolean expected) throws Exception {
        NewQuestionDTO dto = new NewQuestionDTO("title","desc",1);

        when(service.addNewQuestion(dto)).thenReturn(expected);

        String jsonRequest = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(post("/questions/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",Matchers.is(expected)));

        verify(service,times(1)).addNewQuestion(dto);
    }

    @ParameterizedTest
    @MethodSource("provideExpectedValueAndUpdateValue")
    @WithMockUser(username = MOCK_USERNAME)
    void updateQuestion(boolean expected, UpdateDTO dto) throws Exception {
        when(service.updateQuestion(dto)).thenReturn(expected);

        String jsonRequest = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(put("/questions/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",Matchers.is(expected)));

        verify(service,times(1)).updateQuestion(dto);
    }

    @ParameterizedTest
    @MethodSource("provideExpectedValueAndDeleteValue")
    @WithMockUser(username = MOCK_USERNAME)
    void deleteQuestionById(boolean expected, DeleteRequestDTO dto) throws Exception {
        when(service.deleteQuestionById(dto)).thenReturn(expected);

        String jsonRequest = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(delete("/questions/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",Matchers.is(expected)));

        verify(service,times(1)).deleteQuestionById(dto);
    }

    private static Stream<Arguments> provideExpectedList() {
        return Stream.of(
          Arguments.of(List.of(
                  new QuestionDTO(1,1,"title","desc", LocalDateTime.now(),0),
                  new QuestionDTO(2,2,"titles","description", LocalDateTime.now(),0)
          )),
          Arguments.of(List.of())
        );
    }
    private static Stream<Arguments> provideIdAndExpectedList() {
        return Stream.of(
          Arguments.of(1,List.of(
                  new QuestionDTO(1,1,"title","desc", LocalDateTime.now(),0),
                  new QuestionDTO(2,2,"titles","description", LocalDateTime.now(),0)
          )),
          Arguments.of(2,List.of())
        );
    }
    private static Stream<Arguments> provideExpectedValue() {
        return Stream.of(
                Arguments.of(true),
                Arguments.of(false)
        );
    }
    private static Stream<Arguments> provideExpectedValueAndUpdateValue() {
        return Stream.of(
                Arguments.of(true, new UpdateDTO(1,1,"Title","Desc")),
                Arguments.of(false, new UpdateDTO(2,1,"Title","Desc"))
        );
    }
    private static Stream<Arguments> provideExpectedValueAndDeleteValue() {
        return Stream.of(
                Arguments.of(true, new DeleteRequestDTO(1,1)),
                Arguments.of(false, new DeleteRequestDTO(1,2))
        );
    }
}
