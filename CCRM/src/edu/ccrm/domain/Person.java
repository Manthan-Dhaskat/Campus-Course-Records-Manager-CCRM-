package edu.ccrm.domain;

import java.time.LocalDate;

public abstract class Person {
	private String Id;
	private String Name;
	private String Email;
	private LocalDate Date;
	
	public Person(String Id, String Name,String Email) {
		this.Id=Id;
		this.Name=Name;
		this.Email=Email;
		this.Date=LocalDate.now();
	}
	
	public String getId() {return Id;}
	public String getName() {return Name;}
	public String getEmail() {return Email;}
	public LocalDate getDate() {return Date;}
	
	public void setName(String Name) {this.Name=Name;}
	public void setEmail(String Email) {this.Email=Email;}
	
	public abstract String getRole();
	
	@Override
	public String toString() {
		return String.format("%s (%s) - %s",Name ,getRole(),Email);
	}
}
