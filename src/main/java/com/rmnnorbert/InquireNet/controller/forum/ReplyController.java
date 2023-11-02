package com.rmnnorbert.InquireNet.controller.forum;

import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.forum.reply.NewReplyDTO;
import com.rmnnorbert.InquireNet.dto.forum.reply.ReplyDTO;
import com.rmnnorbert.InquireNet.dto.forum.reply.ReplyUpdateDTO;
import com.rmnnorbert.InquireNet.service.forum.reply.ReplyService;
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
    public boolean updateReply(@RequestBody ReplyUpdateDTO updateDTO) {
        return replyService.updateReply(updateDTO);
    }
    @DeleteMapping("/")
    public boolean deleteReplyById(@RequestBody DeleteRequestDTO dto) {
        return replyService.deleteReplyById(dto);
    }
}
