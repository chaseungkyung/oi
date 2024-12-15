package com.oi.controller;

import com.oi.mvc.annotation.Controller;
import com.oi.mvc.annotation.RequestMapping;
import com.oi.mvc.view.ModelAndView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Controller
public class OimarketController {

    @RequestMapping(value = "/marketplace/main")
    public ModelAndView listoi(HttpServletRequest req , HttpServletResponse resp)throws ServletException, IOException{
        return new ModelAndView("oimarket/main");
    }

    @RequestMapping(value = "/marketplace/write")
    public ModelAndView writeForm(HttpServletRequest req , HttpServletResponse resp)throws ServletException, IOException{
        //거래 작성 폼
        ModelAndView mav = new ModelAndView("oimarket/write");





    return mav;
    }


}
