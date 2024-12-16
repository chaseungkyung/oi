package com.oi.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.oi.dao.RecordMealDAO;
import com.oi.dto.LoginDTO;
import com.oi.dto.RecordMealDTO;
import com.oi.mvc.annotation.Controller;
import com.oi.mvc.annotation.RequestMapping;
import com.oi.mvc.annotation.RequestMethod;
import com.oi.mvc.annotation.ResponseBody;
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
	@ResponseBody
	@RequestMapping(value = "/recordmeal/mealinsert" , method =  RequestMethod.GET)
	public Map<String, Object> mealinsert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 식단 저장
		Map<String, Object> model = new HashMap<String, Object>();
		
		RecordMealDAO dao = new RecordMealDAO();
		
		HttpSession session = req.getSession();
		LoginDTO log = (LoginDTO)session.getAttribute("member");
		String state = "false";
		
		try {
			RecordMealDTO dto = new RecordMealDTO();
			
			System.out.println("식단시간"+req.getParameter("dietFoodTime"));
			System.out.println("식단이름"+req.getParameter("dietFoodName"));
			dto.setMemberId(log.getUserId());
			dto.setDietFoodTime(req.getParameter("dietFoodTime"));
			dto.setDietFoodName(req.getParameter("dietFoodName"));
			dto.setDietFoodDate(req.getParameter("dietFoodDate"));
			dto.setDietFoodUnit(req.getParameter("dietFoodUnit"));
			
			
			dao.insertRecord(dto);
			
			state = "true";

		} catch (Exception e) {
			e.printStackTrace();
		}
		model.put("state", state);
		
		return model;
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
