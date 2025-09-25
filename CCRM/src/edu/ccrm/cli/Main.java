package edu.ccrm.cli;

import edu.ccrm.domain.*;
import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;
import edu.ccrm.io.*;
import edu.ccrm.service.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
	private static final StudentService studentService=new StudentService();
	private static final CourseService courseService=new CourseService();
	private static final EnrollService enrollmentService=new EnrollService();
	private static final ImportExportService ioService=new ImportExportService(studentService,courseService);
	private static final BackupService backupService = new BackupService("Backups");
	
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		int choice;
		
		try {
			ioService.importStudents("exports/students.csv");
			ioService.importCourses("exports/courses.csv");
		}catch(Exception e){
			System.out.println("No previous Data found!");
		}

		do {
			System.out.println("\n==== Campus Course & Records Manager(CCRM) ====");
			System.out.println("1. Manage Students");
			System.out.println("2. Manage Courses");
			System.out.println("3. Enrollmetns & Grades");
			System.out.println("4. Reports");
			System.out.println("5. Import/Export Data");
			System.out.println("6. Backup Data");
			System.out.println("0. Exit");
			System.out.print("Enter your choice: ");
			
			choice=sc.nextInt();
			sc.nextLine();
			
			switch(choice) {
				case 1 -> manageStudents(sc);
				case 2 -> manageCourses(sc);
				case 3 -> manageEnrollments(sc);
				case 4 -> generateReports();
				case 5 -> manageImportExport(sc);
				case 6 -> manageBackup();
				case 0 -> {
					try {
						ioService.exportStudents("exports/students.csv");
						ioService.exportCourses("exports/courses.csv");
						System.out.println("Exiting..... Bye Bye!");
					} catch (IOException e) {
						System.out.println("Failed to Save Data: "+e.getMessage());
					}
				}
				default -> System.out.println("Invalid Choice,Enter a valid Number!");
			}
		}while(choice != 0);
		
		sc.close();
	}
	
	private static void manageStudents(Scanner sc) {
		System.out.println("\n---- Student Manager -----");
		System.out.println("1. Add Student");
		System.out.println("2. List Students");
		System.out.print("Enter choice: ");
		int ch=sc.nextInt();
		sc.nextLine();
		
		switch(ch) {
			case 1 -> {
				System.out.print("Enter ID: ");
				String id=sc.nextLine();
				System.out.print("Enter Name: ");
				String name=sc.nextLine();
				System.out.print("Enter Email: ");
				String email=sc.nextLine();
				System.out.print("Enter RegNo: ");
				String regNo=sc.nextLine();
				studentService.addStudent(new Student(id,name,email,regNo));
				System.out.println("Student Added!");
			}
			case 2 -> studentService.listStudents().forEach(System.out::println);
			default -> System.out.println("Enter a valid Choice!");
		}
	}
	
	private static void manageCourses(Scanner sc) {
		System.out.println("\n---- Course Management ----");
		System.out.println("1. Add Course");
		System.out.println("2. List Courses");
		System.out.print("Enter Choice: ");
		int ch = sc.nextInt();
		sc.nextLine();
		
		switch(ch) {
			case 1 -> {
				System.out.print("Enter Code: ");
				String code=sc.nextLine();
				System.out.print("Enter Title: ");
				String title=sc.nextLine();
				System.out.print("Enter Credits: ");
				int credits=sc.nextInt();sc.nextLine();
				System.out.print("Enter Department: ");
				String dept=sc.nextLine();
				Course c = new Course(code,title,credits,null,dept);
				courseService.addCourse(c);
				System.out.println("Course Added!");
			}
			case 2 -> courseService.listCourses().forEach(System.out::println);
			default -> System.out.println("Enter a valid choice!");
		}
	}
	
	private static void manageEnrollments(Scanner sc) {
		System.out.println("\n---- Enrollment Management ----");
		System.out.print("Enter Student ID: ");
		String sid = sc.nextLine();
		Student s = studentService.getStudent(sid);
		if(s==null) {
			System.out.println("Student not found.");
			return;
		}
		System.out.print("Enter Course Code: ");
		String code = sc.nextLine();
		Course c = courseService.listCourses().stream()
				.filter(course->course.getCode().equalsIgnoreCase(code))
				.findFirst()
				.orElse(null);
		if (c == null) {
			System.out.println("Course not found.");
			return;
		}
		System.out.println("Choose Semester:\n1.SPRING \n2.SUMMER \n3.FALL");
		int semChoice = sc.nextInt();sc.nextLine();
		Semester sem = switch(semChoice) {
			case 1 -> Semester.SPRING;
			case 2 -> Semester.SUMMER;
			case 3 -> Semester.FALL;
			default -> Semester.FALL;
		};
		
		System.out.print("Enter Grade(A,B,C,D,F):");
		String g=sc.nextLine().trim().toUpperCase();
		Grade grade;
		try {
			grade = Grade.valueOf(g);
		}catch(IllegalArgumentException e) {
			System.out.println("Invalid Grade Entered.Enrollment aborted.");
			return;
		}
		
		try {
			enrollmentService.enrollStudent(s, c, sem);
			enrollmentService.assignGrade(s, c, grade);
			System.out.println("Enrollment Successful!");
		}
		catch (DuplicateEnrollmentException e) {
			System.out.println(e.getMessage());
		}
		catch(MaxCreditLimitExceededException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static void generateReports() {
		System.out.println("\n---- Reports ----");
		for(Student s : studentService.listStudents()) {
			double gpa=enrollmentService.computeGPA(s);
			System.out.println(s.getName()+"(RegNo:"+s.getRegNo()+") -> GPA: "+gpa);
		}
	}
	
	private static void manageImportExport(Scanner sc) {
		System.out.println("\n---- Import/Export ----");
		System.out.println("1. Export Students");
		System.out.println("2. Export Courses");
		System.out.println("3. Import Students");
		System.out.println("4. Import Courses");
		System.out.print("Enter choice: ");
		int ch = sc.nextInt();sc.nextLine();
		
		try {
			switch(ch) {
				case 1 -> {
					ioService.exportStudents("exports/students.csv");
					System.out.println("Students exported.");
				}
				case 2 -> {
					ioService.exportCourses("exports/courses.csv");
					System.out.println("Courses exported.");
				}
				case 3 -> {
					ioService.importStudents("exports/students.csv");
					System.out.println("Students imported");
				}
				case 4 -> {
					ioService.importCourses("exports/courses.csv");
					System.out.println("Courses imported");
				}
				default -> System.out.println("Invalid Option.");
			}
		}
		catch (Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
	}
	
	private static void manageBackup() {
		try {
			Path backupFolder = backupService.createBackup("exports");
			long size = backupService.computeFolderSize(backupFolder);
			System.out.println("Backup created at: "+backupFolder);
			System.out.println("Backup size: "+ size + "bytes");
		}
		catch(Exception e) {
			System.out.println("Backup failed: "+e.getMessage());
		}
	}
}
