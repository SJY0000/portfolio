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

@WebServlet("/Data")
public class Data extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String command = request.getParameter("command");
		MarketDAO md = new MarketDAO();
		
		if(command.equals("listdb")) {
			int toilet;
			int parking;
			md.delete();
			
			String[] marketList = request.getParameterValues("market");
			
			List<MarketBean> mbs = new ArrayList<>();
			
			for(int i = 0; i < marketList.length; i++) {
				String[] tmp = marketList[i].split("/");
				
				if(tmp[8].equals("Y")) {
					toilet = 1;
				}else {
					toilet = 0;
				}
				
				if(tmp[9].equals("Y")) {
					parking = 1;
				}else {
					parking = 0;
				}
				
				MarketBean mb = new MarketBean(tmp[0], tmp[1], tmp[2], tmp[3], tmp[4], tmp[5], tmp[6], tmp[7], toilet, parking);
				
				mbs.add(mb);
			}
			md.insert(mbs);
			md.closeAll();
		}
	}

}
