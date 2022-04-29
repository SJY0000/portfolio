package com.myapp.shoppingmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myapp.shoppingmall.dao.CategoryRepository;
import com.myapp.shoppingmall.dao.ProductRepository;
import com.myapp.shoppingmall.entities.Category;
import com.myapp.shoppingmall.entities.Product;

@Controller
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	/**
	 * 입력된 slug 카테고리별로 제품리스트 표시(페이징 포함)
	 * @param slug 카테고리 slug
	 * @param page 표시할 페이지 번호
	 * @return products 페이지로 이동
	 */
	
	@GetMapping("/{slug}")
	public String category(@PathVariable("slug") String slug, Model model, @RequestParam(value = "page", defaultValue = "0") Integer page) {
		int perPage = 6; // @RequestParam에 같이 적어도 되지만 이번엔 따로 적기 (한 페이지당 최대 6개)
		Pageable pageable = PageRequest.of(page, perPage); // 표시할 페이지, 한 페이지에 표시할 데이터 수(6개)
		long count = 0;
		
		// Category 선택(all , 그외 개별 가테고리)
		if(slug.equals("all")) {
			Page<Product> products = productRepo.findAll(pageable);
			count = productRepo.count(); // 전체 상품 수
			
			model.addAttribute("products", products); // 모든 카테고리 상품들
		} else { // Category 개별 선택
			Category category = categoryRepo.findBySlug(slug);
			if(category == null) {
				return "redirect:/"; // Category가 없으면 home으로 이동
			}
			String categoryId = Integer.toString(category.getId());
			String categoryName = category.getName();
			List<Product> products = productRepo.findAllByCategoryId(categoryId, pageable);
			count =productRepo.countByCategoryId(categoryId);
			
			model.addAttribute("products", products); // 선택한 Category의 상품들
			model.addAttribute("categoryName", categoryName);
		}

		// Pagenation을 위해 필요한 정보
		double pageCount = Math.ceil((double)count / (double)perPage); // 13/ 6 = 2.12, int로 나누면 소수점이 출력이 안되기때문에 double타입으로 계산한다.
		
		model.addAttribute("pageCount",pageCount); // 총페이지
		model.addAttribute("perPage", perPage);		// 페이지당 표시 아이템수
		model.addAttribute("count", count);			// 총 아이템 갯수
		model.addAttribute("page", page);			// 현재 페이지
		
		return "products"; // products.html 페이지로 이동
	}
}
