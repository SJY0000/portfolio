package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;


public class UserDAO {
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	

	public UserDAO() {
		
		String dbURL = "jdbc:mysql://localhost:3306/TranditionMarket";
		String dbID = "root";
		String dbPassword ="1234";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
			System.out.println("로딩성공");
		} catch (ClassNotFoundException e) {
			System.out.println("로딩실패");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public int join(User user) {
		String SQL = "insert into user (id, pw, uadd, email, intro, uimg, uname, nick, admin, warning) values (?, ?, ?, ?, ?, ?, ?, ?, 0, 0)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserAddress());
			pstmt.setString(4, user.getUserEmail());
			pstmt.setString(5, user.getUserIntro());
			pstmt.setString(6, user.getUserImg());
			pstmt.setString(7, user.getUserName());
			pstmt.setString(8, user.getUserNick());
			
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.getStackTrace();
			return -1;
		} 
	}
	
	public int login(String userID, String userPassword) {
		String SQL = "SELECT pw from USER where id = ?";
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword))
					return 1;
				else
					return 0;
			}
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2;
	}
	
	public int idCheck(String userID) {
		String SQL = "SELECT count(*) from USER where id = ?";
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				System.out.println("DAO, " + rs.getInt(1));
				if(rs.getInt(1)==1)
					return 0; //아이디 사용불가
				else
					return 1; //아이디 사용가능
			}
			return 2;
			
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int findUno(String userID) {
		String SQL = "SELECT * from USER where id = ?";
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int uno = rs.getInt("uno");
				return uno;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}
}
