package com.myapp.shoppingmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 
 * 쇼핑몰 기본 홈페이지
 *
 */

import com.myapp.shoppingmall.dao.PageRepository;
import com.myapp.shoppingmall.entities.BoardVO;
import com.myapp.shoppingmall.entities.Criteria;
import com.myapp.shoppingmall.entities.Page;
import com.myapp.shoppingmall.service.BoardService;

@Controller
@RequestMapping("/")
public class PageController {

	@Autowired
	private PageRepository pageRepo;
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping
	public String home(Model model) {
		// entities의 page
		Page page = pageRepo.findBySlug("home");
		
		model.addAttribute("page", page);
		
		List<BoardVO> list = boardService.getListPaging(new Criteria(1,5));
		List<BoardVO> noticeList = boardService.getListNoticePaging(new Criteria(1,5));
		
		model.addAttribute("list", list);
		model.addAttribute("noticeList", noticeList);
		
		return "page"; // page.html로 이동
	}
	@GetMapping("/{slug}")
	public String page(@PathVariable("slug") String slug, Model model) {
		Page page = pageRepo.findBySlug(slug);
		
		if (page == null) {
			return "redirect:/"; // 페이지가 없으면 home으로 이동
		}
		model.addAttribute("page", page);
		
		return "page";
	}
	
	@GetMapping("/aboutus")
	public String aboutUs() {
		return "aboutus";
	}
	
	@GetMapping("/login")
	public String login(Model model) {
		
		return "login";
	}
}
