package com.rmnnorbert.InquireNet.controller;

import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.reply.NewReplyDTO;
import com.rmnnorbert.InquireNet.dto.reply.ReplyDTO;
import com.rmnnorbert.InquireNet.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
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
    public ReplyDTO getReplyById(@PathVariable long id) {
        return replyService.getReplyById(id);
    }
    @GetMapping("/a/{id}")
    public List<ReplyDTO> getAllReplyByAnswerId(@PathVariable long id){
        return replyService.getAllReplyByAnswerId(id);
    }
    @PostMapping("/")
    public boolean addNewReply(@RequestBody NewReplyDTO replyDTO) {
        return replyService.addNewReply(replyDTO);
    }
    @PutMapping("/")
    public boolean updateReply(@RequestBody ReplyDTO replyDTO) {
        return replyService.updateReply(replyDTO);
    }
    @DeleteMapping("/")
    public boolean deleteReplyById(@RequestBody DeleteRequestDTO dto) {
        return replyService.deleteReplyById(dto);
    }
}
