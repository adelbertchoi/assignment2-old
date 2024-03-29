package GUIs;

/* @author: Adelbert Choi
 * created: May 5, 2018
 * 
 * ShowUser.java (GUI)
 * 
 */

import java.util.ArrayList;

import Exceptions.NoParentException;
import Interfaces.GUIInterface;
import Network.Adult;
import Network.Child;
import Network.Relationship;
import Network.YoungChild;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ShowUser implements GUIInterface {
	
	Stage primaryStage;
	
	GridPane showUserPane = myUI.createGridPane();
	Scene showUserScene = new Scene(showUserPane);
	
	Text showUserHeader		= myUI.createHeader("Show A User");
	Label ageLabel 			= myUI.createLabel("", DETAILS_LABEL_WIDTH);
	Label statusLabel 		= myUI.createLabel("", DETAILS_LABEL_WIDTH);
	Label imageLabel 		= myUI.createLabel("", DETAILS_LABEL_WIDTH);
	Label genderLabel 		= myUI.createLabel("", DETAILS_LABEL_WIDTH);
	Label stateLabel 		= myUI.createLabel("", DETAILS_LABEL_WIDTH);
	
	Label spouseLabel 		= myUI.createLabel("", DETAILS_LABEL_WIDTH);
	Label parentOneLabel 	= myUI.createLabel("", DETAILS_LABEL_WIDTH);
	Label parentTwoLabel 	= myUI.createLabel("", DETAILS_LABEL_WIDTH);
	
	HBox hbUserConnections	= new HBox(10);
	HBox hbShowUserBtn 		= new HBox(10);
	HBox hbFriendsBtn 		= new HBox(10);
	
	Button backBtn 			= myUI.createButton("Main Menu");
	Button editUserBtn 		= myUI.createButton("Edit Profile");
	Button deleteUserBtn 	= myUI.createButton("Delete Profile");
	Button addFriendBtn 		= myUI.createButton("Add Network");	
	Button findFriendBtn	 	= myUI.createButton("Find Network");
	Button deleteFriendBtn 	= myUI.createButton("Delete Network");
	
	ArrayList<Relationship> userLinks;
	ArrayList<String> friends = new ArrayList<String>();
	ArrayList<String> classmates = new ArrayList<String>();
	ArrayList<String> colleagues = new ArrayList<String>();
	ArrayList<String> dependents = new ArrayList<String>();
	
	Alert deleteUserAlert;
		
	public ShowUser(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void getAllLinks() {
		userLinks = MainGUI.links.getUserRelationships(MainGUI.currentUser.getUsername());
	}
	
	public void getFriends() {
		for ( Relationship link : userLinks ) {
			if ( link.getType().equals("friend") )
				friends.add(link.getUsernameTwo());
		}
	}
	
	public void getClassmates() {
		for ( Relationship link : userLinks ) {
			if ( link.getType().equals("classmate") )
				classmates.add(link.getUsernameTwo());
		}
	}
	
	public void getDependents() {
		for ( Relationship link : userLinks ) {
			if ( link.getType().equals("parent") )
				dependents.add(link.getUsernameTwo());
		}
	}
	
	public void getColleagues() {
		for ( Relationship link : userLinks ) {
			if ( link.getType().equals("colleague") )
				colleagues.add(link.getUsernameTwo());
		}
	}
	
	public void initScene() {
		
		if ( MainGUI.currentUser instanceof Adult )
			showUserHeader.setText(MainGUI.currentUser.getUsername() + " (Adult)");
		else if ( MainGUI.currentUser instanceof Child )
			showUserHeader.setText(MainGUI.currentUser.getUsername() + " (Child)");
		else if ( MainGUI.currentUser instanceof YoungChild ) 
			showUserHeader.setText(MainGUI.currentUser.getUsername() + "(Young Child)");
			
		
		if ( MainGUI.currentUser.getAge() == 0)
			ageLabel.setText("Age : ");
		else 
			ageLabel.setText("Age : " + Integer.toString(MainGUI.currentUser.getAge())); 
		
		statusLabel.setText("Status : " + MainGUI.currentUser.getStatus());
		imageLabel.setText("Image : " + MainGUI.currentUser.getImage());
		genderLabel.setText("Gender : " + MainGUI.currentUser.getGender());
		stateLabel.setText("State : " + MainGUI.currentUser.getState());
		
		if ( MainGUI.currentUser instanceof Adult ) {
			if ( ((Adult) MainGUI.currentUser).getPartner() != null )
				spouseLabel.setText("Spouse : " + ((Adult) MainGUI.currentUser).getPartner().getUsername());
			else 
				spouseLabel.setText("Spouse : ");
			
			showUserPane.add(spouseLabel, 1, 1);
		} else if ( MainGUI.currentUser instanceof Child ) {
			parentOneLabel.setText("Parent 1 : " + ((Child) MainGUI.currentUser).getParentOne().getUsername());
			parentTwoLabel.setText("Parent 2 : " + ((Child) MainGUI.currentUser).getParentTwo().getUsername());
			
			showUserPane.add(parentOneLabel, 1, 1);
			showUserPane.add(parentTwoLabel, 1, 2);
		}
		
		hbFriendsBtn.setAlignment(Pos.BOTTOM_CENTER);
		hbFriendsBtn.getChildren().add(deleteFriendBtn);
		hbFriendsBtn.getChildren().add(findFriendBtn);
		hbFriendsBtn.getChildren().add(addFriendBtn);			
		
		hbShowUserBtn.setAlignment(Pos.BOTTOM_CENTER); 
		hbShowUserBtn.getChildren().add(backBtn);
		hbShowUserBtn.getChildren().add(editUserBtn);
		hbShowUserBtn.getChildren().add(deleteUserBtn);
				
		showUserPane.add(showUserHeader, 0, 0, 2, 1);  
		showUserPane.add(ageLabel, 0, 1);	
		showUserPane.add(statusLabel, 0, 2);	
		showUserPane.add(imageLabel, 0, 3);
		showUserPane.add(genderLabel, 0, 4);
		showUserPane.add(stateLabel, 0, 5);
		showUserPane.add(hbUserConnections, 0, 7, 2, 1);
		showUserPane.add(hbFriendsBtn, 0, 10, 2, 1);
		showUserPane.add(hbShowUserBtn, 0, 11, 2, 1);		
	}
	
	public void getConnectionsSummary() {
		VBox vbFriends	  	= myUI.createConnectionBox(friends, "Friends");
		VBox vbClassmates 	= myUI.createConnectionBox(classmates, "Classmates");
		VBox vbColleagues 	= myUI.createConnectionBox(colleagues, "Colleagues");
		VBox vbDependents 	= myUI.createConnectionBox(dependents, "Dependents");
		
		hbUserConnections.setAlignment(Pos.BOTTOM_CENTER); // Pos.BOTTOM_RIGHT
		boolean hasConnections = false; 
		
		if ( !dependents.isEmpty() && (MainGUI.currentUser instanceof Adult) ) {
			hbUserConnections.getChildren().add(vbDependents);
			hasConnections = true;
		}
		
		if ( !friends.isEmpty() ) {
			hbUserConnections.getChildren().add(vbFriends);
			hasConnections = true;
		}
		
		if ( !classmates.isEmpty() ) {
			hbUserConnections.getChildren().add(vbClassmates);
			hasConnections = true;
		}
		
		if ( !colleagues.isEmpty() ) {
			hbUserConnections.getChildren().add(vbColleagues);
			hasConnections = true;
		}
		
		// user does not have any connections any kind
		if ( !hasConnections )  {
			hbUserConnections.getChildren().add(new Label("User has no Connections. Select add network to get started"));
		}
			
	}

	// Show a Information Alert without Header Text
	public void deleteProfileAlert() {	
		deleteUserAlert = new Alert(AlertType.CONFIRMATION); 
		deleteUserAlert.setTitle("Delete user profile");
 
		deleteUserAlert.setHeaderText(null);
		deleteUserAlert.setContentText("Are you sure you want to delete " + MainGUI.currentUser.getUsername() + "?");
		deleteUserAlert.showAndWait();
		
		// add try catch to get errors when delete a user
	}
	
	public void displayUser() {
		
		initScene();
		getAllLinks();
		getFriends();
		getClassmates();
		getColleagues();
		getDependents();
		getConnectionsSummary();
		
		// back to the main menu
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				MainMenu mainMenu = new MainMenu(primaryStage);
				mainMenu.displayMainMenu();
			}
		});
		
		// edit user profile;
		editUserBtn.setOnAction(new EditUser(primaryStage));

		// add a friend to user
		addFriendBtn.setOnAction(new AddFriend(primaryStage));
		
		// delete a friend to user
		deleteFriendBtn.setOnAction(new DeleteFriend(primaryStage));
		
		deleteUserBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				deleteProfileAlert();
				
				if ( deleteUserAlert.getResult().getText().equals("OK") ) {
					try {
						// procedures to delete user
						MainGUI.users.deleteUser(MainGUI.currentUser.getUsername());
						MainGUI.links.deleteAllUserRelationships(MainGUI.currentUser.getUsername());
						
						MainMenu mainMenu = new MainMenu(primaryStage);
						mainMenu.displayMainMenu();
					} catch (NoParentException np) {
						this.noParentAlert(np.getMessage());
					} 	
				}
			}

			public void noParentAlert(String message) {	
				deleteUserAlert = new Alert(AlertType.INFORMATION); 
				deleteUserAlert.setTitle("Action not allowed");
		 
				deleteUserAlert.setHeaderText(null);
				deleteUserAlert.setContentText(message);
				deleteUserAlert.showAndWait();
			}
			
		});
		
		primaryStage.setTitle("Showing user profile");
		primaryStage.setScene(showUserScene);		
		primaryStage.show();
	}
}
