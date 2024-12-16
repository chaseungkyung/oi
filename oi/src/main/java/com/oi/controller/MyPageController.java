package com.oi.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.oi.dao.MyPageDAO;
import com.oi.dto.LoginDTO;
import com.oi.dto.MemberDTO;
import com.oi.mvc.annotation.Controller;
import com.oi.mvc.annotation.RequestMapping;
import com.oi.mvc.annotation.RequestMethod;
import com.oi.mvc.view.ModelAndView;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class MyPageController {
    
    private MyPageDAO myPageDAO = new MyPageDAO();

    @RequestMapping(value = "/mypage/mypage", method = RequestMethod.GET)
    public ModelAndView mypage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        LoginDTO login = (LoginDTO) session.getAttribute("member");

        if (login == null) {
            return new ModelAndView("redirect:/access/login");
        }

        String userId = login.getUserId();
        MemberDTO member = myPageDAO.getDetails(userId);

        if (member == null) {
            ModelAndView mav = new ModelAndView("error");
            mav.addObject("msg", "사용자 정보를 찾을 수 없습니다.");
            return mav;
        }

        ModelAndView mav = new ModelAndView("mypage/mypage");
        mav.addObject("member", member);
        mav.addObject("memberDetails", member.getMemberDetails());
        mav.addObject("bodyRecord", member.getBodyRecord());

        return mav;
    }

    @RequestMapping(value = "/mypage/updatePersonal", method = RequestMethod.POST)
    public ModelAndView updatePersonal(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        LoginDTO login = (LoginDTO) session.getAttribute("member");

        if (login == null) {
            return new ModelAndView("redirect:/access/login");
        }

        String userId = login.getUserId();

        // 폼에서 전송된 값 받기
        String userPwd = req.getParameter("userPwd");
        String userNickName = req.getParameter("usernickName");
        String zipCode = req.getParameter("zipCode");
        String address = req.getParameter("address");
        String email = req.getParameter("email");
        String height = req.getParameter("height");
        String weight = req.getParameter("weight");
        String bmi = req.getParameter("bmi");
        String muscle = req.getParameter("muscle");

        MemberDTO member = myPageDAO.getDetails(userId);
        if (member == null) {
            ModelAndView mav = new ModelAndView("error");
            mav.addObject("msg", "사용자 정보를 찾을 수 없습니다.");
            return mav;
        }

        // 수정할 값 설정
        member.setMemberPw(userPwd);
        member.setNickName(userNickName);
        member.getMemberDetails().setAddress(address);
        member.getMemberDetails().setAddressNum(zipCode); // zipCode를 addressNum으로 가정
        member.getMemberDetails().setEmail(email);

        member.getBodyRecord().setHeight(Integer.parseInt(height));
        member.getBodyRecord().setWeight(Integer.parseInt(weight));
        member.getBodyRecord().setBodyFat(Integer.parseInt(bmi));
        member.getBodyRecord().setBodyMuscle(Integer.parseInt(muscle));

        try {
            myPageDAO.updateMemberInfo(member);
        } catch (SQLException e) {
            e.printStackTrace();
            ModelAndView mav = new ModelAndView("error");
            mav.addObject("msg", "업데이트 중 오류가 발생하였습니다.");
            return mav;
        }

        return new ModelAndView("redirect:/oi/mypage/mypage");
    }
}
