package com.oi.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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
	
	@RequestMapping(value = "/recordmeal/mealmain") // method 삭제
	public ModelAndView main(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1 ; ;				// 월은 0월부터 11월까지
		int date = cal.get(Calendar.DATE);
		
		String today = String.format("%04d%02d%02d", year, month, date);
		
		HttpSession session = req.getSession();
	    LoginDTO log = (LoginDTO) session.getAttribute("member");
	    
		try {
			String memberId = log.getUserId();
	        RecordMealDAO dao = new RecordMealDAO();
	        ModelAndView mav = new ModelAndView("mealList");
	        List<RecordMealDTO> mealList = dao.getMealListToday(memberId);
	      
	        mav.addObject("mealList", mealList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ModelAndView mav = new ModelAndView("recordmeal/recordmeal");
		mav.addObject("today", today);
		return mav;
	
	}
	
	@ResponseBody
	@RequestMapping(value = "/recordmeal/mealList", method = RequestMethod.GET)
	public Map<String, Object> mealList(HttpServletRequest req, HttpServletResponse resp) {
		Map<String, Object> model = new HashMap<String, Object>();
	    HttpSession session = req.getSession();
	    LoginDTO log = (LoginDTO) session.getAttribute("member");

	    try {
	        String memberId = log.getUserId();
	        RecordMealDAO dao = new RecordMealDAO();
	        List<RecordMealDTO> mealList = dao.getMealListByMemberId(memberId);

	        model.put("mealList", mealList);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.put("error", "데이터를 불러오는 중 오류가 발생했습니다.");
	    }

	    return model;
	}
		
	@ResponseBody
	@RequestMapping(value = "/recordmeal/mealinsert" , method =  RequestMethod.POST)
	public Map<String, Object> mealinsert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 식단 저장
		RecordMealDAO dao = new RecordMealDAO();
		HttpSession session = req.getSession();
		LoginDTO log = (LoginDTO)session.getAttribute("member");
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		try {
			RecordMealDTO dto = new RecordMealDTO();
			
			dto.setMemberId(log.getUserId());		//userId는... session에 저장됐으니까 따로 log로 불러오고..
			
			dto.setDietFoodTime(req.getParameter("dietFoodTime"));
			dto.setDietFoodName(req.getParameter("dietFoodName"));
			dto.setDietFoodDate(req.getParameter("dietFoodDate"));
			dto.setCapacity(Integer.parseInt(req.getParameter("capacity")));
			dto.setKcal(Integer.parseInt(req.getParameter("kcal")));
			
	        System.out.println("Received dietFoodTime: " + dto.getDietFoodTime());
	        System.out.println("Received dietFoodName: " + dto.getDietFoodName());
	        System.out.println("Received dietFoodDate: " + dto.getDietFoodDate());
	        System.out.println("Received capacity: " + dto.getCapacity());
	        System.out.println("Received kcal: " + dto.getKcal());
	        
			dao.insertRecord(dto);
			
			model.put("state", "true");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
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
