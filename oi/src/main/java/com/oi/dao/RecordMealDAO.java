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

	public void insertRecord(RecordMealDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "INSERT INTO mealrecord(dietFoodNum, memberId, dietFoodTime, dietFoodDate, dietFoodUnit, dietFoodName, capacity, kcal)  "
					+ " VALUES ( SEQ_MEALRECORD.NEXTVAL, ?, SYSDATE, SYSDATE, 1, ?, ?, ?)";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getMemberId());
			pstmt.setString(2, dto.getDietFoodName());
			pstmt.setInt(3, dto.getCapacity());
			pstmt.setInt(4, dto.getKcal());

			pstmt.executeUpdate();
			
			// 자동커밋 되기때문에 하지 않아도 됨 
			// 만약 이렇게 하고싶다면 try 문 가장 상단에 conn.setAutoCommit(false)넣고 시작 
		//	conn.commit();
		} catch (SQLException e) {
		//	conn.rollback();
			// 자동커밋을 false 로 하지않았기때문에 rollback 사용할 수 없음 
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		//	conn.setAutoCommit(true);
		}

	}

	
	public List<RecordMealDTO> getMealListByMemberId(String memberId) throws SQLException{
		List<RecordMealDTO> mealList = new ArrayList<RecordMealDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT dietFoodNum, memberId, dietFoodDate, dietFoodName, capacity, kcal ");
			sb.append(" FROM mealrecord WHERE memberId = ? ORDER BY dietFoodDate DESC ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, memberId);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
	            RecordMealDTO dto = new RecordMealDTO();

	            dto.setDietFoodNum(rs.getInt("dietFoodNum"));
	            dto.setDietFoodTime(rs.getString("dietFoodTime"));
	            dto.setDietFoodDate(rs.getString("dietFoodDate"));
	            dto.setDietFoodName(rs.getString("dietFoodName"));
	            dto.setCapacity(rs.getInt("capacity"));
	            dto.setKcal(rs.getInt("kcal"));

	            mealList.add(dto);
	        }
			
			
		} catch (SQLException e) {
	        e.printStackTrace();
	        throw e;
	        
	    } finally {
	        DBUtil.close(rs);
	        DBUtil.close(pstmt);
	    }

	    return mealList;
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
			sb.append(" FROM mealrecord ");
			sb.append(" WHERE memberId = ? AND dietFoodDate =? ");
			sb.append(" ORDER BY dietFoodDate DESC");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, memberId);
			pstmt.setString(2, dietFoodDate);
			
			rs = pstmt.executeQuery();
			
			while( rs.next()) {
				dto = new RecordMealDTO();		// while문 안에 들어가야 개수만큼 객체 생성
				
				dto.setMemberId(rs.getString("memberId"));
				dto.setDietFoodName(rs.getString("dietFoodName"));
				dto.setDietFoodDate(rs.getString("dietFoodDate"));
				dto.setDietFoodUnit(rs.getString("dietFoodUnit"));
				dto.setCapacity(rs.getInt("capacity"));
				dto.setKcal(rs.getInt("kcal"));
				
				list.add(dto);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		
		return list;
	}

	
	
	
}
