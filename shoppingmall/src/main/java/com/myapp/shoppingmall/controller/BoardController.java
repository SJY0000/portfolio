package com.myapp.shoppingmall.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myapp.shoppingmall.entities.BoardVO;
import com.myapp.shoppingmall.entities.Criteria;
import com.myapp.shoppingmall.entities.PageMakerDTO;
import com.myapp.shoppingmall.service.BoardService;

import lombok.extern.java.Log;

@Controller
@RequestMapping
@Log	// console의 Log 출력 (print out 대신 사용)
public class BoardController {
	
	private BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}

	@GetMapping("/board")
	public String boardListGet(Criteria cri, Model model) {
		log.info("게시판 리스트 페이지 진입");
		model.addAttribute("boardList", boardService.getListPaging(cri));
		
		int total = boardService.getTotal(cri);
		PageMakerDTO pmk = new PageMakerDTO(total, cri);
		model.addAttribute("pmk", pmk);
		
		return "list";
	}
	@GetMapping("/notice")
	public String NoticeListGet(Criteria cri, Model model) {
		model.addAttribute("boardList", boardService.getListNoticePaging(cri));

		int total = boardService.getTotal(cri);
		PageMakerDTO pmk = new PageMakerDTO(total, cri);
		model.addAttribute("pmk", pmk);
		
		return "notice";
	}
	
//	@GetMapping("/list")
//	public String boardListGet(Model model) {
//		log.info("게시판 리스트 페이지 진입");
//		model.addAttribute("boardList", boardService.getList());
//		return "list";
//	}
	/**
	 * 게시글 조회하기
	 * @param bno
	 * @param model
	 * @return
	 */
//	@GetMapping("/list/{bno}")
//	public String getBoard(@PathVariable("bno") int bno, Model model) {
//		model.addAttribute("board", boardService.getPage(bno));
//		return "get";
//	}
	
	@GetMapping("/get")
	public String getBoard(@RequestParam("bno") int bno, Model model, Criteria cri) { // bno 값이 없으면 에러남, @RequestParam 안적으면 에러 안남, 변수 사용하면 무조건 있어야 함
		model.addAttribute("board", boardService.getPage(bno));
		model.addAttribute("cri", cri);
		return "get";
	}
	
	@GetMapping("/enrollNotice")
	public String noticeEnrollGet(Model model) {
		log.info("공지사항 등록 페이지 진입");
		model.addAttribute("board", new BoardVO());
		return "enrollNotice";
	}
	
	@GetMapping("/enrollBoard")
	public String boardEnrollGet(Model model) {
		log.info("게시판 등록 페이지 진입");
		model.addAttribute("board", new BoardVO());
		return "enrollBoard";
	}
	
	@PostMapping("/enrollNotice")
	public String noticeEnrollPost(BoardVO board, RedirectAttributes attr) {
		boardService.enrollNotice(board);
		attr.addFlashAttribute("message", "게시글 등록 완료!");
		
		return "redirect:/notice"; // Post 다음 Redirect
	}
	
	@PostMapping("/enrollBoard")
	public String boardEnrollPost(BoardVO board, RedirectAttributes attr) {
		boardService.enrollBoard(board);
		attr.addFlashAttribute("message", "게시글 등록 완료!");
		
		return "redirect:/board"; // Post 다음 Redirect
	}
	
//	@GetMapping("/modify/{bno}")
//	public String boardModify(@PathVariable("bno") int bno, Model model) {
//		model.addAttribute("board", boardService.getPage(bno));
//		return "modify";
//	}
	
	@GetMapping("/modify")
	public String boardModifyGet(@RequestParam("bno") int bno, Model model, Criteria cri) {
		model.addAttribute("board", boardService.getPage(bno));
		model.addAttribute("cri",cri);
		return "modify";
	}
	
	@PostMapping("/modify")
	public String boardModifyPOST(BoardVO board,  RedirectAttributes attr) {
		boardService.modify(board);
		attr.addFlashAttribute("message", "수정 성공");
		return "redirect:/board";		// post에 redirect 하는 이유는 새로 고침 하면 중복전송이 되기 때문에 그것을 방지하기위해서 post - redirect - get 방식
	}
	
	@GetMapping("/delete")
	public String boardDelete(@RequestParam("bno") int bno) {
		boardService.delete(bno);
		return "redirect:/board";
	}
}
