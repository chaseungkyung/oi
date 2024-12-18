package com.oi.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.oi.dao.RecordWorkDAO;
import com.oi.dto.LoginDTO;
import com.oi.dto.RecordWorkDTO;
import com.oi.mvc.annotation.Controller;
import com.oi.mvc.annotation.RequestMapping;
import com.oi.mvc.view.ModelAndView;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class RecordWorkController {
	
	// DAO 선언: 클래스 필드에 추가
    private RecordWorkDAO recordWorkDAO = new RecordWorkDAO();

	// 운동 기록 메인 페이지로 이동
	@RequestMapping("/todayworkout/main")
	public ModelAndView intomain(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    HttpSession session = req.getSession();
	    LoginDTO login = (LoginDTO) session.getAttribute("member");

	    if (login == null) {
	        return new ModelAndView("redirect:/access/login");
	    }

	    return new ModelAndView("todayworkout/todayworkoutmain");
	}
	
	// 운동 기록 작성 (INSERT)
    @RequestMapping("/todayworkout/insertRecord")
    public ModelAndView insertRecord(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        LoginDTO login = (LoginDTO) session.getAttribute("member");

        if (login == null) {
            return new ModelAndView("redirect:/access/login");
        }

        String memberId = login.getUserId(); // LoginDTO에서 memberId 가져오기

        try {
            // 파라미터에서 값 추출 및 DTO 설정
            RecordWorkDTO dto = new RecordWorkDTO();
            dto.setMemberId(memberId);
            dto.setExerciseDate(req.getParameter("exerciseDate"));
            dto.setExerciseName(req.getParameter("exerciseName"));
            dto.setExerciseContent(req.getParameter("exerciseContent"));
            dto.setExerciseStart(req.getParameter("exerciseStart"));
            dto.setExerciseEnd(req.getParameter("exerciseEnd"));
            dto.setExercisecnt(Integer.parseInt(req.getParameter("exercisecnt")));
            dto.setExerciseunit(req.getParameter("exerciseunit")); // 수정: Integer.parseInt -> String

            // DAO를 통해 데이터베이스에 삽입
            recordWorkDAO.insertRecordWork(dto);

            // 성공 메시지 설정
            req.setAttribute("message", "운동 기록이 성공적으로 등록되었습니다.");
            req.setAttribute("messageType", "success");
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("message", "운동 기록 등록 중 오류가 발생했습니다.");
            req.setAttribute("messageType", "error");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            req.setAttribute("message", "입력 값에 오류가 있습니다.");
            req.setAttribute("messageType", "error");
        }

        return new ModelAndView("todayworkout/todayworkoutmain");
    }
	
	// 운동 기록 리스트 출력
    @RequestMapping("/todayworkout/list")
    public ModelAndView listRecords(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        LoginDTO login = (LoginDTO) session.getAttribute("member");

        if (login == null) {
            return new ModelAndView("redirect:/access/login");
        }

        String memberId = login.getUserId();

        try {
            List<RecordWorkDTO> recordList = recordWorkDAO.listRecordWorks(memberId);
            req.setAttribute("recordList", recordList);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("message", "운동 기록 리스트 조회 중 오류가 발생했습니다.");
            req.setAttribute("messageType", "error");
            return new ModelAndView("todayworkout/todayworkoutmain");
        }

        return new ModelAndView("todayworkout/todayworkoutlist");
    }

	// 운동 기록 수정 폼 출력
    @RequestMapping("/todayworkout/edit")
    public ModelAndView editRecord(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        LoginDTO login = (LoginDTO) session.getAttribute("member");

        if (login == null) {
            return new ModelAndView("redirect:/access/login");
        }

        String memberId = login.getUserId();
        String exerciseNumStr = req.getParameter("exerciseNum");

        if (exerciseNumStr == null || exerciseNumStr.isEmpty()) {
            req.setAttribute("message", "수정할 운동 기록을 선택해주세요.");
            req.setAttribute("messageType", "error");
            return new ModelAndView("redirect:/todayworkout/list");
        }

        try {
            int exerciseNum = Integer.parseInt(exerciseNumStr);
            RecordWorkDTO dto = recordWorkDAO.getRecordWork(exerciseNum);

            if (dto == null || !dto.getMemberId().equals(memberId)) {
                req.setAttribute("message", "해당 운동 기록을 찾을 수 없거나 권한이 없습니다.");
                req.setAttribute("messageType", "error");
                return new ModelAndView("redirect:/todayworkout/list");
            }

            req.setAttribute("recordWorkDTO", dto);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            req.setAttribute("message", "유효하지 않은 운동 기록 번호입니다.");
            req.setAttribute("messageType", "error");
            return new ModelAndView("redirect:/todayworkout/list");
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("message", "운동 기록 조회 중 오류가 발생했습니다.");
            req.setAttribute("messageType", "error");
            return new ModelAndView("redirect:/todayworkout/list");
        }

        return new ModelAndView("todayworkout/todayworkoutedit");
    }

 // 운동 기록 수정 처리
    @RequestMapping("/todayworkout/updateRecord")
    public ModelAndView updateRecord(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        LoginDTO login = (LoginDTO) session.getAttribute("member");

        if (login == null) {
            return new ModelAndView("redirect:/access/login");
        }

        String memberId = login.getUserId();

        try {
            // 파라미터에서 값 추출 및 DTO 설정
            RecordWorkDTO dto = new RecordWorkDTO();
            dto.setExerciseNum(Integer.parseInt(req.getParameter("exerciseNum"))); // exerciseNum 추가
            dto.setMemberId(memberId);
            dto.setExerciseDate(req.getParameter("exerciseDate"));
            dto.setExerciseName(req.getParameter("exerciseName"));
            dto.setExerciseContent(req.getParameter("exerciseContent"));
            dto.setExerciseStart(req.getParameter("exerciseStart"));
            dto.setExerciseEnd(req.getParameter("exerciseEnd"));
            dto.setExercisecnt(Integer.parseInt(req.getParameter("exercisecnt")));
            dto.setExerciseunit(req.getParameter("exerciseunit")); // 수정: Integer.parseInt -> String

            // DAO를 통해 데이터베이스에 업데이트
            recordWorkDAO.updateRecordWork(dto);

            // 성공 메시지 설정
            req.setAttribute("message", "운동 기록이 성공적으로 수정되었습니다.");
            req.setAttribute("messageType", "success");
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("message", "운동 기록 수정 중 오류가 발생했습니다.");
            req.setAttribute("messageType", "error");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            req.setAttribute("message", "입력 값에 오류가 있습니다.");
            req.setAttribute("messageType", "error");
        }

        // 수정 후 리스트 페이지로 리다이렉트
        return new ModelAndView("redirect:/todayworkout/list");
    }

	// 운동 기록 삭제 처리
    @RequestMapping("/todayworkout/deleteRecord")
    public ModelAndView deleteRecord(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        LoginDTO login = (LoginDTO) session.getAttribute("member");

        if (login == null) {
            return new ModelAndView("redirect:/access/login");
        }

        String memberId = login.getUserId();
        String exerciseNumStr = req.getParameter("exerciseNum");

        if (exerciseNumStr == null || exerciseNumStr.isEmpty()) {
            req.setAttribute("message", "삭제할 운동 기록을 선택해주세요.");
            req.setAttribute("messageType", "error");
            return new ModelAndView("redirect:/todayworkout/list");
        }

        try {
            int exerciseNum = Integer.parseInt(exerciseNumStr);
            RecordWorkDTO dto = recordWorkDAO.getRecordWork(exerciseNum);

            if (dto == null || !dto.getMemberId().equals(memberId)) {
                req.setAttribute("message", "해당 운동 기록을 찾을 수 없거나 권한이 없습니다.");
                req.setAttribute("messageType", "error");
                return new ModelAndView("redirect:/todayworkout/list");
            }

            // DAO를 통해 데이터베이스에서 삭제
            recordWorkDAO.deleteRecordWork(exerciseNum);

            // 성공 메시지 설정
            req.setAttribute("message", "운동 기록이 성공적으로 삭제되었습니다.");
            req.setAttribute("messageType", "success");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            req.setAttribute("message", "유효하지 않은 운동 기록 번호입니다.");
            req.setAttribute("messageType", "error");
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("message", "운동 기록 삭제 중 오류가 발생했습니다.");
            req.setAttribute("messageType", "error");
        }

        return new ModelAndView("redirect:/todayworkout/list");
    }
}
