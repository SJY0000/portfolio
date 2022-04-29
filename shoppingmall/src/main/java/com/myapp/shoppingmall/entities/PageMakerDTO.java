package com.myapp.shoppingmall.entities;


/**
 * 페이지 네이션을 위해서 전체게시물 수와 cri를 입력받아 계산하여
 * 시작페이지, 끝페이지, 이전,다음페이지 유무를 저장
 * 
 *
 */
public class PageMakerDTO {
	
	// 시작 페이지(현재페이지)
	private int startPage;
	
	// 마지막 페이지
	private int endPage;
	
	// 이전페이지, 다음페이지 유무
	private boolean prev, next;
	
	// 전체 게시글 수
	private int total;
	
	// 현재 페이지, 페이지당 게시글 출력 수 정보
	private Criteria cri;

	public PageMakerDTO(int total, Criteria cri) {
		this.total = total;
		this.cri = cri;
		
		// 마지막 페이지 : 10 단위로 표시 1~10, 11~20, 21~30, ceil은 올림
		// 현재 페이지가 1이면 끝페이지는 10, 11이면 끝페이지는 20
		this.endPage = (int)(Math.ceil(cri.getPageNum()/10.0)) * 10;
		
		// 시작페이지
		this.startPage = this.endPage - 9;
		
		// 실제 마지막 페이지
		int realend = (int)(Math.ceil(total * 1.0/cri.getAmount()));
		
		// 실제 마지막 페이지(realend)가 화면에 보이는 마지막페이지(endPage)보다 작은경우, endPage값을 조정
		if(realend < this.endPage) {
			this.endPage = realend; // realend = this.endPage 하면 적용이 안됨
		}
		
		// < 이전페이지가 true이려면 시작 페이지(startPage)값이 1보다 큰 경우 true
		this.prev = this.startPage > 1;
		
		// > 다음페이지가 true이려면 마지막 페이지(endPage)값이 realend값보다 작은 경우 true
		this.next= this.endPage < realend;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Criteria getCri() {
		return cri;
	}

	public void setCri(Criteria cri) {
		this.cri = cri;
	}

	@Override
	public String toString() {
		return "PageMakerDTO [startPage=" + startPage + ", endPage=" + endPage + ", prev=" + prev + ", next=" + next
				+ ", total=" + total + ", cri=" + cri + "]";
	}
	
	
}
