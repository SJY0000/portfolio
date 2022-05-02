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

@WebServlet("/View")
public class View extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action == null) {
			request.getRequestDispatcher("main.jsp").forward(request, response);
		} else if(action.equals("viewMarket")){
			MarketBean viewMK = new MarketBean();
			List<MarketImgBean> viewMKIMG = new ArrayList<>();
			MarketDAO md = new MarketDAO();
			int mno = Integer.parseInt(request.getParameter("mno"));
			viewMK = md.search(mno);
			viewMKIMG = md.importIMG(viewMK);
			request.setAttribute("viewMK", viewMK);
			if(viewMKIMG.isEmpty()) {
				request.setAttribute("MKIMGdataNull", true);
			}else {
				request.setAttribute("MKIMGdataNull", false);
				request.setAttribute("viewMKIMG", viewMKIMG);
			}
			request.getRequestDispatcher("marketView.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
