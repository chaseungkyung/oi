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
public class RecordMealController {
	
	@RequestMapping(value = "/recordmeal/main", method = RequestMethod.GET)
	public ModelAndView intomain(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		return new ModelAndView("recordmeal/recordmeal");
	}
	
	@RequestMapping(value = "/recordmeal/putmeal")
	public ModelAndView putmeal(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		return new ModelAndView("recordmeal/");
	}
	
	@RequestMapping(value = "/recordmeal/mealupdate")
	public ModelAndView mealupdate(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		return new ModelAndView("recordmeal/");
	}
	
	@RequestMapping(value = "/recordmeal/mealdelete")
	public ModelAndView mealdelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		return new ModelAndView("recordmeal/");
	}
	
	
}
