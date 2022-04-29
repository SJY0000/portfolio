package com.myapp.shoppingmall.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myapp.shoppingmall.dao.CategoryRepository;
import com.myapp.shoppingmall.entities.Category;


@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

	@Autowired
	private CategoryRepository categoryRepo;
	
	@GetMapping
	public String index(Model model) {
		List<Category> categories = categoryRepo.findAllByOrderBySortingAsc();
		model.addAttribute("categories", categories);
		return "admin/categories/index";
	}
	@GetMapping("/add")
	public String add(@ModelAttribute Category category) {
		
		return "admin/categories/add";
	}
	
	@PostMapping("/add")
	public String add(@Valid Category category, BindingResult bindingResult, RedirectAttributes attr) {
		//Validation 결과 Error가 있으면 이전페이지로 이동
		if (bindingResult.hasErrors()) {
			return "admin/categories/add";
		}
		// 검사 통과 시
		attr.addFlashAttribute("message","성공적으로 카테고리 추가됨");
		attr.addFlashAttribute("alertClass","alert-success"); // 부트스트랩 경고창(성공색으로 변경)
		
		String name = category.getName().toLowerCase().replace(" ", "-");
		Category nameExist = categoryRepo.findByName(category.getName()); // name으로 category 테이블 검색
		
		if (nameExist != null) { // 같은 Name이 존재할 시 저장안함
			attr.addFlashAttribute("message","카테고리가 이미 존재하고 있습니다. 다시 입력해주세요.");
			attr.addFlashAttribute("alertClass","alert-danger"); // 부트스트랩 경고창(성공색으로 변경)
			attr.addFlashAttribute("category", category);
		} else {
			category.setSlug(name); // 소문자, -으로 수정된 name을 slug로 Update
			category.setSorting(100); // 기본 Sorting 값
			
			categoryRepo.save(category);
		}
		
		return "redirect:/admin/categories/add";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) {
		Category category = categoryRepo.getById(id); // id로 데이터 검색
		model.addAttribute(category);
		return "admin/categories/edit"; // 수정페이지로 이동
	}
	
	@PostMapping("/edit")
	public String edit(@Valid Category category, BindingResult bindingResult, RedirectAttributes attr) {
		//Validation 결과 Error가 있으면 이전페이지로 이동
		if (bindingResult.hasErrors()) {
			return "admin/categories/edit";
		}
		// 검사 통과 시
		attr.addFlashAttribute("message","성공적으로 페이지 수정됨");
		attr.addFlashAttribute("alertClass","alert-success"); // 부트스트랩 경고창(성공색으로 변경)
		
		// Slug 검사
		// Slug를 미입력 시 Title을 소문자로 하고 공백을 - 으로 변환, 입력시에도 소문자 공백은 - 변환
		String name = category.getName().toLowerCase().replace(" ", "-");
		Category nameExist = categoryRepo.findByName(name); // Slug로 DB검색하여 있으면 category로 Return (현재 출력 중인 id는 제외)
		
		if (nameExist != null) { // 같은 Slug가 존재할 시 저장안함
			attr.addFlashAttribute("message","카테고리가 이미 존재하고 있습니다. 다시 입력해주세요.");
			attr.addFlashAttribute("alertClass","alert-danger"); // 부트스트랩 경고창(성공색으로 변경)
			attr.addFlashAttribute("category", category);
		} else {
			category.setSlug(name); // 소문자, -으로 수정된 Slug를 Update
			category.setSorting(100); // 기본 Sorting 값
			
			categoryRepo.save(category);
		}
		
		return "redirect:/admin/categories/edit/" + category.getId();
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id, RedirectAttributes attr) {
		categoryRepo.deleteById(id);

		attr.addFlashAttribute("message", "성공적으로 삭제되었습니다.");
		attr.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/admin/categories";
	}
	
	@PostMapping("/reorder") // AJAX로 오기 때문에 view가 필요없음, @ResponseBody 앞에 넣어줘서 View로 Return 하지 않고 문자열 "ok"로 Return
	public @ResponseBody String reorder(@RequestParam("id[]") int[] id) {
		int count = 1;
		Category category;
		
		// 변경될 때 마다 테이블 모든 id값들이 넘어와서 전부 값이 재정렬됨
		for (int categoryId : id) {
			category = categoryRepo.getById(categoryId); // 1. DB에서 id로 page 객체 검색
			category.setSorting(count); // 2. 불러온 page객체에 Sorting값 변경
			categoryRepo.save(category);	// 3. 변경된 page객체 저장
			count++;				// 4. count 값 증가
		}
		return "ok";
	}
	
}
