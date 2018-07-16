package com.cqmike.servlet;

public class Question {

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	public String getOptA() {
		return optA;
	}

	public void setOptA(String optA) {
		this.optA = optA;
	}	
	
	public String getOptB() {
		return optB;
	}

	public void setOptB(String optB) {
		this.optB = optB;
	}	
	
	public String getOptC() {
		return optC;
	}

	public void setOptC(String optC) {
		this.optC = optC;
	}
	
	public String getOptD() {
		return optD;
	}

	public void setOptD(String optD) {
		this.optD = optD;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	private int id = 0;
	private String question = "";
	private String optA = "";
	private String optB = "";
	private String optC = "";
	private String optD = "";
	private String answer = "";
	
	public Question() {
		
	}
	public Question(int id, 
			String question, String optA, 
			String optB, String optC, String optD, String answer) {
		this.id = id;
		this.question = question;
		this.optA = optA;
		this.optB = optB;
		this.optC = optC;
		this.optD = optD;
		this.answer = answer;
	}
	public static void main(String[] args) {
		

	}

}
