package resources.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.ConcurrentModificationException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import se.lu.ics.model.Department;
import se.lu.ics.model.DepartmentRegister;
import se.lu.ics.model.Teacher;

public class TeacherSceneController implements Initializable {

	private Teacher teacher = new Teacher();
	private DepartmentRegister departmentRegister = new DepartmentRegister();
	private Department department = new Department();
	private Teacher selectedTeacher;

	@FXML
	private Stage stage;
	@FXML
	private Scene scene;
	@FXML
	private Parent root;

	// main part of teacher (tableview, create etc)
	@FXML
	private TextField textFieldTeacherAddName;
	@FXML
	private ComboBox<String> comboBoxTeacherAddTitle;
	@FXML
	private TextField textFieldTeacherAddAddress;
	@FXML
	private TextField textFieldTeacherAddSalary;
	@FXML
	private ComboBox<String> comboBoxTeacherAddDepartment;
	@FXML
	private TableView<Teacher> tableViewTeachers;
	@FXML
	private TableColumn<Teacher, String> tableColumnTeacherEmployeeId;
	@FXML
	private TableColumn<Teacher, String> tableColumnTeacherName;
	@FXML
	private Button buttonTeacherUpdate;
	@FXML
	private Label labelTeacherErrorMessage;
	@FXML
	private Label labelTeacherSuccessMessage;
	@FXML
	private Label labelTeacherAddNameError;
	@FXML
	private Label labelTeacherAddTitleError;
	@FXML
	private Label labelTeacherAddAddressError;
	@FXML
	private Label labelTeacherAddSalaryError;
	@FXML
	private Label labelTeacherAddDepartmentError;

	// Update teacher
	@FXML
	private AnchorPane anchorPaneTeacherUpdate;
	@FXML
	private TextField textFieldTeacherUpdateName;
	@FXML
	private TextField textFieldTeacherUpdateAddress;
	@FXML
	private TextField textFieldTeacherUpdateSalary;
	@FXML
	private ComboBox<String> comboBoxTeacherUpdateTitle;
	@FXML
	private ComboBox<String> comboBoxTeacherUpdateDepartment;
	@FXML
	private Label labelTeacherUpdateErrorMessage;
	@FXML
	private Label labelTeacherUpdateNameError;
	@FXML
	private Label labelTeacherUpdateTitleError;
	@FXML
	private Label labelTeacherUpdateAddressError;
	@FXML
	private Label labelTeacherUpdateSalaryError;
	@FXML
	private Label labelTeacherUpdateDepartmentError;

	public void initialize(URL arg0, ResourceBundle arg1) {
		// disable update-pane
		anchorPaneTeacherUpdate.setVisible(false);
		anchorPaneTeacherUpdate.disableProperty().set(true);

		// adding options for department-comboboxes
		for (int i = 0; i < departmentRegister.getDepartments().size(); i++) {
			comboBoxTeacherAddDepartment.getItems().add(departmentRegister.getDepartments().get(i).getName());
			comboBoxTeacherUpdateDepartment.getItems().add(departmentRegister.getDepartments().get(i).getName());
		}

		// adding to tableview
		tableColumnTeacherEmployeeId.setCellValueFactory(new PropertyValueFactory<Teacher, String>("employeeId"));
		tableColumnTeacherName.setCellValueFactory(new PropertyValueFactory<Teacher, String>("name"));
		tableViewTeachers.setItems(department.getTeacherData());

		// adding options for title-comboboxes
		comboBoxTeacherAddTitle.getItems().addAll(teacher.getTitles());
		comboBoxTeacherUpdateTitle.getItems().addAll(teacher.getTitles());

		// hiding error messages
		labelTeacherUpdateNameError.setText(" ");
		labelTeacherUpdateTitleError.setText(" ");
		labelTeacherUpdateAddressError.setText(" ");
		labelTeacherUpdateSalaryError.setText(" ");
		labelTeacherUpdateDepartmentError.setText(" ");
		labelTeacherUpdateErrorMessage.setText(" ");
		labelTeacherErrorMessage.setText(" ");
		labelTeacherSuccessMessage.setText(" ");

		// listener for tableview (used for update-anchor pane)
		tableViewTeachers.setOnMouseClicked(event -> {
			selectedTeacher = tableViewTeachers.getSelectionModel().getSelectedItem();
		});
	}

	public void buttonHomeClicked(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/MenuScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void buttonTeacherCreateClicked(ActionEvent event) {
		// hiding error messages
		labelTeacherAddNameError.setText(" ");
		labelTeacherAddTitleError.setText(" ");
		labelTeacherAddAddressError.setText(" ");
		labelTeacherAddSalaryError.setText(" ");
		labelTeacherAddDepartmentError.setText(" ");
		labelTeacherErrorMessage.setText(" ");
		labelTeacherSuccessMessage.setText(" ");

		// string array to calculate total words in name field
		String[] numberOfWords = textFieldTeacherAddName.getText().split(" ");

		//int to calculate total letters in name field
		String enteredName = textFieldTeacherAddName.getText().replace(" ", "");
		int numberOfChars = enteredName.length();
		
		// Taken from:
		// https://stackoverflow.com/questions/36150181/how-to-differentiate-numbers-and-strings
		// string array to find any numbers in name field
		String[] numbers = textFieldTeacherAddName.getText().split("\\D+");
		int sum = 0;
		for (String number : numbers) {
			try {
				sum += Integer.parseInt(number);
			} catch (Exception exception) {
			}
		}

		// make sure salary doesn't have letters
		try {
			if (textFieldTeacherAddSalary.getText() != "") {
				double checkSalary = Double.parseDouble(textFieldTeacherAddSalary.getText());
			}
			else if (textFieldTeacherAddName.getText().isEmpty() == true && comboBoxTeacherAddTitle.getValue().equals(null)
					&& textFieldTeacherAddAddress.getText().isEmpty() == true
					&& textFieldTeacherAddSalary.getText().isEmpty() == true
					&& comboBoxTeacherAddDepartment.getValue().equals(null)) {
				labelTeacherErrorMessage.setText("Please fill out all the fields");
				if (textFieldTeacherAddName.getText().isEmpty() == true) {
					labelTeacherAddNameError.setText("*");
				}
				if (comboBoxTeacherAddTitle.getValue() == null) {
					labelTeacherAddTitleError.setText("*");
				}
				if (textFieldTeacherAddAddress.getText().isEmpty() == true) {
					labelTeacherAddAddressError.setText("*");
				}
				if (textFieldTeacherAddSalary.getText().isEmpty() == true) {
					labelTeacherAddSalaryError.setText("*");
				}
				if (comboBoxTeacherAddDepartment.getValue() == null) {
					labelTeacherAddDepartmentError.setText("*");
				}
			}
			if (numberOfWords.length != 2 || sum > 0 || numberOfChars<4) {
				// make sure only first and last name are entered, at least 4 letters
				if (numberOfWords.length != 2 || numberOfChars<4) {
					labelTeacherAddNameError.setText("*");
					labelTeacherErrorMessage.setText("Please enter first and last name.");
				}
				// make sure name doesn't have numbers
				if (sum > 0) {
					labelTeacherAddNameError.setText("*");
					labelTeacherErrorMessage.setText("Only enter letters.");
				}
			}
			// check that all fields are filled in
			else if (textFieldTeacherAddName.getText().isEmpty() == true || comboBoxTeacherAddTitle.getValue().equals(null)
					|| textFieldTeacherAddAddress.getText().isEmpty() == true
					|| textFieldTeacherAddSalary.getText().isEmpty() == true
					|| comboBoxTeacherAddDepartment.getValue().equals(null)) {
				labelTeacherErrorMessage.setText("Please fill out all the fields");
				if (textFieldTeacherAddName.getText().isEmpty() == true) {
					labelTeacherAddNameError.setText("*");
				}
				if (comboBoxTeacherAddTitle.getValue() == null) {
					labelTeacherAddTitleError.setText("*");
				}
				if (textFieldTeacherAddAddress.getText().isEmpty() == true) {
					labelTeacherAddAddressError.setText("*");
				}
				if (textFieldTeacherAddSalary.getText().isEmpty() == true) {
					labelTeacherAddSalaryError.setText("*");
				}
				if (comboBoxTeacherAddDepartment.getValue() == null) {
					labelTeacherAddDepartmentError.setText("*");
				}
			}  else {
				Teacher tempTeacher = new Teacher(textFieldTeacherAddName.getText(), comboBoxTeacherAddTitle.getValue(),
						textFieldTeacherAddAddress.getText(), Double.parseDouble(textFieldTeacherAddSalary.getText()),
						departmentRegister.findDepartment(comboBoxTeacherAddDepartment.getValue()));
				tempTeacher.setEmployeeId(tempTeacher.getName());
				tempTeacher.setDepartment(departmentRegister.findDepartment(comboBoxTeacherAddDepartment.getValue()));

				// add to table view
				department.addTeacherData(tempTeacher);
				tableColumnTeacherEmployeeId
						.setCellValueFactory(new PropertyValueFactory<Teacher, String>("employeeId"));
				tableColumnTeacherName.setCellValueFactory(new PropertyValueFactory<Teacher, String>("name"));
				tableViewTeachers.setItems(department.getTeacherData());
				labelTeacherSuccessMessage.setText("Teacher added");
				labelTeacherErrorMessage.setText("");
			}
		} catch (NumberFormatException exception) {
			// update error messages
			labelTeacherAddNameError.setText(" ");
			labelTeacherAddTitleError.setText(" ");
			labelTeacherAddAddressError.setText(" ");
			labelTeacherAddSalaryError.setText("*");
			labelTeacherAddDepartmentError.setText(" ");
			labelTeacherErrorMessage.setText("Only enter digits");
			labelTeacherSuccessMessage.setText("");
		} catch (StringIndexOutOfBoundsException exception) {
			labelTeacherAddNameError.setText("*");
			labelTeacherAddTitleError.setText(" ");
			labelTeacherAddAddressError.setText(" ");
			labelTeacherAddSalaryError.setText(" ");
			labelTeacherAddDepartmentError.setText(" ");
			labelTeacherErrorMessage.setText("Please enter full first and last name");
			labelTeacherSuccessMessage.setText("");
		} catch (NullPointerException exception) {
			//department not selected
			if(comboBoxTeacherAddTitle.getValue() == null || textFieldTeacherAddAddress.getText().isEmpty() == true || textFieldTeacherAddSalary.getText().isEmpty() == true || comboBoxTeacherAddDepartment.getValue() == null) {
				if (textFieldTeacherAddName.getText().isEmpty() == true) {
					labelTeacherAddNameError.setText("*");
				}
				if (comboBoxTeacherAddTitle.getValue() == null) {
					labelTeacherAddTitleError.setText("*");
				}
				if (textFieldTeacherAddAddress.getText().isEmpty() == true) {
					labelTeacherAddAddressError.setText("*");
				}
				if (textFieldTeacherAddSalary.getText().isEmpty() == true) {
					labelTeacherAddSalaryError.setText("*");
				}
				if (comboBoxTeacherAddDepartment.getValue() == null) {
					labelTeacherAddDepartmentError.setText("*");
				}
				
				labelTeacherErrorMessage.setText("Please fill out all the fields");
				labelTeacherSuccessMessage.setText("");
			}
			//name incomplete
			else if(numberOfWords.length!=2 || sum!=0 || numberOfChars<4){
				labelTeacherAddNameError.setText("*");
				labelTeacherErrorMessage.setText("Name incomplete");
			}
			
			else{
				labelTeacherAddNameError.setText(" ");
				labelTeacherAddTitleError.setText(" ");
				labelTeacherAddAddressError.setText(" ");
				labelTeacherAddSalaryError.setText(" ");
				labelTeacherAddDepartmentError.setText("*");
				labelTeacherErrorMessage.setText("Please select department");
				labelTeacherSuccessMessage.setText("");
			}
		}
	}

	public void buttonTeacherUpdateClicked(ActionEvent event) {
		try {
			if(selectedTeacher.getName() == null) {
				labelTeacherErrorMessage.setText("Please select teacher");
				labelTeacherSuccessMessage.setText(" ");
			}
			else {
				// set error message to nothing
				labelTeacherAddNameError.setText(" ");
				labelTeacherAddTitleError.setText(" ");
				labelTeacherAddAddressError.setText(" ");
				labelTeacherAddSalaryError.setText(" ");
				labelTeacherAddDepartmentError.setText(" ");
				labelTeacherErrorMessage.setText(" ");
				labelTeacherSuccessMessage.setText("");
	
				// show pop-up
				anchorPaneTeacherUpdate.setVisible(true);
				anchorPaneTeacherUpdate.disableProperty().set(false);
	
				// pre-fill-in fields
				textFieldTeacherUpdateName.setText(selectedTeacher.getName());
				comboBoxTeacherUpdateTitle.setValue(selectedTeacher.getTitle());
				textFieldTeacherUpdateAddress.setText(selectedTeacher.getAddress());
				textFieldTeacherUpdateSalary.setText(Double.toString(selectedTeacher.getHourlySalary()));
				comboBoxTeacherUpdateDepartment.setValue(selectedTeacher.getDepartment().getName());
			}
		}

		// error if a teacher isn't selected
		catch (NullPointerException exception) {
			anchorPaneTeacherUpdate.setVisible(false);
			anchorPaneTeacherUpdate.disableProperty().set(true);
			labelTeacherErrorMessage.setText("Please select the teacher you wish to update.");
		}
	}

	public void buttonTeacherUpdateCancelClicked(ActionEvent event) {
		anchorPaneTeacherUpdate.setVisible(false);
		anchorPaneTeacherUpdate.disableProperty().set(true);
	}

	public void buttonTeacherUpdateConfirmClicked(ActionEvent event) {
		// hiding error messages
		labelTeacherUpdateNameError.setText(" ");
		labelTeacherUpdateTitleError.setText(" ");
		labelTeacherUpdateAddressError.setText(" ");
		labelTeacherUpdateSalaryError.setText(" ");
		labelTeacherUpdateDepartmentError.setText(" ");
		labelTeacherUpdateErrorMessage.setText(" ");
		labelTeacherErrorMessage.setText(" ");
		labelTeacherSuccessMessage.setText(" ");

		// string array to calculate total words in name field
		String[] numberOfWords = textFieldTeacherUpdateName.getText().split(" ");

		//int to calculate total letters in name field
		String enteredName = textFieldTeacherUpdateName.getText().replace(" ", "");
		int numberOfChars = enteredName.length();
		
		// Taken from:
		// https://stackoverflow.com/questions/36150181/how-to-differentiate-numbers-and-strings
		// string array to find any numbers in name field
		String[] numbers = textFieldTeacherUpdateName.getText().split("\\D+");
		int sum = 0;
		for (String number : numbers) {
			try {
				sum += Integer.parseInt(number);
			} catch (Exception exception) {
			}
		}

		// make sure salary doesn't have letters
		try {
			if (textFieldTeacherUpdateSalary.getText() != "") {
				double checkSalary = Double.parseDouble(textFieldTeacherUpdateSalary.getText());
			}
			else if (textFieldTeacherUpdateName.getText().isEmpty() == true && comboBoxTeacherUpdateTitle.getValue().equals(null)
					&& textFieldTeacherUpdateAddress.getText().isEmpty() == true
					&& textFieldTeacherUpdateSalary.getText().isEmpty() == true
					&& comboBoxTeacherUpdateDepartment.getValue().equals(null)) {
				labelTeacherUpdateErrorMessage.setText("Please fill out all the fields");
				if (textFieldTeacherUpdateName.getText().isEmpty() == true) {
					labelTeacherUpdateNameError.setText("*");
				}
				if (comboBoxTeacherUpdateTitle.getValue() == null) {
					labelTeacherUpdateTitleError.setText("*");
				}
				if (textFieldTeacherUpdateAddress.getText().isEmpty() == true) {
					labelTeacherUpdateAddressError.setText("*");
				}
				if (textFieldTeacherUpdateSalary.getText().isEmpty() == true) {
					labelTeacherUpdateSalaryError.setText("*");
				}
				if (comboBoxTeacherUpdateDepartment.getValue() == null) {
					labelTeacherUpdateDepartmentError.setText("*");
				}
			}
			if (numberOfWords.length != 2 || sum > 0 || numberOfChars<4) {
				// make sure only first and last name are entered, at least 4 letters
				if (numberOfWords.length != 2 || numberOfChars<4) {
					labelTeacherUpdateNameError.setText("*");
					labelTeacherUpdateErrorMessage.setText("Please enter first and last name.");
				}
				// make sure name doesn't have numbers
				if (sum > 0) {
					labelTeacherUpdateNameError.setText("*");
					labelTeacherUpdateErrorMessage.setText("Only enter letters.");
				}
			}
			// check that all fields are filled in
			else if (textFieldTeacherUpdateName.getText().isEmpty() == true
					|| textFieldTeacherUpdateAddress.getText().isEmpty() == true
					|| textFieldTeacherUpdateSalary.getText().isEmpty() == true) {
				labelTeacherUpdateErrorMessage.setText("Please fill out all the fields");
				if (textFieldTeacherUpdateName.getText().isEmpty() == true) {
					labelTeacherUpdateNameError.setText("*");
				}
				if (comboBoxTeacherUpdateTitle.getValue() == null) {
					labelTeacherUpdateTitleError.setText("*");
				}
				if (textFieldTeacherUpdateAddress.getText().isEmpty() == true) {
					labelTeacherUpdateAddressError.setText("*");
				}
				if (textFieldTeacherUpdateSalary.getText().isEmpty() == true) {
					labelTeacherUpdateSalaryError.setText("*");
				}
				if (comboBoxTeacherUpdateDepartment.getValue() == null) {
					labelTeacherUpdateDepartmentError.setText("*");
				}
			}  else {
				// update teacher
				Teacher tempTeacher = new Teacher();
				tempTeacher = selectedTeacher;
				department.updateTeacher(tempTeacher.getEmployeeId(), textFieldTeacherUpdateName.getText(),
						comboBoxTeacherUpdateTitle.getValue(), textFieldTeacherUpdateAddress.getText(),
						Double.parseDouble(textFieldTeacherUpdateSalary.getText()),
						departmentRegister.findDepartment(comboBoxTeacherUpdateDepartment.getValue()));

				labelTeacherSuccessMessage.setText("Teacher updated");
				labelTeacherErrorMessage.setText("");
				
				//hide pop-up
				anchorPaneTeacherUpdate.setVisible(false);
				anchorPaneTeacherUpdate.disableProperty().set(true);
				
				// update tableview and hide pop-up
				tableViewTeachers.refresh();
				tableViewTeachers.setItems(department.getTeacherData());
				anchorPaneTeacherUpdate.setVisible(false);
				anchorPaneTeacherUpdate.disableProperty().set(true);
				labelTeacherSuccessMessage.setText("Teacher updated.");
			}
		} catch (NumberFormatException exception) {
			// update error messages
			labelTeacherUpdateNameError.setText(" ");
			labelTeacherUpdateTitleError.setText(" ");
			labelTeacherUpdateAddressError.setText(" ");
			labelTeacherUpdateSalaryError.setText("*");
			labelTeacherUpdateDepartmentError.setText(" ");
			labelTeacherUpdateErrorMessage.setText("Only enter digits");
			labelTeacherSuccessMessage.setText("");
		} catch (StringIndexOutOfBoundsException exception) {
			labelTeacherUpdateNameError.setText("*");
			labelTeacherUpdateTitleError.setText(" ");
			labelTeacherUpdateAddressError.setText(" ");
			labelTeacherUpdateSalaryError.setText(" ");
			labelTeacherUpdateDepartmentError.setText(" ");
			labelTeacherUpdateErrorMessage.setText("Please enter full first and last name");
			labelTeacherSuccessMessage.setText("");
		} catch (NullPointerException exception) {
			//department not selected
			if(comboBoxTeacherUpdateTitle.getValue() == null || textFieldTeacherUpdateAddress.getText().isEmpty() == true || textFieldTeacherAddSalary.getText().isEmpty() == true || comboBoxTeacherUpdateDepartment.getValue() == null) {
				if (textFieldTeacherUpdateName.getText().isEmpty() == true) {
					labelTeacherUpdateNameError.setText("*");
				}
				if (comboBoxTeacherUpdateTitle.getValue() == null) {
					labelTeacherUpdateTitleError.setText("*");
				}
				if (textFieldTeacherUpdateAddress.getText().isEmpty() == true) {
					labelTeacherUpdateAddressError.setText("*");
				}
				if (textFieldTeacherUpdateSalary.getText().isEmpty() == true) {
					labelTeacherUpdateSalaryError.setText("*");
				}
				if (comboBoxTeacherUpdateDepartment.getValue() == null) {
					labelTeacherUpdateDepartmentError.setText("*");
				}
				
				labelTeacherUpdateErrorMessage.setText("Please fill out all the fields");
				labelTeacherSuccessMessage.setText("");
			}
			//name incomplete
			else if(numberOfWords.length!=2 || sum!=0 || numberOfChars<4){
				labelTeacherUpdateNameError.setText("*");
				labelTeacherUpdateErrorMessage.setText("Name incomplete");
			}
			
			else{
				labelTeacherUpdateNameError.setText(" ");
				labelTeacherUpdateTitleError.setText(" ");
				labelTeacherUpdateAddressError.setText(" ");
				labelTeacherUpdateSalaryError.setText(" ");
				labelTeacherUpdateDepartmentError.setText("*");
				labelTeacherUpdateErrorMessage.setText("Please select department");
				labelTeacherSuccessMessage.setText("");
			}
		}
	}

	public void buttonTeacherRemoveClicked(ActionEvent event) {
		try {
		Teacher tempTeacher = new Teacher();
		tempTeacher = selectedTeacher;
		if(tempTeacher.getName().equals(null)) {
			labelTeacherErrorMessage.setText("Please select teacher");
			labelTeacherSuccessMessage.setText(" ");
		}
		else {
			department.removeTeacher(tempTeacher.getEmployeeId());
			Teacher emptyTeacher = new Teacher();
			tempTeacher = emptyTeacher;
			selectedTeacher = emptyTeacher;
			tableViewTeachers.setItems(department.getTeacherData());
		}
		}catch(ConcurrentModificationException exception) {
			labelTeacherErrorMessage.setText(" ");
			labelTeacherSuccessMessage.setText("Teacher removed");
			Teacher emptyTeacher = new Teacher();
			selectedTeacher = emptyTeacher;


		}catch(NullPointerException exception) {
			labelTeacherErrorMessage.setText("Please select teacher");
			labelTeacherSuccessMessage.setText(" ");

		}
	}
}
