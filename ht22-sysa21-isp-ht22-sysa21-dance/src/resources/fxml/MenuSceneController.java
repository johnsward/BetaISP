package resources.fxml;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuSceneController {

	@FXML
	private Stage stage;
	@FXML
	private Scene scene;
	@FXML
	private Parent root;
	
	public void menuButtonTeachersClicked(ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/TeacherScene.fxml"));
		stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void menuButtonCoursesClicked(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/CourseScene.fxml"));
		stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void menuButtonDepartmentsClicked(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/DepartmentScene.fxml"));
		stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
}
