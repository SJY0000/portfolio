package model;

import model.Boards;
// 제이슨 형태로 보낼 클래스
public class BoardJson { 
	
	private boolean status;  //상태 (성공/실패)
	private String message;  //메세지 입력
	private Boards board; 
	
	
	public boolean getStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public Boards getBoard() {
		return board;
	}

	public void setBoard(Boards board) {
		this.board = board;
	}
	

}
