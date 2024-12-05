package com.oi.controller;

import java.io.IOException;

import com.oi.dao.LoginDAO;
import com.oi.dto.LoginDTO;
import com.oi.mvc.annotation.Controller;
import com.oi.mvc.annotation.RequestMapping;
import com.oi.mvc.annotation.RequestMethod;
import com.oi.mvc.view.ModelAndView;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	private LoginDAO dao = new LoginDAO();
	
	@RequestMapping(value="/access/login" , method = RequestMethod.GET)
	public ModelAndView loginform(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ModelAndView mav = new ModelAndView("entry/login");
		
		return mav;
	}
	
	// 로그인 폼 작성시 
	@RequestMapping(value="/access/login" , method = RequestMethod.POST)
	public ModelAndView loginComplete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 넘어오는 파라미터 : id, pwd 
		ModelAndView mav = null;
		
		HttpSession session = req.getSession();
		
		String id = req.getParameter("userid");
		String pwd = req.getParameter("userpwd");
		
		LoginDTO dto = dao.getLoginInfo(id, pwd);
		if(dto == null) {
			mav = new ModelAndView("entry/login");
			mav.addObject("msg", "아이디 또는 비밀번호가 일치하지않습니다");
			return mav;
		}
		
		session.setMaxInactiveInterval(60*30);
		session.setAttribute("member", dto);
		
		mav = new ModelAndView("redirect:/main");
		return mav;
	}
	
	// 로그아웃 
	@RequestMapping(value="/access/logout" , method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ModelAndView mav = new ModelAndView("redirect:/main");
		
		HttpSession session =  req.getSession();
		session.invalidate();
		
		return mav;
	}

}
