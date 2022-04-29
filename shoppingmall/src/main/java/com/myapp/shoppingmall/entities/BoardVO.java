package com.myapp.shoppingmall.entities;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BoardVO {

	private int bno;	// 게시판번호
	private String title;	// 게시판 제목
	private String content;	// 게시판 내용
	private String writer;	// 게시판 작성자
	private LocalDateTime regdate; // timestamp에서 날짜시간을 가져오는 자바 날짜시간/데이터
	private LocalDateTime updateDate;
	private String kinds;	// 게시판 분류
}
