package com.hbut.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hbut.util.ConnectSQL;

public class RoomDao {
	
	/**
	 * 根据查询条件查询教室情况
	 * @param weekNum
	 * @param dayNum
	 * @param sectionNum
	 * @param seatNum
	 * @return
	 */
	public static ResultSet searchRoom(int weekNum,int dayNum,int sectionNum,int seatNum){
		ResultSet rs = null;
		Connection conn = ConnectSQL.getConnection();
		String sql = "SELECT * FROM hbut_room  "
				+"WHERE SeatNum = "+seatNum+" AND RoomNO NOT IN ( "
				+"SELECT hbut_room.RoomNO "
				+"FROM hbut_teachprogram TP,hbut_room "
				+"WHERE TP.RoomNO = hbut_room.RoomNO "
				+"AND "+weekNum+" BETWEEN TP.StartWeek AND TP.endWeek "
				+"AND TP.WeekNum = "+weekNum+" "
				+"AND TP.Section != "+sectionNum +")";
//		System.out.println(sql);
		try {
			PreparedStatement pstate = conn.prepareStatement(sql);
			rs = pstate.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * 返回课表中某天某节次空教室
	 * @param weekNum
	 * @param section
	 * @return
	 */
	public static ResultSet queryRoom(int weekNum,int section) {
		ResultSet rs = null;
		Connection conn = ConnectSQL.getConnection();
		String sql = "select RoomNO,SeatNum from hbut_room WHERE RoomNO NOT IN ( "
				+"SELECT RoomNO FROM hbut_teachprogram WHERE Section = "
				+section+" AND WeekNum = "+weekNum +" )";
		try{
			PreparedStatement pstate = conn.prepareStatement(sql);
			rs = pstate.executeQuery();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * 查询所有的教室
	 * @return
	 */
	public static ResultSet queryAllRoom() {
		ResultSet rs = null;
		Connection conn = ConnectSQL.getConnection();
		String sql = "select RoomNO,SeatNum from hbut_room ";
		try{
			PreparedStatement pstate = conn.prepareStatement(sql);
			rs = pstate.executeQuery();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return rs;
	}

}
