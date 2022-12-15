package se.lu.ics.model;

import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Department {

	private String name;
	private String address;
	private double budget;
	private DepartmentRegister departmentRegister = new DepartmentRegister();
	private Teacher headOfDepartment;

	private double calculateDepartmentCost;

	// arraylist for teachers
	private static ObservableList<Teacher> teacherData = FXCollections.observableArrayList();

	public Department() {

	}

	public Department(String name, String address, double budget) {
		this.name = name;
		this.address = address;
		this.budget = budget;
	}

	public void updateDepartment() {

	}

	public double calculateMedianSalary() { 
		double medianSalary = 0;
		double salary = 0;
		for (Department department : departmentRegister.getDepartments()) {
			for (Teacher teacher : teacherData.sorted()) {
				if(teacherData.size() % 2 == 1) {
					return salary;
				}
				salary += teacher.getHourlySalary();
				medianSalary = (salary+1) / 2;

			}

		}

		return medianSalary;
		
	}

	public double calculateDepartmentCost() {
		double cost = 0;
		for (Teacher teacher : teacherData) {
			for (Course course : teacher.getCourses()) {
				cost += course.calculateCourseCost();
			}

		}
		return cost;
	}

	public double getCalculateDepartmentCost() {
		calculateDepartmentCost = 0;
		for (Teacher teacher : teacherData) {
			for (Course course : teacher.getCourses()) {
				calculateDepartmentCost += course.calculateCourseCost();
			}
		}
		return calculateDepartmentCost;
	}

	public void setCalculateDepartmentCost(double calculateDepartmentCost) {
		this.calculateDepartmentCost = calculateDepartmentCost;
	}

	// Getters and Setters
	public Teacher getHeadOfDepartment() {
		return headOfDepartment;
	}

	public void setHeadOfDepartment(Teacher headOfDepartment) {
		this.headOfDepartment = headOfDepartment;
	}

	public String getHeadOfDepartmentName() {
		return headOfDepartment.getName();
	}

	public String getTeacherNames() {
		for (Teacher teacher : teacherData) {
			return teacher.getName();
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public void removeTeacher(String employeeId) {
		for (Teacher teacher : teacherData) {
			if (teacher.getEmployeeId().equals(employeeId)) {
				teacherData.remove(teacher);
			}
		}
	}

	public Teacher findTeacherId(String employeeId) {
		for (Teacher teacher : teacherData) {
			if (teacher.getEmployeeId().equals(employeeId)) {
				return teacher;
			}
		}
		return null;
	}

	// Update teacher
	public void updateTeacher(String employeeId, String name, String title, String address, double hourlySalary,
			Department department) {
		for (Teacher teacher : teacherData) {
			if (teacher.getEmployeeId().equals(employeeId)) {
				teacher.setEmployeeId(name);
				teacher.setName(name);
				teacher.setTitle(title);
				teacher.setAddress(address);
				teacher.setHourlySalary(hourlySalary);
				teacher.setDepartment(department);
			}
		}
	}

	public Teacher findTeacher(String name, String address, double salary) {
		for (Teacher teacher : teacherData) {
			if (teacher.getName().equals(name) && teacher.getAddress().equals(address)
					&& teacher.getHourlySalary() == salary) {
				return teacher;
			}
		}
		return null;
	}

	// Add teacher
	public void addTeacherData(Teacher teacher) {
		this.teacherData.add(teacher);
	}

	public ObservableList<Teacher> getTeacherData() {
		return teacherData;
	}

	public void setTeacherData(ObservableList<Teacher> teacherData) {
		this.teacherData = teacherData;
	}

	public Teacher findTeacherByName(String name) {
		for (Teacher teacher : teacherData) {
			if (teacher.getName().equals(name)) {
				return teacher;
			}
		}
		return null;
	}

	public DepartmentRegister getDepartmentRegister() {
		return departmentRegister;
	}

	public void setDepartmentRegister(DepartmentRegister departmentRegister) {
		this.departmentRegister = departmentRegister;
	}
}
