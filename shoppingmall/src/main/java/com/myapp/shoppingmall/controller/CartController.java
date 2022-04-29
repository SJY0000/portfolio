package com.myapp.shoppingmall.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myapp.shoppingmall.dao.Cart;
import com.myapp.shoppingmall.dao.ProductRepository;
import com.myapp.shoppingmall.entities.Product;

@Controller
@RequestMapping("/cart")
@SuppressWarnings("unchecked")  // 타입이 확실할 때 사용함, 경고창을 띄우지 않음
public class CartController {

	@Autowired
	private ProductRepository productRepo;
	
	/**
	 * 제품의 id를 입력받아 Session에 CartList를 저장한다.
	 * @param id
	 * @param session
	 * @return
	 */
	@GetMapping("/add/{id}")
	public String add(@PathVariable("id") int id, HttpSession session, Model model, @RequestParam(required = false) String cartPage) {
		//0. 상품을 DB에서 검색
		Product product = productRepo.getById(id);
		
		//1. Session에 상품 저장 (Map<id,cart>로 그룹을 만든다. 
		if (session.getAttribute("cart") == null) { // Session에 Cart가 없으면
			HashMap<Integer, Cart> cart = new HashMap<>();
			cart.put(id, new Cart(id, product.getName(), product.getPrice(), 1, product.getImage()));
			session.setAttribute("cart", cart);		
		
		} else {//2. 이미 Cart가 있을경우 ((1) 그 상품이 이미 담겨져 있을 경우, (2) 없을 경우
			HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");
			
			if(cart.containsKey(id)) { // Cart에 그 상품이 있을 경우
				int qty = cart.get(id).getQuantity(); // 현재 Cart에 담긴 상품의 수량
				cart.put(id, new Cart(id, product.getName(), product.getPrice(), ++qty, product.getImage())); // session에서 참조변수를 불러오는 것이라 call by reference로 인행 원본데이터에도 영향을 줌
			} else {
			cart.put(id, new Cart(id, product.getName(), product.getPrice(), 1, product.getImage()));
			session.setAttribute("cart", cart); // 새로 생성하는 것이기 때문에 session에 저장해야할 필요가 있다.
			}
		}
		
		HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");
		
		int size = 0; // Cart에 담긴 상품의 갯수
		int total = 0; // 총 가격
		
		for (Cart item : cart.values()) {// 장바구니 cart 객체들을 반복, cart.values()는 key값을 빼고 데이터만 반복
			size += item.getQuantity();
			total += item.getQuantity() * Integer.parseInt(item.getPrice()); // 상품 수량 * 가격 = 총 가격
		}
		model.addAttribute("csize", size);
		model.addAttribute("ctotal", total);

		if(cartPage != null) { // cart.html 페이지에서 (+)버튼을 눌렀을 때는 다시 Cart페이지로 새로고침
			return "redirect:/cart/view";
		}
		
		return "cart_view";
		
	}
	
	@GetMapping("/view")
	public String view(HttpSession session, Model model) {
		if (session.getAttribute("cart") == null) {
			return "redirect:/"; // Cart가 없을 경우 홈페이지로 이동
		}
		HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");
		
		model.addAttribute("cart", cart);
		model.addAttribute("noCartView", true); // 왼쪽 Cart_view는 필요없음
		
		return "cart";
	}
	
	@GetMapping("/subtract/{id}")
	public String subtract(@PathVariable("id") int id, HttpSession session, Model model, HttpServletRequest httpServletRequest) {
		HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");
		// 현재 cart에 담긴 상품의 갯수를 가져오기
		int qty = cart.get(id).getQuantity();
		if (qty == 1) { // 1에서 빼면 0 => cart에서 없어져야함
			cart.remove(id); // key값으로 삭제
			if(cart.size() == 0) { // cart에 상품이 담겨있지 않으면
				session.removeAttribute("cart"); // session에서 삭제
			}
		} else {
			cart.get(id).setQuantity(--qty);			
		}
		
		String referenLink = httpServletRequest.getHeader("Referer"); // 요청된 이전주소의 정보가 들어있음
		
		return "redirect:" + referenLink; // 다시 이전페이지로 이동
	}
	
	@GetMapping("/remove/{id}")
	public String remove(@PathVariable("id") int id, HttpSession session, Model model, HttpServletRequest httpServletRequest) {
		HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");
		
		cart.remove(id); // id로 삭제
			
		if(cart.size() == 0) { // cart에 상품이 담겨있지 않으면
			session.removeAttribute("cart"); // session에서 삭제
		}

		String referenLink = httpServletRequest.getHeader("Referer"); // 요청된 이전주소의 정보가 들어있음
		
		return "redirect:" + referenLink; // 다시 이전페이지로 이동
	}
	
	@GetMapping("/clear")
	public String clear(HttpSession session, Model model, HttpServletRequest httpServletRequest) {
		session.removeAttribute("cart"); // session에서 삭제

		String referenLink = httpServletRequest.getHeader("Referer"); // 요청된 이전주소의 정보가 들어있음
		
		return "redirect:" + referenLink; // 다시 이전페이지로 이동
	}
}
