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

	    // 로그인 정보가 없으면 로그인 페이지로 리다이렉트
	    if (login == null) {
	        return new ModelAndView("redirect:/access/login");
	    }

	    // LoginDTO에서 userId를 가져옴
	    String userId = login.getUserId();

	    // userId를 이용해 닉네임 가져오기
	    MemberDTO member = myPageDAO.getNicknameById(userId);
	    if (member == null) {
	        // 닉네임을 찾을 수 없는 경우 처리
	        ModelAndView mav = new ModelAndView("error");
	        mav.addObject("msg", "사용자 정보를 찾을 수 없습니다.");
	        return mav;
	    }

	    // ModelAndView에 닉네임 추가
	    ModelAndView mav = new ModelAndView("mypage/mypage");
	    mav.addObject("nickname", member.getNickName());

	    return mav;
	}
}