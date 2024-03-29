package com.rmnnorbert.InquireNet.controller.forum;

import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.forum.question.NewQuestionDTO;
import com.rmnnorbert.InquireNet.dto.forum.question.QuestionDTO;
import com.rmnnorbert.InquireNet.dto.update.UpdateDTO;
import com.rmnnorbert.InquireNet.service.forum.question.QuestionService;
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
    @GetMapping("/all/{id}")
    public List<QuestionDTO> getAllQuestionsOfUser(@PathVariable long id) {
        return questionService.getAllQuestionOfUser(id);
    }
    @GetMapping("/last")
    public long getLastQuestion() {
        return questionService.getLastQuestion();
    }
    @GetMapping("/{id}")
    public QuestionDTO getQuestionById(@PathVariable long id) {
        return questionService.getQuestionById(id);
    }
    @PostMapping("/")
    public boolean addNewQuestion(@RequestBody NewQuestionDTO question) {
        return questionService.addNewQuestion(question);
    }
    @PutMapping("/")
    public boolean updateQuestion(@RequestBody UpdateDTO updateDTO) {
        return questionService.updateQuestion(updateDTO);
    }
    @DeleteMapping("/")
    public boolean deleteQuestionById(@RequestBody DeleteRequestDTO dto) {
        return questionService.deleteQuestionById(dto);
    }
}
