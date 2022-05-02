package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.MarketBean;

public class BookmarkDAO {
	private Connection conn;
	private ResultSet rs;
	private PreparedStatement pstmt;

	public BookmarkDAO() {
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

	public boolean deleteBookmark(String id, String mno) {
		String SQL = "delete from bookmark where uno =(select uno from user where id = ? ) and mno = ?";
		Boolean delete = false;
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			pstmt.setString(2, mno);

			delete = pstmt.executeUpdate() != 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return delete;
	}

	public List<MarketBean> showBookmark(String id) {
		String SQL = "select * from market where mno in (select mno from bookmark where uno = (select uno from user where id = ?))";
		List<MarketBean> marketList = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				MarketBean market = new MarketBean();
				market.setMname(rs.getString("mname"));
				market.setMadd(rs.getString("madd"));
				market.setMno(rs.getInt("mno"));
//				market.setMarketImg("url");
				marketList.add(market);
			}

		} catch (SQLException e) {
			System.out.println("SQL문 에러");
			e.printStackTrace();
		}

		return marketList;
	}
	public String getMarketImg(int mno) {
		String url = "null";
		String SQL = "select url from mimg where mno = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, mno);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				url = rs.getString("url");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return url;
	}
}
