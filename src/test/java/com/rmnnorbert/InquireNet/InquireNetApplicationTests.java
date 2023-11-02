package com.rmnnorbert.InquireNet;

import com.rmnnorbert.InquireNet.controller.forum.AnswerController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class InquireNetApplicationTests {
	@Autowired
	AnswerController answerController;
	@Test
	void contextLoads() {
		//test if initialized bean has been successfully injected into an auto-wired attribute or not.
		Assertions.assertThat(answerController).isNotNull();
	}

}
