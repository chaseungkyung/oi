package com.oi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.oi.dto.MemberDTO;
import com.oi.util.DBConn;
import com.oi.util.DBUtil;

public class MyPageDAO {
	private Connection conn = DBConn.getConnection();
	
	public MemberDTO getNicknameById(String id) {
		MemberDTO dto = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql;
        
        try {
        	sql = "SELECT nickname FROM member WHERE memberid = ?";
        	 pstmt = conn.prepareStatement(sql);
             pstmt.setString(1, id);

             rs = pstmt.executeQuery();

             if (rs.next()) {
                 dto = new MemberDTO();
                 dto.setNickName(rs.getString("nickname"));
             }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
            DBUtil.close(pstmt);
		}
        	return dto;
	}
}
