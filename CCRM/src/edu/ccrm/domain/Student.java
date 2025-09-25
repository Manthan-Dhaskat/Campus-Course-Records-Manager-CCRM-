package edu.ccrm.domain;

import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
	private String regNo;
	private boolean active;
	private List<Course> enrolledCourse;
	
	public Student(String Id,String Name,String Email,String regNo) {
		super(Id,Name,Email);
		assert Id != null && !Id.isBlank();
		assert regNo!=null && !regNo.isBlank();
		this.regNo=regNo;
		this.active=true;
		this.enrolledCourse=new ArrayList<>();
	}
	
	public String getRegNo() {return regNo;}
	public boolean isActive() {return active;}
	public List<Course> getEnrolledCourses(){return enrolledCourse;}
	
	public void deactivate() {this.active=false;}
	
	@Override
	public String getRole() {
		return "Student";
	}
}
