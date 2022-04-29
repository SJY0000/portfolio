package com.myapp.shoppingmall.entities;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Entity
@Table(name = "users") // DB의 users Table과 매칭
@Data
public class User implements UserDetails{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank
	@Size(min = 2, message = "최소 2글자 이상 입력해주세요.")
	private String username;
	
	@NotBlank
	@Size(min = 4, message = "비밀번호는 최소 4글자이상 입력해주세요.")
	private String password;
	
	@Transient // 사용은 할 수 있지만 데이터는 없는 더미, 패스워드 확인 시 사용
	private String confirmPassword;
	
	@Email(message = "이메일 양식에 맞게 입력해주세요.")
	private String email;
	
	@Column(name = "phone_number")
	@Size(min = 6, message = "전화번호를 제대로 입력해주세요.")
	private String phoneNumber;
	
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 권한 목록을 Return(USER 권한)
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public boolean isAccountNonExpired() {
		// 계정 만료 여부
		return true; // 만료 안됨
	}

	@Override
	public boolean isAccountNonLocked() {
		// 계정 잠금 여부
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// 비밀번호 만료 여부
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 사용 가능한 계정인지 체크
		return true;
	}

}
