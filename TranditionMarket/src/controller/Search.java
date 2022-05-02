package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MarketDAO;
import model.MarketBean;
import model.MarketImgBean;

@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action == null) {
			request.getRequestDispatcher("main.jsp").forward(request, response);
		} else if (action.equals("search")) {
			String searchKW = request.getParameter("searchKW");
			request.setAttribute("searchKW", searchKW);
			List<MarketBean> searchedMK = new ArrayList<>();
			List<MarketImgBean> searchedMKIMG = new ArrayList<>();
			MarketDAO md = new MarketDAO();
			searchedMK = md.search(searchKW);
			if(searchedMK.isEmpty()) {
				request.setAttribute("MKdataNull", true);
			}
			else {
				for (int i = 0; i < searchedMK.size(); i++) {
					searchedMKIMG = md.importIMG(searchedMK.get(i));
					if(searchedMKIMG.isEmpty()) {
						request.setAttribute("MKIMGdataNull"+i, true);
					}else {
						request.setAttribute("MKIMGdataNull"+i, false);
						request.setAttribute("searchedMKIMG"+i, searchedMKIMG);
					}
				}
				request.setAttribute("MKdataNull", false);
				request.setAttribute("searchedMK", searchedMK);
			}
			request.getRequestDispatcher("search.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
