package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;

@WebServlet("/IdCheckController")
public class IdCheckController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserDAO userDAO;
       
	@Override
	public void init() throws ServletException {
		userDAO = new UserDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String userID = request.getParameter("userID");
		System.out.println("Controller, View에서 가져온 Parameter = " + userID);
		response.getWriter().write(userDAO.idCheck(userID) + "");
	}

}
