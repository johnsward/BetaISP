package se.lu.ics.model;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Calendar; 

public class Teaching {

	private int date;
	private double hours;
	private Teacher teacher;
	private Course course;
	
	// Associations 
	
	private ArrayList<Course> courses = new ArrayList<Course>();
	private ArrayList<Teacher> teachers = new ArrayList<Teacher>();

	public Teaching(int date, double hours, Teacher teacher, Course course) {
		this.hours=hours; 
		this.date = date;
		this.teacher = teacher;
		this.course = course;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}


	public double getHours() {
		return hours;
	}

	public void setHours(double hours) {
		this.hours = hours;
	}

	public ArrayList<Course> getCourses() {
		return courses;
	}

	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}

	public ArrayList<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(ArrayList<Teacher> teachers) {
		this.teachers = teachers;
	}

}
