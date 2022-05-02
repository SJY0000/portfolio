package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BookmarkDAO;

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
		default:
			showBookmark(request, response);
			break;
		}
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
