package com.myapp.shoppingmall.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myapp.shoppingmall.dao.BoardMapper;
import com.myapp.shoppingmall.entities.BoardVO;
import com.myapp.shoppingmall.entities.Criteria;



@Service
public class BoardServiceImpl implements BoardService {

	private BoardMapper boardMapper;
	
	public BoardServiceImpl(BoardMapper boardMapper) {
		this.boardMapper = boardMapper;
	}
	
	@Override
	public void enrollNotice(BoardVO board) {
		boardMapper.enrollNotice(board);
	}
	
	@Override
	public void enrollBoard(BoardVO board) {
		boardMapper.enrollBoard(board);
	}
	
	@Override
	public List<BoardVO> getListNoticePaging(Criteria cri) {
		return boardMapper.getListNoticePaging(cri);
	}

	@Override
	public List<BoardVO> getList() {
		return boardMapper.getList();
	}

	@Override
	public BoardVO getPage(int bno) {
		return boardMapper.getPage(bno);
	}

	@Override
	public int modify(BoardVO board) {
		return boardMapper.modify(board);
	}

	@Override
	public int delete(int bno) {
		return boardMapper.delete(bno);
	}

	@Override
	public List<BoardVO> getListPaging(Criteria cri) {
		return boardMapper.getListPaging(cri);
	}

	@Override
	public int getTotal(Criteria cri) {
		return boardMapper.getTotal(cri);
	}


}
