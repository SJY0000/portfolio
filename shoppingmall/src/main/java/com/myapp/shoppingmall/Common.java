package com.myapp.shoppingmall;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.myapp.shoppingmall.dao.Cart;
import com.myapp.shoppingmall.dao.CategoryRepository;
import com.myapp.shoppingmall.dao.PageRepository;
import com.myapp.shoppingmall.entities.Category;
import com.myapp.shoppingmall.entities.Page;

// 모든 Controller에 적용
@ControllerAdvice
public class Common {

	@Autowired
	private PageRepository pageRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@ModelAttribute
	public void sharedData(Model model, HttpSession session, Principal principal) {
		if (principal != null) { // 인증된 상태
			model.addAttribute("principal" ,principal.getName()); // 유저네임을 전달
		}
		// cpages에 모든 페이지들을 순서대로 담아서 전달
		List<Page> cpages = pageRepo.findAllByOrderBySortingAsc();
		List<Category> categories = categoryRepo.findAllByOrderBySortingAsc();
		// 현재 Cart 상태
		boolean cartActive = false; // Cart가 존재하지 않을 때 false
		
		if (session.getAttribute("cart") != null) {
			@SuppressWarnings("unchecked")
			HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");
			
			int size = 0; // Cart에 담긴 상품의 갯수
			int total = 0; // 총 가격
			
			for (Cart item : cart.values()) {// 장바구니 cart 객체들을 반복, cart.values()는 key값을 빼고 데이터만 반복
				size += item.getQuantity();
				total += item.getQuantity() * Integer.parseInt(item.getPrice()); // 상품 수량 * 가격 = 총 가격
			}
			model.addAttribute("csize", size);
			model.addAttribute("ctotal", total);
			cartActive = true;					// Cart가 존재함
		}
		
		model.addAttribute("cpages", cpages);
		model.addAttribute("ccategories", categories);
		model.addAttribute("cartActive", cartActive); // Cart가 존재하면 true, 없으면 false
	}
}
