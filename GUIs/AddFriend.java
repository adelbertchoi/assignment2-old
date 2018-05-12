package GUIs;

/* @author: Adelbert Choi
 * created: May 5, 2018
 * 
 * AddFriend.java (GUI)
 * 
 */

import java.util.ArrayList;
import java.util.Collections;

import Interfaces.GUIInterface;
import Network.Adult;
import Network.Child;
import Network.User;
import Network.YoungChild;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

class AddFriend implements EventHandler<ActionEvent>, GUIInterface {
	
	private Stage primaryStage;
	
	GridPane addFriendPane 	= myUI.createGridPane();
	Scene addFriendScene 	= new Scene(addFriendPane);	
	
	Text addFriendHeader 	= myUI.createHeader("Add a Friend");
	Label addFriendLabel 	= myUI.createLabel("Choose a friend: ");
	Label addFriendType  	= myUI.createLabel("Connection Type: ");
	Label addFriendNotif 	= new Label("");
	ChoiceBox<String> addFriendChoices = new ChoiceBox<String>();
	ListView<String> friendsList = new ListView<String>();
	
	HBox hbAddFriendBtn 		= new HBox(10);
	Button addFriendBtn 		= myUI.createButton("Add Network");
	Button backBtn 			= myUI.createButton("Back");
	
	ArrayList<String> possibleFriends = new ArrayList<String>();
	
	public AddFriend(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public void getPossibleFriends() {
		for ( String possibleFriend : MainGUI.users.getAllProfiles().keySet() ) {
			// if already friend
			if ( MainGUI.currentUser.getFriends().contains(MainGUI.users.getProfile(possibleFriend)) ) 
				continue;
			// if this user
			else if ( MainGUI.currentUser.getUsername().equals(possibleFriend) ) 
				continue;
			// adults only add adults
			else if ( (MainGUI.currentUser instanceof Adult) && (MainGUI.users.getProfile(possibleFriend) instanceof Adult) ) 
				possibleFriends.add(possibleFriend); 
			// child only add child
			else if ( (MainGUI.currentUser instanceof Child) && (MainGUI.users.getProfile(possibleFriend) instanceof Child) ) 
				possibleFriends.add(possibleFriend);
		}
	}
	
	public void initFriendsListView() {
		// sort friends in alphabetical order
		Collections.sort(possibleFriends);
		
		for(String friend : possibleFriends) {
			friendsList.getItems().add(friend);	
		}
	}
	
	public void initScene() {
		
		// add friend type choices
		addFriendChoices.getItems().add("friend");
		
		// young childs cannot add classmates
		if ( !(MainGUI.currentUser instanceof YoungChild) )
			addFriendChoices.getItems().add("classmate");
		
		// if adult user add additional choice
		if ( (MainGUI.currentUser instanceof Adult) ) 
			addFriendChoices.getItems().add("colleague");
		
		addFriendChoices.setValue("friend");
		addFriendChoices.setPrefSize(150, 20);
		
		getPossibleFriends();		
		initFriendsListView();
					
		hbAddFriendBtn.setAlignment(Pos.BOTTOM_CENTER); // Pos.BOTTOM_RIGHT
		hbAddFriendBtn.getChildren().add(backBtn);
		hbAddFriendBtn.getChildren().add(addFriendBtn);
				
		addFriendPane.add(addFriendHeader, 0, 0, 2, 1);  
		addFriendPane.add(addFriendLabel, 0, 1);
		addFriendPane.add(addFriendNotif, 0, 2, 2, 1);
		addFriendPane.add(friendsList, 0, 3, 2, 1);
		addFriendPane.add(addFriendType, 0, 4);
		addFriendPane.add(addFriendChoices, 1, 4);
		addFriendPane.add(hbAddFriendBtn, 0, 7, 2, 1);
	}
	
	@Override
	public void handle(ActionEvent e) { 		
		
		initScene();
		
		friendsList.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				addFriendNotif.setTextFill(Color.BLACK);
				addFriendNotif.setText("Add " + friendsList.getSelectionModel().getSelectedItem() + " as " +  
									   addFriendChoices.getValue() + "?" );
			}
		});	
		
		// add friend button
		addFriendBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {	
				
				try {
					User friendToAdd = MainGUI.users.getProfile(friendsList.getSelectionModel().getSelectedItem());
					
					MainGUI.currentUser.addFriend(friendToAdd);
					MainGUI.links.addRelationship(MainGUI.currentUser.getUsername(), friendToAdd.getUsername(), addFriendChoices.getValue());
					addFriendNotif.setText("");
					
					friendsList.getItems().remove(friendsList.getSelectionModel().getSelectedItem());
				} catch (NullPointerException np) {
					addFriendNotif.setTextFill(Color.FIREBRICK);
					addFriendNotif.setText("Select a friend first");
				}
			}
		});
		
		// back to user profile
		backBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {					
				ShowUser showUser = new ShowUser(primaryStage);
				showUser.displayUser();
			}
		});
	
		primaryStage.setTitle("Add a friend to user");
		primaryStage.setScene(addFriendScene);
		primaryStage.show();
	}
}