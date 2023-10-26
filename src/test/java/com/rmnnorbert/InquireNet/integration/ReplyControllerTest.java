package com.rmnnorbert.InquireNet.integration;

import annotations.IntegrationTest;
import com.rmnnorbert.InquireNet.controller.ReplyController;
import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.reply.NewReplyDTO;
import com.rmnnorbert.InquireNet.dto.reply.ReplyDTO;
import com.rmnnorbert.InquireNet.dto.reply.ReplyUpdateDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@IntegrationTest
@Sql({"/database/clear.sql", "/database/testInit.sql"})
class ReplyControllerTest {
    @Autowired
    private ReplyController replyController;
    @Test
    void getAllReplyShouldReturnExpectedReplyDTOList() {
        int expectedListSize = 1;

        assertThat(replyController.getAllReply())
                .size().isEqualTo(expectedListSize);
    }

    @Test
    void getReplyByIdShouldReturnReplyDTO() {
        long searchedId = 1;

        String expectedDescription = "reply 1";

        assertThat(replyController.getReplyById(searchedId))
                .extracting(ReplyDTO::description)
                .isEqualTo(expectedDescription);
    }

    @Test
    void getReplyByIdShouldReturnNotFoundException() {
        long searchedId = 10;

        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> replyController.getReplyById(searchedId));
    }
    @Test
    void getAllReplyByAnswerIdShouldReturnExpectedReplyDTOList() {
        long searchedId = 1;
        int expectedListSize = 1;

        assertThat(replyController.getAllReplyByAnswerId(searchedId))
                .size().isEqualTo(expectedListSize);

        assertThat(replyController.getAllReplyByAnswerId(searchedId))
                .extracting(ReplyDTO::description)
                .containsExactly(
                        "reply 1"
                );
    }

    @Test
    @Sql({"/database/clear.sql", "/database/table.sql"})
    void addNewReplyShouldReturnTrue() {
        boolean expected = true;
        NewReplyDTO dto = new NewReplyDTO("desc",1,1);

        assertThat(replyController.addNewReply(dto))
                .isEqualTo(expected);

    }
    @Test
    void updateReplyShouldReturnTrue() {
        boolean expected = true;
        ReplyUpdateDTO dto = new ReplyUpdateDTO(1,1, "descc");

        assertThat(replyController.updateReply(dto))
                .isEqualTo(expected);
    }
    @Test
    void updateReplyShouldReturnNotFoundException() {
        ReplyUpdateDTO dto = new ReplyUpdateDTO(10,1, "descc");

        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> replyController.updateReply(dto));
    }

    @Test
    void deleteReplyByIdShouldReturnTrue() {
        DeleteRequestDTO dto = new DeleteRequestDTO(1,1);

        boolean expected = true;

        assertThat(replyController.deleteReplyById(dto))
                .isEqualTo(expected);
    }

    @Test
    void deleteReplyByIdShouldReturnNotFoundException() {
        DeleteRequestDTO dto = new DeleteRequestDTO(10,10);

        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> replyController.deleteReplyById(dto));
    }

}
