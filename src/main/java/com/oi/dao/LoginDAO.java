package com.oi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.oi.dto.LoginDTO;
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
}
