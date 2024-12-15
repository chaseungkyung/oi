package com.oi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.oi.dto.RecordMealDTO;
import com.oi.util.DBConn;
import com.oi.util.DBUtil;

public class RecordMealDAO {
	private Connection conn = DBConn.getConnection();

	public void insertSchedule(RecordMealDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "INSERT INTO mealrecord( dietFoodNum, memberId, dietFoodTime, dietFoodDate, dietFoodUnit, dietFoodName, capacity, kcal "
					+ " VALUES(schedule_seq.NEXTVAL, ?, ?, SYSDATE, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getMemberId());
			pstmt.setString(2, dto.getDietFoodTime());
			pstmt.setString(3, dto.getDietFoodUnit());
			pstmt.setString(4, dto.getDietFoodName());
			pstmt.setString(5, dto.getCapacity());
			pstmt.setInt(6, dto.getKcal());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}

	}

	public List<RecordMealDTO> listMonth(String memberId, String dietFoodDate) {

		List<RecordMealDTO> list = new ArrayList<>();
		RecordMealDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		// String에서 연산하면 메모리 다시 잡기 때문에 append  + 추가의 느낌
		
		try {
			sb.append("SELECT memberId, dietFoodTime, dietFoodDate, dietFoodUnit, capacity, kcal ");
			sb.append(" FROM mealrecord");
			sb.append(" WHERE memberId = ? AND dietFoodDate =? ");
			sb.append(" ORDER BY dietFoodDate,  DESC ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, memberId);
			pstmt.setString(2, dietFoodDate);
			
			rs = pstmt.executeQuery();
			
			while( rs.next()) {
				dto = new RecordMealDTO();		// while 안에 들어가야 개수만큼 객체 생성
				
				dto.setMemberId(rs.getString("memberId"));
				dto.setDietFoodName(rs.getString("dietFoodName"));
				dto.setDietFoodDate(rs.getString("dietFoodDate"));
				dto.setDietFoodUnit(rs.getString("dietFoodUnit"));
				dto.setCapacity(rs.getString("capacity"));
				dto.setKcal(rs.getInt("kcal"));
				
				list.add(dto);
			}
		} catch (Exception e) {
			
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		
		return list;
	}

	
	
	
}
