package com.ap.quizzer.Model;

import javax.persistence.*;

@Entity
@Table(name = "problems")
public class Problem {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String question;
	private String answer;
	private int type;
	private String[] options = new String[4];

	public String[] getOptions() {
		return options;
	}
	public void setOptions(String[] options) {
		this.options = options;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
