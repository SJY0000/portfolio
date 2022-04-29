package com.myapp.shoppingmall.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myapp.shoppingmall.dao.CategoryRepository;
import com.myapp.shoppingmall.dao.ProductRepository;
import com.myapp.shoppingmall.entities.Category;
import com.myapp.shoppingmall.entities.Product;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private CategoryRepository categoryRepo;

	@GetMapping
	public String index(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page) {
		int perPage = 6; // @RequestParam에 같이 적어도 되지만 이번엔 따로 적기 (한 페이지당 최대 6개)
		Pageable pageable = PageRequest.of(page, perPage); // 표시할 페이지, 한 페이지에 표시할 데이터 수(6개)
		// data.domain.page
		Page<Product> products = productRepo.findAll(pageable);
		List<Category> categories = categoryRepo.findAll();

		// Category id와 name을 map에 담아 index페이지에 전송
		HashMap<Integer, String> cateIdAndName = new HashMap<>();
		
		for (Category category : categories) {
			cateIdAndName.put(category.getId(), category.getName());
		}

		model.addAttribute("products", products);
		model.addAttribute("cateIdAndName", cateIdAndName);
		
		// Pagenation을 위해 필요한 정보
		long count = productRepo.count(); // 전체 갯수 (long 타입으로 Return)
		double pageCount = Math.ceil((double)count / (double)perPage); // 13/ 6 = 2.12, int로 나누면 소수점이 출력이 안되기때문에 double타입으로 계산한다.
		
		model.addAttribute("pageCount",pageCount); // 총페이지
		model.addAttribute("perPage", perPage);		// 페이지당 표시 아이템수
		model.addAttribute("count", count);			// 총 아이템 갯수
		model.addAttribute("page", page);			// 현재 페이지
		
		return "/admin/products/index";
	}

	@GetMapping("/add")
	public String add(@ModelAttribute Product product, Model model) {
		List<Category> categories = categoryRepo.findAll();

		model.addAttribute("categories", categories);
//		model.addAttribute("product", new Product()); // @ModelAttribute Product product 동일함

		// 상품을 추가하는 add 페이지에 product 객체와 product의 category를 선택할 수 있게 리스트 전달
		return "/admin/products/add";
	}

	@PostMapping("/add")
	public String add(@Valid Product product, BindingResult bindingResult, MultipartFile file, RedirectAttributes attr,
			Model model) throws IOException { // 유효성검사 실패시 bindingResult로 데이터 넘어옴, 파일은 따로 받아서 정리

		if (bindingResult.hasErrors()) {
			List<Category> categories = categoryRepo.findAll();

			model.addAttribute("categories", categories);
			return "/admin/products/add";
		}

		boolean fileOk = false;
		byte[] bytes = file.getBytes(); // Upload된 이미지 파일의 데이터
		String fileName = file.getOriginalFilename(); // Upload된 파일의 이름
		Path path = Paths.get("src/main/resources/static/media/" + fileName); // 파일을 저장할 위치와 이름까지

		if (fileName.endsWith("jpg") || fileName.endsWith("png")) {
			fileOk = true; // 확장자가 .jpg, .png 만 등록 가능
		}

		// 성공적으로 추가됨
		attr.addFlashAttribute("message", "상품이 성공적으로 등록되었습니다.");
		attr.addFlashAttribute("alertClass", "alert-success");
		// slug 만들기
		String slug = product.getName().toLowerCase().replace(" ", "-");
		// 똑같은 상품명이 있는지 확인
		Product prductExists = productRepo.findByName(product.getName());

		if (!fileOk) { // 파일 Upload가 안됬거나 확장자가 jpg, png가 아님
			attr.addFlashAttribute("message", "이미지는 jpg나 png 확장자를 사용해주세요");
			attr.addFlashAttribute("alertClass", "alert-danger");
			attr.addFlashAttribute("product", product);
		} else if (prductExists != null) {
			attr.addFlashAttribute("message", "이미 등록된 상품입니다. 상품명을 변경해주세요.");
			attr.addFlashAttribute("alertClass", "alert-danger");
			attr.addFlashAttribute("product", product);
		} else { // product와 image를 저장한다.
			product.setSlug(slug);
			product.setImage(fileName); // 이미지는 파일의 이름만 입력(주소는 /media/폴더 이므로 동일)
			productRepo.save(product); // product 저장

			Files.write(path, bytes); // (이미지파일이 저장될 이미지파일 이름이 포함된 주소, 파일데이터)
		}
		return "redirect:/admin/products/add";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) {
		Product product = productRepo.getById(id);
		
		List<Category> categories = categoryRepo.findAll();
		
		model.addAttribute("product",product);
		model.addAttribute("categories",categories);
		
		return "admin/products/edit";
	}
	
	@PostMapping("/edit")
	public String edit(@Valid Product product, BindingResult bindingResult, 
						MultipartFile file, RedirectAttributes attr, Model model) throws IOException { // 유효성검사 실패시 bindingResult로 데이터 넘어옴, 파일은 따로 받아서 정리
		// 미리 id로 수정하기전의 상품 정보를 불러옴
		Product currentProduct = productRepo.getById(product.getId());
		
		if (bindingResult.hasErrors()) {
			List<Category> categories = categoryRepo.findAll();

			model.addAttribute("categories", categories);
			product.setImage(currentProduct.getImage());
			return "/admin/products/edit";
		}

		boolean fileOk = false;
		byte[] bytes = file.getBytes(); // Upload된 이미지 파일의 데이터
		String fileName = file.getOriginalFilename(); // Upload된 파일의 이름
		Path path = Paths.get("src/main/resources/static/media/" + fileName); // 파일을 저장할 위치와 이름까지

		if(!file.isEmpty()) { // 이미지 파일이 존재하면
			if (fileName.endsWith("jpg") || fileName.endsWith("png")) {
				fileOk = true; // 확장자가 .jpg, .png 만 등록 가능
			}
		} else { // 이미지는 수정 안함
			fileOk = true; // 기존 이미지 사용함
		}
		
		// 성공적으로 수정됨
		attr.addFlashAttribute("message", "상품이 성공적으로 수정되었습니다.");
		attr.addFlashAttribute("alertClass", "alert-success");

		String slug = product.getName().toLowerCase().replace(" ", "-");
		// 이름으로 찾고 현재 product의 id 값을 제외한 데이터만 검색
		Product prductExists = productRepo.findByNameAndIdNot(product.getName(), product.getId());

		if (!fileOk) {
			attr.addFlashAttribute("message", "이미지는 jpg나 png 확장자를 사용해주세요");
			attr.addFlashAttribute("alertClass", "alert-danger");
			attr.addFlashAttribute("product", product);
		} else if (prductExists != null) {
			attr.addFlashAttribute("message", "이미 등록된 상품입니다. 상품명을 변경해주세요.");
			attr.addFlashAttribute("alertClass", "alert-danger");
			attr.addFlashAttribute("product", product);
		} else { // product와 image를 저장한다.
			product.setSlug(slug);
			
			if(!file.isEmpty()) {
				Path currentpath = Paths.get("src/main/resources/static/media/" + currentProduct.getImage());
				Files.delete(currentpath); // 새로운 이미지파일이 있기 때문에 기존 파일을 삭제
				product.setImage(fileName);
				Files.write(path, bytes); // 새 이미지 파일 저장
			} else {
				product.setImage(currentProduct.getImage());
			}
			productRepo.save(product);
		}
		return "redirect:/admin/products/edit/" + product.getId();
	}
	
	@GetMapping("delete/{id}")
	public String delete(@PathVariable("id") int id, RedirectAttributes attr) throws IOException {
		// id로 상품을 삭제하기 전에 먼저 id로 제품객체를 불러와서 이미지 파일을 삭제한 후 제품 삭제
		Product currentProduct = productRepo.getById(id);
		
		Path currentPath = Paths.get("src/main/resources/static/media/" + currentProduct.getImage());
		
		Files.delete(currentPath);
		
		productRepo.deleteById(id);
		
		attr.addAttribute("message", "성공적으로 삭제되었습니다.");
		attr.addAttribute("alertClass", "alert-success");
		
		return "redirect:/admin/products";
	}
}
