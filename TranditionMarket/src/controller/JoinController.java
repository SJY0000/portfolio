package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.UserDAO;
import model.User;

@WebServlet("/Join")
public class JoinController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UserDAO userDAO;
	
	public void init(ServletConfig config) throws ServletException {
		userDAO = new UserDAO();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		//서버의 위치 - WebContent/upload/member
		String path = "/upload/member";
		System.out.println(path);
		
		//서버 컴퓨터의 실제 저장 폴더 위치
		String savePath = request.getServletContext().getRealPath(path);
		System.out.println("파일 실제 저장경로 : " + savePath);
		//용량제한
		int maxSize = 100 * 1024 * 1024;
		//데이터의 엔코딩 방식
		String encoding = "UTF-8";
		
		// DefaultFileRenamePolicy() : 업로드 파일명이 중복되면 파일명 뒤에 1을 붙인다.
		
		MultipartRequest multi = new MultipartRequest(request, savePath, maxSize, encoding, new DefaultFileRenamePolicy());
		
		String userimg = path + "/" + multi.getFilesystemName("userImg"); // multi.getFilesystemName() = 서버에 실제로 올라간 파일명을 의미
		String noimg =  path + "/noImage.png"; 
		
		
		// 위에 multi가 생성되면 request에서는 데이터를 받을 수 없다. multi에서 받아야 한다.
		User user = new User();
		user.setUserId(multi.getParameter("userID"));
		user.setUserPassword(multi.getParameter("userPassword"));
		user.setUserName(multi.getParameter("userName"));
		user.setUserNick(multi.getParameter("userNick"));
		user.setUserAddress(multi.getParameter("userAddress"));
		user.setUserEmail(multi.getParameter("userEmail"));
		user.setUserIntro(multi.getParameter("userIntro"));
		
		if(multi.getFilesystemName("userImg") == null) {
			user.setUserImg(noimg);
		} else {
			user.setUserImg(userimg); 
		}
		
		
		System.out.println("joincontroller : " + user.getUserImg());
		
		int joinResult = userDAO.join(user);
		System.out.println(joinResult);
		
		if(joinResult == 1) {
			response.sendRedirect("login.jsp");
		} else {
			response.sendRedirect("join.jsp");
		}
	}
	

}
