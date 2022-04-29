package com.myapp.shoppingmall.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myapp.shoppingmall.dao.AdminRepository;
import com.myapp.shoppingmall.dao.UserRepository;
import com.myapp.shoppingmall.entities.User;
import com.myapp.shoppingmall.entities.admin;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private AdminRepository adminRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// DB에서 필요한 유저 또는 관리자정보를 읽어온다. (입력 parameter는 username)
		User user = userRepo.findByUsername(username);
		admin admin = adminRepo.findByUsername(username);
		
		if(admin != null) return admin;
		if(user != null) return user;
		
		throw new UsernameNotFoundException("유저 " + username + "이 없습니다.");
	}

}
