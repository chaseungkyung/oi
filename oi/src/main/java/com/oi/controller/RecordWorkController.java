package com.oi.controller;

import java.io.IOException;

import com.oi.mvc.annotation.Controller;
import com.oi.mvc.annotation.RequestMapping;
import com.oi.mvc.annotation.RequestMethod;
import com.oi.mvc.view.ModelAndView;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class RecordWorkController {
	
	@RequestMapping(value = "/todayworkout/main", method = RequestMethod.GET)
	public ModelAndView intomain(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		return new ModelAndView("todayworkout/todayworkoutmain");
	}
	
	@RequestMapping(value = "/todayworkout/writedown")
	public ModelAndView putmeal(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		return new ModelAndView("todayworkout/");
	}
	
	@RequestMapping(value = "/todayworkout/updatedoing")
	public ModelAndView mealupdate(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		return new ModelAndView("todayworkout/");
	}
	
	@RequestMapping(value = "/todayworkout/deletedoing")
	public ModelAndView mealdelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		return new ModelAndView("todayworkout/");
	}
	
	
}
