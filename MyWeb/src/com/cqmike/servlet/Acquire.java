package com.cqmike.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.cqmike.util.DBConnection;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class Acquire extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/plain;charset=utf-8");
//		String student_id = request.getParameter("id");
//		String student_name = request.getParameter("name");
//		System.out.println("id:" + student_id);
//		System.out.println("name:" + student_name);
		try {
			////////////
			// do something
			////////////
			DBConnection db = new DBConnection();
			ResultSet rs;
//			if(student_name.isEmpty()){
				rs = db.executeQuery("select * from qadata ");
//			}else {
//				rs = db.executeQuery("select * from student where name like '" + student_name.substring(0, 1)+"%" +"'");
//			}
			
			
			int id = 0;
			String question = "";
			String optA = "";
			String optB = "";
			String optC = "";
			String optD = "";
			String answer = "";
			ArrayList list = new ArrayList();
			
			
			while(rs.next()){
				id=rs.getInt(1);
				question=rs.getString(2);
				optA=rs.getString(3);
				optB=rs.getString(4);
				optC=rs.getString(5);
				optD=rs.getString(6);
				answer=rs.getString(7);
				Question q = new Question(id, question, optA, optB, optC, optD, answer);
				list.add(q);
			}
			db.close();
			
			Random r = new Random();
			int j=0;
			int flag;
			int[] ques= {-1,-1,-1,-1};
			while(true) {
				flag=0;
				int randnumber = r.nextInt(10);
				System.out.println(randnumber);
				if(j>0) {
					for (int i = 0; i < ques.length; i++) {
						if(randnumber==ques[i]){
							flag=1;
							break;
						}
					}
				}
				if(flag==1){
					continue;
				}
				ques[j] = randnumber;
				j++;
				if(j==4) {
					break;
				}
			}
			
			
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			JSONObject obj = new JSONObject();
			
			Question q1 = (Question) list.get(ques[0]);
			JSONObject obj1 = new JSONObject();
			
//			obj.put("result", "ok");
//			obj.put("id", id);
			obj1.put("question", q1.getQuestion());
			obj1.put("optA", q1.getOptA());
			obj1.put("optB", q1.getOptB());
			obj1.put("optC", q1.getOptC());
			obj1.put("optD", q1.getOptD());
			obj1.put("answer", q1.getAnswer());
			obj.put("Qa1", obj1);
			
			JSONObject obj2 = new JSONObject();
			Question q2 = (Question) list.get(ques[1]);
			obj2.put("question", q2.getQuestion());
			obj2.put("optA", q2.getOptA());
			obj2.put("optB", q2.getOptB());
			obj2.put("optC", q2.getOptC());
			obj2.put("optD", q2.getOptD());
			obj2.put("answer", q2.getAnswer());
			obj.put("Qa2", obj2);

			JSONObject obj3 = new JSONObject();
			Question q3 = (Question) list.get(ques[2]);
			obj3.put("question", q3.getQuestion());
			obj3.put("optA", q3.getOptA());
			obj3.put("optB", q3.getOptB());
			obj3.put("optC", q3.getOptC());
			obj3.put("optD", q3.getOptD());
			obj3.put("answer", q3.getAnswer());
			obj.put("Qa3", obj3);
			
			JSONObject obj4 = new JSONObject();
			Question q4 = (Question) list.get(ques[3]);
			obj4.put("question", q4.getQuestion());
			obj4.put("optA", q4.getOptA());
			obj4.put("optB", q4.getOptB());
			obj4.put("optC", q4.getOptC());
			obj4.put("optD", q4.getOptD());
			obj4.put("answer", q4.getAnswer());
			obj.put("Qa4", obj4);
			
			
		//	System.out.println(obj.toString());
			out.print(obj.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	public static void main(String[] args) {
//		doSearch("123");
	}

}
