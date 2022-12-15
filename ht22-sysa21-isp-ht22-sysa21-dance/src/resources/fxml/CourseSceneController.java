package resources.fxml;

import java.io.IOException;

import javafx.collections.FXCollections;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import resources.fxml.MenuSceneController;
import se.lu.ics.model.Course;
import se.lu.ics.model.Department;
import se.lu.ics.model.Teacher;

public class CourseSceneController {

	@FXML
	private Stage stage;
	@FXML
	private Scene scene;
	@FXML
	private Parent root;

	private Teacher teacher = new Teacher();
	private Department department = new Department();
	private Course selectedCourse;

	public void buttonHomeClicked(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/MenuScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	// TableView showing courses
	@FXML
	private TextField textFieldCourseFilter;
	@FXML
	private TableView<Course> tableViewCourses;
	@FXML
	private TableColumn<Course, String> tableColumnCourseName;
	@FXML
	private TableColumn<Course, String> tableColumnCourseCode;
	@FXML
	private TableColumn<Course, Double> tableColumnCourseCredits;
	@FXML
	private TableColumn<Course, String> tableColumnCourseType;
	@FXML
	private TableColumn<Course, String> tableColumnCourseResponsibleTeacher;
	@FXML
	private Label labelCourseErrorMessage;

	// Add and update panes
	@FXML
	private AnchorPane anchorPaneUpdateCourse;
	@FXML
	private AnchorPane anchorPaneAddCourse;

	// Update course
	@FXML
	private TextField textFieldCourseName;
	@FXML
	private TextField textFieldCourseCode;
	@FXML
	private TextField textFieldCourseCredits;
	@FXML
	private ComboBox<String> comboBoxCourseUpdateCycleType;
	@FXML
	private ComboBox<String> comboBoxCourseResponsibleTeacher;

	// Add course
	@FXML
	private TextField textFieldCourseAddName;
	@FXML
	private TextField textFieldCourseAddCourseCredits;
	@FXML
	private ComboBox<String> comboBoxCourseAddCycleType;
	@FXML
	private ComboBox<String> comboBoxCourseAddResponsible;
	@FXML
	private Label labelCourseAddNameError;
	@FXML
	private Label labelCourseAddCreditsError;
	@FXML
	private Label labelCourseAddCycleTypeError;
	@FXML
	private Label labelCourseAddResponsibleError;
	@FXML
	private Label labelCourseAddError;

	public void initialize() {
		// Populating the columns with values
		tableColumnCourseName.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));
		tableColumnCourseCode.setCellValueFactory(new PropertyValueFactory<Course, String>("courseCode"));
		tableColumnCourseCredits.setCellValueFactory(new PropertyValueFactory<Course, Double>("numberOfCredits"));
		tableColumnCourseType.setCellValueFactory(new PropertyValueFactory<Course, String>("cycleType"));
		tableColumnCourseResponsibleTeacher
				.setCellValueFactory(new PropertyValueFactory<Course, String>("responsibleTeacherName"));

		// Adding values to comboBox for cycle type
		comboBoxCourseUpdateCycleType
				.setItems(FXCollections.observableArrayList("First Cycle", "Second Cycle", "Third Cycle"));
		comboBoxCourseAddCycleType
				.setItems(FXCollections.observableArrayList("First Cycle", "Second Cycle", "Third Cycle"));

		// Disable pop-ups
		anchorPaneAddCourse.disableProperty().set(true);
		anchorPaneAddCourse.opacityProperty().set(0);
		anchorPaneUpdateCourse.disableProperty().set(true);
		anchorPaneUpdateCourse.opacityProperty().set(0);

		// Adding values to comboBox for responsible teacher
		for (int i = 0; i < department.getTeacherData().size(); i++) {
			comboBoxCourseAddResponsible.getItems().add(department.getTeacherData().get(i).getName());
		}

		// Filtered list
		// Idea from:
		// https://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/PersonTableController.java
		// Inserting the ObservableList located in class Teacher into a FilteredList
		FilteredList<Course> filteredCourseList = new FilteredList<>(teacher.getCourses(), s -> true);
		// Setting a predicate when the filter is changing depending on what user types
		textFieldCourseFilter.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredCourseList.setPredicate(course -> {
				// If the filter/search bar is empty it will display the whole list
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				// Looking through all the values in the tableView and depending on what user
				// types the filter changes
				String lowerCaseFilter = newValue.toLowerCase();
				if (course.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;

				} else if (course.getCourseCode().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;

				} else if (String.valueOf(course.getCourseCode()).indexOf(lowerCaseFilter) != -1) {
					return true;

				} else if (course.getCycleType().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (course.getResponsibleTeacher().getName().toLowerCase().indexOf(lowerCaseFilter) != -1)
					return true;
				else
					return false;
			});

		});

		// Inserting the FilteredList into a SortedList
		SortedList<Course> sortedCourseList = new SortedList<>(filteredCourseList);
		// Adding all values to the tableView
		sortedCourseList.comparatorProperty().bind(tableViewCourses.comparatorProperty());
		tableViewCourses.setItems(sortedCourseList);

		// Selected row
		tableViewCourses.setOnMouseClicked(event -> {
			selectedCourse = tableViewCourses.getSelectionModel().getSelectedItem();
		});
	}

	public void deleteCourseButtonClicked(ActionEvent event) {
		Course tempCourse = new Course();
		tempCourse = selectedCourse;
		teacher.removeCourse(tempCourse.getCourseCode());
		tableViewCourses.setItems(teacher.getCourses());
	}

	public void buttonCourseUpdateClicked(ActionEvent event) {
		try {
			// Make anchorpane visible and remove error message
			labelCourseErrorMessage.setText(" ");
			anchorPaneUpdateCourse.disableProperty().set(false);
			anchorPaneUpdateCourse.opacityProperty().set(1);

			// pre-fill fields
			textFieldCourseName.setText(selectedCourse.getName());
			textFieldCourseCredits.setText(Double.toString(selectedCourse.getNumberOfCredits()));
			comboBoxCourseUpdateCycleType.setValue(selectedCourse.getCycleType());
			comboBoxCourseResponsibleTeacher.setValue(selectedCourse.getResponsibleTeacherName());
		} catch (NullPointerException exception) {
			anchorPaneUpdateCourse.disableProperty().set(true);
			anchorPaneUpdateCourse.opacityProperty().set(0);
			labelCourseErrorMessage.setText("Please select the course you wish to update.");
		}

	}

	public void buttonCourseUpdateConfirmClicked(ActionEvent event) {
		Course tempCourse = new Course();
		tempCourse = selectedCourse;

		// updating course code if cycle type is changed
		if (tempCourse.getCycleType() != comboBoxCourseUpdateCycleType.getValue()) {
			tempCourse.setCourseCode(comboBoxCourseUpdateCycleType.getValue());
		}
		teacher.updateCourse(tempCourse.getCourseCode(), textFieldCourseName.getText(),
				Double.parseDouble(textFieldCourseCredits.getText()), comboBoxCourseUpdateCycleType.getValue(),
				department.findTeacherByName(comboBoxCourseResponsibleTeacher.getValue()));

		//updating tableview and exiting pop-up
		tableViewCourses.refresh();
		tableViewCourses.setItems(teacher.getCourses());
		anchorPaneUpdateCourse.disableProperty().set(true);
		anchorPaneUpdateCourse.opacityProperty().set(0);

	}

	public void buttonCourseUpdateCancelClicked(ActionEvent event) {
		anchorPaneUpdateCourse.disableProperty().set(true);
		anchorPaneUpdateCourse.opacityProperty().set(0);
	}

	public void buttonCourseAddClicked(ActionEvent event) {
		// hiding error messages
		labelCourseAddNameError.setText(" ");
		labelCourseAddCreditsError.setText(" ");
		labelCourseAddCycleTypeError.setText(" ");
		labelCourseAddResponsibleError.setText(" ");
		labelCourseAddError.setText(" ");

		textFieldCourseAddName.setText("");
		textFieldCourseAddCourseCredits.setText("");
		comboBoxCourseAddCycleType.setValue(null);
		comboBoxCourseAddResponsible.setValue(null);
		
		// making pop-up visible
		anchorPaneAddCourse.disableProperty().set(false);
		anchorPaneAddCourse.opacityProperty().set(1);
	}

	public void buttonCourseAddCancelClicked(ActionEvent event) {
		anchorPaneAddCourse.disableProperty().set(true);
		anchorPaneAddCourse.opacityProperty().set(0);
	}

	public void buttonCourseAddConfirmClicked(ActionEvent event) {
		// hiding error messages
		labelCourseAddNameError.setText(" ");
		labelCourseAddCreditsError.setText(" ");
		labelCourseAddCycleTypeError.setText(" ");
		labelCourseAddResponsibleError.setText(" ");

		// checking credits to make sure no letters are entered
		try {
			if(textFieldCourseAddCourseCredits.getText() != "") {
				int checkCredits = Integer.parseInt(textFieldCourseAddCourseCredits.getText());
			}

			// checking if fields are empty
			if (textFieldCourseAddName.getText().isEmpty() == true
					|| textFieldCourseAddCourseCredits.getText().isEmpty() == true
					|| comboBoxCourseAddCycleType.getValue() == null || comboBoxCourseAddResponsible.getValue() == null)

			{
				labelCourseAddError.setText("Please fill out all the fields");

				if (textFieldCourseAddName.getText().isEmpty() == true) {
					labelCourseAddNameError.setText("*");
				}
				if (textFieldCourseAddCourseCredits.getText().isEmpty() == true) {
					labelCourseAddCreditsError.setText("*");
				}

				if (comboBoxCourseAddCycleType.getValue() == null) {
					labelCourseAddCycleTypeError.setText("*");
				}
				if (comboBoxCourseAddResponsible.getValue() == null) {
					labelCourseAddResponsibleError.setText("*");
				}
			}
			else {
				Course tempCourse = new Course(textFieldCourseAddName.getText(),
						Double.parseDouble(textFieldCourseAddCourseCredits.getText()),
						comboBoxCourseAddCycleType.getValue(), teacher);
				teacher.addCourse(tempCourse);
				tempCourse.setCourseCode(comboBoxCourseAddCycleType.getValue());
				tempCourse.addCourseTeachers(department.findTeacherByName(comboBoxCourseAddResponsible.getValue()));
				tempCourse.setResponsibleTeacher(department.findTeacherByName(comboBoxCourseAddResponsible.getValue()));

				anchorPaneAddCourse.disableProperty().set(true);
				anchorPaneAddCourse.opacityProperty().set(0);
			}
		} catch (Exception exception) {
			labelCourseAddNameError.setText(" ");
			labelCourseAddCreditsError.setText("*");
			labelCourseAddCycleTypeError.setText(" ");
			labelCourseAddResponsibleError.setText(" ");
			labelCourseAddError.setText("Only enter digits");
		}
	}
}
