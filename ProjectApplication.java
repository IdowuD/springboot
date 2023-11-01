package com.hccs.project;

import org.springframework.boot.SpringApplication;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


@SpringBootApplication
public class ProjectApplication {
	public static String searchByName_CourseNumber(ArrayList<Student> students, String key1, String key2) {
		for(int i = 0; i < students.size(); i++) {
			ArrayList<Course> courses = students.get(i).getCourses();
			int j = 0;
			
			if(students.get(i).getFirstName().equalsIgnoreCase(key1) && (courses.get(j).getCourseNumber().equalsIgnoreCase(key2) || courses.get(j + 1).getCourseNumber().equalsIgnoreCase(key2))) {
				return students.get(i).toString();
			}
		}
		
		return "No data could be found.";
		
	}
	
	public static ArrayList<String> calculateGPA(ArrayList<Student> students) {
		ArrayList<String> gpas = new ArrayList<>();
		
		
		double gpa = 0;
		
		for(Student student:students) {
			ArrayList<Course> courses = student.getCourses();
			long totalCredits = 0;
			long gradePoints = 0;
			for(Course course:courses) {
				totalCredits += course.getCreditHours();
		
				if(course.getGrade().equals("A")) {
					gradePoints += 4 * course.getCreditHours();
					
				}else if(course.getGrade().equals("B")) {
					gradePoints += 3 * course.getCreditHours();
				}else if(course.getGrade().equals("C")) {
					gradePoints += 2 * course.getCreditHours();
				}else if(course.getGrade().equals("D")) {
					gradePoints += 1 * course.getCreditHours();
				}else {
					gradePoints += 0 * course.getCreditHours();
				}
			}
			
			if(totalCredits == 0) {
				gpas.add(student.getFirstName() + "'s gpa: " + 0);
			}else {
				gpa = (double)gradePoints / totalCredits;
				gpas.add(student.getFirstName() + "'s gpa: " + gpa);
			}
			
		}
		
		return gpas;
	}

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
		
		ArrayList<Student> students = new ArrayList<Student>();
		
		Client client = Client.create();
		WebResource webResource = client.resource("https://hccs-advancejava.s3.amazonaws.com/student_course.json");
		
		ClientResponse clientResponse = webResource.accept("application/json").get(ClientResponse.class);
		if(clientResponse.getStatus() != 200) {
			throw new RuntimeException(clientResponse.toString());
		}
		try {
			JSONArray jsonArray = (JSONArray) new JSONParser().parse(clientResponse.getEntity(String.class));
			@SuppressWarnings("unchecked")
			Iterator<Object> iterator = jsonArray.iterator();
			
			String fName;
			String gender;
			String email;
			String courseNumber;
			String grade;
			long creditHrs;
			
			while(iterator.hasNext()) {
				ArrayList<Course> courses = new ArrayList<Course>();
				JSONObject jsonObject = (JSONObject) iterator.next();
				fName = (String)jsonObject.get("first_name");
				gender = (String)jsonObject.get("gender");
				email = (String)jsonObject.get("email");
				
				Student student = new Student(fName, gender, email);
				JSONArray classes = (JSONArray) jsonObject.get("course");
				
				if(classes == null) {
					courses.add(new Course("", "", 0));
					courses.add(new Course("", "", 0));
					student.setCourse(courses);
					students.add(student);
				}else {
					JSONObject subject =  (JSONObject)classes.get(0);
					courseNumber = (String)subject.get("courseNo");
					grade = (String)subject.get("grade");
					creditHrs = (long)subject.get("creditHours");
					Course course = new Course(courseNumber, grade, creditHrs);
					courses.add(course);
					student.setCourse(courses);
					
					subject =  (JSONObject)classes.get(1);
					courseNumber = (String)subject.get("courseNo");
					grade = (String)subject.get("grade");
					creditHrs = (long)subject.get("creditHours");
					course = new Course(courseNumber, grade, creditHrs);
					courses.add(course);
					student.setCourse(courses);	
					students.add(student);
				}		
			}
			
			System.out.println(searchByName_CourseNumber(students, "aida", "cs12"));
			System.out.println(calculateGPA(students));
			
			
		}catch(ParseException ex) {
			System.out.println("Unable to parse JSON");
			ex.printStackTrace();
		}
	}

}
