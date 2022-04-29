package com.myapp.shoppingmall.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

//실제 Table과 매칭
@Entity
@Table(name = "products")
@Data // Get, Set, Construct, toSring 생성됨
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "상품명을 입력해주세요.")
	@Size(min = 2, message = "최소 2글자 이상 입력해주세요.")
	private String name;
	
	private String slug;
	
	@Size(min = 5, message = "최소 5자 이상 입력해주세요.")
	private String description;	// 상품 설명
	private String image;	// 상품 이미지 파일 이름
	
	@Pattern(regexp = "^[1-9][0-9]*") // 맨앞자리 1~9만 사용가능, 나머지는 0~9 사용가능 숫자만 1 ~ 99999999 이런 식으로 표현 가능
	private String price; // 문자열로 하고 int로 변환해서 사용
	
	@Pattern(regexp = "^[1-9][0-9]*", message = "카테고리를 선택해주세요") // 생성 후 Update는 안됨
	@Column(name = "category_id", updatable = false)
	private String categoryId;	// 상품의 카테고리 id
	
	@Column(name = "created_at")
	@CreationTimestamp			// Insert 시 자동으로 날짜 입력
	private LocalDateTime createdAt;	// 상품 등록 날짜
	
	@Column(name = "updated_at")
	@UpdateTimestamp			// Update 시 자동으로 날짜 입력
	private LocalDateTime updatedAt;	// 상품 수정 날짜
	
}
