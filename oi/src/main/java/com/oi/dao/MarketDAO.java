package com.oi.dao;

import com.oi.dto.MarketDTO;
import com.oi.util.DBConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MarketDAO {
    private Connection conn = DBConn.getConnection();

    public void insertgoods(MarketDTO dto)throws SQLException{
        PreparedStatement pstmt =null;
        String sql;

        try {
            sql = "INSERT INTO goods(goodsListNum,goodsName,goodsPrice,goodsImage,goodsPhoto,goodsExp,goodsLikeCnt,goodsHitCnt)values(goods_seq.NEXTVAL,?,?,?,?,?,?,0) ";
            pstmt =conn.prepareStatement(sql);
            pstmt.setString(1,dto.getGoodsName());
            pstmt.setInt(2,dto.getGoodsPrice());
            pstmt.setString(3,dto.getGoodsPhoto());


        }catch (Exception e){


        }


    }


}
