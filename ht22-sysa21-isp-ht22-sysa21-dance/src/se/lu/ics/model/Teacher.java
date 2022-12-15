package se.lu.ics.model;




import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Teacher {



	private String employeeId;
	private String name;
	private String title;
	private String address;
	private double hourlySalary;
	//Association class
	private ArrayList<Teaching> teaching = new ArrayList<Teaching>(); 
	
	private static ObservableList<Course> responsible = FXCollections.observableArrayList();
	private Department department = new Department();
	private static ObservableList<Course> courses = FXCollections.observableArrayList();
	

	// Create teacher, empty constructor
	public Teacher() {
		
	}


	// Titles array
	private String[] titles = { "None", "Assistant Professor", "Lecturer", "Professor" };

	public String[] getTitles() {
		return titles;
	}

	public void setTitles(String[] titles) {
		this.titles = titles;
	}

	//Add to Teaching
	public void AddTeaching(Teaching teaching) {
		this.teaching.add(teaching); 
	}
	// Create teacher
	public Teacher(String name, String title, String address, double hourlySalary, Department department) {
		this.name = name;
		this.title = title;
		this.address = address;
		this.hourlySalary = hourlySalary;
		this.department = department;
	}

	// Set responsible
	public void setResponsible(Course course) {
		if (responsible.size() <= 3) {
			responsible.add(course);
		}
	}

	// Get responsible
	public ObservableList<Course> getResponsible() {
		return responsible;
	}

	// Find course
	public Course findCourse(String courseCode) {
		for (Course course : courses) {
			if (course.getCourseCode().equals(courseCode)) {
				return course;
			}
		}
		return null;
	}

	// Remove course
	public void removeCourse(String courseCode) {
		for (Course course : courses) {
			if (course.getCourseCode().equals(courseCode)) {
				courses.remove(course);
			}
		}
	}

	// Getters and Setters
	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String name) {
		// Split to first and last name
		String[] fullName = name.split(" ");
		String firstName = fullName[0];
		String lastName = fullName[1];
		if (lastName.equals("å") || lastName.equals("ä")) {
			lastName = "a";
		} else if (lastName.equals("ö")) {
			lastName = "o";
		} else {
			// Set first 2 letters
			Character firstNameFirstLetter;
			firstNameFirstLetter = firstName.toLowerCase().charAt(0);
			Character firstNameSecondLetter;
			firstNameSecondLetter = firstName.toLowerCase().charAt(1);

			if (firstNameFirstLetter.equals('å')) {
				firstNameFirstLetter = 'a';
			}
			if (firstNameFirstLetter.equals('ä')) {
				firstNameFirstLetter = 'a';
			}
			if (firstNameFirstLetter.equals('ö')) {
				firstNameFirstLetter = 'o';
			}

			if (firstNameSecondLetter.equals('å') || firstNameSecondLetter.equals('ä')) {
				firstNameSecondLetter = 'a';
			}
			if (firstNameSecondLetter.equals('ö')) {
				firstNameSecondLetter = 'o';
			}

			// Generate 4 random digits
			int min = 1000;
			int max = 9999;

			// Convert random numbers to String
			double randomNumberDouble = Math.random() * (max - min + 1) + min;
			int randomNumberInt = (int) randomNumberDouble;
			String randomNumberString = Integer.toString(randomNumberInt);

			// Set final 2 letters
			Character lastNameFirstLetter;
			lastNameFirstLetter = lastName.toLowerCase().charAt(0);
			Character lastNameSecondLetter;
			lastNameSecondLetter = lastName.toLowerCase().charAt(1);

			// if last name first letter is å, ä or ö
			if (lastNameFirstLetter.equals('å')) {
				lastNameFirstLetter = 'a';
			}
			if (lastNameFirstLetter.equals('ä')) {
				lastNameFirstLetter = 'a';
			}
			if (lastNameFirstLetter.equals('ö')) {
				lastNameFirstLetter = 'o';
			}

			// if last name second letter is å, ä or ö
			if (lastNameSecondLetter.equals('å')) {
				lastNameSecondLetter = 'a';
			}
			if (lastNameSecondLetter.equals('ä')) {
				lastNameSecondLetter = 'a';
			}
			if (lastNameSecondLetter.equals('ö')) {
				lastNameSecondLetter = 'o';
			}

			this.employeeId = "" + firstNameFirstLetter + firstNameSecondLetter + randomNumberString
					+ lastNameFirstLetter + lastNameSecondLetter;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getHourlySalary() {
		return hourlySalary;
	}

	public void setHourlySalary(double hourlySalary) {
		this.hourlySalary = hourlySalary;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}



	public void updateCourse(String courseCode, String name, double numberOfCredits, String cycleType, Teacher responsibleTeacher) {
		for (Course course : courses) {
			if (course.getCourseCode().equals(courseCode)) {
				course.setName(name);
				course.setNumberOfCredits(numberOfCredits);
				course.setCycleType(cycleType);
				course.setResponsibleTeacher(responsibleTeacher);
			}
		} 
	}

	public ObservableList<Course> getCourses() {
		return courses;
	}

	public void setCourses(ObservableList<Course> testCourses) {
		this.courses = testCourses;
	}

	public void addCourse(Course course) {
		courses.add(course);
	}

	public ArrayList<Teaching> getTeaching() {
		return teaching;
	}

	public void setTeaching(ArrayList<Teaching> teaching) {
		this.teaching = teaching;
	}
}
