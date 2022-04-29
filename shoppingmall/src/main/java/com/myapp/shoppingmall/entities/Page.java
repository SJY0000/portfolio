package com.myapp.shoppingmall.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

// 실제 Table과 매칭
@Entity
@Table(name = "pages")
@Data // Get, Set, Construct, toSring 생성됨
public class Page {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotBlank(message = "제목을 입력해주세요.")
	@Size(min = 2, message = "최소 2글자 이상 입력해주세요.")
	private String title;
	private String slug; // title을 소문자 띄워쓰기 특수문자등을 - 으로 변환
	@NotBlank(message = "내용을 입력해주세요.")
	@Size(min = 5, message = "최소 5글자 이상 입력해주세요.")
	private String content;
	private int sorting; // 정렬 순서
}
