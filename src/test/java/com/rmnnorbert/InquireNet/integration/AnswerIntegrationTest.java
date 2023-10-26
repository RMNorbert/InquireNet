package com.rmnnorbert.InquireNet.integration;

import annotations.IntegrationTest;
import com.rmnnorbert.InquireNet.controller.AnswerController;
import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dto.answer.AnswerDTO;
import com.rmnnorbert.InquireNet.dto.answer.AnswerRequestDTO;
import com.rmnnorbert.InquireNet.dto.answer.VoteDTO;
import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.update.UpdateDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
@SpringBootTest
@Sql({"/database/clear.sql", "/database/testInit.sql"})
class AnswerIntegrationTest {
    @Autowired
    private AnswerController answerController;
    private static final long SEARCHED_ID = 1;
    @Test
    void getAllAnswersShouldReturnExpectedAnswerDTOList() {
        String expectedDescription1 = "answer description 1";
        String expectedDescription2 = "answer description 2";

        assertThat(answerController.getAllAnswers())
                .extracting(AnswerDTO::description)
                .containsExactly(
                        expectedDescription1,
                                expectedDescription2
                );
    }

   @Test
   void getAnswerByIdShouldReturnExpectedAnswerDTO() {
       String expectedDescription = "answer description 1";

       assertThat(answerController.getAnswerById(SEARCHED_ID).answer_id())
                .isEqualTo(SEARCHED_ID);

       assertThat(answerController.getAnswerById(SEARCHED_ID).description())
                .isEqualTo(expectedDescription);
    }


    @Test
    void getAnswerByIdWithWrongIdShouldThrowNotFoundException() {
        long searchedId = 3;

        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> answerController.getAnswerById(searchedId));
    }

    @Test
    void getNumberOfAnswersByUserIdShouldReturnExpectedNumber() {
        int numberOfAnswers = 1;

        assertThat(answerController.getNumberOfAnswersByUserId(SEARCHED_ID))
                .isEqualTo(numberOfAnswers);

    }

    @Test
    void getAllAnswersByQuestionIdShouldReturnExpectedAnswerDTOList() {
        long expectedId = 1;
        int expectedSize = 1;

        assertThat(answerController.getAllAnswersByQuestionId(SEARCHED_ID)).size().isEqualTo(expectedSize);

        assertThat(answerController.getAllAnswersByQuestionId(SEARCHED_ID))
                .extracting(AnswerDTO::answer_id)
                .isEqualTo(List.of(expectedId));
    }

    @Test
    @Sql({"/database/clear.sql", "/database/table.sql"})
    void addNewAnswerShouldReturnExpectedBoolean() {
        AnswerRequestDTO dto = new AnswerRequestDTO(10L,"desc",15L);
        boolean expected = true;

        assertThat(answerController.addNewAnswer(dto)).isEqualTo(expected);
    }

    @Test
    public void deleteAnswerByIdShouldReturnExpectedBoolean() {
        DeleteRequestDTO dto = new DeleteRequestDTO(1,1);
        boolean expected = true;

        assertThat(answerController.deleteAnswerById(dto)).isEqualTo(expected);
    }

    @Test
    void voteOnAnswerByIdShouldReturnExpectedBoolean() {
        VoteDTO dto = new VoteDTO("features/vote",1,1,1);
        boolean expected = true;

        assertThat(answerController.voteOnAnswerById(dto)).isEqualTo(expected);
    }

    @Test
    void updateAnswerShouldReturnExpectedBoolean() {
        VoteDTO dto = new VoteDTO("features/vote",1,1,1);
        boolean expected = true;

        assertThat(answerController.voteOnAnswerById(dto)).isEqualTo(expected);
    }
    @Test
    void updateAnswerShouldReturnNotFoundException() {
        UpdateDTO dto = new UpdateDTO(10, 10, "title", "desc");

        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> answerController.updateAnswer(dto));
    }

}
