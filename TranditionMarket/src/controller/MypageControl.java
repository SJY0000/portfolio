package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.MypageDAO;
import model.User;

@WebServlet("/Mypage")
public class MypageControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MypageDAO mypageDAO;

	public void init(ServletConfig config) throws ServletException {
		mypageDAO = new MypageDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF8");
		String action = request.getParameter("cmd") != null ? request.getParameter("cmd") : "show";
		HttpSession session = request.getSession();
		if(session.getAttribute("userID") == null) {
			RequestDispatcher rd = request.getRequestDispatcher("main.jsp");
			rd.forward(request, response);
		} else {
			try {
				switch (action) {
				case "edit":
					editMydata(request, response);
					break;
				case "delete":
					deleteMydata(request, response);
					break;
				case "deleteBm":
					deleteBookmark(request, response);
					break;
				case "checkNick":
					checkNick(request, response);
					break;
				case "editProfile":
					editProfile(request, response);
					break;
				case "deleteProfile":
					deleteProfile(request, response);
					break;
				default:
					show(request, response);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		

	}

	private void editProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String path = "/upload/member";
		String savePath = request.getServletContext().getRealPath(path);
		int MaxSize = 1024*1024*15;
		
		MultipartRequest multi = new MultipartRequest(request,savePath, MaxSize, "utf-8", new DefaultFileRenamePolicy());
		
		String id = multi.getParameter("userId");
		String userImg = multi.getFilesystemName("userImg");
		String userImgPath = path + "/" + userImg;
		
		int result = mypageDAO.editMyProfile(id, userImgPath);
		
		if(result == 1) {
			System.out.println("프로필 사진 변경 완료");
			response.sendRedirect("Mypage");
		} else {
			System.out.println("프로필 사진 변경 실패");
		}
		
		
	}

	private void deleteProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("userId");
		
		boolean delete = mypageDAO.deleteProfile(id);
		if(delete) {
			System.out.println("기본 프로필 사진으로 변경됨");
			response.sendRedirect("Mypage");
		} else {
			System.out.println("프로필 삭제 실패");
		}
	}

	private void checkNick(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userNick = request.getParameter("userNick");
		int result = mypageDAO.checkNick(userNick);
		PrintWriter out = response.getWriter();
		out.write(result+"");
		
	}

	private void deleteBookmark(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("userId");
		String mname = request.getParameter("mname");
		boolean delete = mypageDAO.deletBookmark(id, mname);
		if(delete) {
			System.out.println("즐겨찾기 삭제 완료");
			response.sendRedirect("Mypage");
		} else {
			System.out.println("즐겨찾기 삭제 실패");
		}
	}

	private void show(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("myPage.jsp");
		rd.forward(request, response);
	}

	private void deleteMydata(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("userId");

		boolean delete = mypageDAO.deleteUser(id);
		if (delete) {
			System.out.println("탈퇴완료");
			response.sendRedirect("main.jsp");
		} else {
			response.sendRedirect("Mypage");
		}
	}

	private void editMydata(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = new User();
		
		user.setUserId(request.getParameter("userId"));
		user.setUserName(request.getParameter("userName"));
		user.setUserNick(request.getParameter("userNick"));
		user.setUserEmail(request.getParameter("userEmail"));
		user.setUserAddress(request.getParameter("userAddress"));
		user.setUserPassword(request.getParameter("userPw"));
		user.setUserIntro(request.getParameter("userIntro"));
		
		boolean edited = mypageDAO.editMyData(user);
		if(edited) {
			System.out.println("수정완료");
			response.sendRedirect("Mypage");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
