package com.oi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.oi.dto.CompleteTodayDTO;
import com.oi.util.DBConn;
import com.oi.util.DBUtil;
import com.oi.util.MyMultipartFile;

public class CompleteTodayDAO {
	private Connection conn = DBConn.getConnection();
	
	public boolean insertCompleteWork(CompleteTodayDTO dto) throws SQLException{
		PreparedStatement pstmt = null;
		String sql;
		boolean result = false;
		try {
			conn.setAutoCommit(false);
			
			sql = "INSERT INTO wotd (wNum, memberId, postCateNum, todayCon, todayDate, todayUpdate, todayBlind) VALUES (seq_wotd.NEXTVAL,?,10,?,SYSDATE,SYSDATE,1) ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getMemberId());
			pstmt.setString(2, dto.getContent());
			
			pstmt.executeUpdate();
			
			pstmt = null;
			sql = null;

			sql = "INSERT INTO  wotdphoto (wpPhotoNum, wNum, wpFile) VALUES (seq_wotd.CURRVAL,seq_wotdPhoto.NEXTVAL, ?)";
			pstmt = conn.prepareStatement(sql);
			
			for( MyMultipartFile file : dto.getFilenames()) {
				pstmt.setString(1, file.getSaveFilename());
				pstmt.executeUpdate();
			}
			
			result = true;
			conn.setAutoCommit(true);
			
		} catch (Exception e) {
			conn.setAutoCommit(true);
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt);
		}
		return result;
	}
	
	public List<CompleteTodayDTO> getData(int offset, int size) {
		List<CompleteTodayDTO> list = new ArrayList<CompleteTodayDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT wNum, memberId, todayCon, TO_CHAR(todayDate,'YYYY-MM-DD')todayDate FROM wotd WHERE todayBlind = 0 ORDER BY todayDate DESC OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, offset);
			pstmt.setInt(2, size);
			
			rs = pstmt.executeQuery();
			///////// 잠시 보류 
			while (rs.next()) {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		return list;
	}
}
