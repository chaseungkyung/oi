package com.oi.controller;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.oi.dao.CompleteTodayDAO;
import com.oi.dto.CompleteTodayDTO;
import com.oi.dto.LoginDTO;
import com.oi.mvc.annotation.Controller;
import com.oi.mvc.annotation.RequestMapping;
import com.oi.mvc.annotation.RequestMethod;
import com.oi.mvc.view.ModelAndView;
import com.oi.util.FileManager;
import com.oi.util.MyMultipartFile;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@Controller
public class WorkToDayController {
	private CompleteTodayDAO dao = new CompleteTodayDAO();
	private FileManager filemanager = new FileManager();

	@RequestMapping(value = "/completeworkout/main")
	public ModelAndView intomain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		return new ModelAndView("worktoday/wtdmain");
	}

	@RequestMapping(value = "/completeworkout/insertwtd", method = RequestMethod.GET)
	public ModelAndView insertForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ModelAndView mav =  new ModelAndView("worktoday/wtdinsert");
		
		/*
		// 이전에 실패한 적이 있으면 alert 창 띄울거임 
		HttpSession session = req.getSession();
		boolean result = (Boolean)session.getAttribute("result");
		if(result == false) {
			mav.addObject("before", "false");
		}
		*/
		
		return mav;
	}

	@RequestMapping(value = "/completeworkout/insertwtd", method = RequestMethod.POST)
	public ModelAndView insertSubmit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 오운완 게시글 등록
		// 넘어오는 파라미터 : content, file
		ModelAndView mav = new ModelAndView("redirect:/completeworkout/main");

		HttpSession session = req.getSession();
		LoginDTO login = (LoginDTO) session.getAttribute("member");
		CompleteTodayDTO dto = new CompleteTodayDTO();

		try {
			// 파일 저장
			String pathname = req.getServletContext().getRealPath("/") + "oifiles" + File.pathSeparator;
			Collection<Part> parts = req.getParts();

			List<MyMultipartFile> files = filemanager.doFileUpload(parts, pathname);
			
			dto.setFilenames(files);
			dto.setMemberId(login.getUserId());
			dto.setContent(req.getParameter("content"));
			
			// 인서트 성공시 true 실패시 false 
			boolean result = dao.insertCompleteWork(dto);
			
			// 실패시 insertform 에서 alert 창 띄우기 = 실패 ! 
			if(! result) {
				session.setAttribute("result", result);
				return new ModelAndView("redirect:/completeworkout/insertwtd");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
}
