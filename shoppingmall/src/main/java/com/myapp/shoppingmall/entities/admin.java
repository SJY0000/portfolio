package com.myapp.shoppingmall.entities;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Entity
@Table(name = "admin") // DB의 admin Table과 매칭
@Data
public class admin implements UserDetails{
	private static final long serialVersionUID = 2L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String username;

	private String password;


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 권한 목록을 Return(ADMIN 권한)
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	@Override
	public boolean isAccountNonExpired() {
		// 계정 만료 여부
		return true; // 만료 안됨
	}

	@Override
	public boolean isAccountNonLocked() {
		// 계정 잠금 여부
		return true; // 잠기지 않음
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// 비밀번호 만료 여부
		return true; // 만료 안됨
	}

	@Override
	public boolean isEnabled() {
		// 사용 가능한 계정인지 체크
		return true; // 사용 가능
	}

}
