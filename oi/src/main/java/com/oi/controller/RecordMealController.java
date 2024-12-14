package com.oi.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

import com.oi.dao.RecordMealDAO;
import com.oi.dto.LoginDTO;
import com.oi.dto.RecordMealDTO;
import com.oi.mvc.annotation.Controller;
import com.oi.mvc.annotation.RequestMapping;
import com.oi.mvc.annotation.RequestMethod;
import com.oi.mvc.view.ModelAndView;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class RecordMealController {
	
	@RequestMapping(value = "/recordmeal/mealmain", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1 ; ;				// 월은 0월부터 11월까지
		int date = cal.get(Calendar.DATE);
		
		String today = String.format("%04d%02d%02d", year, month, date);
		
		ModelAndView mav = new ModelAndView("recordmeal/recordmeal");
		mav.addObject("today", today);
		return mav;
	}
	
	
	//AJAX-TEXT
	@RequestMapping(value = "/recordmeal/mealmonth", method = RequestMethod.GET)
	public ModelAndView mealmonth(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ModelAndView mav = new ModelAndView("/recordmeal/recordmeal");
		
		return mav;
	}

	
	
	@RequestMapping(value = "/recordmeal/mealinsert")
	public ModelAndView mealinsert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		LoginDTO login = (LoginDTO)session.getAttribute("member");
		RecordMealDTO dto = new RecordMealDTO();
		dto.setMemberId(login.getUserId());
		
		
		
		
		return new ModelAndView("recordmeal/");
	}
	
	@RequestMapping(value = "/recordmeal/mealupdate")
	public ModelAndView mealupdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return new ModelAndView("recordmeal/");
	}
	
	@RequestMapping(value = "/recordmeal/mealdelete")
	public ModelAndView mealdelete(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
		return new ModelAndView("recordmeal/");
	}
	
	
}
