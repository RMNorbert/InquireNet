package com.rmnnorbert.InquireNet.integration;

import annotations.IntegrationTest;
import com.rmnnorbert.InquireNet.controller.IndexController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
@SpringBootTest
class IndexControllerTest {
    @Autowired
    private IndexController controller;
    @Test
    public void testIndex() {
        assertThat(controller.index()).isEqualTo("/index.html");
    }
}
