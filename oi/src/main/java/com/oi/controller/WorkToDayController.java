package com.oi.controller;

import java.io.IOException;

import com.oi.mvc.annotation.Controller;
import com.oi.mvc.annotation.RequestMapping;
import com.oi.mvc.view.ModelAndView;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class WorkToDayController {
	
	@RequestMapping(value = "/completeworkout/main")
	public ModelAndView intomain(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		return new ModelAndView("worktoday/wtdmain");
	}
}
