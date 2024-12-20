package com.oi.controller;

import com.oi.dao.MarketDAO;
import com.oi.dto.LoginDTO;
import com.oi.dto.MarketDTO;
import com.oi.dto.Marketfile;
import com.oi.dto.ReplyDTO;
import com.oi.mvc.annotation.Controller;
import com.oi.mvc.annotation.RequestMapping;
import com.oi.mvc.annotation.RequestMethod;
import com.oi.mvc.annotation.ResponseBody;
import com.oi.mvc.view.ModelAndView;
import com.oi.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
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
        mav.addObject("mode","registration");





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
            dto.setCategoryNum( Integer.parseInt(childCategoryParam));
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




    @RequestMapping(value = "/marketplace/update",method = RequestMethod.GET)
    public ModelAndView updategoods(HttpServletRequest req , HttpServletResponse resp)throws ServletException, IOException{
//수정폼
        MarketDAO dao = new MarketDAO();
        HttpSession  session = req.getSession();
        //넘어올 파라미터 : 페이지
        LoginDTO info = (LoginDTO) session.getAttribute("member");

        String page= req.getParameter("page");

        try {
            long GoodsListNum = Long.parseLong(req.getParameter("num"));
            MarketDTO dto =  dao.findById(GoodsListNum);
            if (dto ==null){
                return new ModelAndView("redirect:/marketplace/main?page="+page);
            }

//            //게시물을 올린 사용자가 아니면
//            if (!dto.getMemberId().equals(info.getMemberId())){
//                return new ModelAndView("redirect:/marketplace/main?page="+page);
//            }
            List<MarketDTO> listFile = dao.listMarketFile(GoodsListNum);

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
    @RequestMapping(value = "/marketplace/update",method = RequestMethod.POST)
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
            System.out.println(req.getParameter("childCategory"));

            dto.setCategoryNum(Integer.parseInt(req.getParameter("childCategory")));
            dto.setGoodsListNum(Long.parseLong(req.getParameter("num")));
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


        return new ModelAndView("redirect:/marketplace/main?page="+page);
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
                return new ModelAndView("redirect:/marketplace/main?page="+page);
            }
            dto.setGoodsExp(dto.getGoodsExp().replaceAll("\n","<br>"));

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

@RequestMapping(value = "/marketplace/deleteFile")
public ModelAndView deleteFile(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        //수정에서 파일만 삭제
    MarketDAO  dao= new MarketDAO();
    HttpSession session = req.getSession();
    LoginDTO info = (LoginDTO) session.getAttribute("member");
    FileManager fileManager =new FileManager();

    //파일 저장 경로
    String root = session.getServletContext().getRealPath("/");
    String pathname = root + "uploads" + File.separator + "photo";

    String page = req.getParameter("page");

    try {
        long num = Long.parseLong(req.getParameter("num"));
        long fileNum = Long.parseLong(req.getParameter("fileNum"));

        MarketDTO dto = dao.findById(num);

        if (dto == null){
            return new ModelAndView("redirect:/marketplace/main?page="+page);
        }

        if (!info.getUserId().equals(dto.getMemberId())){
            return new ModelAndView("redirect:/marketplace/main?page="+page);
        }

        MarketDTO vo = dao.findByFileId(fileNum);
        if (vo != null){
            fileManager.doFiledelete(pathname,vo.getImageFilename());
            dao.deleteMarketFile("one",fileNum);
        }
        return new ModelAndView("redirect:/marketplace/update?num="+num+"&page="+page);


    }catch (Exception e){
        e.printStackTrace();
    }


    return  new ModelAndView("redirect:/marketplace/main?page="+page);

}

      @RequestMapping(value = "/marketplace/delete")
      public ModelAndView delete(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        //삭제 완료
          MarketDAO dao = new MarketDAO();
          HttpSession session = req.getSession();
          LoginDTO info = (LoginDTO) session.getAttribute("member");
          FileManager fileManager=new FileManager();

          //파일 저장 경로
          String root = session.getServletContext().getRealPath("/");
          String pathname = root + "uploads" + File.separator + "photo";

          String page = req.getParameter("page");

          try {
              long  num = Long.parseLong(req.getParameter("num"));
              MarketDTO dto = dao.findById(num);
              if (dto==null){
                  return new ModelAndView("redirect:/marketplace/main?page="+page);
              }
              //게시물을 올린 사용자가 아니면
              if (!info.getUserId().equals(dto.getMemberId())){
                  return new ModelAndView("redirect:/marketplace/main?page="+page);
              }
              //이미지 파일 지우기
              List<MarketDTO> listFile =dao.listMarketFile(num);
              for (MarketDTO vo : listFile){
                  fileManager.doFiledelete(pathname,vo.getImageFilename());

              }
              dao.deleteMarketFile("all",num);

              //테이블 데이터 삭제
              dao.deletephoto(num);
              dao.deletegoods(num);

          }catch (Exception e){
              e.printStackTrace();
          }

        return new ModelAndView("redirect:/marketplace/main?page="+page);
      }

      @ResponseBody
      @RequestMapping(value = "/marketplace/insertReply",method = RequestMethod.POST)
     public Map<String,Object> insertReply(HttpServletRequest req,HttpServletResponse resp)throws ServletException{
          Map<String, Object> model = new HashMap<String, Object>();
          String state = "false";
          MarketDAO dao = new MarketDAO();
          HttpSession session = req.getSession();
          try {
              LoginDTO info = (LoginDTO) session.getAttribute("member");
              ReplyDTO dto = new ReplyDTO();

              dto.setNum(Long.parseLong(req.getParameter("num")));
              dto.setContent(req.getParameter("content"));
              dto.setParentNum(Long.parseLong(req.getParameter("parentNum")));

              dto.setUserId(info.getUserId());

              dao.insertReply(dto);
              state="true";




          }catch (Exception e){
              e.printStackTrace();
          }

          model.put("state",state);

          return model;

      }

      @RequestMapping(value = "/marketplace/listReply",method = RequestMethod.GET)
      public ModelAndView listReply(HttpServletRequest req, HttpServletResponse resp)throws ServletException,IOException{
        MarketDAO dao = new MarketDAO();
        MyUtil util = new MyUtilBootstrap();
        HttpSession session = req.getSession();
        LoginDTO info = (LoginDTO) session.getAttribute("member");

        try {
            long num = Long.parseLong(req.getParameter("num"));
            String pageNo= req.getParameter("pageNo");
            int current_page=1;
            if (pageNo != null) {
                current_page = Integer.parseInt(pageNo);
            }

            int size = 5;
            int total_page = 0;
            int replyCount = 0;

            replyCount = dao.dataCountReply(num);
            total_page = util.pageCount(replyCount, size);
            if (current_page > total_page)
                current_page = total_page;

            int offset = (current_page - 1) * size;
            if (offset < 0)
                offset = 0;

            List<ReplyDTO> list = dao.listReply(num, offset, size);

            for (ReplyDTO dto : list) {
                dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
            }
            String paging = util.pagingMethod(current_page,total_page,"listPage");
            ModelAndView mav = new ModelAndView("oimarket/listReply");

            mav.addObject("listReply",list);
            mav.addObject("pageNo", current_page);
            mav.addObject("replyCount", replyCount);
            mav.addObject("total_page", total_page);
            mav.addObject("paging", paging);

return mav;
        }catch (Exception e){
            e.printStackTrace();
            resp.sendError(406);
            throw e;
        }


      }
        @RequestMapping(value = "/marketplace/listReplyAnswer",method = RequestMethod.GET)
      public  ModelAndView listReplyAnswer(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
        //넘어오는 파라미터 : parentNum
        MarketDAO dao = new MarketDAO();
        try{
            long parentNum=Long.parseLong(req.getParameter("parentNum"));
            List<ReplyDTO> list=dao.listReplyAnswer(parentNum);
            for (ReplyDTO dto :list){
                dto.setContent(dto.getContent().replaceAll("\n","<br>"));

            }
            ModelAndView mav = new ModelAndView("oimarket/listReplyAnswer");
            mav.addObject("listReplyAnswer",list);
            return mav;


        }catch (Exception e){
            e.printStackTrace();
            resp.sendError(406);
            throw e;
        }

      }
      @ResponseBody
      @RequestMapping(value = "/marketplace/listReplyAnswer",method = RequestMethod.POST)
    public Map<String,Object> countReplyAnswer(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        Map<String,Object> model = new HashMap<String,Object>();
        MarketDAO dao = new MarketDAO();
        int count =0;
        try {

            long parentNum = Long.parseLong(req.getParameter("parentNum"));
            count = dao.dataCountReplyAnswer(parentNum);

        }catch (Exception e){
            e.printStackTrace();
        }
        model.put("count",count);
        return model;

      }


      @ResponseBody
    @RequestMapping(value = "/marketplace/deleteReply",method = RequestMethod.POST)
    public Map<String,Object> deleteReply(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{

        Map<String,Object> model = new HashMap<String,Object>();
        MarketDAO dao =  new MarketDAO();


        HttpSession session = req.getSession();
        LoginDTO info = (LoginDTO) session.getAttribute("member");
        String state = "false";

        try {
            long replyNum = Long.parseLong(req.getParameter("replyNum"));
            dao.deleteReply(replyNum,info.getUserId(), Integer.parseInt(info.getUserLevel()));
            state="true";
        }catch (Exception e){
            e.printStackTrace();
        }
        model.put("state",state);
        return model;
      }

      @ResponseBody
    @RequestMapping(value = "/marketplace/insertgoodsLike",method = RequestMethod.POST)
    public Map<String,Object> insertgoodsLike(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{

        Map<String,Object> model = new HashMap<String,Object>();

        MarketDAO dao = new MarketDAO();
        HttpSession session = req.getSession();
        LoginDTO info = (LoginDTO) session.getAttribute("member");
        String state="false";
        int insertgoodsLike = 0;

        try {
            long num = Long.parseLong(req.getParameter("num"));
            String userLiked=req.getParameter("userLiked");
            String userId = info.getUserId();

            if (userLiked.equals("true")) {
                dao.deletegoodsLike(num, userId);
            } else {
                dao.insertgoodsLike(num, userId);
            }
            insertgoodsLike = dao.countgoodsLike(num);
            state = "true";


        }catch (SQLException e){
            state="liked"; //?
        }catch (Exception e){
            e.printStackTrace();
        }
        model.put("state",state);
        model.put("insertgoodsLike",insertgoodsLike);
                return model;

      }



}
