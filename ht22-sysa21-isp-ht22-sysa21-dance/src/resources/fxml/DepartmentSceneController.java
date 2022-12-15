package resources.fxml;

import java.io.IOException;
import java.util.ResourceBundle;
import javax.print.DocFlavor.URL;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import se.lu.ics.model.Course;
import se.lu.ics.model.Department;
import se.lu.ics.model.DepartmentRegister;
import se.lu.ics.model.Teacher;
import se.lu.ics.model.Teaching;

public class DepartmentSceneController {

	@FXML
	private Stage stage;
	@FXML
	private Scene scene;
	@FXML
	private Parent root;

	// main scene (tableview, create, remove etc)
	@FXML
	private TableView<Department> tableViewDepartment;
	@FXML
	private TableColumn<Department, String> tableColumnDepartmentName;
	@FXML
	private TableColumn<Department, String> tableColumnDepartmentAddress;
	@FXML
	private TableColumn<Department, Double> tableColumnDepartmentBudget;
	@FXML
	private TableColumn<Department, String> tableColumnDepartmentHeadOfDepartment;
	@FXML
	private TextField textFieldDepartmentFilter;
	@FXML
	private Label labelDepartmentErrorMessage;
	@FXML
	private Label labelDepartmentSuccessMessage;

	// create anchorpane
	@FXML
	private AnchorPane anchorPaneDepartmentCreate;
	@FXML
	private TextField textFieldDepartmentCreateName;
	@FXML
	private TextField textFieldDepartmentCreateAddress;
	@FXML
	private TextField textFieldDepartmentCreateBudget;
	@FXML
	private ComboBox<String> comboBoxDepartmentCreateHeadOfDepartment;
	@FXML
	private Label labelDepartmentCreateNameError;
	@FXML
	private Label labelDepartmentCreateAddressError;
	@FXML
	private Label labelDepartmentCreateBudgetError;
	@FXML
	private Label labelDepartmentCreateHeadOfDepartmentError;
	@FXML
	private Label labelDepartmentCreateErrorMessage;
	@FXML
	private Label labelDepartmentCreateSuccessMessage;

	// update department
	@FXML
	private AnchorPane anchorPaneDepartmentUpdate;
	@FXML
	private TextField textFieldDepartmentUpdateName;
	@FXML
	private TextField textFieldDepartmentUpdateAddress;
	@FXML
	private TextField textFieldDepartmentUpdateBudget;
	@FXML
	private ComboBox<String> comboBoxDepartmentUpdateHeadOfDepartment;
	@FXML
	private AnchorPane anchorPaneDepartmentTotalCost;
	@FXML
	private ComboBox<String> comboBoxDepartmentsTotalCost;
	@FXML
	private Text totalCostHead;
	@FXML
	private Label labelDepartmentInTotalCost;
	@FXML
	private TableView<Department> tableViewTotalCostDepartments;
	@FXML
	private TableColumn<Department, String> tableColumnDepartmentInTotalCost;
	@FXML
	private TableColumn<Department, Double> tableColumnCostInTotalCost;
	@FXML
	private TableColumn<Department, Double> tableColumnTotalInTotalCostTableView;
	@FXML
	private Label totalCostDepartments;
	@FXML
	private Button buttonTotalCostConfirm;
	@FXML
	private Button buttonTotalCostCancel;
	@FXML
	private Button buttonTotalCostOk;
	@FXML
	private AnchorPane anchorPaneButtonTotalCostCancelMessage; 
	@FXML 
	private Button totalCostCancelNo; 
	@FXML 
	private Button totalCostCancelYes; 
	@FXML 
	private Label areYouSure; 

	Department department = new Department();
	DepartmentRegister departmentRegister = new DepartmentRegister();
	private Department selectedDepartment;
	Teacher teacher = new Teacher();
	Course course = new Course();

	public void initialize() {
		// hide pop-up
		anchorPaneDepartmentCreate.disableProperty().set(true);
		anchorPaneDepartmentCreate.setVisible(false);
		anchorPaneDepartmentUpdate.disableProperty().set(true);
		anchorPaneDepartmentUpdate.setVisible(false);
		anchorPaneDepartmentTotalCost.disableProperty().set(true);
		anchorPaneDepartmentTotalCost.setVisible(false);

		// assign values to tableview
		tableColumnDepartmentName.setCellValueFactory(new PropertyValueFactory<Department, String>("name"));
		tableColumnDepartmentAddress.setCellValueFactory(new PropertyValueFactory<Department, String>("address"));
		tableColumnDepartmentBudget.setCellValueFactory(new PropertyValueFactory<Department, Double>("budget"));
		tableColumnDepartmentHeadOfDepartment
				.setCellValueFactory(new PropertyValueFactory<Department, String>("headOfDepartmentName"));
		initializeTableView();

		// assign value to head of department combobox
		for (int i = 0; i < department.getTeacherData().size(); i++) {
			comboBoxDepartmentCreateHeadOfDepartment.getItems().add(department.getTeacherData().get(i).getName());
		}

		// track mouse-click
		tableViewDepartment.setOnMouseClicked(event -> {
			selectedDepartment = tableViewDepartment.getSelectionModel().getSelectedItem();
		});
	}

	public void initializeTableView() {
		tableViewDepartment.setItems(departmentRegister.getDepartments());
	}

	public void buttonHomeClicked(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/MenuScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void buttonDepartmentCreateClicked(ActionEvent event) {
		// hide error messages
		labelDepartmentCreateNameError.setText(" ");
		labelDepartmentCreateAddressError.setText(" ");
		labelDepartmentCreateBudgetError.setText(" ");
		labelDepartmentCreateErrorMessage.setText(" ");
		labelDepartmentCreateSuccessMessage.setText(" ");

		// make fields empty
		textFieldDepartmentCreateName.setText("");
		textFieldDepartmentCreateAddress.setText("");
		textFieldDepartmentCreateBudget.setText("");
		comboBoxDepartmentCreateHeadOfDepartment.setValue(null);
		;

		// show pop-up
		anchorPaneDepartmentCreate.disableProperty().set(false);
		anchorPaneDepartmentCreate.setVisible(true);
	}

	public void buttonDepartmentCreateConfirmClicked(ActionEvent event) {
		// hide error messages
		labelDepartmentCreateNameError.setText(" ");
		labelDepartmentCreateAddressError.setText(" ");
		labelDepartmentCreateBudgetError.setText(" ");
		labelDepartmentCreateHeadOfDepartmentError.setText(" ");
		labelDepartmentCreateErrorMessage.setText(" ");
		labelDepartmentCreateSuccessMessage.setText(" ");

		// make sure salary doesn't have letters
		try {
			if (textFieldDepartmentCreateBudget.getText() != "") {
				double checkBudget = Double.parseDouble(textFieldDepartmentCreateBudget.getText());
			}
			// Taken from:
			// https://stackoverflow.com/questions/36150181/how-to-differentiate-numbers-and-strings
			// string array to find any numbers in name field
			String[] numbers = textFieldDepartmentCreateName.getText().split("\\D+");
			int sum = 0;
			for (String number : numbers) {
				try {
					sum += Integer.parseInt(number);
				} catch (Exception exception) {
				}
			}

			// check if fields are empty
			if (textFieldDepartmentCreateName.getText().isEmpty() == true
					&& textFieldDepartmentCreateAddress.getText().isEmpty() == true
					&& textFieldDepartmentCreateBudget.getText().isEmpty() == true
					&& comboBoxDepartmentCreateHeadOfDepartment.getValue() == null) {

				if (textFieldDepartmentCreateName.getText().isEmpty() == true) {
					labelDepartmentCreateNameError.setText("*");
				}
				if (textFieldDepartmentCreateAddress.getText().isEmpty() == true) {
					labelDepartmentCreateAddressError.setText("*");
				}
				if (textFieldDepartmentCreateBudget.getText().isEmpty() == true) {
					labelDepartmentCreateBudgetError.setText("*");
				}
				if (comboBoxDepartmentCreateHeadOfDepartment.getValue() == null) {
					labelDepartmentCreateHeadOfDepartmentError.setText("*");
				}

				labelDepartmentCreateErrorMessage.setText("Please fill out all the information fields");
				labelDepartmentCreateSuccessMessage.setText("");

			}
			// make sure name doesn't have numbers
			if (sum > 0) {
				labelDepartmentCreateNameError.setText("*");
				labelDepartmentCreateErrorMessage.setText("Only enter letters.");
			} else if (textFieldDepartmentCreateName.getText().isEmpty() == true
					|| textFieldDepartmentCreateAddress.getText().isEmpty() == true
					|| textFieldDepartmentCreateBudget.getText().isEmpty() == true
					|| comboBoxDepartmentCreateHeadOfDepartment.getValue() == null) {

				if (textFieldDepartmentCreateName.getText().isEmpty() == true) {
					labelDepartmentCreateNameError.setText("*");
				}
				if (textFieldDepartmentCreateAddress.getText().isEmpty() == true) {
					labelDepartmentCreateAddressError.setText("*");
				}
				if (textFieldDepartmentCreateBudget.getText().isEmpty() == true) {
					labelDepartmentCreateBudgetError.setText("*");
				}
				if (comboBoxDepartmentCreateHeadOfDepartment.getValue() == null) {
					labelDepartmentCreateHeadOfDepartmentError.setText("*");
				}

				labelDepartmentCreateErrorMessage.setText("Please fill out all the information fields");
				labelDepartmentCreateSuccessMessage.setText("");
			}

			else {
				Department tempDepartment = new Department();
				tempDepartment.setName(textFieldDepartmentCreateName.getText());
				tempDepartment.setAddress(textFieldDepartmentCreateAddress.getText());
				tempDepartment.setBudget(Double.parseDouble(textFieldDepartmentCreateBudget.getText()));
				tempDepartment.setHeadOfDepartment(
						department.findTeacherByName(comboBoxDepartmentCreateHeadOfDepartment.getValue()));
				departmentRegister.addDepartment(tempDepartment);

				// update error messages
				labelDepartmentErrorMessage.setText(" ");
				labelDepartmentSuccessMessage.setText("Department was created successfully");

				// hide pop-up
				anchorPaneDepartmentCreate.setVisible(false);
				anchorPaneDepartmentCreate.disableProperty().set(true);
			}
		} catch (NumberFormatException exception) {
			labelDepartmentCreateNameError.setText(" ");
			labelDepartmentCreateAddressError.setText(" ");
			labelDepartmentCreateBudgetError.setText("*");
			labelDepartmentCreateHeadOfDepartmentError.setText(" ");
			labelDepartmentCreateErrorMessage.setText("Only enter digits");
			labelDepartmentCreateSuccessMessage.setText("");
		} catch (NullPointerException exception) {
			// department not selected
			if (textFieldDepartmentCreateName.getText().isEmpty() == true
					|| textFieldDepartmentCreateAddress.getText().isEmpty() == true
					|| textFieldDepartmentCreateBudget.getText().isEmpty() == true
					|| comboBoxDepartmentCreateHeadOfDepartment.getValue().equals(null)) {
				if (textFieldDepartmentCreateName.getText().isEmpty() == true) {
					labelDepartmentCreateNameError.setText("*");
				}
				if (textFieldDepartmentCreateAddress.getText().isEmpty() == true) {
					labelDepartmentCreateAddressError.setText("*");
				}
				if (textFieldDepartmentCreateBudget.getText().isEmpty() == true) {
					labelDepartmentCreateBudgetError.setText("*");
				}
				if (comboBoxDepartmentCreateHeadOfDepartment.getValue().equals(null)) {
					labelDepartmentCreateHeadOfDepartmentError.setText("*");
				}
			}

			labelDepartmentCreateErrorMessage.setText("Please fill out all the fields");
			labelDepartmentCreateSuccessMessage.setText("");
		}
	}

	public void buttonDepartmentCreateCancelClicked(ActionEvent event) {
		// hide pop-up
		anchorPaneDepartmentCreate.setVisible(false);
		anchorPaneDepartmentCreate.disableProperty().set(true);
	}

	public void buttonDepartmentUpdateClicked(ActionEvent event) {
		anchorPaneDepartmentUpdate.disableProperty().set(false);
		anchorPaneDepartmentUpdate.setVisible(true);
	}

	public void buttonDepartmentRemoveClicked(ActionEvent event) {

		Department tempDepartment = new Department();
		tempDepartment = selectedDepartment;

		tableViewDepartment.setOnMouseClicked(ActionEvent -> {
			selectedDepartment = tableViewDepartment.getSelectionModel().getSelectedItem();
		});
		departmentRegister.deleteDepartment(tempDepartment.getName());
		tableViewDepartment.setItems(departmentRegister.getDepartments());
		tableViewDepartment.refresh();
	}

	public void buttonDepartmentTotalCostClicked(ActionEvent event) {

		anchorPaneDepartmentTotalCost.disableProperty().set(false);
		anchorPaneDepartmentTotalCost.setVisible(true);
		anchorPaneButtonTotalCostCancelMessage.setVisible(false); 
		anchorPaneButtonTotalCostCancelMessage.disableProperty().set(true);
		for (int i = 0; i < departmentRegister.getDepartments().size(); i++) {
			comboBoxDepartmentsTotalCost.getItems().add(departmentRegister.getDepartments().get(i).getName());

		}

	}

	public void comboBoxTotalCostUsed(ActionEvent event) {
		for (Department department : departmentRegister.getDepartments()) {
			if (departmentRegister.getDepartments().contains(department)) {
				comboBoxDepartmentsTotalCost.getValue();
				tableColumnDepartmentInTotalCost
						.setCellValueFactory(new PropertyValueFactory<Department, String>("name"));
				tableColumnCostInTotalCost
						.setCellValueFactory(new PropertyValueFactory<Department, Double>("calculateDepartmentCost"));
				tableViewTotalCostDepartments.setItems(departmentRegister.getDepartments());
				tableViewTotalCostDepartments.refresh();

			}

		}

	}
	
	public void buttonDepartmentTotalCostCancelClicked(ActionEvent event) {
		
		anchorPaneButtonTotalCostCancelMessage.setVisible(true); 
		anchorPaneButtonTotalCostCancelMessage.disableProperty().set(false);
		
	}
	
	public void buttonDepartmentTotalCostOkClicked(ActionEvent event) {
		
			anchorPaneDepartmentTotalCost.disableProperty().set(true);
			anchorPaneDepartmentTotalCost.setVisible(false);
	}
	
	public void buttonDepartmenTotalCostOkClicked(ActionEvent event) {
		
		anchorPaneDepartmentTotalCost.disableProperty().set(true);
		anchorPaneDepartmentTotalCost.setVisible(false);
		
	}
	
	
	
	
	public void buttonDepartmentShowAllClicked(ActionEvent event) {

	}
}
