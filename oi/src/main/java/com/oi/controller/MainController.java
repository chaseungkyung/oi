package com.oi.controller;

import java.io.IOException;
import java.util.List;

import com.oi.dao.MarketDAO;
import com.oi.dto.MarketDTO;
import com.oi.mvc.annotation.Controller;
import com.oi.mvc.annotation.RequestMapping;
import com.oi.mvc.view.ModelAndView;
import com.oi.util.MyUtil;
import com.oi.util.MyUtilBootstrap;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class MainController {
	@RequestMapping("/main")
	public ModelAndView main(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ModelAndView mav = new ModelAndView("entry/main");
		MarketDAO dao = new MarketDAO();
        MyUtil util = new MyUtilBootstrap();

        try {
            String page = req.getParameter("page");
            int current_page = 1;
            if (page != null) {
                current_page = Integer.parseInt(page);
            }

            // 전체데이터 개수
            int dataCount = dao.dataCount();

            // 전체페이지수
            int size = 12;
            int total_page = util.pageCount(dataCount, size);
            if (current_page > total_page) {
                current_page = total_page;
            }

            // 게시물 가져오기
            int offset = (current_page - 1) * size;
            if(offset < 0) offset = 0;

            List<MarketDTO> list = dao.listMarket(offset, size);

            //데이터 받아오기
            for (MarketDTO dto : list){
                dao.getFiles(dto);


               for  ( long s : dto.getFile().getFile().keySet()){
                   System.out.println(dto.getFile().getFile().get(s));
               }
            }

            // 페이징 처리
            String cp = req.getContextPath();
            String articleUrl = cp + "/marketplace/article?page=" + current_page;


            // 포워딩할 list에 전달할 값
            mav.addObject("list", list);
            mav.addObject("dataCount", dataCount);
            mav.addObject("articleUrl", articleUrl);



        }catch (Exception e){
e.printStackTrace();
        }


		return mav;
	}
}
