package se.lu.ics.model;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Course {
	private String name;
	private String courseCode;
	private double numberOfCredits;
	private String cycleType;
	private ArrayList<Teaching> teaching = new ArrayList<Teaching>(); 

	private Teacher responsibleTeacher;

	private static ObservableList<Teacher> courseTeachers = FXCollections.observableArrayList();

	public Course() {

	}

	public Course(String name, double numberOfCredits, String cycleType, Teacher responsibleTeacher) {
		this.name = name;
		this.numberOfCredits = numberOfCredits;
		this.cycleType = cycleType;
		this.responsibleTeacher = responsibleTeacher;
//		setCourseCode(cycleType);
	}
	//Add to Teaching
		public void AddTeaching(Teaching teaching) {
			this.teaching.add(teaching); 
		}

	public void addCourseTeachers(Teacher teacher) {
		courseTeachers.add(teacher);
	}

	public Teacher findTeachers(String employeeId) {
		for (Teacher teacher : courseTeachers) {
			if (teacher.getEmployeeId().equals(employeeId)) {
				return teacher;
			}
		}
		return null;
	}

	public double calculateCourseCost() {
		double cost = 0;
		for (Teacher teacher : courseTeachers)	{
			for(Teaching teach : teaching) {
				cost += teacher.getHourlySalary() * teach.getHours(); 
			}
			
		}
		return cost;
	}

	// Getters and Setters
	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String cycleType) {
		// Getting first and second letter of the cycleType first,second or third
		char cycleFirstLetter = cycleType.toUpperCase().charAt(0);
		char cycleSecondLetter = cycleType.toUpperCase().charAt(1);

//		Character firstNameFirstLetter;
//		firstNameFirstLetter = firstName.toLowerCase().charAt(0);

		int min = 1000;
		int max = 9999;

		double randomNumberDouble = Math.random() * (max - min + 1) + min;
		int randomNumberInt = (int) randomNumberDouble;
		String randomNumberString = Integer.toString(randomNumberInt);

		this.courseCode = "" + cycleFirstLetter + cycleSecondLetter + randomNumberString;
	}

	public String getCycleType() {
		return cycleType;
	}

	public void setCycleType(String cycleType) {
		this.cycleType = cycleType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getNumberOfCredits() {
		return numberOfCredits;
	}

	public void setNumberOfCredits(double numberOfCredits) {
		if (numberOfCredits >= 0) {
			this.numberOfCredits = numberOfCredits;
		} else {
			System.out.println("You can not set credits to less than 0");
		}
	}

	public Teacher getResponsibleTeacher() {
		return responsibleTeacher;
	}

	public void setResponsibleTeacher(Teacher responsibleTeacher) {
		this.responsibleTeacher = responsibleTeacher;
	}

	public String getResponsibleTeacherName() {
		return responsibleTeacher.getName();
	}

	public ObservableList<Teacher> getCourseTeachers() {
		for (Teacher teacher : courseTeachers) {

		}
		return courseTeachers;
	}

	public void setCourseTeachers(ObservableList<Teacher> courseTeachers) {
		this.courseTeachers = courseTeachers;
	}

	public ArrayList<Teaching> getTeaching() {
		return teaching;
	}

	public void setTeaching(ArrayList<Teaching> teaching) {
		this.teaching = teaching;
	}

}
