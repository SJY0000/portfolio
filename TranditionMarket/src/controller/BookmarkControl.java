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

import dao.BookmarkDAO;
import dao.UserDAO;

@WebServlet("/Bookmark")
public class BookmarkControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private BookmarkDAO bookmarkDAO;
    public BookmarkControl() {
    }

	public void init(ServletConfig config) throws ServletException {
		bookmarkDAO = new BookmarkDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF8");
		String action = request.getParameter("cmd") != null ? request.getParameter("cmd") : "show";

		switch(action) {
		case "delete":
			deleteBookmark(request, response);
			break;
		case "insert":
			insertBookmark(request, response); 
			break;
		case "check":
			checkBookmark(request, response);
			break;
		default:
			showBookmark(request, response);
			break;
		}
	}

	private boolean checkBookmark(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("userId");
		int mno = Integer.parseInt(request.getParameter("mno"));
		UserDAO userDAO = new UserDAO();
		int uno = userDAO.findUno(id);

		boolean isExist = bookmarkDAO.bookmark(uno, mno);
		PrintWriter out = response.getWriter();
		out.print(isExist);
		return isExist;
	}

	private void deleteBookmark(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("userId");
		String mno = request.getParameter("mno");
		boolean delete = bookmarkDAO.deleteBookmark(id, mno);
		if(delete) {
			System.out.println("즐겨찾기 삭제 완료");
			response.sendRedirect("Bookmark");
		}
		
	}
	
	private void insertBookmark(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("userID");
		int mno = Integer.parseInt(request.getParameter("mno"));
		UserDAO userDAO = new UserDAO();
		int uno = userDAO.findUno(id);
		
		bookmarkDAO.insertBookmark(uno, mno);
	}

	private void showBookmark(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String id = request.getParameter("userId");
//		bookmarkDAO.showBookmark(id);
		RequestDispatcher rd = request.getRequestDispatcher("bookmark.jsp");
		rd.forward(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
