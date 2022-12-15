package se.lu.ics.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DepartmentRegister {
private Department department; 
	private static ObservableList<Department> departments = FXCollections.observableArrayList();

	// find department
	public Department findDepartment(String name) {
		for (Department department : departments) {
			if (department.getName().equals(name)) {
				return department;
			}
		}
		return null;
	}

	// delete department
	public void deleteDepartment(String name) {
		Department department = findDepartment(name);
		if (department != null) {
			departments.remove(department);
		}
	}

	// add department
	public void addDepartment(Department department) {
		departments.add(department);
	}

	// getters and setters
	public ObservableList<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(ObservableList<Department> departments) {
		this.departments = departments;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}
