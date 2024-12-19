package com.oi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.oi.dto.FaqDTO;
import com.oi.util.DBConn;
import com.oi.util.DBUtil;

public class FaqDAO {
	private Connection conn = DBConn.getConnection();
	
	public List<FaqDTO> listCategory(int enabled) {
		List<FaqDTO> list = new ArrayList<FaqDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = " SELECT faqCateNum, faqCateName, orderNo, enabled FROM faqCategory ";
			if(enabled == 1) {
				sql += " WHERE enabled = 1 ";
			}
			sql += " ORDER BY orderNo ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				FaqDTO dto = new FaqDTO();
				dto.setFaqCateNum(rs.getLong("faqCateNum"));
				dto.setFaqCateName(rs.getString("faqCateName"));
				dto.setOrderNo(rs.getInt("orderNo"));
				dto.setEnabled(rs.getInt("enabled"));
				
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
	
	public void insertFaqCategory(FaqDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {				
			sql = "INSERT INTO faqCategory(faqCateNum, faqCateName, orderNo, enabled) VALUES (seq_faqCategory.NEXTVAL, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getFaqCateName());
			pstmt.setInt(2, dto.getOrderNo());
			pstmt.setInt(3, dto.getEnabled());

			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	}
	
	public void updateFaqCategory(FaqDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE faqCategory SET faqCateName=?, enabled=?, orderNo=? WHERE faqCateNum=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getFaqCateName());
			pstmt.setInt(2, dto.getEnabled());
			pstmt.setInt(3, dto.getOrderNo());
			pstmt.setLong(4, dto.getFaqCateNum());
			
			pstmt.executeUpdate();
	
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}  finally {
			DBUtil.close(pstmt);
		}	
	}
	
	public void deleteFaqlCategory(long faqCateNum) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM faqCategory WHERE faqCateNum=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	}
	
	public void insertFaq(FaqDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO faq(faqNum, faqCateNum, memberId, faqTitle, faqContent) VALUES (seq_faq.NEXTVAL, ?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, dto.getFaqCateNum());
			pstmt.setString(2, dto.getMemberId());
			pstmt.setString(3, dto.getFaqTitle());
			pstmt.setString(4, dto.getFaqContent());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	}
	
	public int dataCount(long faqCateNum) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT COUNT(*) FROM faq f JOIN faqCategory c ON f.faqCateNum = c.faqCateNum WHERE c.enabled = 1 ";
			if(faqCateNum != 0) {
				sql += " AND f.faqCateNum = ? ";
			}
			pstmt = conn.prepareStatement(sql);

			if(faqCateNum != 0) {
				pstmt.setLong(1, faqCateNum);
			}
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		return result;
	}
	
	public List<FaqDTO> listFaq(long faqCateNum, int offset, int size) {
		List<FaqDTO> list = new ArrayList<FaqDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" SELECT faqNum, f.memberId, faqTitle, faqContent, f.faqCateNum, faqCateName ");
			sb.append(" FROM faq f JOIN member m ON f.memberId = m.memberId ");
			sb.append(" JOIN faqCategory c ON f.faqCateNum = c.faqCateNum ");
			sb.append(" WHERE c.enabled = 1 ");

			if(faqCateNum != 0) {
				sb.append("  AND f.faqCateNum = ? ");
			}
			sb.append(" ORDER BY faqNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			if(faqCateNum != 0) {
				pstmt.setLong(1, faqCateNum);
				pstmt.setInt(2, offset);
				pstmt.setInt(3, size);
			} else {
				pstmt.setInt(1, offset);
				pstmt.setInt(2, size);
			}
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				FaqDTO dto = new FaqDTO();
				
				dto.setFaqNum(rs.getLong("faqNum"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setFaqTitle(rs.getString("faqTitle"));
				dto.setFaqContent(rs.getString("faqContent"));
				dto.setFaqCateNum(rs.getLong("faqCateNum"));
				dto.setFaqCateName(rs.getString("faqCateName"));
				
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
	
	public int dataCount(long faqCateNum, String schType, String kwd) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
	
		try {
			sql = "SELECT COUNT(*) FROM faq f JOIN faqCategory c ON f.faqCateNum = c.faqCateNum WHERE c.enabled = 1 ";
			
			if(schType.equals("all")) {
				sql += " AND ( INSTR(faqTitle, ?) >= 1 OR INSTR(faqContent, ?) >= 1 )";
			} else {
				sql += " AND INSTR(" + schType + ", ?) >= 1 ";
			}
			if(faqCateNum != 0) {
				sql += " AND f.faqCateNum = ? ";
			}
			
			pstmt = conn.prepareStatement(sql);
			
			if(schType.equals("all")) {
				pstmt.setString(1, kwd);
				pstmt.setString(2, kwd);
				if(faqCateNum != 0) {
					pstmt.setLong(3, faqCateNum);
				}
			} else {
				pstmt.setString(1, kwd);
				if(faqCateNum != 0) {
					pstmt.setLong(2, faqCateNum);
				}
			}
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		return result;
	}
	
	public List<FaqDTO> listFaq(long faqCateNum, int offset, int size, String schType, String kwd) {
		List<FaqDTO> list = new ArrayList<FaqDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" SELECT faqNum, f.memberId, faqTitle, faqContent, f.faqCateNum, faqCateName ");
			sb.append(" FROM faq f JOIN member m ON f.memberId = m.memberId ");
			sb.append(" JOIN faqCategory c ON f.faqCateNum = c.faqCateNum ");
			sb.append(" WHERE c.enabled = 1 ");
			
			if(schType.equals("all")) {
				sb.append(" AND (INSTR(faqTitle, ?) >= 1 OR INSTR(faqContent, ?) >= 1) ");
			} else {
				sb.append(" AND INSTR(" + schType + ", ?) >= 1");
			}
			if(faqCateNum != 0) {
				sb.append("  AND f.faqCateNum = ? ");
			}
			sb.append(" ORDER BY faqNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
		
			pstmt = conn.prepareStatement(sb.toString());
			
			if(schType.equals("all")) {
				pstmt.setString(1, kwd);
				pstmt.setString(2, kwd);
				if(faqCateNum != 0) {
					pstmt.setLong(3, faqCateNum);
					pstmt.setInt(4, offset);
					pstmt.setInt(5, size);
				} else {
					pstmt.setInt(3, offset);
					pstmt.setInt(4, size);
				} 
			} else {
				pstmt.setString(1, kwd);
				if(faqCateNum != 0) {
					pstmt.setLong(2, faqCateNum);
					pstmt.setInt(3, offset);
					pstmt.setInt(4, size);
				} else { 
					pstmt.setInt(2, offset);
					pstmt.setInt(3, size);
				}
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				FaqDTO dto = new FaqDTO();
				
				dto.setFaqNum(rs.getLong("faqNum"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setFaqTitle(rs.getString("faqTitle"));
				dto.setFaqContent(rs.getString("faqContent"));
				dto.setFaqCateNum(rs.getLong("faqCateNum"));
				dto.setFaqCateName(rs.getString("faqCateName"));
				
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
	
	public FaqDTO findById(long faqNum) {
		FaqDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT f.faqNum, f.memberId, faqTitle, faqContent, f.faqCateNum, faqCateName " 
				+ " FROM faq f JOIN member m ON f.memberId = m.memberId "	
				+ " JOIN faqCategory c ON f.faqCateNum = c.faqCateNum WHERE f.faqNum = ? "	;
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, faqNum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new FaqDTO();
				
				dto.setFaqNum(rs.getLong("faqNum"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setFaqTitle(rs.getString("faqTitle"));
				dto.setFaqContent(rs.getString("faqContent"));
				dto.setFaqCateNum(rs.getLong("faqCateNum"));
				dto.setFaqCateName(rs.getString("faqCateName"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		return dto;
	}
	
	public void updateFaq(FaqDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE faq SET faqTitle=?, faqContent=?, faqCateNum=? WHERE faqNum=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getFaqTitle());
			pstmt.setString(2, dto.getFaqContent());
			pstmt.setLong(3, dto.getFaqCateNum());
			pstmt.setLong(4, dto.getFaqNum());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	}
	
	public void deleteFaq(long faqNum) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM faq WHERE faqNum=?";
					
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, faqNum);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	}
}
