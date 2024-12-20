package com.oi.controller.admin;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oi.dao.FaqDAO;
import com.oi.dto.FaqDTO;
import com.oi.dto.LoginDTO;
import com.oi.mvc.annotation.Controller;
import com.oi.mvc.annotation.RequestMapping;
import com.oi.mvc.annotation.RequestMethod;
import com.oi.mvc.annotation.ResponseBody;
import com.oi.mvc.view.ModelAndView;
import com.oi.util.MyUtil;
import com.oi.util.MyUtilBootstrap;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class FaqManageController {
	
	@RequestMapping(value = "/admin/faq/main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ModelAndView mav = new ModelAndView("admin/faq/main");
		
		FaqDAO dao = new FaqDAO();
		
		try {
			List<FaqDTO> listCategory = dao.listCategory(1);
			
			mav.addObject("listCategory", listCategory);
			
		} catch (Exception e) {
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/admin/faq/list", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ModelAndView mav = new ModelAndView("admin/faq/list");
		//oi/admin/faq/list
		MyUtil util = new MyUtilBootstrap();
		FaqDAO dao = new FaqDAO();
		
		try {
			int size = 10;
			int total_page = 0;
			int dataCount = 0;
			
			String pageNo = req.getParameter("pageNo");
			int current_page = 1;
			if(pageNo != null) {
				current_page = Integer.parseInt(pageNo);
			}
			long faqCateNum = 0;
			String strFaqCateNum = req.getParameter("faqCateNum");
			if(strFaqCateNum != null) {
				faqCateNum = Long.parseLong(strFaqCateNum);
			}
			
			String schType = req.getParameter("schType");
			String kwd = req.getParameter("kwd");
			if(schType == null) {
				schType = "all";
				kwd = "";
			}
			kwd = URLDecoder.decode(kwd, "utf-8");
			
			if(kwd.length() == 0) {
				dataCount = dao.dataCount(faqCateNum);
			} else {
				dataCount = dao.dataCount(faqCateNum, schType, kwd);
			}
			
			if(dataCount != 0) {
				total_page = util.pageCount(dataCount, size);
			}
			
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			int offset = (current_page - 1) * size;
			if(offset < 0) offset = 0;
			
			List<FaqDTO> list = null;
			
			if(kwd.length() == 0) {
				list = dao.listFaq(faqCateNum, offset, size);
			} else {
				list = dao.listFaq(faqCateNum, offset, size, schType, kwd);
			}
				
			for(FaqDTO dto : list) {
				dto.setFaqContent(dto.getFaqContent().replaceAll("\n", "<br>"));
			}
			
			String paging = util.pagingMethod(current_page, total_page, "listPage");
	
			mav.addObject("list", list);
			mav.addObject("pageNo", current_page);
			mav.addObject("dataCount", dataCount);
			mav.addObject("total_page", total_page);
			mav.addObject("paging", paging);

		} catch (Exception e) {
			resp.sendError(406);
			throw e;
		}
		return mav;
	}
	
	@RequestMapping(value = "/admin/faq/write", method = RequestMethod.GET)
	public ModelAndView writeFrom(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ModelAndView mav = new ModelAndView("admin/faq/write");
		
		FaqDAO dao = new FaqDAO();
		
		try {
			List<FaqDTO> listCategory = dao.listCategory(1);
			
			mav.addObject("listCategory", listCategory);
			mav.addObject("mode", "write");
			mav.addObject("pageNo", "1");
			
		} catch (Exception e) {
			resp.sendError(406);
			throw e;
		}
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value = "/admin/faq/write", method = RequestMethod.POST)
	public Map<String, Object> writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, Object> model = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		FaqDAO dao = new FaqDAO();
		
		String state = "false";
		
		try {
			LoginDTO meto = (LoginDTO)session.getAttribute("member");
			
			FaqDTO dto = new FaqDTO();
			dto.setFaqCateNum(Long.parseLong(req.getParameter("faqCateNum")));
			dto.setMemberId(meto.getUserId());
			dto.setFaqTitle(req.getParameter("faqTitle"));
			dto.setFaqContent(req.getParameter("faqContent"));
			
			dao.insertFaq(dto);
			
			state = "true";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.put("state", state);
		
		return model;
	}
	
	@RequestMapping(value = "/admin/faq/update", method = RequestMethod.GET)
	public ModelAndView updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ModelAndView mav = new ModelAndView("admin/faq/write");
		
		FaqDAO dao = new FaqDAO();
		
		try {
			long faqNum = Long.parseLong(req.getParameter("faqNum"));
			String page = req.getParameter("pageNo");
			
			FaqDTO dto = dao.findById(faqNum);
			if(dto == null) {
				throw new NullPointerException();
			}
			
			List<FaqDTO> listCategory = dao.listCategory(1);
			
			mav.addObject("dto", dto);
			mav.addObject("listCategory", listCategory);
			mav.addObject("pageNo", page);
			mav.addObject("mode", "update");
			
		} catch (Exception e) {
			resp.sendError(406);
			throw e;
		}
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value = "/admin/faq/update", method = RequestMethod.POST)
	public Map<String, Object> updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, Object> model = new HashMap<String, Object>();
		String state = "false";
	
		FaqDAO dao = new FaqDAO();
	
		try {
			FaqDTO dto = new FaqDTO();
		
			dto.setFaqCateNum(Long.parseLong(req.getParameter("faqCateNum")));
			dto.setFaqTitle(req.getParameter("faqTitle"));
			dto.setFaqContent(req.getParameter("faqContent"));
			dto.setFaqNum(Long.parseLong(req.getParameter("faqNum")));
		
			dao.updateFaq(dto);
		
			state = "true";
		} catch (Exception e) {
		}
		model.put("state", state);
	
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/admin/faq/delete", method = RequestMethod.POST)
	public Map<String, Object> deleteFaq(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, Object> model = new HashMap<String, Object>();
		String state = "false";
		
		FaqDAO dao = new FaqDAO();
		
		try {
			long faqNum = Long.parseLong(req.getParameter("faqNum"));
			dao.deleteFaq(faqNum);
			
			state = "true";
			
		} catch (Exception e) {
		}
		model.put("state", state);
		
		return model;
	}
	
	@RequestMapping(value = "/admin/faq/listAllCategory", method = RequestMethod.GET)
	public ModelAndView	listCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ModelAndView mav = new ModelAndView("/admin/faq/listCategory");

		FaqDAO dao = new FaqDAO();
		try {
			List<FaqDTO> listCategory = dao.listCategory(0);
			
			mav.addObject("listCategory", listCategory);
			
		} catch (Exception e) {
			resp.sendError(406);
			throw e;
		}
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value = "/admin/faq/insertCategory", method = RequestMethod.POST)
	public Map<String, Object> insertCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, Object> model = new HashMap<String, Object>();
		String state = "false";
		
		FaqDAO dao = new FaqDAO();
		
		try {
			FaqDTO dto = new FaqDTO();
			
			dto.setFaqCateName(req.getParameter("faqCateName"));
			dto.setOrderNo(Integer.parseInt(req.getParameter("orderNo")));
			dto.setEnabled(Integer.parseInt(req.getParameter("enabled")));
			
			dao.insertFaqCategory(dto);
			
			state = "true";
			
		} catch (Exception e) {
		}
		
		model.put("state", state);
		
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = "/admin/faq/updateCategory", method = RequestMethod.POST)
	public Map<String, Object> updateCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, Object> model = new HashMap<String, Object>();
		
		String state = "false";
		
		FaqDAO dao = new FaqDAO();
		
		try {
			FaqDTO dto = new FaqDTO();
			
			dto.setFaqCateNum(Long.parseLong(req.getParameter("faqCateNum")));
			dto.setFaqCateName(req.getParameter("faqCateName"));
			dto.setOrderNo(Integer.parseInt(req.getParameter("orderNo")));
			dto.setEnabled(Integer.parseInt(req.getParameter("enabled")));
			
			dao.updateFaqCategory(dto);
			
			state = "true";
		} catch (Exception e) {
		}
		
		model.put("state", state);
		
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = "/admin/faq/deleteCategory", method = RequestMethod.POST)
	public Map<String, Object> deleteCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, Object> model = new HashMap<String, Object>();
		
		FaqDAO dao = new FaqDAO();
		String state = "false";
		
		try {
			long faqCateNum = Long.parseLong(req.getParameter("faqCateNum"));
			dao.deleteFaqlCategory(faqCateNum);
			state = "true";
			
		} catch (Exception e) {
		}
		
		model.put("state", state);
		
		return model;
	}
	
}
