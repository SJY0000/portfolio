package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AdminDAO;
import model.User;

@WebServlet("/admin")
public class AdminControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private AdminDAO adminDAO;
    public AdminControl() {
    }

	public void init(ServletConfig config) throws ServletException {
		adminDAO = new AdminDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF8");
		String action = request.getParameter("cmd") != null ? request.getParameter("cmd") : "show";
		try {
			switch (action) {
			case "edit":
				editUserData(request, response);
				break;
			case "delete":
				deleteUser(request, response);
				break;
			default:
				showUserList(request, response);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void editUserData(HttpServletRequest request, HttpServletResponse response) throws IOException {
		User user = new User();
		
		user.setUserId(request.getParameter("userId"));
		user.setUserNick(request.getParameter("userNick"));
		user.setUserImg(request.getParameter("userImg"));
		System.out.println(user.getUserImg() + "1234");
		if(user.getUserImg() == null || user.getUserImg() == "") {
			user.setUserImg("/upload/member/noImage.png");
		}
		user.setWarning(Integer.parseInt(request.getParameter("warning")));
		
		boolean edit = adminDAO.editUserData(user);
		if(edit) {
			System.out.println("회원정보 수정 완료");
			response.sendRedirect("admin");
		}
	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("userId");
		
		boolean delete = adminDAO.deleteUser(id);
		if(delete) {
			System.out.println("회원강제탈퇴 완료");
			response.sendRedirect("admin");
		}
	}

	private void showUserList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<User> users = adminDAO.showUserList(); // DB의 모든 연락처 가져오기
		request.setAttribute("users", users);
		RequestDispatcher rd = request.getRequestDispatcher("adminPage.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
