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
					+ " VALUES ( SEQ_MEALRECORD.NEXTVAL, ?, SYSDATE, SYSDATE, ?, ?, ?, ?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getMemberId());
			pstmt.setString(2, dto.getDietFoodTime());
			pstmt.setString(3, dto.getDietFoodName());
			pstmt.setInt(4, dto.getCapacity());
			pstmt.setInt(5, dto.getKcal());

			pstmt.executeUpdate();

			// 자동커밋 되기때문에 하지 않아도 됨
			// 만약 이렇게 하고싶다면 try 문 가장 상단에 conn.setAutoCommit(false)넣고 시작
			// conn.commit();
		} catch (SQLException e) {
			// conn.rollback();
			// 자동커밋을 false 로 하지않았기때문에 rollback 사용할 수 없음
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}

	}

	// 내 모든 식단
	public List<RecordMealDTO> getMealListByMemberId(String memberId) throws SQLException {
		List<RecordMealDTO> mealList = new ArrayList<RecordMealDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append("SELECT dietFoodNum, memberId, TO_CHAR(dietFoodDate,'YYYY-MM-DD') dietFoodDate, dietFoodUnit, dietFoodName, capacity, kcal ");
			sb.append(" FROM mealrecord WHERE memberId = ? ORDER BY dietFoodDate DESC ");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, memberId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				RecordMealDTO dto = new RecordMealDTO();

				dto.setDietFoodNum(rs.getInt("dietFoodNum"));
				dto.setDietFoodTime(rs.getString("dietFoodUnit"));
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

	// 오늘  식단
	
	public List<RecordMealDTO> getMealListToday(String memberId) throws SQLException {
		List<RecordMealDTO> mealList = new ArrayList<RecordMealDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT dietFoodNum, memberId, TO_CHAR(dietFoodDate,'YYYY-MM-DD') dietFoodDate, dietFoodUnit, dietFoodName, capacity, kcal ");
			sb.append(" FROM mealrecord WHERE memberId = ? AND TO_CHAR(SYSDATE,'YYYY-MM-DD') = TO_CHAR(dietFoodDate,'YYYY-MM-DD') ORDER BY dietFoodDate DESC ");
			
			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, memberId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				RecordMealDTO dto = new RecordMealDTO();

				dto.setDietFoodNum(rs.getInt("dietFoodNum"));
				dto.setDietFoodTime(rs.getString("dietFoodUnit"));
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
	
	
	
	public void deleteMeal(String memberId) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = "";
		
		try {
			sql = "DELETE FROM mealrecord WHERE memberId = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	}
}
	
	