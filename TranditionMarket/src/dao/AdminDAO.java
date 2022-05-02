package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.User;

public class AdminDAO {
	private Connection conn;
	private ResultSet rs;
	private PreparedStatement pstmt;
	
	public AdminDAO() {
		String URL = "jdbc:mysql://localhost:3306/tranditionMarket?useSSL=false";
		String id = "root";
		String pw = "1234";
		
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
			if(pstmt != null) pstmt.close();
			if(rs != null) rs.close();
			if(conn != null) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	회원 강퇴 메소드
	public boolean deleteUser(String id) {
		boolean delete = false;
		String SQL = "delete from user where id = ?";
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			
			delete = pstmt.executeUpdate() != 0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return delete;
	}
	
//	회원목록을 가져오는 메소드
	public List<User> showUserList() {
		List<User> userList = new ArrayList<>();
		// 관리자가 아닌 모든 유저 목록 호출
		String SQL = "select * from user where admin = 0";
		
		try {
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				User user = new User();
				user.setUserId(rs.getString("id"));
				user.setUserPassword(rs.getString("pw"));
				user.setUserName(rs.getString("uname"));
				user.setUserNick(rs.getString("nick"));
				user.setUserAddress(rs.getString("uadd"));
				user.setUserEmail(rs.getString("email"));
				user.setUserImg(rs.getString("uimg"));
				user.setWarning(rs.getInt("warning"));
				userList.add(user);
			}
		} catch (SQLException e) {
			System.out.println("sql문 에러");
			e.printStackTrace();
		} 
		
		return userList;
		
	}
	
	// 회원 프로필사진, 닉네임 수정
	public boolean editUserData(User user) {
		boolean edit = false;
		String SQL = "update user set uimg = ? , nick = ?, warning =?  where id = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserImg());
			pstmt.setString(2, user.getUserNick());
			pstmt.setInt(3, user.getWarning());
			pstmt.setString(4, user.getUserId());
			
			edit = pstmt.executeUpdate() != 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return edit;
	}
	

}
