package com.oi.controller;

import java.io.IOException;

import com.oi.dao.MyPageDAO;
import com.oi.dto.LoginDTO;
import com.oi.dto.MemberDTO;
import com.oi.mvc.annotation.Controller;
import com.oi.mvc.annotation.RequestMapping;
import com.oi.mvc.view.ModelAndView;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class MyPageController {
	
	private MyPageDAO myPageDAO = new MyPageDAO();

	@RequestMapping(value = "/mypage/mypage")
	public ModelAndView mypage(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {

	    // 세션에서 로그인 정보 가져오기
	    HttpSession session = req.getSession();
	    LoginDTO login = (LoginDTO) session.getAttribute("member");

	    if (login == null) {
	        return new ModelAndView("redirect:/access/login");
	    }

	    // LoginDTO에서 userId를 가져와서 memberId로 사용
	    String userId = login.getUserId();

	    // userId를 이용해 MemberDTO 전체 정보를 가져오기
	    MemberDTO member = myPageDAO.getDetails(userId);

	    if (member == null) {
	        ModelAndView mav = new ModelAndView("error");
	        mav.addObject("msg", "사용자 정보를 찾을 수 없습니다.");
	        return mav;
	    }

	    // ModelAndView에 회원 정보 추가
	    ModelAndView mav = new ModelAndView("mypage/mypage");
	    mav.addObject("member", member);
	    mav.addObject("memberDetails", member.getMemberDetails());
	    mav.addObject("bodyRecord", member.getBodyRecord());

	    return mav;
	}
}