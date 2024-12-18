package com.oi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.oi.dto.BodyRecordDTO;
import com.oi.dto.MemberDTO;
import com.oi.dto.MemberDetailsDTO;
import com.oi.dto.MyPageCommentDTO;
import com.oi.dto.MyPageGoodsDTO;
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
		    		      "FROM OI.member m " +
		    		      "LEFT JOIN OI.memberDetails md ON m.memberId = md.memberId " +
		    		      "LEFT JOIN OI.bodyRecord br ON m.memberId = br.memberId " +
		    		      "WHERE m.memberId = ?";

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
		            memberDetails.setName(rs.getString("name"));
		            memberDetails.setBirth(rs.getString("birth"));
		            memberDetails.setEmail(rs.getString("email"));
		            memberDetails.setAddress(rs.getString("address"));
		            memberDetails.setAddressNum(rs.getString("addressNum"));
		            memberDetails.setProfilePhoto(rs.getString("profilePhoto"));

		            dto.setMemberDetails(memberDetails);

		            // BodyRecordDTO 값 설정
		            BodyRecordDTO bodyRecord = new BodyRecordDTO();
		            bodyRecord.setGender(rs.getString("gender"));
		            bodyRecord.setHeight(rs.getInt("height"));
		            bodyRecord.setWeight(rs.getInt("weight"));
		            bodyRecord.setBodyRecordDate(rs.getString("bodyRecordDate"));
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
		
		public void updateMemberInfo(MemberDTO member) throws SQLException {
		    PreparedStatement pstmt = null;
		    String sql = null;

		    try {
		        // Member
		        sql = "UPDATE member SET memberPw = ?, nickName = ? WHERE memberId = ?";
		        pstmt = conn.prepareStatement(sql);
		        pstmt.setString(1, member.getMemberPw());
		        pstmt.setString(2, member.getNickName());
		        pstmt.setString(3, member.getMemberId());
		        pstmt.executeUpdate();
		        DBUtil.close(pstmt);

		        // MemberDetails
		        sql = "UPDATE memberDetails SET address = ?, addressNum = ?, email = ?, profilePhoto = ? WHERE memberId = ?";
		        pstmt = conn.prepareStatement(sql);
		        pstmt.setString(1, member.getMemberDetails().getAddress());
		        pstmt.setInt(2, Integer.parseInt(member.getMemberDetails().getAddressNum())); // addressNum이 숫자라 가정
		        pstmt.setString(3, member.getMemberDetails().getEmail());
		        pstmt.setString(4, member.getMemberDetails().getProfilePhoto());
		        pstmt.setString(5, member.getMemberId());
		        pstmt.executeUpdate();
		        DBUtil.close(pstmt);

		        // BodyRecord
		        sql = "UPDATE bodyRecord SET height = ?, weight = ?, bodyFat = ?, bodyMuscle = ?, bodyRecordDate = ? WHERE memberId = ?";
		        pstmt = conn.prepareStatement(sql);
		        pstmt.setInt(1, member.getBodyRecord().getHeight());
		        pstmt.setInt(2, member.getBodyRecord().getWeight());
		        pstmt.setInt(3, member.getBodyRecord().getBodyFat());
		        pstmt.setInt(4, member.getBodyRecord().getBodyMuscle());
		        pstmt.setString(5, (member.getBodyRecord().getBodyRecordDate()));
		        pstmt.setString(6, member.getMemberId()); // memberId 설정
		        
		        pstmt.executeUpdate();

		    } catch (SQLException e) {
		        e.printStackTrace();
		        throw e;
		    } finally {
		        DBUtil.close(pstmt);
		    }
		}
		
		// 찜한 게시물 보기
		public List<MyPageGoodsDTO> getMyPageList(String memberId) {
		    List<MyPageGoodsDTO> mList = new ArrayList<>();
		    PreparedStatement pstmt = null;
		    ResultSet rs = null;
		    String sql = null;

		    try {
		        sql = "SELECT g.goodsListNum, g.goodsName, g.goodsPrice, g.goodsExp, g.goodsDate " +
		              "FROM likePost lp " +
		              "JOIN goods g ON lp.listNum = g.goodsListNum " +
		              "WHERE lp.memberId = ?";

		        pstmt = conn.prepareStatement(sql);
		        pstmt.setString(1, memberId);

		        rs = pstmt.executeQuery();

		        while (rs.next()) {
		            MyPageGoodsDTO dto = new MyPageGoodsDTO();
		            dto.setGoodsListNum(rs.getInt("goodsListNum"));
		            dto.setGoodsName(rs.getString("goodsName"));
		            dto.setGoodsPrice(rs.getInt("goodsPrice"));
		            dto.setGoodsExp(rs.getString("goodsExp"));
		            dto.setGoodsDate(rs.getString("goodsDate"));

		            mList.add(dto);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        DBUtil.close(rs);
		        DBUtil.close(pstmt);
		    }
		    return mList;
		}
		
		// 오운완 댓글
		public List<MyPageCommentDTO> getWotdComments(String memberId) {
		    List<MyPageCommentDTO> commentList = new ArrayList<>();
		    PreparedStatement pstmt = null;
		    ResultSet rs = null;
		    String sql = null;

		    try {
		        sql = "SELECT m.memberId, c.wComCon, c.wComDate " +
		              "FROM wotdComment c " +
		              "JOIN member m ON c.memberId = m.memberId " +
		              "WHERE c.memberId = ?";

		        pstmt = conn.prepareStatement(sql);
		        pstmt.setString(1, memberId);

		        rs = pstmt.executeQuery();

		        while (rs.next()) {
		        	MyPageCommentDTO dto = new MyPageCommentDTO();
		            dto.setMemberId(rs.getString("memberId"));
		            dto.setCommentContent(rs.getString("wComCon"));
		            dto.setCommentDate(rs.getString("wComDate"));

		            commentList.add(dto);
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        DBUtil.close(rs);
		        DBUtil.close(pstmt);
		    }

		    return commentList;
		}
		
		// 중고거래 댓글
		public List<MyPageCommentDTO> getGoodsComments(String memberId) {
		    List<MyPageCommentDTO> commentList = new ArrayList<>();
		    PreparedStatement pstmt = null;
		    ResultSet rs = null;
		    String sql = null;

		    try {
		        sql = "SELECT m.memberId, c.gcComCon, c.gcInsertNum, g.goodsName " +
		              "FROM goodsComment c " +
		              "JOIN goods g ON c.goodsListNum = g.goodsListNum " +
		              "JOIN member m ON c.memberId = m.memberId " +
		              "WHERE c.memberId = ?";

		        pstmt = conn.prepareStatement(sql);
		        pstmt.setString(1, memberId);

		        rs = pstmt.executeQuery();

		        while (rs.next()) {
		        	MyPageCommentDTO dto = new MyPageCommentDTO();
		            dto.setMemberId(rs.getString("memberId"));
		            dto.setCommentContent(rs.getString("gcComCon"));
		            dto.setCommentDate(rs.getString("gcInsertNum"));
		            dto.setPostTitle(rs.getString("goodsName"));

		            commentList.add(dto);
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        DBUtil.close(rs);
		        DBUtil.close(pstmt);
		    }

		    return commentList;
		}
	}

