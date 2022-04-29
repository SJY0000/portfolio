package com.myapp.shoppingmall.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// 1. WebSecurityConfigurerAdapter 상속, 2. @EnableWebSecurity
@Configuration // Class 안에 등록할 객체 또는 메소드가 있음을 표시
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	// Security는 1. 인증(로그인) , 2. 허가(role에 따른 권한)
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean // 이 메소드를 Spring에 빈(메소드)로 등록
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(); // 비밀번호 인코더 객체
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 인증메소드 구현, 인증하기위해서 userDetailsService로 User의 username, password, role 등을 찾아서 인증
		auth.userDetailsService(userDetailsService) // 관리자 또는 유저를 찾음
			.passwordEncoder(encoder());			// 비밀번호를 다시 풀기위한 암호화 객체 필요
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 허가(rore에 따른 권한설정)
		http.authorizeHttpRequests()
			.antMatchers("/category/**").hasAnyRole("USER", "ADMIN") // 카테고리 안의 페이지는 유저, 관리자 접근가능
			.antMatchers("/admin/**").hasAnyRole("ADMIN")			// 관리자 안의 페이지는 관리지만 접근가능
			.antMatchers("/").permitAll()							// 누구나 접근 가능
			.and()
			.formLogin().loginPage("/login")						// 로그인페이지 주소
			.and()
			.logout().logoutSuccessUrl("/")							// Logout 시 홈으로 이동
			.and()
			.exceptionHandling().accessDeniedPage("/");				// 예외 발생시 홈으로 이동
		
	}
}
