package com.oi.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.oi.dto.BodyRecordDTO;
import com.oi.dto.MemberDTO;
import com.oi.dto.MemberDetailsDTO;
import com.oi.util.DBConn;
import com.oi.util.DBUtil;

	public class MyPageDAO {
		private Connection conn = DBConn.getConnection();
		
		public MemberDTO getDetails(String memberId) {
		    MemberDTO dto = null;
		    PreparedStatement pstmt = null;
		    ResultSet rs = null;
		    String sql;

		    try {
		        sql = "SELECT " +
		                "m.memberId, " +
		                "m.memberPw, " +
		                "m.nickName, " +
		                "TO_CHAR(md.birth, 'YYYY-MM-DD') AS birth, " +
		                "md.email, " +
		                "md.address, " +
		                "md.addressNum, " +
		                "md.name, " +
		                "md.profilePhoto, " +
		                "br.gender, " +
		                "br.height, " +
		                "br.weight, " +
		                "br.bodyRecordDate, " +
		                "br.bodyFat, " +
		                "br.bodyMuscle " +
		                "FROM " +
		                "Member m " +
		                "INNER JOIN " +
		                "MemberDetails md ON m.memberId = md.memberId " +
		                "INNER JOIN " +
		                "BodyRecord br ON m.memberId = br.memberId " +
		                "WHERE " +
		                "m.memberId = ?";

		        pstmt = conn.prepareStatement(sql);
		        pstmt.setString(1, memberId);

		        rs = pstmt.executeQuery();

		        if (rs.next()) {
		            dto = new MemberDTO();
		            dto.setMemberId(rs.getString("memberId"));
		            dto.setMemberPw(rs.getString("memberPw"));
		            dto.setNickName(rs.getString("nickName"));

		            // MemberDetailsDTO 값 설정
		            MemberDetailsDTO memberDetails = new MemberDetailsDTO();
		            memberDetails.setBirth(rs.getString("birth"));
		            memberDetails.setEmail(rs.getString("email"));
		            memberDetails.setAddress(rs.getString("address"));
		            memberDetails.setAddressNum(rs.getString("addressNum"));
		            memberDetails.setName(rs.getString("name"));
		            memberDetails.setProfilePhoto(rs.getString("profilePhoto"));

		            dto.setMemberDetails(memberDetails);

		            // BodyRecordDTO 값 설정
		            BodyRecordDTO bodyRecord = new BodyRecordDTO();
		            bodyRecord.setGender(rs.getString("gender"));
		            bodyRecord.setHeight(rs.getInt("height"));
		            bodyRecord.setWeight(rs.getInt("weight"));
		            Date bodyRecordDate = rs.getDate("bodyRecordDate");
		            if (bodyRecordDate != null) {
		                bodyRecord.setBodyRecordDate(bodyRecordDate.toLocalDate());
		            }
		            bodyRecord.setBodyFat(rs.getInt("bodyFat"));
		            bodyRecord.setBodyMuscle(rs.getInt("bodyMuscle"));

		            dto.setBodyRecord(bodyRecord);
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

