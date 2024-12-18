package com.oi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.oi.dto.CompleteTodayDTO;
import com.oi.dto.WotdCommentDTO;
import com.oi.dto.Wotdfile;
import com.oi.util.DBConn;
import com.oi.util.DBUtil;

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
			
			Wotdfile files = dto.getFile();
			
			sql = "INSERT INTO  wotdphoto (wpPhotoNum, wNum, wpFile) VALUES (seq_wotdPhoto.NEXTVAL,seq_wotd.CURRVAL, ?)";
			pstmt = conn.prepareStatement(sql);
			
			for(String s : files.getSaveFileName()) {
				pstmt.setString(1, s);
				pstmt.executeUpdate();
			}
			
			result = true;
			conn.commit();
			
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.setAutoCommit(true);
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
	
	// 데이터 개수 byid
	public int DataCount(String memberid) {
		int result = 0;
		PreparedStatement pt = null;
		ResultSet rs = null;
		String sql; 
		
		try {
			sql = "SELECT COUNT(*) FROM wotd WHERE todayblind = 1 AND memberid = ?";
			pt = conn.prepareStatement(sql);
			
			pt.setString(1, memberid);
			
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
			sql.append(" SELECT  w.wNum, m.memberId, todayCon,TO_CHAR(todayUpdate,'YYYY-MM-DD')todayUpdate, nickName, NVL(profilePhoto,'default')profilePhoto");
			sql.append(" FROM wotd w JOIN member m ON m.memberId = w.memberId ");
			sql.append(" LEFT OUTER JOIN memberdetails md ON m.memberId = md.memberId ");
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
				dto.setContent(rs.getString("todayCon"));
				dto.setUpdatedate(rs.getString("todayUpdate"));
				dto.setNickName(rs.getString("nickname"));
				dto.setProfilePhoto(rs.getString("profilePhoto"));
				dto.setLoved(likeCount(rs.getLong("wNum")));
				dto.setCommentcount(commentCount(rs.getLong("wNum")));
				
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

	// 아이디 별 게시글 받기 
	public List<CompleteTodayDTO> getDataById(int offset, int size, String memberId){
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<CompleteTodayDTO> list = new ArrayList<CompleteTodayDTO>();
		String sql;
		try {
			sql = "SELECT wnum, todayCon,TO_CHAR(todayUpdate,'YYYY-MM-DD')todayUpdate FROM wotd WHERE memberid = ? AND todayBlind = 1 ORDER BY todayUpdate DESC OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, memberId);
			ps.setLong(2, offset);
			ps.setLong(3, size);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				CompleteTodayDTO dto = new CompleteTodayDTO();
				
				dto.setWnum(rs.getLong("wnum"));
				dto.setContent(rs.getString("todayCon"));
				dto.setUpdatedate(rs.getString("todayUpdate"));
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps);
			DBUtil.close(rs);
		}
		
		return list;
	}
	
	// 파일 불러오기 by id 
	public void getFilesById(CompleteTodayDTO dto, String memberId) {
		List<String> list = new ArrayList<String>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql; 
		
		try {
			sql = "SELECT wp.wnum, wp.wpfile FROM wotdphoto wp JOIN wotd w ON w.wnum = wp.wnum\r\n"
					+ "WHERE w.memberid = ? AND wp.wnum = ?  FETCH FIRST 1 ROWS ONLY";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, memberId);
			ps.setLong(2, dto.getWnum());
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				dto.getFile().setParentnum(rs.getLong("wNum"));
				list.add(rs.getString("wpfile"));
				dto.getFile().setSaveFileName(list.toArray(new String [list.size()]));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
	}
	
	
	// 파일 불러오기 
	public void getFiles(CompleteTodayDTO dto) {
		List<String> list = new ArrayList<String>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql; 
		
		try {
			sql = "SELECT wpPhotoNum, wNum, wpFile FROM wotdphoto WHERE wnum = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setLong(1, dto.getWnum());
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				dto.getFile().setFile(rs.getLong("wpPhotoNum"),rs.getString("wpFile"));
				dto.getFile().setParentnum(rs.getLong("wNum"));
				list.add(rs.getString("wpFile"));
			}
			dto.getFile().setSaveFileName(list.toArray(new String [list.size()]));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
	}
	
	// 좋아요 개수 알아내기
	public int likeCount(long wnum) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = 0;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*),0) FROM wotdlike WHERE wNum = ? " ;
			ps = conn.prepareStatement(sql);
			ps.setLong(1, wnum);
			rs = ps.executeQuery();
			
			if(rs.next()) result = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
		return result;
	}
	
	// 댓글 개수 알아내기
	public int commentCount(long wnum) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = 0;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*),0) FROM wotdcomment WHERE wNum = ?  AND wotdParCom = 0 " ;
			ps = conn.prepareStatement(sql);
			ps.setLong(1, wnum);
			rs = ps.executeQuery();
			
			if(rs.next()) result = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
		return result;
	}
	
	// 게시물에대한 좋아요 여부 
	public boolean likeOrNot(String userId, long wnum) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		boolean result = false;
		try {
			sql = "SELECT * FROM wotdlike WHERE memberId =? AND wnum = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			pstmt.setLong(2, wnum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) result = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		
		return result;
	}
	
	// 게시글에대한 좋아요 insert
	public void insertLike(long num, String userId) throws SQLException{
		PreparedStatement pstmt = null;
		String sql;
		try {
			sql = "INSERT INTO wotdLike (memberId, wNum) VALUES (?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			pstmt.setLong(2, num);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	}
	
	// 게시글에대한 좋아요 취소 
	public void deleteLike(long num, String userId) throws SQLException{
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM wotdlike WHERE wNum = ? AND memberId = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, num);
			pstmt.setString(2, userId);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	}
	
	// 게시물 알아내기
	public CompleteTodayDTO findByNum(long wnum) {
		CompleteTodayDTO dto = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "SELECT w.wNum, wpfile, todaycon,nickname,NVL(profilePhoto,'default')profilePhoto FROM wotd w JOIN wotdphoto ph ON ph.wnum = w.wnum JOIN member m ON m.memberid= w.memberid LEFT OUTER JOIN memberdetails md ON md.memberid = w.memberid  WHERE w.wnum = ? ";
			
			ps = conn.prepareStatement(sql);
			
			ps.setLong(1, wnum);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				dto = new CompleteTodayDTO();
				
				dto.setWnum(rs.getLong("wNum"));
				dto.setContent(rs.getString("todaycon"));
				dto.setNickName(rs.getString("nickname"));
				dto.setProfilePhoto(rs.getString("profilephoto"));
				getFiles(dto);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps);
			DBUtil.close(rs);
		}
		return dto;
	}
	
	// 댓글 등록 
	public void insertComment (CompleteTodayDTO dto) {
		PreparedStatement ps = null;
		String sql;
		try {
			sql = " INSERT INTO wotdcomment (wcomnum,wnum,memberid,postcatenum,wcomcon,blindcnt,wotdparcom,wcomdate,wcomupdate) VALUES (seq_wotdComment.NEXTVAL,?,?,11,?,1,0,SYSDATE,SYSDATE)";
			
			ps = conn.prepareStatement(sql);
			
			ps.setLong(1, dto.getWnum());
			ps.setString(2, dto.getMemberId());
			ps.setString(3, dto.getContent());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps);
		}
	}
	
	// 해당게시물에 대한 댓글 목록 
	// wotdparcom = 0 인애들이 부모 
	public void getComments(CompleteTodayDTO dto) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT wcomnum, NVL(profilePhoto,'default')profilePhoto ,md.memberid,nickname, wcomcon, wotdparcom, TO_CHAR(wcomupdate,'YYYY-MM-DD')wcomupdate FROM wotdcomment c JOIN member m ON m.memberid = c.memberid LEFT OUTER JOIN memberdetails md ON md.memberid = c.memberid WHERE wnum = ? AND wotdparcom = 0 ";
			ps = conn.prepareStatement(sql);
			
			ps.setLong(1, dto.getWnum());
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				WotdCommentDTO obj = new WotdCommentDTO();
				
				obj.setCommentseq(rs.getInt("wcomnum"));
				obj.setMemberId(rs.getString("memberid"));
				obj.setMemberPhoto(rs.getString("profilephoto"));
				obj.setWriternickname(rs.getString("nickname"));
				obj.setParent(rs.getLong("wotdparcom"));
				obj.setWrittenDate(rs.getString("wcomupdate"));
				obj.setInnercontent(rs.getString("wcomcon"));
				
				dto.setComments(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
	}
	
	
	// update 
	public void updateArticle(CompleteTodayDTO dto) throws SQLException {
		PreparedStatement ps = null;
		String sql ;
		try {
			conn.setAutoCommit(false);
			
			sql = "UPDATE wotd SET todaycon = ? , todayUpdate = SYSDATE	WHERE wnum = ? ";
			
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, dto.getContent());
			ps.setLong(2, dto.getWnum());
			
			ps.executeUpdate();
			
			ps = null;
			sql = null;
			
			sql = "INSERT INTO wotdphoto (wpphotonum,wnum,wpfile) VALUES (seq_wotdPhoto.nextval,?,?)";
			
			ps = conn.prepareStatement(sql);
			
			for(String name : dto.getFile().getSaveFileName()) {
				ps.setLong(1, dto.getWnum());
				ps.setString(2, name );
				
				ps.executeUpdate();
			}
			
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
			}
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(ps);
			conn.setAutoCommit(true);
		}
	}
	
	// 삭제 
	public void	deleteArticle(long wnum) throws SQLException {
		PreparedStatement ps = null;
		
		try {
			conn.setAutoCommit(false);
			
			String []sqls = {"DELETE FROM wotdlike WHERE wnum = ?",
					"DELETE FROM wotdphoto WHERE wnum = ?",
					"DELETE FROM wotdComment WHERE wnum = ?",
					"DELETE FROM wotd WHERE wnum = ?"} ;
			
			for(String sql : sqls ) {
				ps = conn.prepareStatement(sql);
				ps.setLong(1, wnum);
				ps.executeUpdate();
				ps = null;
			}
			
			conn.commit();
		
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
			}
			e.printStackTrace();
			throw e;
		} finally {
			conn.setAutoCommit(true);
			DBUtil.close(ps);
		}
	}
	
}
