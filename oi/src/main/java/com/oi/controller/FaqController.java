package com.oi.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import com.oi.dao.FaqDAO;
import com.oi.dto.FaqDTO;
import com.oi.mvc.annotation.Controller;
import com.oi.mvc.annotation.RequestMapping;
import com.oi.mvc.annotation.RequestMethod;
import com.oi.mvc.view.ModelAndView;
import com.oi.util.MyUtil;
import com.oi.util.MyUtilBootstrap;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class FaqController {
	
	@RequestMapping(value = "/faq/main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ModelAndView mav = new ModelAndView("faq/main");
		
		FaqDAO dao = new FaqDAO();
		
		try {
			List<FaqDTO> listCategory = dao.listCategory(1);
			
			mav.addObject("listCategory", listCategory);
			
		} catch (Exception e) {
		} 
		return mav;
	}
	
	@RequestMapping(value = "/faq/list", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ModelAndView mav = new ModelAndView("faq/list");
		
		MyUtil util = new MyUtilBootstrap();
		FaqDAO dao = new FaqDAO();
		
		try {
			int size = 0;
			int total_page = 0;
			int dataCount = 0;
			
			String pageNo = req.getParameter("pageNO");
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
			mav.addObject("current_page", current_page);
			mav.addObject("dataCount", dataCount);
			mav.addObject("total_page", total_page);
			mav.addObject("paging", paging);
			
		} catch (Exception e) {
			resp.sendError(406);
			throw e;
		}
		return mav;
	}
}
