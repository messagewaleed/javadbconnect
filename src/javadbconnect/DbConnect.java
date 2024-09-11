package javadbconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DbConnect {
	
	record User(String name, String address) {};
	
	Connection dbCon;
	
	Statement statement;

	public static void main(String[] args) {

		try {
//			Load the driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
//			Try establishing the connection
			DbConnect ref = new DbConnect();
			ref.dbCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/learners", "root", "");
			
//			Get a reference to the statement object
			ref.statement = ref.dbCon.createStatement();
			
			Scanner scan = new Scanner(System.in);
			System.out.println("Enter name, address of the new User");
			
//			ref.getAllUsers();
			ref.addNewUser(new User(scan.nextLine(), scan.nextLine()));
			
//			System.out.println("Connected to db now...");
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("issues while loading the driver" + e.getMessage());
		} catch (SQLException e) {
			System.out.println("Issues while connecting : " + e.getMessage());
		}

	}
	
//	get all users
	void getAllUsers() {
		
//		Write the query
		String query = "select * from user";

		try {
			
			
//			Execute the query
			ResultSet resultSet = statement.executeQuery(query);
			
//			Traverse through the results
			System.out.println("Users registered:");
			while(resultSet.next()) {
				System.out.println(resultSet.getString("userName")
						+ " with Id : " + resultSet.getInt("userId")
						+ " from : " + resultSet.getString("userAddress")
						);
			}
			
			dbCon.close();
			
			
		} catch (SQLException e) {
			System.out.println("Issue while getting statement : " + e.getMessage());
		}
		
		
	}
	
	
//	Add a new User
	void addNewUser(User user) {
		
		
		try {
			String query = "insert into user(userName, userAddress) values('" + user.name() +"', '" + user.address() + "')";
			if(statement.executeUpdate(query) > 0)
				System.out.println("User Registered...");
			
			
			
			
		} catch (SQLException e) {
			System.out.println("Issues while adding user : " + e.getMessage());
		}
		
		
	}
	
	
	
	
	
	
	

}
