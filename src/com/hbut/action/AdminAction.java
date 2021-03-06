package com.hbut.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.hbut.bean.TeachProgramBean;
import com.hbut.dao.TeachProgramDao;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AdminAction extends ActionSupport{
	
	private static final long serialVersionUID = -3432242185210308329L;
	private HttpServletRequest request;
	@SuppressWarnings("unused")
	private HttpServletResponse response;
	@SuppressWarnings({ "unused", "rawtypes" })
	private Map session = ActionContext.getContext().getSession();
	private static String scheduleClassNO;
	
	public AdminAction(){
		request = ServletActionContext.getRequest();
	}
	
	/**
	 * 排课界面
	 * @return
	 */
	public String scheduleCoursePage() {
		String userNO = request.getParameter("UserNO").toString();
		System.out.println(userNO);
		return SUCCESS;
	}
	
	/**
	 * 教室查看
	 * @return
	 */
	public String searchEmptyRoom() {
		return SUCCESS;
	}
	
	/**
	 * 提交排课信息或首次进入排课界面将已有课程计划的节次直接显示课程信息
	 * @return
	 */
	public String schedulePage(){
		scheduleClassNO = request.getParameter("scheduleClassNO");
		request.setAttribute("action", "submitSchedule");
		//查询当前班级号为scheduleClassNO的课表
		String[][] timeTable = TeachProgramDao.queryTimeTable(scheduleClassNO);
		request.setAttribute("timeTable", timeTable);
		return SUCCESS;
	}
	
	/**
	 * 提交排课信息
	 * @return
	 */
	public String submitSchedule(){
//		List<TeachProgramBean> tpList = new ArrayList<TeachProgramBean>();
		String courseNO,roomNO,classNO,teacherNO;
		int startWeek,endWeek,section,weekNum;
		System.out.println("UPDATE SCHEDULE");
		for(int i = 0 ; i < 5 ; i++ ){
				for(int j = 0 ; j< 7 ; j++){
					courseNO = request.getParameter("courseNO"+i+j);
					if( !  (courseNO == null) && !courseNO.equals("WU")  ){
						System.out.println("courseNO:"+courseNO+i+j);
						
						roomNO = request.getParameter("roomNO"+i+j);
						classNO = scheduleClassNO;
						teacherNO = request.getParameter("teacherNO"+i+j);
						startWeek = Integer.valueOf(  request.getParameter("startWeek"+i+j)  );
						endWeek = Integer.valueOf(  request.getParameter("endWeek"+i+j)  );
						section = Integer.valueOf( i  );
						weekNum = Integer.valueOf( j );
					
						TeachProgramBean tp = new TeachProgramBean(courseNO,roomNO,classNO,teacherNO,startWeek,endWeek,section,weekNum);
						TeachProgramDao.saveTeacheProgram(tp);
					}
				}
		}
		//查询当前班级号为scheduleClassNO的课表
		String[][] timeTable = TeachProgramDao.queryTimeTable(scheduleClassNO);
		request.setAttribute("timeTable", timeTable);
		return SUCCESS;
	}
	
	/**
	 * 更新排课信息界面
	 * @return
	 */
	public String updateSchedulePage(){
//		String userNO = request.getParameter("UserNO").toString();
//		System.out.println(userNO);
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 */
	public String updateSchedule(){
		scheduleClassNO = request.getParameter("scheduleClassNO");
		request.setAttribute("action", "update");
		//查询当前班级号为scheduleClassNO的课表
		String[][] timeTable = TeachProgramDao.queryTimeTable(scheduleClassNO);
//		showTable(timeTable);
		request.setAttribute("timeTable", timeTable);
		return SUCCESS;
	}
	
	/**
	 * 更新排课信息
	 * @return
	 */
	public String update(){
		return SUCCESS;
	}
	
//	private void showTable(String[][] table){
//		System.out.println("--------------------------");
//		for(int i = 0 ; i < 5 ; i++){
//			for(int j = 0 ; j< 7 ; j++){
//				if(table[i][j] != null){
//					String[] x = table[i][j].split("<br>");
//					String[] y = x[3].split("-|周");
//					System.out.println("split1----------------");
//					for(String a:x){
//						System.out.print(a+"  ");
//					}
//					System.out.println("split1   y----------------");
//					for(String a:y){
//						System.out.print(a+"  ");
//					}
//					System.out.println("split2----------------");
////					System.out.print(table[i][j]+" ");
//				}
//			}
//			System.out.println();
//		}
//		System.out.println("--------------------------");
//	}
	
}
