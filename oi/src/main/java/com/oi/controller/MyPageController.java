package com.oi.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import com.oi.dao.MyPageDAO;
import com.oi.dto.LoginDTO;
import com.oi.dto.MemberDTO;
import com.oi.mvc.annotation.Controller;
import com.oi.mvc.annotation.RequestMapping;
import com.oi.mvc.annotation.RequestMethod;
import com.oi.mvc.view.ModelAndView;
import com.oi.util.FileManager;
import com.oi.util.MyMultipartFile;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;


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
        
        FileManager fileManager = new FileManager();
		
        // 파일 저장 경로
     	String root = session.getServletContext().getRealPath("/");
     	String pathname = root + "uploads" + File.separator + "photo";

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
        // 폼에서 전송된 값 받기
        String userPwd = req.getParameter("userPwd");
        String userNickName = req.getParameter("usernickName");
        String zipCode = req.getParameter("zipCode");
        String address = req.getParameter("address");
        String email = req.getParameter("email");
        String height = req.getParameter("height");
        String weight = req.getParameter("weight");
        String bodyRecordDate = req.getParameter("registerDate");
        String bmi = req.getParameter("bmi");
        String muscle = req.getParameter("muscle");


        // 수정할 값 설정
        member.setMemberPw(userPwd);
        member.setNickName(userNickName);
        member.getMemberDetails().setAddress(address);
        member.getMemberDetails().setAddressNum(zipCode);
        member.getMemberDetails().setEmail(email);

        member.getBodyRecord().setHeight(Integer.parseInt(height));
        member.getBodyRecord().setWeight(Integer.parseInt(weight));
        member.getBodyRecord().setBodyRecordDate(bodyRecordDate);
        member.getBodyRecord().setBodyFat(Integer.parseInt(bmi));
        member.getBodyRecord().setBodyMuscle(Integer.parseInt(muscle));
        
        // 파일 업로드 처리
        Part filePart = req.getPart("profileImage"); // 폼의 input name과 일치해야함.
        if (filePart != null && filePart.getSize() > 0) {
            // 기존 프로필 사진 삭제 (선택 사항)
            String oldProfilePhoto = member.getMemberDetails().getProfilePhoto();
            if (oldProfilePhoto != null && !oldProfilePhoto.isEmpty()) {
                fileManager.doFiledelete(pathname, oldProfilePhoto);
            }
            
            // 새 프로필 사진 업로드
            MyMultipartFile uploadedFile = fileManager.doFileUpload(filePart, pathname);
            if (uploadedFile != null) {
                member.getMemberDetails().setProfilePhoto(uploadedFile.getSaveFilename());
            } else {
                // 업로드 실패 처리
                ModelAndView mav = new ModelAndView("error");
                mav.addObject("msg", "프로필 사진 업로드에 실패했습니다.");
                return mav;
            }
        }

   
        try { // 정보 수정
            myPageDAO.updateMemberInfo(member);
            // 사진을 바꿨으니까, 세션을 한번 더 하고, 저장된 사진 정보 변경
            if (member.getMemberDetails().getProfilePhoto() != null ) {
            	login.setSaveprofile(member.getMemberDetails().getProfilePhoto()); 	
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            ModelAndView mav = new ModelAndView("error");
            mav.addObject("msg", "업데이트 중 오류가 발생하였습니다.");
            return mav;
        }

        return new ModelAndView("redirect:/mypage/mypage");
    }
    
    @RequestMapping("/mypage/comment")
    public ModelAndView commentPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        HttpSession session = req.getSession();
        LoginDTO login = (LoginDTO) session.getAttribute("member");

        if (login == null) {
            return new ModelAndView("redirect:/access/login");
        }

        // 댓글 페이지로 이동
        return new ModelAndView("mypage/comment");
    }
    
    @RequestMapping("/mypage/mycomment")
    public ModelAndView mycommentPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	HttpSession session = req.getSession();
    	LoginDTO login = (LoginDTO) session.getAttribute("member");
    	
    	if (login == null) {
    		return new ModelAndView("redirect:/access/login");
    	}
    	
    	// 댓글 페이지로 이동
    	return new ModelAndView("mypage/comment");
    }
    
    @RequestMapping("/mypage/boardlike")
    public ModelAndView boardLike(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	HttpSession session = req.getSession();
    	LoginDTO login = (LoginDTO) session.getAttribute("member");
    	
    	if (login == null) {
    		return new ModelAndView("redirect:/access/login");
    	}
    	
    	// 댓글 페이지로 이동
    	return new ModelAndView("mypage/comment");
    }
   
    @RequestMapping("/mypage/todayworklike")
    public ModelAndView todayworkLike(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	HttpSession session = req.getSession();
    	LoginDTO login = (LoginDTO) session.getAttribute("member");
    	
    	if (login == null) {
    		return new ModelAndView("redirect:/access/login");
    	}
    	
    	// 댓글 페이지로 이동
    	return new ModelAndView("mypage/todayworklike");
    }
}
