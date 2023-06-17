package com.rmnnorbert.InquireNet.controller;

import com.rmnnorbert.InquireNet.dto.reply.NewReplyDTO;
import com.rmnnorbert.InquireNet.dto.reply.ReplyDTO;
import com.rmnnorbert.InquireNet.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@CrossOrigin
@RequestMapping("reply")
public class ReplyController {
    private final ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @GetMapping("/all")
    public List<ReplyDTO> getAllReply() {
        return replyService.getAllReply();
    }

    @GetMapping("/{id}")
    public Optional<ReplyDTO> getReplyById(@PathVariable int id) {
        return replyService.getReplyById(id);
    }
    @CrossOrigin
    @GetMapping("/a/{id}")
    public List<ReplyDTO> getAllReplyByAnswerId(@PathVariable int id){
        return replyService.getAllReplyByAnswerId(id);
    }

    @PostMapping("/")
    public int addNewReply(@RequestBody NewReplyDTO replyDTO) {
        return replyService.addNewReply(replyDTO);
    }

    @DeleteMapping("/{id}")
    public boolean deleteReplyById(@PathVariable int id) {
        return replyService.deleteReplyById(id);
    }
}
