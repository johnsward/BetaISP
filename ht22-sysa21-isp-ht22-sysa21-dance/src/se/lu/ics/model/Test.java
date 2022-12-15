package se.lu.ics.model;

public class Test {
	public static void main(String [] args) {
		
		DepartmentRegister dReg = new DepartmentRegister();

		Department department1 = new Department("Informatics", "vägen1", 1000.5);
		dReg.addDepartment(department1);
		Department department2 = new Department("Economics", "vägen1", 1000.5);
		dReg.addDepartment(department2);
		Department department3 = new Department("Test", "vägen1", 1000.5);
		dReg.addDepartment(department3);
		

		 Teacher teacher1 = new Teacher("Olle",  "Proffesor", "LärarVägen",  157,  department1);
		 teacher1.setDepartment(department1);
		   
	  		
	}	
}
