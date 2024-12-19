package com.oi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.oi.dto.RecordWorkDTO;
import com.oi.util.DBConn;
import com.oi.util.DBUtil;

public class RecordWorkDAO {
	private Connection conn = DBConn.getConnection();
    PreparedStatement pstmt = null;
    String sql = null;

    public void insertRecordWork(RecordWorkDTO dto) throws SQLException {
        try {  
            sql = "INSERT INTO exerciseRecord (" +
                  "exerciseNum, memberId, exerciseDate, exerciseName, exerciseContent, " +
                  "exerciseStart, exerciseEnd, exercisecnt, exerciseunit) " +
                  "VALUES (exercise_seq.NEXTVAL, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?, TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS'), TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS'), ?, ?)";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, dto.getMemberId());
            pstmt.setString(2, dto.getExerciseDate());
            pstmt.setString(3, dto.getExerciseName());
            pstmt.setString(4, dto.getExerciseContent());

            // 'T'을 공백으로 대체하고 ':00' 초를 추가하여 형식 일치
            String exerciseStartFormatted = dto.getExerciseStart().replace("T", " ") + ":00";
            String exerciseEndFormatted = dto.getExerciseEnd().replace("T", " ") + ":00";

            pstmt.setString(5, exerciseStartFormatted);
            pstmt.setString(6, exerciseEndFormatted);
            pstmt.setInt(7, dto.getExercisecnt());
            pstmt.setString(8, dto.getExerciseunit()); // 수정: setInt -> setString

            pstmt.executeUpdate();
        
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            DBUtil.close(pstmt);
        }
    }
    
    public List<RecordWorkDTO> listRecordWorks(String memberId) throws SQLException {
        List<RecordWorkDTO> recordList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM exerciseRecord WHERE memberId = ? ORDER BY exerciseDate DESC";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                RecordWorkDTO dto = new RecordWorkDTO();
                dto.setExerciseNum(rs.getInt("exerciseNum"));
                dto.setMemberId(rs.getString("memberId"));
                dto.setExerciseDate(rs.getString("exerciseDate"));
                dto.setExerciseName(rs.getString("exerciseName"));
                dto.setExerciseContent(rs.getString("exerciseContent"));
                dto.setExerciseStart(rs.getString("exerciseStart"));
                dto.setExerciseEnd(rs.getString("exerciseEnd"));
                dto.setExercisecnt(rs.getInt("exercisecnt"));
                dto.setExerciseunit(rs.getString("exerciseunit"));
                recordList.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }

        return recordList;
    }
    
    public void updateRecordWork(RecordWorkDTO dto) throws SQLException {
        PreparedStatement pstmt = null;
        String sql = "UPDATE exerciseRecord SET " +
                     "exerciseDate = TO_DATE(?, 'YYYY-MM-DD'), " +
                     "exerciseName = ?, " +
                     "exerciseContent = ?, " +
                     "exerciseStart = TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS'), " +
                     "exerciseEnd = TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS'), " +
                     "exercisecnt = ?, " +
                     "exerciseunit = ? " +
                     "WHERE exerciseNum = ?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getExerciseDate());
            pstmt.setString(2, dto.getExerciseName());
            pstmt.setString(3, dto.getExerciseContent());

            // 'T'을 공백으로 대체하고 ':00' 초를 추가하여 형식 일치
            String exerciseStartFormatted = dto.getExerciseStart().replace("T", " ") + ":00";
            String exerciseEndFormatted = dto.getExerciseEnd().replace("T", " ") + ":00";
            pstmt.setString(4, exerciseStartFormatted);
            pstmt.setString(5, exerciseEndFormatted);

            pstmt.setInt(6, dto.getExercisecnt());
            pstmt.setString(7, dto.getExerciseunit());
            pstmt.setInt(8, dto.getExerciseNum());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            DBUtil.close(pstmt);
        }
    }
    
    public void deleteRecordWork(int exerciseNum) throws SQLException {
        PreparedStatement pstmt = null;
        String sql = "DELETE FROM exerciseRecord WHERE exerciseNum = ?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, exerciseNum);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            DBUtil.close(pstmt);
        }
    }
    
    // 특정 운동 번호를 조건으로 리스트 출력
    public RecordWorkDTO getRecordWork(int exerciseNum) throws SQLException {
        RecordWorkDTO dto = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM exerciseRecord WHERE exerciseNum = ?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, exerciseNum);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dto = new RecordWorkDTO();
                dto.setExerciseNum(rs.getInt("exerciseNum"));
                dto.setMemberId(rs.getString("memberId"));
                dto.setExerciseDate(rs.getString("exerciseDate"));
                dto.setExerciseName(rs.getString("exerciseName"));
                dto.setExerciseContent(rs.getString("exerciseContent"));
                dto.setExerciseStart(rs.getString("exerciseStart"));
                dto.setExerciseEnd(rs.getString("exerciseEnd"));
                dto.setExercisecnt(rs.getInt("exercisecnt"));
                dto.setExerciseunit(rs.getString("exerciseunit"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }

        return dto;
    }
}