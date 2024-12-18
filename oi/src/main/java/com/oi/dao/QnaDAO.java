package com.oi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.oi.dto.QnaDTO;
import com.oi.util.DBConn;
import com.oi.util.DBUtil;

public class QnaDAO {
	private Connection conn = DBConn.getConnection();

	// 데이터 추가
	public void insertQuestion(QnaDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "INSERT INTO question(questionNum, memberId, questiondate, questionTitle, questionCon) "
					+ " VALUES (seq_question.NEXTVAL, ?, SYSDATE, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getQuestionId());
			pstmt.setString(2, dto.getQuestionTitle());
			pstmt.setString(3, dto.getQuestionCon());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	}

	// 데이터 개수
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM question";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return result;
	}

	// 검색에서의 데이터 개수
	public int dataCount(String kwd) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) "
					+ " FROM question "
					+ " WHERE INSTR(questionTitle, ?) >= 1 OR INSTR(questionCon, ?) >= 1 OR INSTR(ansContent, ?) >= 1 ";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, kwd);
			pstmt.setString(2, kwd);
			pstmt.setString(3, kwd);

			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return result;
	}

	// 게시물 리스트
	public List<QnaDTO> listQuestion(int offset, int size) {
		List<QnaDTO> list = new ArrayList<QnaDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT q.questionNum, q.memberId questionId, nickName, questionTitle, ");
			sb.append("       TO_CHAR(questionDate, 'YYYY-MM-DD') questionDate, a.memberId answerId ");
			sb.append(" FROM question q ");
			sb.append(" JOIN member m ON q.memberId = m.memberId ");
			sb.append(" left outer join answer a on q.questionNum = a.questionNum ");
			sb.append(" ORDER BY q.questionNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, offset);
			pstmt.setInt(2, size);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				QnaDTO dto = new QnaDTO();

				dto.setQuestionNum(rs.getLong("questionNum"));
				dto.setQuestionId(rs.getString("questionId"));
				dto.setQuestionName(rs.getString("nickName"));
				dto.setQuestionTitle(rs.getString("questionTitle"));
				dto.setQuestionDate(rs.getString("questionDate"));
				dto.setAnswerId(rs.getString("answerId"));

				list.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return list;
	}

	public List<QnaDTO> listQuestion(int offset, int size, String kwd) {
		List<QnaDTO> list = new ArrayList<QnaDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT q.questionNum, q.memberId questionId, nickName, questionTitle, ");
			sb.append("       TO_CHAR(questionDate, 'YYYY-MM-DD') questionDate, a.memberId answerId  ");
			sb.append(" FROM question q ");
			sb.append(" JOIN member m ON q.memberId = m.memberId ");
			sb.append(" left outer join answer a on q.questionNum = a.questionNum ");
			sb.append(" WHERE INSTR(questionTitle, ?) >= 1 OR INSTR(questionCon, ?) >= 1");
			sb.append(" ORDER BY q.questionNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, kwd);
			pstmt.setString(2, kwd);
			pstmt.setInt(3, offset);
			pstmt.setInt(4, size);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				QnaDTO dto = new QnaDTO();

				dto.setQuestionNum(rs.getLong("questionNum"));
				dto.setQuestionId(rs.getString("memberId"));
				dto.setQuestionName(rs.getString("nickName"));
				dto.setQuestionTitle(rs.getString("questionTitle"));
				dto.setQuestionDate(rs.getString("questionDate"));
				dto.setAnswerId(rs.getString("answerId"));

				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return list;
	}

	// 해당 게시물 보기
	public QnaDTO findById(long num) {
		QnaDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT q.questionNum, q.memberId questionId, m.nickName questionName, questionTitle, questionCon, questionDate, "
					+ " a.memberId answerId, m2.nickName answerName, AnsContent, AnsDate "
					+ " FROM question q "
					+ " left outer join answer a on q.questionNum = a.questionNum "
					+ " JOIN member m ON q.memberId=m.memberId "
					+ " LEFT OUTER JOIN member m2 ON a.memberId=m.memberId "
					+ " WHERE q.questionNum = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, num);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new QnaDTO();
				
				dto.setQuestionNum(rs.getLong("questionNum"));
				dto.setQuestionId(rs.getString("questionId"));
				dto.setQuestionName(rs.getString("questionName"));
				dto.setQuestionTitle(rs.getString("questionTitle"));
				dto.setQuestionCon(rs.getString("questionCon"));
				dto.setQuestionDate(rs.getString("questionDate"));
				dto.setAnswerId(rs.getString("answerId"));
				dto.setAnswerName(rs.getString("answerName"));
				dto.setAnsContent(rs.getString("AnsContent"));
				dto.setAnsDate(rs.getString("AnsDate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return dto;
	}

	// 이전글
	public QnaDTO findByPrev(long num, String kwd) {
		QnaDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			if (kwd != null && kwd.length() != 0) {
				sb.append(" SELECT questionNum, questionTitle, memberId ");
				sb.append(" FROM question ");
				sb.append(" WHERE ( questionNum > ? ) ");
				sb.append("     AND ( INSTR(questionTitle, ?) >= 1 OR INSTR(questionCon, ?) >= 1) ");
				sb.append(" ORDER BY num ASC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, num);
				pstmt.setString(2, kwd);
				pstmt.setString(3, kwd);
			} else {
				sb.append(" SELECT questionNum, questionTitle, memberId ");
				sb.append(" FROM question ");
				sb.append(" WHERE questionNum > ? ");
				sb.append(" ORDER BY questionNum ASC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, num);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new QnaDTO();
				
				dto.setQuestionNum(rs.getLong("questionNum"));
				dto.setQuestionId(rs.getString("memberId"));
				dto.setQuestionTitle(rs.getString("questionTitle"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return dto;
	}

	// 다음글
	public QnaDTO findByNext(long num, String kwd) {
		QnaDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			if (kwd != null && kwd.length() != 0) {
				sb.append(" SELECT questionNum, questionTitle, memberId ");
				sb.append(" FROM question ");
				sb.append(" WHERE ( num < ? ) ");
				sb.append("     AND ( INSTR(questionTitle, ?) >= 1 OR INSTR(questionCon, ?) >= 1) ");
				sb.append(" ORDER BY questionNum DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, num);
				pstmt.setString(2, kwd);
				pstmt.setString(3, kwd);
			} else {
				sb.append(" SELECT questionNum, questionTitle, memberId ");
				sb.append(" FROM question ");
				sb.append(" WHERE questionNum < ? ");
				sb.append(" ORDER BY questionNum DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, num);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new QnaDTO();
				
				dto.setQuestionNum(rs.getLong("questionNum"));
				dto.setQuestionId(rs.getString("memberId"));
				dto.setQuestionTitle(rs.getString("questionTitle"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return dto;
	}

	// 게시물 수정
	public void updateQuestion(QnaDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE question SET questionTitle=?, questionCon=? WHERE questionNum=? AND memberId=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getQuestionTitle());
			pstmt.setString(2, dto.getQuestionCon());
			pstmt.setLong(3, dto.getQuestionNum());
			pstmt.setString(4, dto.getQuestionId());
			
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	}
	public void insertAnswer(QnaDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "insert into answer(AnsNum, questionNum, memberId, AnsDate, AnsTitle, AnsContent) values(seq_answer.nextVal, ?, ?, sysDate, ?, ?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, dto.getQuestionNum());
			pstmt.setString(2, dto.getAnswerId());
			pstmt.setString(3, "1");
			pstmt.setString(4, dto.getAnsContent());
			
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}

	}

	public void updateAnswer(QnaDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "update answer set ansContent = ? where questionNum = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getAnsContent());
			pstmt.setLong(2, dto.getQuestionNum());
			
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}

	}
	
	public void deleteAnswer(QnaDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "delete from answer where questionNum = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, dto.getQuestionNum());
			
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}

	}
	
	// 게시물 삭제
	public void deleteQuestion(long num, String userId, int userLevel) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			if (userLevel >= 51) {
				sql = "select ansNum from answer where questionNum=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, num);
				rs  = pstmt.executeQuery();
				
				while(rs.next()) {
					sql = "DELETE FROM answer WHERE ansNum=?";
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setLong(1, rs.getLong("ansNum"));
					
					pstmt.executeUpdate();
				}
				
				sql = "DELETE FROM question WHERE questionNum=?";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, num);
				
				pstmt.executeUpdate();
				
			} else {
				
				sql = "select ansNum from answer a join question q on a.questionNum = q.questionNum where q.questionNum=? and q.memberId = ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, num);
				pstmt.setString(2, userId);
				rs  = pstmt.executeQuery();
				
				while(rs.next()) {
					sql = "DELETE FROM answer WHERE ansNum=?";
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setLong(1, rs.getLong("ansNum"));
					
					pstmt.executeUpdate();
				}
				sql = "DELETE FROM question WHERE questionNum=? AND memberId=?";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, num);
				pstmt.setString(2, userId);
				
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	}
}
