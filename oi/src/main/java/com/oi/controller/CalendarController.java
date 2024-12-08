package com.oi.controller;

import java.io.IOException;

import com.oi.mvc.annotation.Controller;
import com.oi.mvc.annotation.RequestMapping;
import com.oi.mvc.view.ModelAndView;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CalendarController {

	@RequestMapping(value = "/mypage/calendar/main")
	public ModelAndView intomain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		return new ModelAndView("calendar/calendarmain");
	}

	@RequestMapping(value = "/mypage/calendar/calendarinsert")
	public ModelAndView calendarinsert(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		return new ModelAndView("");
	}
	
	@RequestMapping(value = "/mypage/calendar/calendarupdate")
	public ModelAndView calendarupdate(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		return new ModelAndView("");
	}
	
	@RequestMapping(value = "/mypage/calendar/calendardelete")
	public ModelAndView calendardelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		return new ModelAndView("");
	}
}
