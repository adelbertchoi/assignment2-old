package GUIs;

import Interfaces.GUIInterface;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import javafx.geometry.Pos;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class EditUser implements EventHandler<ActionEvent>, GUIInterface {
	
	GridPane editUserPane 	= myUI.createGridPane();
	Scene editUserScene 		= new Scene(editUserPane);
	Stage primaryStage;
	
	Text warnings 			= myUI.warningText("");
	Text editUserHeader 		= myUI.createHeader("");
	Label ageText 			= myUI.createLabel("Age: ");
	Label statusText 		= myUI.createLabel("Enter Status: ");
	Label imageText 			= myUI.createLabel("Add Image: ");
	Label genderText 		= myUI.createLabel("Add Gender: ");
	Label stateText 			= myUI.createLabel("Add State: ");
	// TextField ageField 		= myUI.createTextField("");
	TextField statusField 	= myUI.createTextField("");
	TextField imageField 	= myUI.createTextField("");
	
	ArrayList<String> genderChoices = new ArrayList<String>(Arrays.asList("M", "F"));
	ArrayList<String> stateChoices = new ArrayList<String>(Arrays.asList("NSW", "NT", "QLD", "SA", "TAS", "VIC", "WA"));
	ChoiceBox<String> genderField = myUI.createChoiceBox(genderChoices , true);
	ChoiceBox<String> stateField  = myUI.createChoiceBox(stateChoices, true);
	
	HBox hbEditUserBtn = new HBox(10);
	HBox hbWarnings = new HBox();	
	Button editUserBtn = myUI.createButton("Save");
	Button backBtn = myUI.createButton("Back");
	
	public EditUser(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public void initScene() {
		
		editUserHeader.setText("Edit " + MainGUI.currentUser.getUsername() + "'s User Profile");
		
		// ageField.setText(Integer.toString(MainGUI.currentUser.getAge()));
		statusField.setText(MainGUI.currentUser.getStatus());
		imageField.setText(MainGUI.currentUser.getImage());
		genderField.setValue(MainGUI.currentUser.getGender());
		stateField.setValue(MainGUI.currentUser.getState());
		
		hbEditUserBtn.setAlignment(Pos.BOTTOM_CENTER); // Pos.BOTTOM_RIGHT
		hbEditUserBtn.getChildren().add(backBtn);
		hbEditUserBtn.getChildren().add(editUserBtn);

		hbWarnings.getChildren().add(warnings);
		hbWarnings.setPrefHeight(60);	
		
		editUserPane.add(editUserHeader, 0, 0, 2, 1);
		editUserPane.add(ageText, 0, 1); editUserPane.add(new Label(Integer.toString(MainGUI.currentUser.getAge())), 1, 1);
		// editUserPane.add(ageText, 0, 1); 	editUserPane.add(ageField, 1, 1);
		editUserPane.add(statusText, 0, 2); 	editUserPane.add(statusField, 1, 2);
		editUserPane.add(imageText, 0, 3); 	editUserPane.add(imageField, 1, 3);
		editUserPane.add(genderText, 0, 4); 	editUserPane.add(genderField, 1, 4);
		editUserPane.add(stateText, 0, 5); 	editUserPane.add(stateField, 1, 5);		
		editUserPane.add(hbWarnings, 0, 6, 2, 1);
		editUserPane.add(hbEditUserBtn, 0, 9, 2, 1);
	}
	
	public void handle(ActionEvent e) {

		initScene();

		// creating a new user
		editUserBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				
				// find a better way to implement these
				MainGUI.currentUser.setStatus(statusField.getText()); 
				MainGUI.currentUser.setImage(imageField.getText());
				MainGUI.currentUser.setGender(genderField.getValue());
				MainGUI.currentUser.setState(stateField.getValue());
				
				Alert editAlert = new Alert(AlertType.INFORMATION); 
				editAlert.setTitle("Changes Made");
		 
				editAlert.setHeaderText(null);
				editAlert.setContentText("All changes made saved");
				editAlert.showAndWait();
				
				ShowUser showUser = new ShowUser(primaryStage);
				showUser.displayUser();
			}
		});

		// back to the main menu
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				ShowUser showUser = new ShowUser(primaryStage);
				showUser.displayUser();
			}
		});

		primaryStage.setScene(editUserScene);
		primaryStage.setTitle("Edit User");
		primaryStage.show();
	}
	
}

