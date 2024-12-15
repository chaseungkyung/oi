package com.oi.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.oi.dao.FindExerciseDAO;
import com.oi.dto.LoginDTO;
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
public class FindGymController {
	@RequestMapping(value = "/findGymController/view", method = RequestMethod.GET)
	public ModelAndView findGymController(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return new ModelAndView("/findgym/findgym");
	}
	
	@ResponseBody
	@RequestMapping(value = "/findGymController/list", method = RequestMethod.GET)
	public Map<String, Object> listLocationGym(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, double[]> map = new HashMap<String, double[]>();
		
		FindExerciseDAO dao = new FindExerciseDAO();
		double lng = Double.parseDouble(req.getParameter("lng"));
		double lat = Double.parseDouble(req.getParameter("lat"));
		try {
			map = dao.listGym(lng, lat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.put("gymList", map);
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = "/findGymController/address", method = RequestMethod.GET)
	public Map<String, Object> address(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, Object> model = new HashMap<String, Object>();
		FindExerciseDAO dao = new FindExerciseDAO();
		
		HttpSession session = req.getSession();
		String address = "";
		try {
			if(session.getAttribute("member") == null) {
				address = "서울 마포구 월드컵북로 21";
			} else {
				LoginDTO info = (LoginDTO)session.getAttribute("member");
				
				address = dao.memberAddress(info.getUserId());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.put("address", address);
		return model;
	}

}
