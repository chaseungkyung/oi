package com.oi.controller;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.oi.dao.CompleteTodayDAO;
import com.oi.dto.CompleteTodayDTO;
import com.oi.dto.LoginDTO;
import com.oi.dto.Wotdfile;
import com.oi.mvc.annotation.Controller;
import com.oi.mvc.annotation.RequestMapping;
import com.oi.mvc.annotation.RequestMethod;
import com.oi.mvc.view.ModelAndView;
import com.oi.util.FileManager;
import com.oi.util.MyMultipartFile;
import com.oi.util.MyUtil;
import com.oi.util.MyUtilGeneral;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@Controller
public class WorkToDayController {
	private CompleteTodayDAO dao = new CompleteTodayDAO();
	private FileManager filemanager = new FileManager();
	private MyUtil util = new MyUtilGeneral();
	@RequestMapping(value = "/completeworkout/main", method = RequestMethod.GET)
	public ModelAndView intomain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		return new ModelAndView("worktoday/wtdmain");
	}
	
	// AJAX - TEXT
	// 리스트 무한 스크롤 
	@RequestMapping(value = "/completeworkout/list", method = RequestMethod.GET)
	public ModelAndView getListmore(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("here");
		// 넘어오는 파라미터 : page 
		
		ModelAndView mav = new ModelAndView("worktoday/wtdcontent");
		boolean state = false;
		try {
			int page;
			if(req.getParameter("page") != null) {
				page = Integer.parseInt(req.getParameter("page"));
			}else {
				page = 1;
			}
			
			// 데이터 개수 불러오기 
			int dataCount = dao.DataCount();
			
			// 한번에 출력할 개수
			int size = 10;
			// 건너뛸개수 
			int offset = (page - 1)* size;
			if(offset < 0) offset = 0;
			
			// 총 페이지 수 
			int total_page = util.pageCount(dataCount, size);
			
			// 데이터 불러오기 
			List<CompleteTodayDTO> list = dao.getData(offset, size);
			
			// 데이터 savename 받아오기 키 값 photonum : string 으로 저장 되어있음 
			for(CompleteTodayDTO dto : list) {
				state = true;
				dao.getFiles(dto);
			}
			//확인용
			System.out.println(state);
			
			mav.addObject("list", list);
			mav.addObject("dataCount", dataCount);
			mav.addObject("total_page", total_page);
			mav.addObject("page", page);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
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
			String pathname = req.getServletContext().getRealPath("/") + "oifiles" +  File.separator + "photo";
			Collection<Part> parts = req.getParts();

			List<MyMultipartFile> files = filemanager.doFileUpload(parts, pathname);
			
			Wotdfile filedto =  dto.getFile();
			
			String [] names = new String [files.size()];
			
			for(int i = 0; i < files.size(); i ++) {
				names[i] = files.get(i).getSaveFilename();
			}
			
			filedto.setSaveFileName(names);
			dto.setMemberId(login.getUserId());
			dto.setContent(req.getParameter("content"));
			
			// 인서트 성공시 true 실패시 false 
			boolean result = dao.insertCompleteWork(dto);
			
			// 테스트용
			System.out.println(result);
			
			// 실패시 insertform 에서 alert 창 띄우기 = 실패 ! 
			/*
			if(! result) {
				session.setAttribute("result", result);
				return new ModelAndView("redirect:/completeworkout/insertwtd");
			}
			*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
}
