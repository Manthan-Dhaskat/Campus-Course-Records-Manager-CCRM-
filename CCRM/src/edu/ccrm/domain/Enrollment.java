package edu.ccrm.domain;

import java.time.LocalDate;

public class Enrollment {
	private Student student;
	private Course course;
	private Semester semester;
	private Grade grade;
	private LocalDate enrollDate;


	public Enrollment(Student student, Course course, Semester semester) {
		this.student=student;
		this.course=course;
		this.semester=semester;
		this.enrollDate=LocalDate.now();
	}

	public Student getStudent() {return student;}
	public Course getCourse() {return course;}
	public Semester getSemester() {return semester;}
	public LocalDate getEnrollDate() {return enrollDate;}
	public Grade getGrade() {return grade;}
	public void setGrade(Grade grade) {this.grade=grade;}

	@Override
	public String toString() {
		return String.format("Enrollment: %s in %s (%s) - Grade: %s",
			student.getName(),
			course.getTitle(),
			semester.getLabel(),
			grade!=null?grade:"Not Yet Assigned");
	}
}