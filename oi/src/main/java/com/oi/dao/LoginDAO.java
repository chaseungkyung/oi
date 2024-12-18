package com.oi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.oi.dto.LoginDTO;
import com.oi.dto.RegisterDTO;
import com.oi.util.DBConn;
import com.oi.util.DBUtil;

public class LoginDAO {

	private Connection conn = DBConn.getConnection();
	
	public LoginDTO getLoginInfo(String id, String pwd) {
		LoginDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT m.memberid, m.nickname, m.userlevel, NVL(profilePhoto,'default')profilePhoto FROM member m LEFT OUTER JOIN memberDetails md ON m.memberId = md.memberId WHERE m.memberid=? AND memberpw = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new LoginDTO();
				dto.setUserId(rs.getString("memberid"));
				dto.setNickname(rs.getString("nickname"));
				dto.setUserLevel(rs.getString("userlevel"));
				dto.setSaveprofile(rs.getString("profilePhoto"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		return dto;
	}
	
	public boolean idCheck(String idtext) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql;
		boolean result = true;
		try {
			sql = "SELECT memberid FROM member WHERE memberid = ? ";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, idtext);
			
			rs = ps.executeQuery();
			
			if(rs.next()) result = false ;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps);
			DBUtil.close(rs);
		}
		return result;
	}
	
	public void insertMember(RegisterDTO dto) throws SQLException {
		PreparedStatement ps = null;
		String sql;
		try {
			conn.setAutoCommit(false);
			sql = "INSERT INTO member (memberid,memberpw,nickname,memebersignup,loginok,userlevel) VALUES (?,?,?,SYSDATE,1,1)";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, dto.getUserid());
			ps.setString(2, dto.getPwd());
			ps.setString(3, dto.getUsername());
			
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
			}
			e.printStackTrace();
			throw e;
		}finally {
			DBUtil.close(ps);
			conn.setAutoCommit(true);
		}
	}
}
