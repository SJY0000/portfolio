package com.myapp.shoppingmall.entities;

import java.util.Arrays;

/**
 * 페이지 계산을 위한 클래스
 * 
 *
 */
public class Criteria {

	private int pageNum; // 현재 페이지
	
	private int amount; // 한 페이지당 보여질 게시글 수

	private int skip; // 스킵할 페이지 수 ((pageNum -1) * amount)
	
	private String keyword; // 검색어 키워드
	
	private String type; // 검색 타입(View에서 선택된)
	
	private String[] typeArr; // 검색 타입 배열(type을 배열로 변환)
	
	public Criteria() { // 기본 생성자 -> 기본 세팅: pageNum = 1; amount = 10 
		this(1,10); // 전체 생성자를 통해 (1,10)을 입력해 객체 생성 -> Criteria(1,10)
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
		this.skip = (pageNum - 1) * amount;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		// 검색할 타입만 설정되면 typeArr은 자동으로 생성
		this.typeArr = type.split(""); // 한 문자씩 끊어서 배열로 만듬
	}

	public String[] getTypeArr() {
		return typeArr;
	}

	public void setTypeArr(String[] typeArr) {
		this.typeArr = typeArr;
	}

	public int getPageNum() {
		return pageNum;
	}

	// 새로 페이지 숫자를 설정 했을 때 skip도 같이 계산하여 입력
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
		this.skip = (pageNum - 1) * amount;
	}

	public int getAmount() {
		return amount;
	}

	// 페이지당 보여질 게시글 수를 새로 설정 했을 때 skip도 같이 계산하여 입력
	public void setAmount(int amount) {
		this.amount = amount;
		this.skip = (pageNum - 1) * amount;
	}

	public int getSkip() {
		return skip;
	}

	public void setSkip(int skip) {
		this.skip = skip;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "Criteria [pageNum=" + pageNum + ", amount=" + amount + ", skip=" + skip + ", keyword=" + keyword
				+ ", type=" + type + ", typeArr=" + Arrays.toString(typeArr) + "]";
	}
}
