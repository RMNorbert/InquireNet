package com.rmnnorbert.InquireNet.controller;

import annotations.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
@UnitTest
@ExtendWith(MockitoExtension.class)
class IndexControllerTest {
    private IndexController indexController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.indexController = new IndexController();
    }

    @Test
    public void testIndex() {
        String expected = "/index.html";
        String actual = indexController.index();
        assertEquals(expected, actual);
    }
}
