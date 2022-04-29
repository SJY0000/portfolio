package com.myapp.shoppingmall.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.shoppingmall.entities.ReplyVO;
import com.myapp.shoppingmall.service.ReplyService;


@RestController
@RequestMapping("/reply")
public class ReplyController {

	private ReplyService replyService;
	
	// 생성자 주입시에는 @Autowired 필요없음
	public ReplyController(ReplyService replyService) {
		this.replyService = replyService;
	}
	
	@PostMapping
	public ReplyVO replyEnrollPost(@RequestBody ReplyVO reply) {
		// 입력한 json 타입 데이터를 받아서 reply객체 리턴
		replyService.enroll(reply);
		
		return reply;
	}
	
	@GetMapping("/{bno}")
	public List<ReplyVO> replyListGet(@PathVariable("bno") int reply_bno) {		
		return replyService.getReplyList(reply_bno); // json 형태로 전달됨
	}
	
	// View에 data-id 속성을 줬기 때문에 객체를 특정할 값을 안받아도 됨
	@PutMapping
	public ReplyVO replyUpdatePut(@RequestBody ReplyVO reply) {
		replyService.modify(reply); // DB에 댓글 데이터 수정하기
		return reply;
	}
	
	@DeleteMapping("/{id}")
	public void replyDelete(@PathVariable("id") int id) {
		
		replyService.delete(id);
	}

}
