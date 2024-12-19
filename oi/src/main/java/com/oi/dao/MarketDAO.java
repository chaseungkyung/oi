package com.oi.dao;

import com.oi.dto.MarketDTO;
import com.oi.dto.Marketfile;
import com.oi.dto.ReplyDTO;
import com.oi.util.DBConn;
import com.oi.util.DBUtil;
import oracle.jdbc.proxy.annotation.Pre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MarketDAO {
    private Connection conn = DBConn.getConnection();

    public boolean insertgoods(MarketDTO dto)throws SQLException{
        PreparedStatement pstmt =null;
        String sql;
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            sql = "INSERT INTO goods(goodsListNum,memberId,goodsName,goodsPrice,categorynum,goodsExp,goodsBlind,goodsDate,goodsHitCnt,POSTCATENum)" +
                    " values(seq_goods.NEXTVAL,?,?,?,?,?,1,SYSDATE,0,30) ";
            pstmt =conn.prepareStatement(sql);
            pstmt.setString(1,dto.getMemberId());
            pstmt.setString(2,dto.getGoodsName());
            pstmt.setInt(3,dto.getGoodsPrice());

            pstmt.setInt(4,dto.getCategoryNum());
            pstmt.setString(5, dto.getGoodsExp());

            pstmt.executeUpdate();

            pstmt=null;
            sql=null;

            Marketfile files = dto.getFile();
            sql = "INSERT INTO goodsphoto(GPNum,GPListNum,GPName) values (seq_goodsphoto.NEXTVAL,seq_goods.CURRVAL,?)";
            pstmt = conn.prepareStatement(sql);
            for(String s : files.getSaveFileName()){
                pstmt.setString(1,s);
                pstmt.executeUpdate();
            }
            result=true;
            conn.commit();



        }catch (Exception e){
            conn.rollback();
            e.printStackTrace();

        }finally {
            conn.setAutoCommit(true);
            DBUtil.close(pstmt);
        }
return result;

    }

    //상품삭제
    public void deletegoods(long num)throws SQLException{
PreparedStatement pstmt = null;
String sql;
try {
    conn.setAutoCommit(false);
    sql="DELETE FROM GOODSCOMMENT WHERE  GOODSLISTNUM=?";
    pstmt=conn.prepareStatement(sql);
         pstmt.setLong(1,num);
            pstmt.executeUpdate();
            pstmt=null;

    sql="DELETE FROM GOODS WHERE GOODSLISTNUM=?";
    pstmt=conn.prepareStatement(sql);
    pstmt.setLong(1,num);
    pstmt.executeUpdate();
}catch (SQLException e){
    e.printStackTrace();
    throw e;
}finally {
    conn.setAutoCommit(true);
    DBUtil.close(pstmt);
}
    }



public int dataCount(){
        int result = 0;
        PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql;
    try {
        sql="SELECT NVL(COUNT(*),0) FROM goods WHERE goodsBlind=1";
        pstmt=conn.prepareStatement(sql);
        rs = pstmt.executeQuery();
        if (rs.next()) result = rs.getInt(1);
    }catch (Exception e){
        e.printStackTrace();
    }finally {
        DBUtil.close(rs);
        DBUtil.close(pstmt);
    }
    return result;
} 

public List<MarketDTO> listMarket(int offset,int size){
    List<MarketDTO> list = new ArrayList<MarketDTO>() ;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    StringBuilder sql = new StringBuilder();

    try {
        sql.append(" SELECT goodsName,GOODSLISTNUM,GOODSPRICE,TO_CHAR(GOODSDATE,'YYYY-MM-DD')goodsdate FROM GOODS WHERE goodsblind=1 ORDER BY goodsdate DESC ");
        sql.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");
        pstmt = conn.prepareStatement(sql.toString());
        pstmt.setInt(1,offset);
        pstmt.setInt(2,size);
        rs = pstmt.executeQuery();
        while (rs.next()){
            MarketDTO dto = new MarketDTO();
            dto.setGoodsName(rs.getString("goodsName"));
            dto.setGoodsPrice(rs.getInt("GOODSPRICE"));
            dto.setGoodsDate(rs.getString("goodsdate"));
            dto.setGoodsListNum(rs.getLong("GOODSLISTNUM"));
            list.add(dto);

        }

    }catch (Exception e){
e.printStackTrace();

    }finally {
        DBUtil.close(rs);
        DBUtil.close(pstmt);
    }

return list;

}
//파일 불러오기
    public void getFiles(MarketDTO dto){
        List<String> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql;
        try {
            sql=" SELECT GPNUM,GPListNum,GPName FROM GOODSPHOTO WHERE GPLISTNUM=?  FETCH FIRST 1 ROWS ONLY";
            pstmt= conn.prepareStatement(sql);
            pstmt.setLong(1,dto.getGoodsListNum());
            rs=pstmt.executeQuery();
            while (rs.next()){
                dto.getFile().setFile(rs.getLong("GPNUM"),rs.getString("GPName"));
                dto.getFile().setParentnum(rs.getLong("GPListNum"));
                list.add(rs.getString("GPName"));
                dto.getFile().setSaveFileName(list.toArray(new String[0]));
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
    }
   //파일 아이디로 작성한 목록들 불러오기
    public MarketDTO findById(long goodsListNum){
        MarketDTO dto = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql;
        try {
            sql = " SELECT GOODSLISTNUM, p.memberId,categorynum,nickname,goodsPrice,goodsExp,goodsDate" +
                    " FROM GOODS p JOIN member m ON p.memberId=m.memberId " +
                    " WHERE GOODSLISTNUM =? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,goodsListNum);
            rs = pstmt.executeQuery();

            if (rs.next()){
                dto = new MarketDTO();
                dto.setGoodsListNum(rs.getLong("GOODSLISTNUM"));
                dto.setMemberId(rs.getString("memberId"));
                dto.setGoodsExp(rs.getString("goodsExp"));
                dto.setGoodsPrice(rs.getInt("goodsPrice"));
                dto.setGoodsDate(rs.getString("goodsDate"));
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        return dto;
    }

    public  MarketDTO findByFileId(long fileNum){
        MarketDTO dto = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        String sql;
        try {
            sql="SELECT GPNum,GPlistnum,Gpname FROM goodsphoto WHERE GPNum=?";
            pstmt=conn.prepareStatement(sql);

            pstmt.setLong(1,fileNum);

            rs=pstmt.executeQuery();

            if (rs.next()){
                dto = new MarketDTO();
                dto.setGoodsListNum(rs.getLong("Gplistnum"));
                dto.setFileNum(rs.getLong("GPNUM"));
                dto.setImageFilename(rs.getString("Gpname"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
return dto;
    }

    public List<MarketDTO> listMarketFile(long goodsListNum){
        List<MarketDTO> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql;
        try {
         sql = "SELECT GPNum,GPlistnum,Gpname FROM goodsphoto WHERE GPListnum=?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setLong(1,goodsListNum);
         rs=pstmt.executeQuery();

         while (rs.next()){
             MarketDTO dto = new MarketDTO();
             dto.setFileNum(rs.getLong("GPNum"));
             dto.setGoodsListNum(rs.getLong("GPlistnum"));
             dto.setImageFilename(rs.getString("Gpname"));
             list.add(dto);

         }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close(pstmt);
        }
        return list;
    }


        public void updategoods(MarketDTO dto)throws SQLException{
            PreparedStatement pstmt = null;
            String sql;
            try {
                conn.setAutoCommit(false);
                sql = "UPDATE goods SET categorynum=?,goodsname=?,goodsexp=? WHERE GOODSLISTNUM=?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setLong(1,dto.getCategoryNum());
                pstmt.setString(2,dto.getGoodsName());
                pstmt.setString(3,dto.getGoodsExp());
                pstmt.setLong(4,dto.getGoodsListNum());
                pstmt.executeUpdate();
                pstmt.close();
                pstmt=null;
                if (dto.getImageFiles()!=null){
                    sql = "INSERT INTO goodsphoto(GPNum,GPLISTNUM,GPName)values(seq_goodsphoto.NEXTVAL,?,?) ";
                    pstmt=conn.prepareStatement(sql);
                    for (String imageFileName:dto.getImageFiles()){
                        pstmt.setLong(1,dto.getGoodsListNum());
                        pstmt.setString(2,imageFileName);

                        pstmt.executeUpdate();
                    }

                }



            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }finally {
                DBUtil.close(pstmt);
                conn.setAutoCommit(true);

            }


        }


        public void deleteMarketFile(String mode, long num)throws SQLException{
        PreparedStatement pstmt = null;
        String sql;
        try {
            if (mode.equals("all")){
                sql="DELETE  FROM GOODSPHOTO WHERE GPLISTNUM=? ";
            }else {
                sql="DELETE FROM GOODSPHOTO WHERE GPNUM=?";
            }
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,num);
            pstmt.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
            throw  e;
        }finally {
            DBUtil.close(pstmt);
        }
        }

        public void deletephoto(long num) throws SQLException{
        PreparedStatement pstmt = null;
        String sql;
        try {
            sql="DELETE FROM GOODSPHOTO WHERE GPLISTNUM=?";
            pstmt=conn.prepareStatement(sql);
            pstmt.setLong(1,num);
        }catch (SQLException e){
            e.printStackTrace();
            throw e;
        }finally {
            DBUtil.close(pstmt);
        }
        }

//게시글의 답글 및 답글저장
    public void insertReply(ReplyDTO dto)throws SQLException{
        PreparedStatement pstmt =null;
        String sql;
        try {
            sql="INSERT INTO" +
                    "    GOODSCOMMENT(gcnum, memberid, goodslistnum, postcatenum, gcparcomnum, gccomcon, gcinsertnum, gcupdatenum, gcblind)" +
                    " VALUES (SEQ_GOODSCOMMENT.nextval,?,?,31,?,?,SYSDATE,SYSDATE,0)";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,dto.getUserId());

            pstmt.setLong(2,dto.getNum());
            pstmt.setLong(3,dto.getParentNum());
            pstmt.setString(4,dto.getContent());
            pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            throw e;

        }finally {
            DBUtil.close(pstmt);
        }

    }
    public int dataCountReply (long num ) {
        int result = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql;
        try {
            sql = "SELECT COUNT(*) FROM GOODSCOMMENT WHERE goodslistnum = ? AND gcparcomnum = 0 AND gcblind = 0";
            pstmt = conn.prepareStatement(sql);

            pstmt.setLong(1, num);
            rs = pstmt.executeQuery();

            if(rs.next()) result = rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
        return result;
    }
   public List<ReplyDTO> listReply(long num,int offset,int size){
        List<ReplyDTO> list = new ArrayList<ReplyDTO>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuilder sb = new StringBuilder();



        try {
            sb.append(" SELECT r.gcnum,r.memberid,m.NickName,goodslistnum,gccomcon, r.gcinsertnum,NVL(answerCount,0) answerCount  ");
            sb.append("    FROM GOODSCOMMENT r ");
            sb.append("     JOIN member m ON r.MEMBERID = m.MEMBERID  ");
            sb.append("   Left OUTER JOIN (  ");
            sb.append("   SELECT gcparcomnum,COUNT(*) answerCount ");
            sb.append("    FROM GOODSCOMMENT ");
            sb.append("    WHERE GCPARCOMNUM !=0 ");
            sb.append(" GROUP BY gcparcomnum ");
            sb.append("   ) a ON r.GCNUM = a.GCPARCOMNUM ");
            sb.append("   WHERE  GOODSLISTNUM=? AND r.GCPARCOMNUM =0 AND r.GCBLIND=0 ");
            sb.append("     ORDER BY  r.GCNUM DESC ");
            sb.append("   OFFSET   ? ROWS FETCH FIRST ? ROWS ONLY ");

         pstmt= conn.prepareStatement(sb.toString());
         pstmt.setLong(1,num);
         pstmt.setInt(2,offset);
         pstmt.setInt(3,size);

         rs = pstmt.executeQuery();
         while (rs.next()){
             ReplyDTO dto = new ReplyDTO();
             dto.setGcNum(rs.getLong("gcnum"));
             dto.setUserId(rs.getString("memberId"));
             dto.setUserName(rs.getString("NickName"));
             dto.setNum(rs.getLong("goodslistnum"));
             dto.setContent(rs.getString("gccomcon"));
             dto.setReg_date(rs.getString("gcinsertnum"));
             list.add(dto);
         }


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
       return list;
   }











}
