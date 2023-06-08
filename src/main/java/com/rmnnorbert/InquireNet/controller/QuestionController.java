package com.rmnnorbert.InquireNet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("questions")
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @CrossOrigin
    @GetMapping("/all")
    public List<QuestionDTO> getAllQuestions() {
        return questionService.getAllQuestions();
    }
    @CrossOrigin
    @GetMapping("/last")
    public Optional<QuestionDTO> getLastQuestions() {
        return questionService.getLastQuestion();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public Optional<QuestionDTO> getQuestionById(@PathVariable int id) {
        return questionService.getQuestionById(id);
    }

    @PostMapping("/")
    public int addNewQuestion(@RequestBody NewQuestionDTO question) {
        return questionService.addNewQuestion(question);
    }

    @DeleteMapping("/{id}")
    public boolean deleteQuestionById(@PathVariable int id) {
        return questionService.deleteQuestionById(id);
    }
}
