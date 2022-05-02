package model;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Boards;

public class Json {
	
	private BoardJson boardJson; //보낼 객체
	private Gson gson;				 //Gson라이브러리 객체
	private PrintWriter out;		 //출력 객체
	private HttpServletResponse response; //응답 객체
	
	public Json(HttpServletResponse response) { //생성자 (response) => 응답으로 제이슨 출력
		boardJson = new BoardJson(); //객체 생성
		gson = new Gson();				 //객체 생성
		
		this.response = response;
		this.response.setContentType("application/json; charset=utf-8"); //응답 타입을 제이슨으로 설정
		
		try {
			out = response.getWriter(); //응답 out객체 생성
		}
		catch (IOException e) { //예외처리
			e.printStackTrace();
		}
	}
	//1. 연락처를 응답으로 보낼때 (수정 요청시 => 그 수정연락처의 내용을 응답으로 보내준다.)
	public void sendContact(Boards board) {
		boardJson.setStatus(true); 	 // 상태 양호
		boardJson.setBoard(board); 
		
		sendResponse(gson.toJson(boardJson)); // 제이슨으로 변환
	}
	//2. 메세지만 응답으로 보낼때 (입력,업데이트,삭제 등은 메세지로 성공 여부만 보낸다.)
	public void sendMessage(boolean status, String message) { 
		boardJson.setStatus(status);  // 상태 입력
		boardJson.setMessage(message); // 메세지 입력
		
		sendResponse(gson.toJson(boardJson)); //상태,메세지 제이슨 변환해서 출력
	}
	// 공통으로 출력 메서드
	private void sendResponse(String jsonData) {
		out.print(jsonData); 
		out.flush(); //혹시 남아있는 데이터 모두 출력(남아있는 내용이 없도록)
	}

}
