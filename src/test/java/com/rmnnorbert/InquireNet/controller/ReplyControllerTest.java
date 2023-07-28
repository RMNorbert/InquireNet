package com.rmnnorbert.InquireNet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.reply.NewReplyDTO;
import com.rmnnorbert.InquireNet.dto.reply.ReplyDTO;
import com.rmnnorbert.InquireNet.dto.reply.ReplyUpdateDTO;
import com.rmnnorbert.InquireNet.service.ReplyService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReplyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReplyService replyService;
    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("provideExpectedList")
    @WithMockUser(username = MOCK_USERNAME)
    void getAllReplyShouldReturnOkStatusAndExpectedReplyDTOList(List<ReplyDTO> expected) throws Exception {
        when(replyService.getAllReply()).thenReturn(expected);

        mockMvc.perform(get("/reply/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(expected.size())));

        verify(replyService, times(1)).getAllReply();
    }

    @Test
    void getReplyByIdShouldReturnOkStatusAndReplyDTO() throws Exception {
        long searchedId = 1;
        ReplyDTO replyDTO = new ReplyDTO(1,1,1,"desc",LocalDateTime.now());

        when(replyService.getReplyById(searchedId)).thenReturn(replyDTO);

        mockMvc.perform(get("/reply/" + searchedId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reply_id", Matchers.is((int)searchedId)))
                .andExpect(jsonPath("$.description", Matchers.is("desc")));

        verify(replyService, times(1)).getReplyById(searchedId);
    }

    @Test
    void getReplyByIdShouldReturnNotFoundStatusAndEmptyBody() throws Exception {
        long searchedId = 1;

        when(replyService.getReplyById(searchedId)).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/reply/" + searchedId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(emptyString()));

        verify(replyService, times(1)).getReplyById(searchedId);
    }
    @ParameterizedTest
    @MethodSource("provideExpectedList")
    @WithMockUser(username = MOCK_USERNAME)
    void getAllReplyByAnswerIdShouldReturnOkStatusAndExpectedReplyDTOList(List<ReplyDTO> expected) throws Exception {
        long searchedId = 1;

        when(replyService.getAllReplyByAnswerId(searchedId)).thenReturn(expected);

        mockMvc.perform(get("/reply/a/" + searchedId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",Matchers.hasSize(expected.size())));

        verify(replyService,times(1)).getAllReplyByAnswerId(searchedId);
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME)
    void addNewReplyShouldReturnOkStatusAndExpectedValue() throws Exception {
        boolean expected = true;
        NewReplyDTO dto = new NewReplyDTO("desc",1,1);

        when(replyService.addNewReply(dto)).thenReturn(expected);

        String jsonRequest = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(post("/reply/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.is(expected)));

        verify(replyService,times(1)).addNewReply(dto);
    }

    @ParameterizedTest
    @MethodSource("provideUpdateValueAndExpectedValue")
    @WithMockUser(username = MOCK_USERNAME)
    void updateReplyShouldReturnOkStatusAndExpectedValue(ReplyUpdateDTO value , boolean expected) throws Exception {
        when(replyService.updateReply(value)).thenReturn(expected);

        String jsonRequest = new ObjectMapper().writeValueAsString(value);

        mockMvc.perform(put("/reply/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",Matchers.is(expected)));

        verify(replyService,times(1)).updateReply(value);
    }
    @Test
    @WithMockUser(username = MOCK_USERNAME)
    void updateReplyShouldReturnNotFoundStatusAndEmptyBody() throws Exception {
        ReplyUpdateDTO dto = new ReplyUpdateDTO(1,1,"desc");

        when(replyService.updateReply(dto)).thenThrow(NotFoundException.class);

        String jsonRequest = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(put("/reply/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isNotFound())
                .andExpect(content().string(emptyString()));

        verify(replyService,times(1)).updateReply(dto);
    }

    @ParameterizedTest
    @MethodSource("provideIdAndDeleteValueAndExpectedValue")
    @WithMockUser(username = MOCK_USERNAME)
    void deleteReplyByIdShouldReturnOkStatusAndExpectedValue(DeleteRequestDTO value, boolean expected) throws Exception {
        when(replyService.deleteReplyById(value)).thenReturn(expected);

        String jsonRequest = new ObjectMapper().writeValueAsString(value);

        mockMvc.perform(delete("/reply/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.is(expected)));

        verify(replyService,times(1)).deleteReplyById(value);
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME)
    void deleteReplyByIdShouldReturnNotFoundStatusAndEmptyBody() throws Exception {
        DeleteRequestDTO dto = new DeleteRequestDTO(1,1);

        when(replyService.deleteReplyById(dto)).thenThrow(NotFoundException.class);

        String jsonRequest = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(delete("/reply/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isNotFound())
                .andExpect(content().string(emptyString()));

        verify(replyService,times(1)).deleteReplyById(dto);
    }
    private static Stream<Arguments> provideExpectedList() {
        return Stream.of(
                Arguments.of(List.of(
                        new ReplyDTO(1,1,1,"title", LocalDateTime.now()),
                        new ReplyDTO(2,2,1,"titles", LocalDateTime.now())
                )),
                Arguments.of(List.of())
        );
    }
    private static Stream<Arguments> provideUpdateValueAndExpectedValue() {
        return Stream.of(
                Arguments.of( new ReplyUpdateDTO(1,1,"desc"),true),
                Arguments.of( new ReplyUpdateDTO(1,1,"desc"),false)
        );
    }
    private static Stream<Arguments> provideIdAndDeleteValueAndExpectedValue() {
        return Stream.of(
                Arguments.of(new DeleteRequestDTO(1,1),true),
                Arguments.of(new DeleteRequestDTO(1,1),true)
        );
    }
}
