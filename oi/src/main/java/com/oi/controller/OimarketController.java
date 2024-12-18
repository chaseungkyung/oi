package com.oi.controller;

import com.oi.dao.MarketDAO;
import com.oi.dto.LoginDTO;
import com.oi.dto.MarketDTO;
import com.oi.dto.Marketfile;
import com.oi.mvc.annotation.Controller;
import com.oi.mvc.annotation.RequestMapping;
import com.oi.mvc.annotation.RequestMethod;
import com.oi.mvc.view.ModelAndView;
import com.oi.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class OimarketController {
    private MarketDAO dao = new MarketDAO();
    private FileManager fileManager = new FileManager();
    private MyUtil util = new MyUtilGeneral();


    @RequestMapping(value = "/marketplace/registration",method = RequestMethod.GET)
    public ModelAndView writeForm(HttpServletRequest req , HttpServletResponse resp)throws ServletException, IOException{
        //글쓰기 폼
        ModelAndView mav = new ModelAndView("oimarket/write");





    return mav;
    }


    @RequestMapping(value = "/marketplace/registration",method = RequestMethod.POST)
    public ModelAndView writeFormSubmit(HttpServletRequest req , HttpServletResponse resp)throws ServletException, IOException{
        //거래 작성 폼
        //넘어온 파라미터 : content,file
        ModelAndView mav = new ModelAndView("redirect:/marketplace/main");
        HttpSession session = req.getSession();
        LoginDTO login = (LoginDTO)session.getAttribute("member");
        MarketDTO dto = new MarketDTO();

        try {
            //파일 저장

            String pathname = req.getServletContext().getRealPath("/")+"uploads"+ File.separator + "photo";
            Collection<Part> parts = req.getParts();

            List<MyMultipartFile> files = fileManager.doFileUpload(parts,pathname);

            Marketfile filedto = dto.getFile();
            String [] names = new String [files.size()];

            for (int i =0;i<files.size();i++){
                names[i] = files.get(i).getSaveFilename();
            }

            filedto.setSaveFileName(names);
            dto.setMemberId(login.getUserId());
            String childCategoryParam = req.getParameter("childCategory");
            int childCategoryNum = Integer.parseInt(childCategoryParam);
            dto.setCategoryNum(childCategoryNum);
            String sb=null;

            sb=req.getParameter("subject");
            System.out.println(sb);
            dto.setGoodsName(req.getParameter("subject"));
            dto.setGoodsPrice(Integer.parseInt(req.getParameter("price")));


            dto.setGoodsExp(req.getParameter("content"));

            boolean result = dao.insertgoods(dto);
            System.out.println(result);





        }catch (Exception e){
            e.printStackTrace();
        }





        return mav;
    }

    @RequestMapping(value = "/marketplace/main")
    public ModelAndView list(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        ModelAndView mav = new ModelAndView("oimarket/main");

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
            String listUrl = cp + "/marketplace/main";
            String articleUrl = cp + "/marketplace/article?page=" + current_page;

            String paging = util.paging(current_page, total_page, listUrl);

            // 포워딩할 list에 전달할 값
            mav.addObject("list", list);
            mav.addObject("dataCount", dataCount);
            mav.addObject("articleUrl", articleUrl);
            mav.addObject("page", current_page);
            mav.addObject("total_page", total_page);
            mav.addObject("paging", paging);



        }catch (Exception e){
e.printStackTrace();
        }


        return mav;
    }




    @RequestMapping(value = "/marketplace/productupdate",method = RequestMethod.GET)
    public ModelAndView updategoods(HttpServletRequest req , HttpServletResponse resp)throws ServletException, IOException{

        MarketDAO dao = new MarketDAO();
        HttpSession  session = req.getSession();
        //넘어올 파라미터 : 페이지
//        SessionInfo info =  (SessionInfo) session.getAttribute("member");
        String page= req.getParameter("page");

        try {
            long goodslistnum = Long.parseLong(req.getParameter("GoodsListNum"));
            MarketDTO dto =  dao.findById(goodslistnum);
            if (dto ==null){
                return new ModelAndView("redirect:/marketplace/main?page="+page);
            }

//            //게시물을 올린 사용자가 아니면
//            if (!dto.getMemberId().equals(info.getMemberId())){
//                return new ModelAndView("redirect:/marketplace/main?page="+page);
//            }
            List<MarketDTO> listFile = dao.listMarketFile(goodslistnum);

            ModelAndView mav = new ModelAndView("oimarket/write");
            mav.addObject("dto",dto);
            mav.addObject("page",page);
            mav.addObject("listFile",listFile);

            mav.addObject("mode","update");

            return mav;

        }catch (Exception e){
            e.printStackTrace();
        }








        return new ModelAndView("redirect:/oimarket/main?page="+page);
    }

    //수정 제출
    @RequestMapping(value = "/marketplace/productupdate",method = RequestMethod.POST)
    public ModelAndView updateSubmit(HttpServletRequest req, HttpServletResponse resp)throws ServletException,IOException{
        MarketDAO dao = new MarketDAO();
        HttpSession session = req.getSession();
        FileManager fileManager = new FileManager();
        //파일 저장 경로
        String root = session.getServletContext().getRealPath("/");
        String pathname = root+"uploads"+ File.separator + "photo";
        String page = req.getParameter("page");

        try {
            MarketDTO dto = new MarketDTO();
            dto.setGoodsListNum(Long.parseLong(req.getParameter("GoodsListNum")));
            dto.setGoodsPrice(Integer.parseInt(req.getParameter("price")));
            dto.setGoodsName(req.getParameter("subject"));
            dto.setGoodsExp(req.getParameter("content"));

            List<MyMultipartFile> multiFiles = fileManager.doFileUpload(req.getParts(),pathname);
            if (multiFiles.size() !=0){
                List<String> imageFiles = multiFiles.stream()
                        .map(s->s.getSaveFilename())
                        .collect(Collectors.toList());
                dto.setImageFiles(imageFiles);
            }
                dao.updategoods(dto);
        }catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }



    //글보기
    @RequestMapping(value = "/marketplace/article",method = RequestMethod.GET)
    public ModelAndView articlegoods(HttpServletRequest req , HttpServletResponse resp)throws ServletException,IOException{
        HttpSession session = req.getSession();
        LoginDTO info = (LoginDTO) session.getAttribute("member");
        MarketDAO dao = new MarketDAO();
        String page = req.getParameter("page");
        try {
            long goodsListNum = Long.parseLong(req.getParameter("goodsListNum"));
            MarketDTO dto = dao.findById(goodsListNum);
            if (dto ==null){
                return new ModelAndView("redirect:/Marketplace/main?page="+page);
            }
//            dto.setGoodsExp(dto.getGoodsExp().replaceAll("\n","<br>"));

            List<MarketDTO> listFile = dao.listMarketFile(goodsListNum);


            ModelAndView mav = new ModelAndView("oimarket/article");
            mav.addObject("dto",dto);
            mav.addObject("listFile",listFile);
            mav.addObject("page",page);
            return mav;

        }catch (Exception e){
            e.printStackTrace();
        }



       return  new ModelAndView("redirect:/marketplace/main?page="+page);

}










}
