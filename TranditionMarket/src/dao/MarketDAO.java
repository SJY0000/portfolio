package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.MarketBean;
import model.MarketImgBean;

public class MarketDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public MarketDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tranditionmarket?useSSL=false", "root", "1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeAll() {
		try {
			if (rs != null)
				rs.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insert(List<MarketBean> mbs) {

		String SQL = "insert into market(mname, mtype, madd, period, lat, longi, store, object, toilet, parking) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			pstmt = conn.prepareStatement(SQL);
			for (int i = 0; i < mbs.size(); i++) {
				MarketBean mb = mbs.get(i);

				pstmt.setString(1, mb.getMname());
				pstmt.setString(2, mb.getMtype());
				pstmt.setString(3, mb.getMadd());
				pstmt.setString(4, mb.getPeriod());
				pstmt.setString(5, mb.getLat());
				pstmt.setString(6, mb.getLongi());
				pstmt.setString(7, mb.getStore());
				pstmt.setString(8, mb.getObject());
				pstmt.setInt(9, mb.getToilet());
				pstmt.setInt(10, mb.getParking());
				pstmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete() {
		String SQL = "delete from market";
		try {
			pstmt = conn.prepareStatement(SQL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<MarketBean> search(String searchKW) {
		List<MarketBean> searchedMK = new ArrayList<>();
		boolean skip = false;

		String SQL = "select * from market where mname like ?";

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, "%" + searchKW + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				skip = false;
				MarketBean mb = new MarketBean();
				mb.setMno(rs.getInt(1));
				mb.setMname(rs.getString(2));
				mb.setMtype(rs.getString(3));
				mb.setMadd(rs.getString(4));
				mb.setPeriod(rs.getString(5));
				mb.setLat(rs.getString(6));
				mb.setLongi(rs.getString(7));
				mb.setStore(rs.getString(8));
				mb.setObject(rs.getString(9));
				mb.setToilet(rs.getInt(10));
				mb.setParking(rs.getInt(11));
				for (int i = 0; i < searchedMK.size(); i++) {
					if (searchedMK.get(i).getMname().equals(mb.getMname())) {
						skip = true;
					}
					if (searchedMK.get(i).getMadd().equals(mb.getMadd())) {
						skip = true;
					}
				}
				if (!skip)
					searchedMK.add(mb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return searchedMK;
	}
	
	public MarketBean search(int mno) {
		MarketBean viewMK = new MarketBean();
		String SQL = "select * from market where mno = ?";

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, mno);

			rs = pstmt.executeQuery();

			while (rs.next()) {				
				viewMK.setMno(rs.getInt(1));
				viewMK.setMname(rs.getString(2));
				viewMK.setMtype(rs.getString(3));
				viewMK.setMadd(rs.getString(4));
				viewMK.setPeriod(rs.getString(5));
				viewMK.setLat(rs.getString(6));
				viewMK.setLongi(rs.getString(7));
				viewMK.setStore(rs.getString(8));
				viewMK.setObject(rs.getString(9));
				viewMK.setToilet(rs.getInt(10));
				viewMK.setParking(rs.getInt(11));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return viewMK;
	}
	
	public List<MarketImgBean> importIMG(MarketBean mb) {
		List<MarketImgBean> searchedMKIMG = new ArrayList<>();
		
		String SQL = "select * from mimg where mno = ?";
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, mb.getMno());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MarketImgBean mib = new MarketImgBean();
				mib.setMino(rs.getInt(1));
				mib.setUrl(rs.getString(2));
				mib.setMno(rs.getInt(3));
				searchedMKIMG.add(mib);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return searchedMKIMG;
	}
	
	public MarketImgBean getImg(int mino) {
		MarketImgBean viewImg = new MarketImgBean();
		
		String SQL = "select * from mimg where mino = ?";
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, mino);
			
			rs = pstmt.executeQuery(); // rs 객체에 검색된 모든 row 를 얻음
			
			while(rs.next()) { // rs 객체의 다음 row 를 얻음
				
				viewImg.setUrl(rs.getString(2)); // 해당 row의 2번째 column을 string 타입으로 얻음
				viewImg.setMno(rs.getInt(3)); // 해당 row의 3번째 column을 int 타입으로 얻음
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return viewImg;
	}
}
