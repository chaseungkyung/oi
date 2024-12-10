package com.oi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.oi.dto.NoticeDTO;
import com.oi.util.DBConn;
import com.oi.util.DBUtil;
import com.oi.util.MyMultipartFile;

public class NoticeDAO {
	private Connection conn = DBConn.getConnection();
	
	public void insertNotice(NoticeDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		long seq;
		
		try {
			sql = "SELECT notice_seq.NEXTVAL FROM dual";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			seq = 0;
			
			if(rs.next()) {
				seq = rs.getLong(1);
			}
			dto.setNoticeNum(seq);
			
			rs.close();
			pstmt.close();
			rs = null;
			pstmt = null;
			
			sql = "INSERT INTO notice(noticeNum, memberId, noticeWriteDate, noticeUpdateDate, noticePhoto, noticeTitle, noticeContent) VALUES (?, ?, SYSDATE, SYSDATE, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, dto.getNoticeNum());
			pstmt.setString(2, dto.getMemberId());
			pstmt.setString(3, dto.getNoticePhoto());
			pstmt.setString(4, dto.getNoticeTitle());
			pstmt.setString(5, dto.getNoticeContent());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			if(dto.getListFile().size() != 0) {
				sql = "INSERT INTO noticeFile(noticeFileNum, noticeNum, noticeSaveFileName, noticeOriFileName) VALUES (noticeFile_seq.NEXTVAL, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				
				for(MyMultipartFile mm : dto.getListFile()) {
					pstmt.setLong(1, dto.getNoticeNum());
					pstmt.setString(2, mm.getSaveFilename());
					pstmt.setString(3, mm.getOriginalFilename());
					
					pstmt.executeUpdate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	}
	
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			
		} catch (Exception e) {

		}
		
		return result;
	}
	
	public int dataCount(String schType, String kwd) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		
		return result;
	}
	//공지사항 리스트
	public List<NoticeDTO> listNotice(int offset, int size) {
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			//관리자 이름을 띄워야 할까?? 조인을 어떻게 해야할지 고민해봐야할듯
			sb.append(" SELECT noticeNum, n.memberId, nickName, content, ");
			sb.append("  noticeWriteDate FROM notice n");
			sb.append(" JOIN member m ON n.memberId = m.memberId ");
			sb.append(" ORDER BY noticeNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, offset);
			pstmt.setInt(2, size);

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				NoticeDTO dto = new NoticeDTO();
				
				dto.setNoticeNum(rs.getLong("num"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setNoticeTitle(rs.getString("noticeTitle"));
				dto.setNoticeWriteDate(rs.getDate("noticeWriteDate"));
				
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
	
	public List<NoticeDTO> listNotice(int offset, int size, String schType, String kwd) {
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" SELECT noticeNum, n.memberId, noticeTitle, ");
			sb.append("  noticeWriteDate FROM notice n ");
			sb.append(" JOIN member m ON n.memberId = m.memberId ");
			
			if(schType.equals("all")) {
				sb.append("");
			} else if(schType.equals("noticeWriteDate")){
				kwd = kwd.replaceAll("(\\-|\\/|\\.)", "");
				sb.append("WHERE TO_CHAR(noticeWriteDate, 'YYYYMMDD') = ?");
			} else {
				sb.append(" WHERE INSTR(" + schType + ", ?) >= 1 ");
			}
			sb.append(" ORDER BY noticeNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			if(schType.equals("all")) {
				pstmt.setString(1, kwd);
				pstmt.setString(2, kwd);
				pstmt.setInt(3, offset);
				pstmt.setInt(4, size);
			} else {
				pstmt.setString(1, kwd);
				pstmt.setInt(2, offset);
				pstmt.setInt(3, size);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				NoticeDTO dto = new NoticeDTO();
				
				dto.setNoticeNum(rs.getLong("noticeNum"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setNoticeTitle(rs.getString("noticeTitle"));
				dto.setNoticeWriteDate(rs.getDate("noticeWriteDate"));
				
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
	
	public List<NoticeDTO> listNotice() {
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		return list;
	}
	
	public NoticeDTO findById(long num) {
		NoticeDTO dto = null;
		
		return dto;
		
	}
	
	public NoticeDTO findByPre(long num, String schType, String kwd) {
		NoticeDTO dto = null;
		
		return dto;
		
	}
	
	public NoticeDTO findByNex(long num, String schType, String kwd) {
		NoticeDTO dto = null;
		
		return dto;
		
	}
	
	public void updateNotice(NoticeDTO dto) throws SQLException {
		
	}
	
	public void deleteNotice(long num) throws SQLException {
		
	}
	
	public void deleteNotice(long[] num) throws SQLException {
		
	}

	public List<NoticeDTO> listNoticeFile(long num) {
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		
		return list;
	}
	
	public NoticeDTO findByNoticeFileId(long noticeFileNum) {
		NoticeDTO dto = null;
		
		return dto;
	}
	
	public NoticeDTO deleteNoticeFileId(long noticeFileNum) {
		NoticeDTO dto = null;
		
		return dto;
	}
}
