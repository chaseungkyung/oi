package com.oi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.oi.util.DBConn;
import com.oi.util.DBUtil;

public class FindExerciseDAO {
	private Connection conn = DBConn.getConnection();
	
	public Map<String, double[]> listGym(double xPos, double yPos) {
		Map<String, double[]> map = new HashMap<String, double[]>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "select nameGym, coordinateX, coordinateY "
					+ " from findExercise"
					+ " where coordinateX > ? - 0.02 and coordinateX < ? + 0.02"
					+ " and coordinateY > ? - 0.013 and coordinateY < ? + 0.013 ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setDouble(1, xPos);
			pstmt.setDouble(2, xPos);
			pstmt.setDouble(3, yPos);
			pstmt.setDouble(4, yPos);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				map.put(rs.getString(1), new double[] {rs.getDouble(2), rs.getDouble(3)});
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		
		return map;
	}
	
	public String memberAddress(String userId) {
		String address = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "select address "
					+ " from memberDetails"
					+ " where userId = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				address = rs.getString(1); 
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt);
			DBUtil.close(rs);
		}
		
		return address;
	}
	
	
}
