package com.rmnnorbert.InquireNet.integration;

import annotations.IntegrationTest;
import com.rmnnorbert.InquireNet.controller.QuestionController;
import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.question.NewQuestionDTO;
import com.rmnnorbert.InquireNet.dto.question.QuestionDTO;
import com.rmnnorbert.InquireNet.dto.update.UpdateDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@IntegrationTest
@Sql({"/database/clear.sql", "/database/testInit.sql"})
class QuestionControllerTest {
    @Autowired
    private QuestionController questionController;
    @Test
    void getAllQuestionsShouldReturnQuestionDTOList() {
        int expectedListSize = 2;

        assertThat(questionController.getAllQuestions())
                .size().isEqualTo(expectedListSize);

        assertThat(questionController.getAllQuestions())
                .extracting(QuestionDTO::title)
                .containsExactly("title 1","title 2");
    }

    @Test
    void getAllQuestionsOfUserShouldReturnExpectedQuestionDTOList() {
        long userId = 1;

        int expectedSize = 1;

        assertThat(questionController.getAllQuestionsOfUser(userId))
                .size().isEqualTo(expectedSize);

        assertThat(questionController.getAllQuestionsOfUser(userId))
                .extracting(QuestionDTO::title)
                .containsExactly("title 1");
    }

    @Test
    void getLastQuestionsShouldReturnExpectedValue()  {
        long expected = 2;

        assertThat(questionController.getLastQuestion())
                .isEqualTo(expected);
    }

    @Test
    void getQuestionByIdShouldReturnQuestionDTO() {
        long searchedId = 1;

        String expectedTitle = "title 1";

        assertThat(questionController.getQuestionById(searchedId))
                .extracting(QuestionDTO::title)
                .isEqualTo(expectedTitle);
    }
    @Test
    void getQuestionByIdShouldReturnNotFoundException() {
        long searchedId = 15L;

        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> questionController.getQuestionById(searchedId));
    }
    @Test
    @Sql({"/database/clear.sql", "/database/table.sql"})
    void addNewQuestion() {
        NewQuestionDTO dto = new NewQuestionDTO("title","desc",1);

        boolean expected = true;

        assertThat(questionController.addNewQuestion(dto))
                .isEqualTo(expected);
    }

    @Test
    void updateQuestion() {
        UpdateDTO dto = new UpdateDTO(1,1,"title","desc");

        boolean expected = true;

        assertThat(questionController.updateQuestion(dto))
                .isEqualTo(expected);
    }

    @Test
    void deleteQuestionById() {
        DeleteRequestDTO dto = new DeleteRequestDTO(1,1);

        boolean expected = true;

        assertThat(questionController.deleteQuestionById(dto))
                .isEqualTo(expected);
    }

}
