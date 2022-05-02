package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Boards;
import model.MarketBean;
import model.User;

public class MypageDAO {
	
	private Connection conn;
	private ResultSet rs;
	private PreparedStatement pstmt;
	
	public MypageDAO() {
		String URL = "jdbc:mysql://localhost:3306/tranditionMarket?useSSL=false";
		String id = "root";
		String pw= "1234";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, id, pw);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void closeAll() {
		try {
			if(rs != null ) rs.close();
			if(conn != null) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 회원 정보 조회
	public User showData(String id) {
		User user = null;
		try {
			String SQL = "select * from user where id = ?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				user = new User();
				user.setUserId(rs.getString("id"));
				user.setUserPassword(rs.getString("pw"));
				user.setUserAddress(rs.getString("uadd"));
				user.setUserEmail(rs.getString("email"));
				user.setUserIntro(rs.getString("intro"));
				user.setUserImg(rs.getString("uimg"));
				user.setUserNick(rs.getString("nick"));
				user.setUserName(rs.getString("uname"));
				user.setAdmin(rs.getByte("admin"));
				user.setWarning(rs.getInt("warning"));
				user.setUno(rs.getInt("uno"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	// 회원 탈퇴
	public boolean deleteUser(String id) {
		String SQL = "delete from user where id = ?";
		boolean delete = false;
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			
			delete = pstmt.executeUpdate() != 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return delete;
		
	}
	
	// 내 정보 수정
	public boolean editMyData(User user) {
		boolean edit = false;
		String SQL = "update user set pw = ? , email = ? , uname = ?, nick = ?, uadd = ?, intro = ? where id = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserPassword());
			pstmt.setString(2, user.getUserEmail());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserNick());
			pstmt.setString(5, user.getUserAddress());
			pstmt.setString(6, user.getUserIntro());
			pstmt.setString(7, user.getUserId());
			
			edit = pstmt.executeUpdate() != 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return edit;
	}
	
	// 임시로 마켓 클래스 지정(통합할때 정리 필요)
	public List<MarketBean> getBookmark(String id){
		List<MarketBean> bookmark = new ArrayList<>();
		String SQL = "SELECT * FROM market WHERE mno IN(SELECT mno FROM bookmark WHERE uno = (SELECT uno FROM USER WHERE id = ? )) limit 0, 5";
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,id);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MarketBean market = new MarketBean();
				market.setMno(rs.getInt("mno"));
				market.setMname(rs.getString("mname"));
				market.setMtype(rs.getString("mtype"));
				market.setMadd(rs.getString("madd"));
				market.setPeriod(rs.getString("period"));
				
				bookmark.add(market);
			}
		} catch (SQLException e) {
			System.out.println("SQL문 에러");
			e.printStackTrace();
		}
		return bookmark;
		
	}
	// 내가 작성한 글 목록을 가져오는 메소드 (Board는 임시로 설정한 model)
	public List<Boards> getMyPost(String id){
		List<Boards> myPost = new ArrayList<>();
		String SQL = "SELECT * FROM board WHERE uno = (SELECT uno FROM USER WHERE id = ?) order by bdate desc limit 0, 5";
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Boards board = new Boards();
				String bdate = rs.getString("bdate");
				board.setTitle(rs.getString("title"));
				board.setReco(rs.getInt("reco"));
				board.setCheck(rs.getInt("check"));
				board.setDate(bdate.substring(0, bdate.length()-2));
				myPost.add(board);
			}
		} catch (SQLException e) {
			System.out.println("SQL문 에러");
			e.printStackTrace();
		}
		return myPost;
		
	}
	
	public boolean deletBookmark(String id, String mname) {
		boolean delete = false;
		String SQL = "DELETE FROM bookmark WHERE uno in (SELECT uno FROM USER WHERE id = ?) AND mno in (SELECT mno FROM market WHERE mname= ? )";
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			pstmt.setString(2, mname);
			
			delete = pstmt.executeUpdate() != 0;
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return delete;
	}

	public int checkNick(String userNick) {
		int result = 1;
		String SQL = "SELECT nick from user where nick = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userNick);
			
			rs = pstmt.executeQuery();
			if (rs.next() || userNick.equals("")) {
				result = 1; // 사용 불가능한 닉네임
			} else {
				result = 0; // 사용 가능한 닉네임
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		
		
		return result;
	}

	public boolean deleteProfile(String id) {
		boolean delete = false;
		
		String SQL = "update user set uimg = '/upload/member/noImage.png' where id = ?";
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			
			delete = pstmt.executeUpdate() != 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		
		return delete;
	}

	public int editMyProfile(String id, String userImgPath) {
		int result = 0;
		
		String SQL = "UPDATE user set uimg = ? where id = ? ";
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userImgPath);
			pstmt.setString(2, id);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
