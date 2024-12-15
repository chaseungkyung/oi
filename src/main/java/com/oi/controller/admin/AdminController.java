package com.oi.controller.admin;

import java.io.IOException;
import java.rmi.ServerException;

import com.oi.mvc.annotation.Controller;
import com.oi.mvc.annotation.RequestMapping;
import com.oi.mvc.view.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AdminController {
	
	@RequestMapping(value = "/admin/main")
	public ModelAndView main(HttpServletRequest req, HttpServletResponse resp) throws ServerException, IOException {
		
		ModelAndView mav = new ModelAndView("admin/home/main");
		
		return mav;
	}
			
	 
}
