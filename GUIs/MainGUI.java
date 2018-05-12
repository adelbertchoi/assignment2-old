package GUIs;


import Network.Adult;
import Network.Child;
import Network.Connections;
import Network.Profiles;
import Network.User;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainGUI extends Application {

	// dummy profiles remember to delete this after
	protected static Profiles users = new Profiles();
	protected static Connections links = new Connections();
	protected static User currentUser;
	
	public void createDummy() throws Exception {
		Adult Alice 	 = new Adult("Alice", 21, "Working", "Alice.png", "F", "");
		Adult Bob	 = new Adult("Bob", 22, "Working in RMIT", "Bob.png", "M", "");
		Adult Don 	 = new Adult("Don", 22);
		Adult Cathy 	 = new Adult("Cathy");
		Adult Adel 	 = new Adult("Adel", 24);
		Adult Deb 	 = new Adult("Deb", 19);
		Adult Ben 	 = new Adult("Ben", 19);
		Adult Philip 	 = new Adult("Philip", 19);
		Adult May 	 = new Adult("May", 19);
		Adult Maya 	 = new Adult("Maya", 19);
		Adult Kim 	 = new Adult("Kim", 19);
		Adult Ali 	 = new Adult("Ali", 19);
		Adult Syl 	 = new Adult("Syl", 19);
		Adult Chia	 = new Adult("Chia", 19);
		
		Alice.addFriend(Bob);
		links.addRelationship("Alice", "Bob", "friend");
		
		Alice.addFriend(Don);
		links.addRelationship("Alice", "Don", "friend");
		
		Alice.addFriend(Cathy);
		links.addRelationship("Alice", "Cathy", "colleague");
			
		Child Jane = new Child("Jane", 12, Alice, Bob);
		links.addRelationship("Alice", "Jane", "parent");
		links.addRelationship("Bob", "Jane", "parent");
		links.changeRelationship("Alice", "Bob", "couple");
		
		Child Mike = new Child("Mike", 12, Alice, Bob);
		links.addRelationship("Alice", "Mike", "parent");
		links.addRelationship("Bob", "Mike", "parent");
		
		Child John = new Child("John", 12, Cathy, Don);
		links.addRelationship("Cathy", "John", "parent");
		links.addRelationship("Don", "John", "parent");
		links.changeRelationship("Cathy", "Don", "couple");
			
		users.addUser(Alice);
		users.addUser(Bob);
		users.addUser(Don);
		users.addUser(Cathy);
		users.addUser(Jane);
		users.addUser(Mike);
		users.addUser(John);
		users.addUser(Adel);
		users.addUser(Deb);
		users.addUser(Ben);
		users.addUser(Philip);
		users.addUser(May);
		users.addUser(Maya);
		users.addUser(Kim);
		users.addUser(Ali);
		users.addUser(Syl);
		users.addUser(Chia);
	}
	
	@Override
	public void start (Stage primaryStage) throws Exception {
		
		createDummy();
		// Main Menu UI
		MainMenu mainMenu = new MainMenu(primaryStage);
		mainMenu.displayMainMenu();
	}

	public static void main(String[] args) {
		Application.launch(args);
		
	}
	
}


