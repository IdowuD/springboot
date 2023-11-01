package com.hccs.project;

import org.springframework.stereotype.Component;

@Component
public class Course {
	private String courseNumber;
	private String grade;
	private long creditHours;
	
	public Course() {
		
	}
	
	public Course(String courseNumber, String grade, long creditHours) {
		this.courseNumber = courseNumber;
		this.grade = grade;
		this.creditHours = creditHours;
	}
	
	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}
	
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public void setCreditHours(long creditHours) {
		this.creditHours = creditHours;
	}
	
	public String getCourseNumber(){
		return this.courseNumber;
	}
	
	public String getGrade() {
		return this.grade;
	}
	
	public long getCreditHours() {
		return this.creditHours;
	}
	
	@Override
	public String toString() {
		return this.courseNumber + " " + this.grade + " " + this.creditHours;
	}

}
