package com.rmnnorbert.InquireNet.controller.forum;

import com.rmnnorbert.InquireNet.dto.forum.answer.AnswerDTO;
import com.rmnnorbert.InquireNet.dto.forum.answer.AnswerRequestDTO;
import com.rmnnorbert.InquireNet.dto.forum.answer.VoteDTO;
import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.update.UpdateDTO;
import com.rmnnorbert.InquireNet.service.forum.answer.AnswerService;
import com.rmnnorbert.InquireNet.service.forum.vote.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("answers")
public class AnswerController {
        private final AnswerService answerService;
        private final VoteService voteService;
        @Autowired
        public AnswerController(AnswerService answerService, VoteService voteService) {
            this.answerService = answerService;
            this.voteService = voteService;
        }
        @GetMapping("/all")
        public List<AnswerDTO> getAllAnswers() {
            return answerService.getAllAnswers();
        }
        @GetMapping("/{id}")
        public AnswerDTO getAnswerById(@PathVariable long id) {
            return answerService.getAnswerById(id);
        }
        @GetMapping("/user/{id}")
        public int getNumberOfAnswersByUserId(@PathVariable long id) {
            return answerService.getNumberOfAnswersByUserId(id);
        }
        @GetMapping("/statistic/{id}")
        public List<Long> getUserAnswersStatistic(@PathVariable long id) {
        return answerService.getAllVotesByUserId(id);
        }
        @GetMapping("/q/{id}")
        public List<AnswerDTO> getAllAnswersByQuestionId(@PathVariable long id){
            return answerService.getAllAnswersByQuestionId(id);
        }
        @PostMapping("/")
        public boolean addNewAnswer(@RequestBody AnswerRequestDTO answerDTO) {
            return answerService.addNewAnswer(answerDTO);
        }
        @DeleteMapping("/")
        public boolean deleteAnswerById(@RequestBody DeleteRequestDTO dto) {
            return answerService.deleteAnswerById(dto);
        }
        @PutMapping("/vote")
        public boolean voteOnAnswerById(@RequestBody VoteDTO voteDTO) {
        return voteService.vote(voteDTO);
       }
       @PutMapping("/")
       public boolean updateAnswer(@RequestBody UpdateDTO updateDTO){
           return answerService.update(updateDTO);
       }
}
