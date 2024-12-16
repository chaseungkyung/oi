package com.oi.controller.admin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import com.oi.dao.NoticeDAO;
import com.oi.dto.LoginDTO;
import com.oi.dto.MemberDTO;
import com.oi.dto.NoticeDTO;
import com.oi.mvc.annotation.Controller;
import com.oi.mvc.annotation.RequestMapping;
import com.oi.mvc.annotation.RequestMethod;
import com.oi.mvc.view.ModelAndView;
import com.oi.util.FileManager;
import com.oi.util.MyMultipartFile;
import com.oi.util.MyUtil;
import com.oi.util.MyUtilBootstrap;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class NoticeManageController {
	
	@RequestMapping(value = "/admin/notice/list")
	public ModelAndView list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ModelAndView mav = new ModelAndView("admin/notice/list");

		NoticeDAO dao = new NoticeDAO();
		MyUtil util = new MyUtilBootstrap();
		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			String schType = req.getParameter("schType");
			String kwd = req.getParameter("kwd");
			if(schType == null) {
				schType = "all";
				kwd = "";
			}
			
			if(req.getMethod().equalsIgnoreCase("GET")) {
				kwd = URLDecoder.decode(kwd, "utf-8");
			}
			
			String pageSize = req.getParameter("size");
			int size = pageSize == null ? 10 : Integer.parseInt(pageSize);
			
			int dataCount, total_page;
			
			if(kwd.length() != 0) {
				dataCount = dao.dataCount(schType, kwd);
			} else {
				dataCount = dao.dataCount();
			}
			
			total_page = util.pageCount(dataCount, size);
			
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			int offset = (current_page -1) * size;
			if(offset < 0) offset = 0 ;
			
			List<NoticeDTO> list;
			if(kwd.length() != 0) {
				list = dao.listNotice(offset, size, schType, kwd);
			} else {
				list = dao.listNotice(offset, size);
			}
			
			List<NoticeDTO> listNotice = null;
			if(current_page == 1) {
				listNotice = dao.listNotice(offset, size);
			}
			
			String cp = req.getContextPath();
			String query = "size=" + size;
			String listUrl;
			String articleUrl;
			
			if(kwd.length() != 0) {
				query += "&schType=" + schType + "&kwd=" + URLEncoder.encode(kwd, "utf-8"); 
			}
			
			listUrl = cp + "/admin/notice/list?" + query;
			articleUrl = cp + "/admin/notice/article?page=" + current_page + "&" + query;
			
			String paging = util.paging(current_page, total_page, listUrl);
			
			mav.addObject("list", list);
			mav.addObject("listNotice", listNotice);
			mav.addObject("articleUrl", articleUrl);
			mav.addObject("dataCount", dataCount);
			mav.addObject("size", size);
			mav.addObject("page", current_page);
			mav.addObject("total_page", total_page);
			mav.addObject("paging", paging);
			mav.addObject("schType", schType);
			mav.addObject("kwd", kwd);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}
	
	@RequestMapping(value = "/admin/notice/write", method = RequestMethod.GET)
	public ModelAndView writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String size = req.getParameter("size");
		
		ModelAndView mav = new ModelAndView("admin/notice/write");
		
		mav.addObject("size", size);
		mav.addObject("mode", "write");
		
		return mav;
	}
	
	@RequestMapping(value = "/admin/notice/write", method = RequestMethod.POST)
	public ModelAndView writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		LoginDTO meto = (LoginDTO)session.getAttribute("member");
		
		FileManager fileManager = new FileManager();
		
		String root = session.getServletContext().getRealPath("/");
		String pathname = root + "uploads" + File.separator + "notice";
		
		NoticeDAO dao = new NoticeDAO();
		
		String size = req.getParameter("size");
		
		try {
			NoticeDTO dto = new NoticeDTO();
			
			dto.setMemberId(meto.getUserId());
			if(req.getParameter("notice") != null) {
				dto.setNotice(Integer.parseInt(req.getParameter("notice")));
			}
			dto.setNoticeTitle(req.getParameter("subject"));
			dto.setNoticeContent(req.getParameter("content"));
		
			List<MyMultipartFile> listFile = fileManager.doFileUpload(req.getParts(), pathname);
			dto.setListFile(listFile);
			
			dao.insertNotice(dto);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/admin/notice/list?size=" + size);
	}
	
	@RequestMapping(value = "/admin/notice/article", method = RequestMethod.GET)
	public ModelAndView article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String page = req.getParameter("page");
		String size = req.getParameter("size");
		String query = "page=" + page + "&size=" + size;

		NoticeDAO dao = new NoticeDAO();
		
		try {
			long noticeNum = Long.parseLong(req.getParameter("num"));
			
			String schType = req.getParameter("schType");
			String kwd = req.getParameter("kwd");
			if(schType == null) {
				schType = "all";
				kwd = "";
			}
			
			kwd = URLDecoder.decode(kwd, "utf-8");
			
			if(kwd.length() != 0) {
				query += "&schType=" + schType + "&kwd=" + URLEncoder.encode(kwd, "UTF-8");
			}
	
			NoticeDTO dto = dao.findById(noticeNum);
			
			if(dto == null) {
				return new ModelAndView("redirect:/admin/notice/list?" + query);
			}
			
			dto.setNoticeContent(dto.getNoticeContent().replaceAll("\n", "<br>"));
			
			NoticeDTO preDto = dao.findByPre(dto.getNoticeNum(), schType, kwd);
			NoticeDTO nexDto = dao.findByNex(dto.getNoticeNum(), schType, kwd);
			
			List<NoticeDTO> listFile = dao.listNoticeFile(noticeNum);
			
			ModelAndView mav = new ModelAndView("admin/notice/article");
			
			mav.addObject("dto", dto);
			mav.addObject("preDto", preDto);
			mav.addObject("nexDto", nexDto);
			mav.addObject("listFile", listFile);
			mav.addObject("query", query);
			mav.addObject("page", page);
			mav.addObject("size", size);
			
			return mav;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("redirect:/admin/notice/list?" + query);
		
	}
	
	@RequestMapping(value = "/admin/notice/download", method = RequestMethod.GET)
	public void download(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		NoticeDAO dao = new NoticeDAO();
		
		HttpSession session = req.getSession();
		
		FileManager fileManager = new FileManager();
		
		String root = session.getServletContext().getRealPath("/");
		String pathname = root + "uploads" + File.separator + "notice";
		
		boolean b = false;
		
		try {
			long noticeFileNum = Long.parseLong(req.getParameter("filenum"));
			
			NoticeDTO dto = dao.findById(noticeFileNum);
			if(dto != null) {
				b = fileManager.doFiledownload(dto.getNoticeSaveFileName(), dto.getNoticeOriFileName(), pathname, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(!b) {
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print("<script>alert('파일 다운로드 실패'); history.back();</script>");
		}
	}
	
	@RequestMapping(value = "/admin/notice/update", method = RequestMethod.GET)
	public ModelAndView updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		NoticeDAO dao = new NoticeDAO();
		
		String page = req.getParameter("page");
		String size = req.getParameter("size");
		
		try {
			long noticeNum = Long.parseLong(req.getParameter("num"));
			
			NoticeDTO dto = dao.findById(noticeNum);
			if(dto == null) {
				return new ModelAndView("redirect:/admin/notice/list?page=" + page + "&size=" + size);
			}
			
			List<NoticeDTO> listFile = dao.listNoticeFile(noticeNum);
			
			ModelAndView mav = new ModelAndView("admin/notice/write");
			
			mav.addObject("dto", dto);
			mav.addObject("listFile", listFile);
			mav.addObject("page", page);
			mav.addObject("size", size);

			mav.addObject("mode", "update");
			
			return mav;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("redirect:/admin/notice/list?page=" + page + "&size=" + size);
	}	
	
	@RequestMapping(value = "/admin/notice/update", method = RequestMethod.POST)
	public ModelAndView updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		MemberDTO meto = (MemberDTO)session.getAttribute("member");
		
		FileManager fileManager = new FileManager();
		
		String root = session.getServletContext().getRealPath("/");
		String pathname = root + "uploads" + File.separator + "notice";
		
		if(meto.getUserLevel() < 100) {
			return new ModelAndView("redirect:/");
		}
		
		NoticeDAO dao = new NoticeDAO();
		
		String page = req.getParameter("page");
		String size = req.getParameter("size");
		
		try {
			NoticeDTO dto = new NoticeDTO();
			
			dto.setNoticeNum(Long.parseLong(req.getParameter("num")));
			dto.setNoticeTitle(req.getParameter("subject"));
			dto.setNoticeContent(req.getParameter("content"));
			
			List<MyMultipartFile> listFile = fileManager.doFileUpload(req.getParts(), pathname);
			dto.setListFile(listFile);
			
			dao.updateNotice(dto);
			
			return new ModelAndView("redirect:/admin/notice/article?page=" + page + "&size=" + size + "&noticeNum" + dto.getNoticeNum());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return new ModelAndView("redirect:/admin/notice/list?page=" + page + "&size=" + size);
	}
	
	@RequestMapping(value = "/admin/notice/deleteFile")
	public ModelAndView deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		HttpSession session = req.getSession();
		FileManager fileManager = new FileManager();
		
		String root = session.getServletContext().getRealPath("/");
		String pathname = root + "uploads" + File.separator + "notice";
		
		NoticeDAO dao = new NoticeDAO();
		
		String page = req.getParameter("page");
		String size = req.getParameter("size");
		
		try {
			long noticeNum = Long.parseLong(req.getParameter("num"));
			long noticeFileNum = Long.parseLong(req.getParameter("filenum"));
			
			NoticeDTO dto = dao.findById(noticeFileNum);
			if(dto != null) {
				fileManager.doFiledelete(pathname, dto.getNoticeSaveFileName());
				
				dao.deleteNoticeFile("one", noticeFileNum);
			}
			
			return new ModelAndView("redirect:/admin/notice/update?noticeNum=" + noticeNum + "&page=" + page + "&size=" + size);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("redirect:/admin/notice/list?page=" + page + "&size=" + size);
	}
	
	@RequestMapping(value = "/admin/notice/delete", method = RequestMethod.GET)
	public ModelAndView delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	HttpSession session = req.getSession();
	FileManager fileManager = new FileManager();
		
	String root = session.getServletContext().getRealPath("/");
	String pathname = root + "uploads" + File.separator + "notice";
		
	NoticeDAO dao = new NoticeDAO();
		
	String page = req.getParameter("page");
	String size = req.getParameter("size");
	String query = "page=" + page + "&size=" + size;
	
	try {
		long noticeNum = Long.parseLong(req.getParameter("num"));
		
		String schType = req.getParameter("schType");
		String kwd = req.getParameter("kwd");
		if(schType == null) {
			schType = "all";
			kwd = "";
		}
		kwd = URLDecoder.decode(kwd, "utf-8");
		
		if(kwd.length() != 0) {
			query += "&schType=" + schType + "&kwd=" + URLEncoder.encode(kwd, "UTF-8");
		}
		
		List<NoticeDTO> listFile = dao.listNoticeFile(noticeNum);
		for(NoticeDTO vo : listFile) {
			fileManager.doFiledelete(pathname, vo.getNoticeSaveFileName());
		}
		
		dao.deleteNoticeFile("all", noticeNum);
		
		dao.deleteNotice(noticeNum);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return new ModelAndView("redirect:/admin/notice/list?" + query);
	}	
	
	@RequestMapping(value = "/admin/notice/deleteList", method = RequestMethod.POST)
	public ModelAndView deleteList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		FileManager fileManager = new FileManager();
			
		String root = session.getServletContext().getRealPath("/");
		String pathname = root + "uploads" + File.separator + "notice";
			
		NoticeDAO dao = new NoticeDAO();
			
		String page = req.getParameter("page");
		String size = req.getParameter("size");
		String query = "page=" + page + "&size=" + size;
		
		try {
			String schType = req.getParameter("schType");
			String kwd = req.getParameter("kwd");
			if(schType == null) {
				schType = "all";
				kwd = "";
			}
			kwd = URLDecoder.decode(kwd, "utf-8");
			
			if(kwd.length() != 0) {
				query += "&schType=" + schType + "&kwd=" + URLEncoder.encode(kwd, "UTF-8");
			}
			
			String []nn = req.getParameterValues("noticeNums");
			long []noticeNums = new long[nn.length];
			for(int i=0; i<nn.length; i++) {
				noticeNums[i] = Long.parseLong(nn[i]);
			}
			
			for(int i=0; i<noticeNums.length; i++) {
				List<NoticeDTO> listFile = dao.listNoticeFile(noticeNums[i]);
				for(NoticeDTO vo : listFile) {
					fileManager.doFiledelete(pathname, vo.getNoticeSaveFileName());
				}
				
				dao.deleteNoticeFile("all", noticeNums[i]);
			}
			
			dao.deleteNotice(noticeNums);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("redirect:/admin/notice/list?" + query);
	}
}