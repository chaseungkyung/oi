package com.oi.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.oi.dao.LoginDAO;
import com.oi.dto.LoginDTO;
import com.oi.dto.RegisterDTO;
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
		
		String preuri = (String)session.getAttribute("preLoginURI");
		session.removeAttribute("preLoginURI");
		
		if(preuri != null) {
			return new ModelAndView(preuri);
		}
		
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
	
	@RequestMapping(value="/access/register" , method = RequestMethod.GET)
	public ModelAndView registerForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ModelAndView mav = new ModelAndView("entry/register");
		return mav;
	}
	
	@RequestMapping(value="/access/register" , method = RequestMethod.POST)
	public ModelAndView registerSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ModelAndView mav = new ModelAndView("redirect:/access/login");
		
		RegisterDTO dto = new RegisterDTO();
		StringBuilder tel = new StringBuilder();
		StringBuilder email = new StringBuilder();
		StringBuilder address = new StringBuilder();
		
		try {
			dto.setUsername(req.getParameter("username"));
			dto.setNickname(req.getParameter("nickname"));
			dto.setPwd(req.getParameter("password"));
			dto.setDob(req.getParameter("dob"));
			
			tel.append(req.getParameter("phone1"));
			tel.append(req.getParameter("phone2"));
			tel.append(req.getParameter("phone3"));
			dto.setTel(tel.toString());
			
			email.append(req.getParameter("email1"));
			email.append("@");
			email.append(req.getParameter("email2"));
			dto.setEmail(email.toString());
			
			dto.setAddressnum(Integer.parseInt(req.getParameter("zipcode")));
			address.append(req.getParameter("address1"));
			if(req.getParameter("address2") != null ) {
				address.append(req.getParameter("address2"));
			}
			dto.setAddress(address.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/access/idcheck" , method = RequestMethod.GET)
	public Map<String, Object> idCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, Object> model = new HashMap<String, Object>();
		
		String id = req.getParameter("id");
		try {
			boolean state = dao.idCheck(id);
			
			if(state) {
				model.put("state", "사용가능한 아이디입니다");
				model.put("result", "true");
			}else {
				model.put("state", "이미 사용중인 아이디입니다");
				model.put("result", "false");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

}
