package com.oi.controller;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oi.dao.CompleteTodayDAO;
import com.oi.dto.CompleteTodayDTO;
import com.oi.dto.LoginDTO;
import com.oi.dto.Wotdfile;
import com.oi.mvc.annotation.Controller;
import com.oi.mvc.annotation.RequestMapping;
import com.oi.mvc.annotation.RequestMethod;
import com.oi.mvc.annotation.ResponseBody;
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
	
	@RequestMapping(value = "/completeworkout/personal", method = RequestMethod.GET)
	public ModelAndView mine(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		LoginDTO login = (LoginDTO) session.getAttribute("member");
		
		
		
		return new ModelAndView("worktoday/mine");
		
		
	}
	
	@RequestMapping(value = "/completeworkout/modalbody", method = RequestMethod.GET)
	public ModelAndView getComment(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// 넘어오는 파라미터 : wnum 
		ModelAndView mav = new ModelAndView("worktoday/wotdcomment");
		try {

			long wnum = Long.parseLong(req.getParameter("wnum"));

			// 해당하는 게시물 가져오기		// 내용 , 닉네임, 프로필사진, 컨텐츠 / 날짜 필요없을듯 
			CompleteTodayDTO dto = dao.findByNum(wnum);
			
			// 댓글 목록 가져오기 
			dao.getComments(dto);
			
			mav.addObject("article", dto);
			mav.addObject("commentlist", dto.getComments());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	
	// AJAX - TEXT
	// 리스트 무한 스크롤 
	@RequestMapping(value = "/completeworkout/list", method = RequestMethod.GET)
	public ModelAndView getListmore(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		// 넘어오는 파라미터 : page 
		
		ModelAndView mav = new ModelAndView("worktoday/wtdcontent");
		
		HttpSession session = req.getSession();
		
		LoginDTO login = (LoginDTO) session.getAttribute("member");
		
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
				
				dao.getFiles(dto);
				dto.setLiked(dao.likeOrNot(login.getUserId(), dto.getWnum()));
				
			}
			
			mav.addObject("list", list);
			//mav.addObject("dataCount", dataCount);
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
			String pathname = req.getServletContext().getRealPath("/") + "uploads" + File.separator + "photo";
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
			
			// 인서트 성공시 true 실패시 false // boolean result = 
			dao.insertCompleteWork(dto);
		
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
	
	@ResponseBody
	@RequestMapping(value="/completeworkout/insertlike", method =RequestMethod.GET )
	public Map<String, Object> insertLike (HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		
		// 넘어오는 파라미터 : 게시물번호 num , mode = insert 인지 delete 인지 
		// 게시물에 대해 좋아요를 인서트 할건지 좋아요를 취소 할건지 
		
		HttpSession session = req.getSession();
		LoginDTO login = (LoginDTO)session.getAttribute("member");
			
		String mode = req.getParameter("mode");
		int count = 0;
		try {
			long num = Long.parseLong(req.getParameter("num"));
			
			if(mode.equalsIgnoreCase("insert")) {
				dao.insertLike(num, login.getUserId());
			}else {
				dao.deleteLike(num, login.getUserId());
			}
			
			count =  dao.likeCount(num);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 반환값 : 좋아요 개수 
		// 성공적 = 클래스 바꿔줘야함 채운하트로 
		Map<String, Object> model = new HashMap<String, Object>();
		
		model.put("count", count);
		
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value="/completeworkout/insertcomment", method =RequestMethod.GET )
	public Map<String, Object> insertComment (HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		
		Map<String,Object> model = new HashMap<String, Object>();
		
		String content =  req.getParameter("content");
		HttpSession session = req.getSession();
		LoginDTO login = (LoginDTO) session.getAttribute("member");
		
		try {
			long wnum = Long.parseLong(req.getParameter("wnum"));
			
			CompleteTodayDTO dto  = new CompleteTodayDTO();
			
			dto.setWnum(wnum);
			dto.setMemberId(login.getUserId());
			dto.setContent(content);
			
			dao.insertComment(dto);
			
			dao.getComments(dto);
			
			model.put("wnum", wnum);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return model;
	}

	
}
