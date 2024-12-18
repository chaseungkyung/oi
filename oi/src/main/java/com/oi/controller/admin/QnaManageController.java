package com.oi.controller.admin;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import com.oi.dao.QnaDAO;
import com.oi.dto.LoginDTO;
import com.oi.dto.QnaDTO;
import com.oi.mvc.annotation.Controller;
import com.oi.mvc.annotation.RequestMapping;
import com.oi.mvc.annotation.RequestMethod;
import com.oi.mvc.view.ModelAndView;
import com.oi.util.MyUtil;
import com.oi.util.MyUtilBootstrap;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class QnaManageController {

	@RequestMapping(value = "/admin/qna/list")
	public ModelAndView list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시물 리스트
		ModelAndView mav = new ModelAndView("admin/qna/list");
		
		QnaDAO dao = new QnaDAO();
		MyUtil util = new MyUtilBootstrap();
		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if (page != null) {
				current_page = Integer.parseInt(page);
			}
			
			// 검색
			String kwd = req.getParameter("kwd");
			if (kwd == null) {
				kwd = "";
			}

			// GET 방식인 경우 디코딩
			if (req.getMethod().equalsIgnoreCase("GET")) {
				kwd = URLDecoder.decode(kwd, "utf-8");
			}

			// 전체 데이터 개수
			int dataCount;
			if (kwd.length() == 0) {
				dataCount = dao.dataCount();
			} else {
				dataCount = dao.dataCount(kwd);
			}
			
			// 전체 페이지 수
			int size = 10;
			int total_page = util.pageCount(dataCount, size);
			if (current_page > total_page) {
				current_page = total_page;
			}

			// 게시물 가져오기
			int offset = (current_page - 1) * size;
			if(offset < 0) offset = 0;
			
			List<QnaDTO> list = null;
			if (kwd.length() == 0) {
				list = dao.listQuestion(offset, size);
			} else {
				list = dao.listQuestion(offset, size, kwd);
			}

			String query = "";
			if (kwd.length() != 0) {
				query = "kwd=" + URLEncoder.encode(kwd, "utf-8");
			}

			// 페이징 처리
			String cp = req.getContextPath();
			String listUrl = cp + "/admin/qna/list";
			String articleUrl = cp + "/admin/qna/article?page=" + current_page;
			if (query.length() != 0) {
				listUrl += "?" + query;
				articleUrl += "&" + query;
			}

			String paging = util.paging(current_page, total_page, listUrl);

			// 포워딩할 JSP에 전달할 속성
			mav.addObject("list", list);
			mav.addObject("page", current_page);
			mav.addObject("total_page", total_page);
			mav.addObject("dataCount", dataCount);
			mav.addObject("size", size);
			mav.addObject("articleUrl", articleUrl);
			mav.addObject("paging", paging);
			mav.addObject("kwd", kwd);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		// JSP로 포워딩
		return mav;
	}

	@RequestMapping(value = "/admin/qna/article", method = RequestMethod.GET)
	public ModelAndView article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글보기
		QnaDAO dao = new QnaDAO();
		
		String page = req.getParameter("page");
		String query = "page=" + page;

		try {
			long num = Long.parseLong(req.getParameter("num"));
			String kwd = req.getParameter("kwd");
			if (kwd == null) {
				kwd = "";
			}
			kwd = URLDecoder.decode(kwd, "utf-8");

			if (kwd.length() != 0) {
				query += "&kwd=" + URLEncoder.encode(kwd, "UTF-8");
			}

			// 게시물 가져오기
			QnaDTO dto = dao.findById(num);
			if (dto == null) {
				return new ModelAndView("redirect:/admin/qna/list?" + query);
			}
			
			dto.setQuestionCon(dto.getQuestionCon().replaceAll("\n", "<br>"));
			if(dto.getAnsContent() != null) {
				dto.setAnsContent(dto.getAnsContent().replaceAll("\n", "<br>"));
			}

			// 이전글 다음글
			QnaDTO prevDto = dao.findByPrev(dto.getQuestionNum(), kwd);
			QnaDTO nextDto = dao.findByNext(dto.getQuestionNum(), kwd);

			ModelAndView mav = new ModelAndView("admin/qna/article");
			
			// JSP로 전달할 속성
			mav.addObject("dto", dto);
			mav.addObject("page", page);
			mav.addObject("query", query);
			mav.addObject("prevDto", prevDto);
			mav.addObject("nextDto", nextDto);

			// 포워딩
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/admin/qna/list?" + query);
	}

	@RequestMapping(value = "/admin/qna/answer", method = RequestMethod.POST)
	public ModelAndView answerSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 답변 완료
		QnaDAO dao = new QnaDAO();

		HttpSession session = req.getSession();
		LoginDTO info = (LoginDTO) session.getAttribute("member");
		
		String page = req.getParameter("page");
		try {
			QnaDTO dto = new QnaDTO();
			
			dto.setQuestionNum(Long.parseLong(req.getParameter("num")));
			dto.setAnsContent(req.getParameter("answer"));
			dto.setAnswerId(info.getUserId());

			dao.insertAnswer(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/admin/qna/list?page=" + page);
	}
	@RequestMapping(value = "/admin/qna/answerupdate", method = RequestMethod.POST)
	public ModelAndView UpdateAnswer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 답변 완료
		QnaDAO dao = new QnaDAO();

		HttpSession session = req.getSession();
		LoginDTO info = (LoginDTO) session.getAttribute("member");
		
		String page = req.getParameter("page");
		try {
			QnaDTO dto = new QnaDTO();
			
			dto.setQuestionNum(Long.parseLong(req.getParameter("num")));
			dto.setAnsContent(req.getParameter("answer"));
			dto.setAnswerId(info.getUserId());

			dao.updateAnswer(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/admin/qna/list?page=" + page);
	}
	

	@RequestMapping(value = "/admin/qna/delete", method = RequestMethod.GET)
	public ModelAndView delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 삭제
		QnaDAO dao = new QnaDAO();

		HttpSession session = req.getSession();
		LoginDTO info = (LoginDTO) session.getAttribute("member");
		
		String page = req.getParameter("page");
		String query = "page=" + page;

		try {
			long num = Long.parseLong(req.getParameter("num"));
			String mode = req.getParameter("mode");
			
			String kwd = req.getParameter("kwd");
			if (kwd == null) {
				kwd = "";
			}
			kwd = URLDecoder.decode(kwd, "utf-8");

			if (kwd.length() != 0) {
				query += "&kwd=" + URLEncoder.encode(kwd, "UTF-8");
			}
			
			if(mode.equals("answer")) {
				// 답변 삭제
				QnaDTO dto = new QnaDTO();
				dto.setQuestionNum(num);
				dto.setAnsContent("");
				dto.setAnswerId("");
				dao.deleteAnswer(dto);
			} else if(mode.equals("question")) {
				// 질문 삭제
				dao.deleteQuestion(num, info.getUserId(), Integer.parseInt(info.getUserLevel()));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/admin/qna/list?" + query);
	}
}
