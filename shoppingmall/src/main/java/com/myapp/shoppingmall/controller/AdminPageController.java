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

import com.myapp.shoppingmall.dao.PageRepository;
import com.myapp.shoppingmall.entities.Page;

@Controller
@RequestMapping("/admin/pages")
public class AdminPageController {

	@Autowired
	private PageRepository pageRepo;
	
	@GetMapping
	public String index(Model model) {
		List<Page> pages = pageRepo.findAllByOrderBySortingAsc();
		model.addAttribute("pages", pages);
		return "admin/pages/index";
	}
	
	@GetMapping("/add")
	public String add(@ModelAttribute Page page) {
//		model.addAttribute("page", new Page());
		return "admin/pages/add";
	}
	
	@PostMapping("/add")
	public String add(@Valid Page page, BindingResult bindingResult, RedirectAttributes attr) {
		//Validation 결과 Error가 있으면 이전페이지로 이동
		if (bindingResult.hasErrors()) {
			return "admin/pages/add";
		}
		// 검사 통과 시
		attr.addFlashAttribute("message","성공적으로 페이지 추가됨");
		attr.addFlashAttribute("alertClass","alert-success"); // 부트스트랩 경고창(성공색으로 변경)
		
		// Slug 검사
		// Slug를 미입력 시 Title을 소문자로 하고 공백을 - 으로 변환, 입력시에도 소문자 공백은 - 변환
		String slug = page.getSlug() == "" ? page.getTitle().toLowerCase().replace(" ", "-") : page.getSlug();
		Page slugExist = pageRepo.findBySlug(slug); // Slug로 DB검색하여 있으면 Page로 Return
		
		if (slugExist != null) { // 같은 Slug가 존재할 시 저장안함
			attr.addFlashAttribute("message","Slug가 이미 존재하고 있습니다. 다시 입력해주세요.");
			attr.addFlashAttribute("alertClass","alert-danger"); // 부트스트랩 경고창(성공색으로 변경)
			attr.addFlashAttribute("page", page);
		} else {
			page.setSlug(slug); // 소문자, -으로 수정된 Slug를 Update
			page.setSorting(100); // 기본 Sorting 값
			
			pageRepo.save(page);
		}
		
		return "redirect:/admin/pages/add";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) {
		Page page = pageRepo.getById(id); // id로 데이터 검색
		model.addAttribute(page);
		return "admin/pages/edit"; // 수정페이지로 이동
	}
	
	@PostMapping("/edit")
	public String edit(@Valid Page page, BindingResult bindingResult, RedirectAttributes attr) {
		//Validation 결과 Error가 있으면 이전페이지로 이동
		if (bindingResult.hasErrors()) {
			return "admin/pages/edit";
		}
		// 검사 통과 시
		attr.addFlashAttribute("message","성공적으로 페이지 수정됨");
		attr.addFlashAttribute("alertClass","alert-success"); // 부트스트랩 경고창(성공색으로 변경)
		
		// Slug 검사
		// Slug를 미입력 시 Title을 소문자로 하고 공백을 - 으로 변환, 입력시에도 소문자 공백은 - 변환
		String slug = page.getSlug() == "" ? page.getTitle().toLowerCase().replace(" ", "-") : page.getSlug();
		Page slugExist = pageRepo.findBySlugAndIdNot(slug, page.getId()); // Slug로 DB검색하여 있으면 Page로 Return (현재 출력 중인 id는 제외)
		
		if (slugExist != null) { // 같은 Slug가 존재할 시 저장안함
			attr.addFlashAttribute("message","Slug가 이미 존재하고 있습니다. 다시 입력해주세요.");
			attr.addFlashAttribute("alertClass","alert-danger"); // 부트스트랩 경고창(성공색으로 변경)
			attr.addFlashAttribute("page", page);
		} else {
			page.setSlug(slug); // 소문자, -으로 수정된 Slug를 Update
			
			pageRepo.save(page);
		}
		
		return "redirect:/admin/pages/edit/" + page.getId();
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id, RedirectAttributes attr) {
		pageRepo.deleteById(id);

		attr.addFlashAttribute("message", "성공적으로 삭제되었습니다.");
		attr.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/admin/pages";
	}
	
	@PostMapping("/reorder") // AJAX로 오기 때문에 view가 필요없음, @ResponseBody 앞에 넣어줘서 View로 Return 하지 않고 문자열 "ok"로 Return
	public @ResponseBody String reorder(@RequestParam("id[]") int[] id) {
		int count = 1;
		Page page;
		
		// 변경될 때 마다 테이블 모든 id값들이 넘어와서 전부 값이 재정렬됨
		for (int pageId : id) {
			page = pageRepo.getById(pageId); // 1. DB에서 id로 page 객체 검색
			page.setSorting(count); // 2. 불러온 page객체에 Sorting값 변경
			pageRepo.save(page);	// 3. 변경된 page객체 저장
			count++;				// 4. count 값 증가
		}
		return "ok";
	}
}
