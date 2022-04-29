package com.myapp.shoppingmall.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.shoppingmall.entities.Page;

public interface PageRepository extends JpaRepository<Page, Integer> {

	// 테이블에 slug라는 컬럼을 프로그램이 알고 있어서 이런식으로 입력해주면 jpa 하이버네이트가 자동으로 생성해줌
	Page findBySlug(String slug);

	Page findBySlugAndIdNot(String slug, int id); // 슬러그를 찾는데 현재 id로 찾은 것은 제외

	// Sorting값이 작은게 위로 오도록 정렬하고 불러오기
	List<Page> findAllByOrderBySortingAsc();
	
	// List<Page>로 리턴되는 findAll() 등 여러 메소드가 이미 생성되어 있음
	
}
