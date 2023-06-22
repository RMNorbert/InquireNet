package com.rmnnorbert.InquireNet.controller;

import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.question.NewQuestionDTO;
import com.rmnnorbert.InquireNet.dto.question.QuestionDTO;
import com.rmnnorbert.InquireNet.dto.question.UpdateQuestionDTO;
import com.rmnnorbert.InquireNet.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("questions")
public class QuestionController {
    private final QuestionService questionService;
    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }
    @GetMapping("/all")
    public List<QuestionDTO> getAllQuestions() {
        return questionService.getAllQuestions();
    }
    @GetMapping("/all/{userId}")
    public List<QuestionDTO> getAllQuestionsOfUser(@PathVariable long userId) {
        return questionService.getAllQuestionOfUser(userId);
    }
    @GetMapping("/last")
    public long getLastQuestions() {
        return questionService.getLastQuestion();
    }
    @GetMapping("/{id}")
    public QuestionDTO getQuestionById(@PathVariable long id) {
        return questionService.getQuestionById(id);
    }
    @PostMapping("/")
    public int addNewQuestion(@RequestBody NewQuestionDTO question) {
        return questionService.addNewQuestion(question);
    }
    @PutMapping("/")
    public boolean updateQuestion(@RequestBody UpdateQuestionDTO question) {
        return questionService.updateQuestion(question);
    }
    @DeleteMapping("/")
    public boolean deleteQuestionById(@RequestBody DeleteRequestDTO dto) {
        return questionService.deleteQuestionById(dto);
    }
}
