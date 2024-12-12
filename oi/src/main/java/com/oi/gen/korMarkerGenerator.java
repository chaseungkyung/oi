package com.oi.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.oi.util.DBConn;
import com.oi.util.DBUtil;

public class korMarkerGenerator {

	public static void main(String[] args) {
		
		Connection conn = DBConn.getConnection();
		
		
		String lastNameList = "준민수영진"
				+ "현재서태원"
				+ "성시우석희"
				+ "경용은연혁"
				+ "병동건상규"
				+ "정인승지강"
				+ "하찬덕대주"
				+ "광호훈철기"
				+ "산섭혁무한"
				+ "수중명영필"
				+ "우솔아빈채"
				+ "슬희나휘아"
				+ "린윤설미솔"
				+ "채류샘시아"
				+ "예윤섬주나"
				+ "다현효진슬"
				+ "비별미류온"
				+ "여서소채림"
				+ "희빈유예효"
				+ "정련서다하";
		
		
		Map<Double, double[]> map = new HashMap<Double, double[]>();
		map.put(38.0, new double[] {127.0, 128.5});
		map.put(37.9, new double[] {126.9, 128.6});
		map.put(37.8, new double[] {126.8, 128.7});
		map.put(37.7, new double[] {126.5, 128.8});
		map.put(37.6, new double[] {126.6, 128.9});
		map.put(37.5, new double[] {126.7, 129.0});
		map.put(37.4, new double[] {126.7, 129.2});
		map.put(37.3, new double[] {126.8, 129.3});
		map.put(37.2, new double[] {126.9, 129.3});
		map.put(37.1, new double[] {126.9, 129.3});
		map.put(37.0, new double[] {126.9, 129.3});
		map.put(36.9, new double[] {126.6, 129.3});
		map.put(36.8, new double[] {126.6, 129.3});
		map.put(36.7, new double[] {126.6, 129.3});
		map.put(36.6, new double[] {126.6, 129.3});
		map.put(36.5, new double[] {126.6, 129.3});
		map.put(36.4, new double[] {126.7, 129.3});
		map.put(36.3, new double[] {126.7, 129.3});
		map.put(36.2, new double[] {126.7, 129.3});
		map.put(36.1, new double[] {126.7, 129.3});
		map.put(36.0, new double[] {126.7, 129.3});
		map.put(35.9, new double[] {126.7, 129.3});
		map.put(35.8, new double[] {126.7, 129.3});
		map.put(35.7, new double[] {126.7, 129.3});
		map.put(35.6, new double[] {126.7, 129.3});
		map.put(35.5, new double[] {126.7, 129.3});
		map.put(35.4, new double[] {126.6, 129.3});
		map.put(35.3, new double[] {126.5, 129.3});
		map.put(35.2, new double[] {126.4, 129.2});
		map.put(35.1, new double[] {126.5, 128.2});
		map.put(35.0, new double[] {126.6, 127.7});
		map.put(34.9, new double[] {126.7, 127.4});
		map.put(34.8, new double[] {126.7, 127.2});
		map.put(34.7, new double[] {126.7, 127.0});
		//38.0000000 34.7000000
		double Ypos;
		double Xpos;
		String name = null;
		for(int i = 0; i < 10000; i++) {
			System.out.println(i);
			Ypos = ((int)(Math.random()*33000000) + 347000001) / 10000000.0 ;
			Xpos = ((int)(Math.random()*((map.get(Math.round(Ypos*10)/10.0)[1]-map.get(Math.round(Ypos*10)/10.0)[0])*10000000)) + (map.get(Math.round(Ypos*10)/10.0)[0]*10000000)) / 10000000.0;
			name = lastNameList.charAt((int) (Math.random() * 100)) + ""
					+ lastNameList.charAt((int) (Math.random() * 100));
			
			PreparedStatement pstmt = null;
			String sql;
			try {
				sql = "insert into findexercise(coordinateNum, nameGym, coordinateX, coordinateY) values(seq_findExercise.nextVal, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, name + "헬스장");
				pstmt.setDouble(2, Xpos);
				pstmt.setDouble(3, Ypos);
				
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DBUtil.close(pstmt);
			}
		}
	}

}
