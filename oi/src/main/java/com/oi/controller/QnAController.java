package com.oi.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import com.oi.dao.QnaDAO;
import com.oi.dto.QnaDTO;
import com.oi.dto.LoginDTO;
import com.oi.util.MyUtil;
import com.oi.util.MyUtilBootstrap;
import com.oi.mvc.annotation.Controller;
import com.oi.mvc.annotation.RequestMapping;
import com.oi.mvc.annotation.RequestMethod;
import com.oi.mvc.view.ModelAndView;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class QnAController {
	@RequestMapping(value = "/QnA/main", method = RequestMethod.GET)
	public ModelAndView qnaController(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return new ModelAndView("qna/list");
	}
	
	@RequestMapping(value = "/qna/list")
	public ModelAndView list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시물 리스트
		ModelAndView mav = new ModelAndView("qna/list");
		
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
			String listUrl = cp + "/qna/list";
			String articleUrl = cp + "/qna/article?page=" + current_page;
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

	@RequestMapping(value = "/qna/write", method = RequestMethod.GET)
	public ModelAndView writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글쓰기 폼
		ModelAndView mav = new ModelAndView("qna/write");		
		mav.addObject("mode", "write");
		return mav;
	}

	@RequestMapping(value = "/qna/write", method = RequestMethod.POST)
	public ModelAndView writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글 저장
		QnaDAO dao = new QnaDAO();
		
		HttpSession session = req.getSession();
		LoginDTO info = (LoginDTO) session.getAttribute("member");
		
		try {
			QnaDTO dto = new QnaDTO();

			// userId는 세션에 저장된 정보
			dto.setQuestionId(info.getUserId());

			// 파라미터
			dto.setQuestionTitle(req.getParameter("title"));
			dto.setQuestionCon(req.getParameter("question"));

			dao.insertQuestion(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/qna/list");
	}

	@RequestMapping(value = "/qna/article", method = RequestMethod.GET)
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
				return new ModelAndView("redirect:/qna/list?" + query);
			}
			
			dto.setQuestionCon(dto.getQuestionCon().replaceAll("\n", "<br>"));
			if(dto.getAnsContent() != null) {
				dto.setAnsContent(dto.getAnsContent().replaceAll("\n", "<br>"));
			}

			// 이전글 다음글
			QnaDTO prevDto = dao.findByPrev(dto.getQuestionNum(), kwd);
			QnaDTO nextDto = dao.findByNext(dto.getQuestionNum(), kwd);

			ModelAndView mav = new ModelAndView("qna/article");
			
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

		return new ModelAndView("redirect:/qna/list?" + query);
	}

	@RequestMapping(value = "/qna/update", method = RequestMethod.GET)
	public ModelAndView updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정 폼
		QnaDAO dao = new QnaDAO();

		HttpSession session = req.getSession();
		LoginDTO info = (LoginDTO) session.getAttribute("member");
		
		String page = req.getParameter("page");

		try {
			long num = Long.parseLong(req.getParameter("num"));
			QnaDTO dto = dao.findById(num);

			if (dto == null) {
				return new ModelAndView("redirect:/qna/list?page=" + page);
			}

			// 게시물을 올린 사용자가 아니면
			if (! dto.getQuestionId().equals(info.getUserId())) {
				return new ModelAndView("redirect:/qna/list?page=" + page);
			}

			ModelAndView mav = new ModelAndView("qna/write");
			
			mav.addObject("dto", dto);
			mav.addObject("page", page);
			mav.addObject("mode", "update");

			return mav;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/qna/list?page=" + page);
	}

	@RequestMapping(value = "/qna/update", method = RequestMethod.POST)
	public ModelAndView updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정 완료
		QnaDAO dao = new QnaDAO();

		HttpSession session = req.getSession();
		LoginDTO info = (LoginDTO) session.getAttribute("member");
		
		String page = req.getParameter("page");
		try {
			QnaDTO dto = new QnaDTO();
			
			dto.setQuestionNum(Long.parseLong(req.getParameter("num")));
			dto.setQuestionTitle(req.getParameter("title"));
			dto.setQuestionCon(req.getParameter("question"));

			dto.setQuestionId(info.getUserId());

			dao.updateQuestion(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/qna/list?page=" + page);
	}

	@RequestMapping(value = "/qna/delete", method = RequestMethod.GET)
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
			
			if(mode.equals("answer") && Integer.parseInt(info.getUserLevel())  >= 51) {
				// 답변 삭제
				QnaDTO dto = new QnaDTO();
				dto.setQuestionNum(num);
				dto.setAnsContent("");
				dto.setAnswerId("");
				dao.updateAnswer(dto);
			} else if(mode.equals("question")) {
				// 질문 삭제
				dao.deleteQuestion(num, info.getUserId(), Integer.parseInt(info.getUserLevel()));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/qna/list?" + query);
	}
}
