package com.oi.controller;

import java.io.IOException;
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
		RecordMealDAO dao = new RecordMealDAO();
		
		HttpSession session = req.getSession();
		LoginDTO dto = (LoginDTO)session.getAttribute("member");
		
		ModelAndView mav = new ModelAndView("recordmeal/month");

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1; // 0 ~ 11
		int todayYear = year;
		int todayMonth = month;
		int todayDate = cal.get(Calendar.DATE);
		
		try {
			String y = req.getParameter("year");
			String m = req.getParameter("month");
			
			if( y != null) {
				year = Integer.parseInt(y);
			}
			if( m != null) {
				month = Integer.parseInt(m);
			}
			
			// year년 month월의 1일은 무슨 요일인지 구하기
			cal.set(year, month -1, 1);
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH) +1;
			int week = cal.get(Calendar.DAY_OF_WEEK	);
			
			// 첫 주의 1일 이전 날짜
			Calendar scal = (Calendar) cal.clone();
			int syear = scal.get(Calendar.YEAR);
			int smonth = scal.get(Calendar.MONTH) + 1;
			int sdate = scal.get(Calendar.DATE);
			
			// 마지막 주의.. year 년도. month월 . 마지막 주의 마지막 요일 (일-토) 날짜
			Calendar ecal = (Calendar) cal.clone();
			int eyear = ecal.get(Calendar.YEAR);
			int emonth = ecal.get(Calendar.MONTH) + 1;
			int edate = ecal.get(Calendar.DATE);
			
			
			
			
			
			
			
		
			
		} catch (Exception e) {
			
		}
		
		return new ModelAndView("recordmeal/recordmeal");
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
