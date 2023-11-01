package com.hccs.project;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component
public class Student {
	private String firstName;
	private String email;
	private String gender;
	
	//@Autowired
	private ArrayList<Course> courses;
	
	public Student() {
		
	}
	
	public Student(String firstName, String email, String gender) {
		this.firstName = firstName;
		this.email = email;
		this.gender = gender;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public void setCourse(ArrayList<Course> courses) {
		this.courses = courses;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getGender() {
		return this.gender;
	}
	
	public ArrayList<Course> getCourses(){
		return this.courses;
	}
	
	@Override
	public String toString() {
		return this.firstName + " " + this.gender + " " + this.email + " " + this.courses;
	}
}
