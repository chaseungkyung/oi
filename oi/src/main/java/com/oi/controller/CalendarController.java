package com.oi.controller;

import java.io.IOException;
import java.util.Calendar;

import com.oi.mvc.annotation.Controller;
import com.oi.mvc.annotation.RequestMapping;
import com.oi.mvc.view.ModelAndView;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CalendarController {

	@RequestMapping(value = "/calendar/calendarmain")
	public ModelAndView intomain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// 메인에 달력 띄우기
		ModelAndView mav = new ModelAndView("calendar/calendarmain");

		try {
			Calendar cal = Calendar.getInstance();
			int ty = cal.get(Calendar.YEAR);
			int tm = cal.get(Calendar.MONTH) + 1;
			int td = cal.get(Calendar.DATE);

			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;

			String sy = req.getParameter("year");
			String sm = req.getParameter("month");

			if (sy != null) {
				year = Integer.parseInt(sy);
			}

			if (sm != null) {
				month = Integer.parseInt(sm);
			}

			cal.set(year, month - 1, 1); // 월 - 1
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH) + 1; // + 1 = 월

			int week = cal.get(Calendar.DAY_OF_WEEK); // 1~7

			// jstl 로 변경 -> 여기부터 추가
			int lastDate = cal.getActualMaximum(Calendar.DATE);

			Calendar preCal = (Calendar) cal.clone();
			preCal.add(Calendar.DATE, -(week - 1));
			int preDate = preCal.get(Calendar.DATE);

			mav.addObject("ty", ty);
			mav.addObject("tm", tm);
			mav.addObject("td", td);

			mav.addObject("year", year);
			mav.addObject("month", month);
			mav.addObject("lastDate", lastDate);
			mav.addObject("preDate", preDate);
			mav.addObject("week", week);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/calendar/putmeal")
	public ModelAndView putmeal(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return new ModelAndView("recordmeal/write");
	}
	
	
	/*
	@RequestMapping(value = "")
	public ModelAndView calinsert(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		return new ModelAndView("");
	}

	@RequestMapping(value = "")
	public ModelAndView calupdate(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		return new ModelAndView("");
	}

	@RequestMapping(value = "")
	public ModelAndView caldelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		return new ModelAndView("");
	}
	*/
	
}
