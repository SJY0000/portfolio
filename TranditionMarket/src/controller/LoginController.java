package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;

@WebServlet("/Login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UserDAO userDAO;

	public void init(ServletConfig config) throws ServletException {
		userDAO = new UserDAO();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter out = response.getWriter();
		
		int loginResult = userDAO.login(request.getParameter("userID"), request.getParameter("userPassword"));
		if(loginResult == 1) {
			HttpSession session = request.getSession();
			session.setAttribute("userID", request.getParameter("userID"));
			response.sendRedirect("main.jsp");
		} else if(loginResult == 0) {
			out.println("<script>");
			out.println("alert('비밀번호가 틀립니다.');");
			out.println("history.back()");
			out.println("</script>");
		} else if(loginResult == -1) {
			out.println("<script>");
			out.println("alert('존재하지 않는 아이디입니다.');");
			out.println("history.back()");
			out.println("</script>");

		} else {
			out.println("<script>");
			out.println("alert('데이터베이스 오류가 발생했습니다.');");
			out.println("history.back()");
			out.println("</script>");
		}
	}

}
