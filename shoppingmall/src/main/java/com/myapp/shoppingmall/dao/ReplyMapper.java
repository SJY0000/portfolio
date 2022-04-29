package com.myapp.shoppingmall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.myapp.shoppingmall.entities.ReplyVO;

@Mapper
public interface ReplyMapper {

	public void enroll(ReplyVO reply); // 댓글 등록
	
	public List<ReplyVO> getReplyList(int reply_bno); // 댓글 리스트 불러오기
	
	public int modify(ReplyVO reply); // 댓글 수정
	
	public int delete(int reply_no); // 댓글 삭제

}
