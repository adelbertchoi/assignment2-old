package FileHandling;

import java.io.*;
import java.util.StringTokenizer;

import Network.Adult;
import Network.User;
import Network.Child;
import Network.YoungChild;
import Network.Connections;
import Network.Profiles;

public class FileReader {
	
	private static Profiles users = new Profiles();
	private static Connections links = new Connections();
	
//	FileReader() {
//	}	
	
	public static void initUsers() {
		// "NSW", "NT", "QLD", "SA", "TAS", "VIC", "WA"
		// username, image, status, gender, age, state
		User Alex = new Adult("Alex Smith", "", "student at RMIT", "M", 21, "WA");
		User Ben = new Adult("Ben Turner", "BenPhoto.jpg", "manager at Coles", "M", 35, "VIC");
		User Han = new Child("Hannah White", "Hannah.png", "student at PLC", "F", 14, "VIC");
		User Zoe = new Adult("Zoe Foster", "", "Founder of ZFX", "F", 28, "VIC");	 
		User Mark = new YoungChild("Mark Turner", "Mark.jpeg", "", "M", 2, "VIC");
		
		users.addUser(Alex);
		users.addUser(Ben);
		users.addUser(Han);
		users.addUser(Zoe);
		users.addUser(Mark);
		
		links.addRelationship(Alex.getUsername(), Ben.getUsername(), "friends");
		links.addRelationship(Ben.getUsername(), Zoe.getUsername(), "couple");
	}
	
	public static void initialiseUser(String line) {
		StringTokenizer tokens = new StringTokenizer(line, ",");

		// assuming the format is as is in the spec sheet
		String username = tokens.nextToken();
		String image = tokens.nextToken().replaceAll("^\"|\"$", "");
		String status = tokens.nextToken().replaceAll("^\"|\"$", "");
		String gender = tokens.nextToken();
		int age = Integer.parseInt(tokens.nextToken());
		String state = tokens.nextToken();
		
		User newUser;
		
		if (age > 16) 
			newUser = new Adult(username, image, status, gender, age, state);
		else if (age > 2)
			newUser = new Child(username, image, status, gender, age, state);
		else
			newUser = new YoungChild(username, image, status, gender, age, state);
		
		users.addUser(newUser);		
	}
	
	public static void readPersonTextFile() {		

		File file = new File("People.txt");
		BufferedReader input = null;
		
		try {
			java.io.FileReader fr = new java.io.FileReader(file);
			input = new BufferedReader(fr);
			
			String next = input.readLine();
			while (next != null) {
				System.out.println(next);
				initialiseUser(next);
				
				next = input.readLine();
			}
			input.close();
			
		} catch (IOException e) {
			System.err.println("File Reading Error!");
			System.exit(0);
		}
	}
	
	public static void writePersonTextFile() {
		FileWriter writer = null;
		
		User user;
		String line;
		
		try {
			writer = new FileWriter("People.txt");

			for (String key : users.getAllProfiles().keySet()) {
				user = users.getAllProfiles().get(key);
				line = user.getUsername() + "," + 
						"\"" + user.getImage() + "\"" + "," +
						"\"" + user.getStatus() + "\"" + "," +
						user.getGender() + "," +
						Integer.toString(user.getAge()) + "," +
						user.getState(); 
				System.out.println(line);
				writer.write(line + "\n"); 
			}
			
			writer.close();

		} catch (IOException e) {
			System.err.println("File cannot be created, or cannot be opened");
			System.exit(0);
		}
	}
	
	public static void run() {
		for (String user : users.getAllProfiles().keySet() ) {
			System.out.println(user);
		}
	}

	
	public static void main(String[] args) {
		
//		initUsers();
//		run();
//		writePersonTextFile();
//		System.out.println("Done");
		
		// this is done
		readPersonTextFile();
		System.out.println("Done");
		
	}
	
}
