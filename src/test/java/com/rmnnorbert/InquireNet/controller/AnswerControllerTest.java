package com.rmnnorbert.InquireNet.controller;

import annotations.UnitTest;
import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dto.answer.AnswerDTO;
import com.rmnnorbert.InquireNet.dto.answer.AnswerRequestDTO;
import com.rmnnorbert.InquireNet.dto.answer.VoteDTO;
import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.update.UpdateDTO;
import com.rmnnorbert.InquireNet.service.AnswerService;
import com.rmnnorbert.InquireNet.service.VoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@UnitTest
@ExtendWith(MockitoExtension.class)
class AnswerControllerTest {
    private AnswerController answerController;
    @Mock
    private AnswerService answerService;
    @Mock
    private VoteService voteService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        this.answerController = new AnswerController(answerService,voteService);
    }

    @ParameterizedTest
    @MethodSource("provideExpectedList")
    void getAllAnswersShouldReturnAnswerDTOList(List<AnswerDTO> expected) {
        when(answerService.getAllAnswers()).thenReturn(expected);

        List<AnswerDTO> actual = answerController.getAllAnswers();
        assertEquals(expected, actual);
        verify(answerService,times(1)).getAllAnswers();
    }

    @Test
    void getAnswerByIdShouldReturnAndExpectedValue() {
        long searchedId = 1;
        AnswerDTO expectedAnswerDTO = new AnswerDTO(1,
                                                    1,
                                                    1,
                                                    "desc",
                                                    LocalDateTime.now(),
                                                    1,
                "features/vote");

        when(answerService.getAnswerById(searchedId)).thenReturn(expectedAnswerDTO);

        AnswerDTO actual = answerController.getAnswerById(searchedId);

        assertEquals(expectedAnswerDTO, actual);
        verify(answerService,times(1)).getAnswerById(searchedId);
    }


    @Test
    void getAnswerByIdWhenServiceThrowsNotFoundException() {
        long searchedId = 1;

        when(answerService.getAnswerById(searchedId)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> answerController.getAnswerById(searchedId));
        verify(answerService,times(1)).getAnswerById(searchedId);
    }

    @Test
    void getNumberOfAnswersByUserIdAndIntegerNumber() {
        long id = 1;
        int numberOfAnswers = 2;

        when(answerService.getNumberOfAnswersByUserId(id)).thenReturn(numberOfAnswers);

        int actual = answerController.getNumberOfAnswersByUserId(id);
        assertEquals(numberOfAnswers, actual);
        verify(answerService,times(1)).getNumberOfAnswersByUserId(id);
    }

    @ParameterizedTest
    @MethodSource("provideIdAndExpectedList")
    void getAllAnswersByQuestionIdShouldReturnExpectedValue(long searchedId, List<AnswerDTO> expected) {
        when(answerService.getAllAnswersByQuestionId(searchedId)).thenReturn(expected);

        List<AnswerDTO> actual = answerController.getAllAnswersByQuestionId(searchedId);
        assertEquals(expected, actual);
        verify(answerService,times(1)).getAllAnswersByQuestionId(searchedId);
    }

    @ParameterizedTest
    @MethodSource("provideExpectedValue")
    void addNewAnswerShouldReturnExpectedValue(boolean expected) {
        AnswerRequestDTO dto = new AnswerRequestDTO(1L,"desc",1L);

        when(answerService.addNewAnswer(dto)).thenReturn(expected);

        boolean actual = answerController.addNewAnswer(dto);
        assertEquals(expected, actual);
        verify(answerService,times(1)).addNewAnswer(dto);
    }

    @ParameterizedTest
    @MethodSource("provideExpectedValue")
    public void deleteAnswerByIdShouldReturnExpectedValue(boolean expected) {
        DeleteRequestDTO dto = new DeleteRequestDTO(1,1);

        when(answerService.deleteAnswerById(dto)).thenReturn(expected);

        boolean actual = answerController.deleteAnswerById(dto);
        assertEquals(expected, actual);
        verify(answerService,times(1)).deleteAnswerById(dto);
    }

    @ParameterizedTest
    @MethodSource("provideExpectedValue")
    void voteOnAnswerByIdShouldReturnExpectedValue(boolean expected) {
        VoteDTO dto = new VoteDTO("features/vote",1,1,1);

        when(voteService.vote(dto)).thenReturn(expected);

        boolean actual = answerController.voteOnAnswerById(dto);
        assertEquals(expected, actual);
        verify(voteService,times(1)).vote(dto);
    }
    @ParameterizedTest
    @MethodSource("provideUpdateDTOAndExpectedValue")
    void updateAnswerShouldReturnExpectedValue(UpdateDTO value, boolean expected) {
        when(answerService.update(value)).thenReturn(expected);

        boolean actual = answerController.updateAnswer(value);
        assertEquals(expected, actual);
        verify(answerService,times(1)).update(value);
    }
    @Test
    void updateAnswerShouldReturnNotFoundStatus() {
        UpdateDTO dto = new UpdateDTO(1,1,"title","desc");

        when(answerService.update(dto)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> answerController.updateAnswer(dto));
        verify(answerService,times(1)).update(dto);
    }
    private static Stream<Arguments> provideIdAndExpectedList() {
        return Stream.of(
                Arguments.of( 1,List.of(
                        new AnswerDTO(1,1,1,"desc",LocalDateTime.now(),1, "features/vote"),
                        new AnswerDTO(2,2,1,"description",LocalDateTime.now(),1, "features/vote")
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
