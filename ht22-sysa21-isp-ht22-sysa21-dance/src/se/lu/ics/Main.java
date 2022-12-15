package se.lu.ics;

import javafx.application.Application; 
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import se.lu.ics.model.Course;
import se.lu.ics.model.Department;
import se.lu.ics.model.DepartmentRegister;
import se.lu.ics.model.Teacher;
import se.lu.ics.model.Teaching;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application{ 
	
	Department department = new Department();
	DepartmentRegister departmentRegister = new DepartmentRegister(); 
	
	
	@Override 
	public void start(Stage primaryStage) { 
		try { 
			//adding options for department-comboboxes
			Department department1 = new Department("Economics","Väg 1",100000);
			Department department2 = new Department("Informatics","Väg 2",130000);
			Department department3 = new Department("Science","Väg 3",50000);
			
			//add departments to observable list
			departmentRegister.addDepartment(department1);
			departmentRegister.addDepartment(department2);
			departmentRegister.addDepartment(department3);
			
			//creating teachers
			Teacher teacher1 = new Teacher("Olle Ström", "Proffesor", "Lärarvägen 45", 159, department1);
			teacher1.setEmployeeId(teacher1.getName());
			teacher1.setDepartment(department1);
			Teacher teacher2 = new Teacher("Stina Olofsson", "Lecturer", "Vägen 3", 159, department1);
			teacher2.setEmployeeId(teacher1.getName());
			teacher2.setDepartment(department1);

			//set head of department
			department1.setHeadOfDepartment(teacher1);
			department2.setHeadOfDepartment(teacher1);
			department3.setHeadOfDepartment(teacher2);
			
			//add teacherData
			department1.addTeacherData(teacher1);
			department1.addTeacherData(teacher2);

			//creating courses
			Course course1 = new Course("SYSA21", 15, "First Cycle", teacher1);
			course1.setCourseCode(course1.getCycleType());
			Course course2 = new Course("SYSA12", 20, "Second Cycle", teacher1);
			course2.setCourseCode(course2.getCycleType());
			Course course3 = new Course("EK17", 30, "Third Cycle", teacher2);
			course3.setCourseCode(course3.getCycleType());
			
			//course1 add teachers + responsible
			teacher1.addCourse(course1);
			course1.addCourseTeachers(teacher1);
			course1.setResponsibleTeacher(teacher1);
			
			//course2 add teachers + responsible
			teacher1.addCourse(course2);
			course2.addCourseTeachers(teacher1);
			course2.setResponsibleTeacher(teacher1);
			
			//course3 add teachers + responsible
			teacher2.addCourse(course3);
			course3.addCourseTeachers(teacher2);
			course3.setResponsibleTeacher(teacher2);
			
			
			Teaching teaching1 = new Teaching(20221213, 100, teacher1, course1); 
			teacher1.AddTeaching(teaching1);
			course1.AddTeaching(teaching1);
			
			//System.out.println(department.calculateMedianSalary());
			
			//teacher.addCourse(course2);
			
		System.out.println(department.calculateMedianSalary());
		
			Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/MenuScene.fxml")); 
			Scene scene = new Scene(root); 
			
			
			primaryStage.setScene(scene); 
	  
			primaryStage.setTitle("Home-Page"); 
			primaryStage.show(); 
	       } catch(Exception e) { 
	    	   e.printStackTrace(); 
	       } 
	     } 

	public static void main(String[] args) { 
		launch(args); 
	} 
} 