package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.websocket.Session;

import  dao.BoardDao;
import model.Boards;
import  model.Json;
 
@WebServlet("/Boards")
public class Boardcontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name = "jdbc/tranditionmarket")
	private DataSource ds;
	private BoardDao boardDao;
	
	
	@Override
	public void init() throws ServletException {
		boardDao = new BoardDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action") != null ? request.getParameter("action") : "home";
		String actiont= request.getParameter("actiont");
		if (actiont == null) actiont = "";
		if (actiont.equals("no")) {
			action = "no";
		}

		switch (action) {
		case "notice":
			list(request, response);
			break;
		case "board": 
			list(request, response);
			break;
		case "save":
			save(request, response);
			break;
		case "write": 
			write(request, response);
			break;
		case "view":
			view(request, response);
			break;
		case "reco":
			reco(request, response);
			break;
		case "commentsave":
			commentsave(request, response);
			break;
		case "editcomment":
			edit(request, response);
			break;
		case "cedit":
			cedit(request, response);
			break;
		case "comment":
			update(request, response);
			break;
		case "cdelete": // 삭제
			cdelete(request, response);
			break;
		case "deletecomment":
			delete(request, response);
			break;
		case "no":
			no(request, response);
			break;
		default: 
			home(request, response);
			break;
		}
	}
	// 댓글 저장
	private void commentsave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String comment = request.getParameter("comment");
		HttpSession session = request.getSession();
		
		int bno = Integer.parseInt(request.getParameter("bno"));
		int uno = Integer.valueOf((String) session.getAttribute("uno"));
		
		boardDao.csave(uno, bno, comment);	
	}

	// 로그인 안했을 때 경고페이지
	private void no(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("no.jsp?action=noLogin");
		rd.forward(request, response);
		
	}
	// 댓글 삭제
	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int bno = Integer.parseInt(request.getParameter("bno"));
		int cno = Integer.parseInt(request.getParameter("cno"));
		String actiont = request.getParameter("actiont");
		String uno = request.getParameter("uno");
		String cuno = request.getParameter("cuno");
		String admin = request.getParameter("admin");
		if (uno.equals(cuno) || admin.equals("1")) {
			boardDao.delete(cno);
			
			Boards board = boardDao.find(bno);
			request.setAttribute("boardlist", board);
			request.setAttribute("bno", bno);
			
			RequestDispatcher rd = request.getRequestDispatcher("viewboard.jsp" + "?acitont=" + actiont);
			rd.forward(request, response);
		} else {			
			RequestDispatcher rd = request.getRequestDispatcher("no.jsp" + "?action=permission&actiont=" + actiont);
			rd.forward(request, response);
		}
	}
	// 게시글 삭제
	private void cdelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("bno") == null) {
			list(request,response);
		}
		
		int bno = Integer.parseInt(request.getParameter("bno"));
		String actiont = request.getParameter("actiont");
		
		boardDao.cdelete(bno);
		
		List<Boards> board = boardDao.findAll(actiont);  
		request.setAttribute("boardlist", board); 
		RequestDispatcher rd = request.getRequestDispatcher("board.jsp");
		rd.forward(request, response); 
	}
	// 게시글 수정
	private void cedit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int bno = Integer.parseInt(request.getParameter("bno"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String actiont = request.getParameter("actiont");
		int check = Integer.parseInt(request.getParameter("check"));
		boardDao.cupdate(bno, title, content);
		
		check++;
		Boards board = boardDao.find(bno);
		boardDao.check(check, bno);
		request.setAttribute("boardlist", board);
		request.setAttribute("bno", bno);
		RequestDispatcher rd = request.getRequestDispatcher("viewboard.jsp" + "?action=" + actiont);
		rd.forward(request, response);
		
	}
	// 댓글 수정
	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String actiont = request.getParameter("actiont");
		String uno = request.getParameter("uno");
		String cuno = request.getParameter("cuno");
		String admin = request.getParameter("admin");
		if (uno.equals(cuno) || admin.equals("1")) {
			Boards board = new Boards();
			
			board.setCno(Integer.parseInt(request.getParameter("cno")));
			board.setCcontent(request.getParameter("ccontent"));
			board.setBno(Integer.parseInt(request.getParameter("bno")));
			
			boolean isUpdated = boardDao.updatecomment(board); //참이면 저장완료
			
			if(isUpdated) {
				
				int bno = Integer.parseInt(request.getParameter("bno"));
				
				Boards board2 = boardDao.find(bno);
				request.setAttribute("boardlist", board2);
				request.setAttribute("bno", bno);
				
				RequestDispatcher rd = request.getRequestDispatcher("viewboard.jsp" + "?acitont=" + actiont);
				rd.forward(request, response);
			}			
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("no.jsp" + "?action=permission&actiont=" + actiont);
			rd.forward(request, response);
		}
	}

	// 댓글 찾기
	private void edit(HttpServletRequest request, HttpServletResponse response) {
		int cno = Integer.parseInt(request.getParameter("cno"));

		Boards board = boardDao.findcomment(cno); 
		
		if(board != null) {
			new Json(response).sendContact(board); //board를 상태와 제이슨 변환해 출력
		}
	}
	// 추천하기 버튼 클릭 시
	private void reco(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int bno = Integer.parseInt(request.getParameter("bno"));
		int reco = Integer.parseInt(request.getParameter("reco"));
		String actiont = request.getParameter("actiont");

		reco++;
		boardDao.reco(reco ,bno);
		Boards board = boardDao.find(bno);
		request.setAttribute("boardlist", board);
		request.setAttribute("bno", bno);
		
		RequestDispatcher rd = request.getRequestDispatcher("viewboard.jsp" + "?acitont=" + actiont);
		rd.forward(request, response);
	}
	// 게시글 열람
	private void view(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int bno = Integer.parseInt(request.getParameter("bno"));
		int check = Integer.parseInt(request.getParameter("check"));
	
		String actiont = request.getParameter("actiont");
		check++;
		boardDao.check(check, bno);
		Boards board = boardDao.find(bno);
		request.setAttribute("boardlist", board);
		request.setAttribute("bno", bno);
	
		RequestDispatcher rd = request.getRequestDispatcher("viewboard.jsp" + "?acitont=" + actiont + "&bno=" + board.getBno());
		rd.forward(request, response);
		
	}

	// 글 db에 등록하기
	private void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		int uno = Integer.valueOf((String) session.getAttribute("uno"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String actiont = request.getParameter("actiont");

		
		boolean issave = boardDao.save(uno, title, content, actiont);
		
		if (!issave) {
			PrintWriter out = response.getWriter();
			out.println("<script>alert('글이 등록되지 않았습니다.');</script>");
		}
	
		response.setCharacterEncoding("UTF-8");
		response.sendRedirect(request.getContextPath() + "/Boards?action=" + actiont);
	}
	// 글쓰기 페이지 이동
	private void write(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("write.jsp");
		rd.forward(request, response);
	}
	// main 페이지 이동
	private void home(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("main.jsp");
		rd.forward(request, response);
	}
	// 게시판 출력
	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
			String action = request.getParameter("action");
			List<Boards> board = boardDao.findAll(action);  
			request.setAttribute("boardlist", board); 
			RequestDispatcher rd = request.getRequestDispatcher("board.jsp");
			rd.forward(request, response); 
		}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
