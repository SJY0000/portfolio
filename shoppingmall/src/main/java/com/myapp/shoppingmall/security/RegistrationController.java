package com.myapp.shoppingmall.security;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.shoppingmall.dao.UserRepository;
import com.myapp.shoppingmall.entities.User;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired // 비밀번호 암호화
	private PasswordEncoder passwordEncoder;
	
	@GetMapping
	public String register(User user) {
		return "register";
	}
	
	@PostMapping
	public String register(@Valid User user, BindingResult bindingResult, Model model) {
		// 1. 유효성검사 시 문제가 있을 경우 돌아감
		if (bindingResult.hasErrors()) {
			return "register";
		}
		// 2. 비밀번호 체크가 실패했을 시 돌아감
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			model.addAttribute("passwordNotMatch", "비밀번호가 일치하지않습니다.");
			return "register";
		}
		// 3. 비밀번호를 암호화해서 입력
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		// 4. DB에 새 유저 저장
		userRepo.save(user);
		
		return "redirect:/login"; // 회원가입이 성공적으로 끝나면 login페이지로 이동
	}
}
