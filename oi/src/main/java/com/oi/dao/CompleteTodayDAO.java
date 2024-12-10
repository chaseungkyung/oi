package com.oi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.oi.dto.CompleteTodayDTO;
import com.oi.dto.wotdfile;
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
			
			wotdfile files = dto.getFile();
			
			sql = "INSERT INTO  wotdphoto (wpPhotoNum, wNum, wpFile) VALUES (seq_wotdPhoto.NEXTVAL,seq_wotd.CURRVAL, ?)";
			pstmt = conn.prepareStatement(sql);
			
			for(String s : files.getSaveFileName()) {
				pstmt.setString(1, s);
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
	
	// 데이터 개수 불러오기
	public int DataCount() {
		int result = 0;
		PreparedStatement pt = null;
		ResultSet rs = null;
		String sql; 
		
		try {
			sql = "SELECT COUNT(*) FROM wotd WHERE todayblind = 1 ";
			pt = conn.prepareStatement(sql);
			
			rs = pt.executeQuery();
			
			if(rs.next()) result = rs.getInt(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pt);
		}
		return result;
	}
	
	// 데이터 불러오기 
	public List<CompleteTodayDTO> getData(int offset, int size) {
		List<CompleteTodayDTO> list = new ArrayList<CompleteTodayDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		
		try {
			sql.append(" SELECT  w.wNum, m.memberId, todayCon,TO_CHAR(todayUpdate,'YYYY-MM-DD')todayUpdate, nickName");
			sql.append(" FROM wotd w JOIN member m ON m.memberId = w.memberId ");
			sql.append(" WHERE todayBlind = 1 ORDER BY todayDate DESC ");
			sql.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, offset);
			pstmt.setInt(2, size);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				CompleteTodayDTO dto = new CompleteTodayDTO();
				
				dto.setWnum(rs.getLong("wNum"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setContent(rs.getString("today"));
				dto.setUpdatedate(rs.getString("todayUpdate"));
				dto.setNickName(rs.getString("nickname"));
				
				list.add(dto);
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
