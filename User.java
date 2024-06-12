package Banking_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
	
private Connection 	connection;

private Scanner scanner;



  public User(Connection conncetion,Scanner scanner)
{
	
	this.connection=conncetion;
	this.scanner=scanner;
	
}



	public void register() throws SQLException
	{
		System.out.println("ENter USerName:");
	    String Name=	scanner.next();
		System.out.println("Enter USerEmail:");
		String Email=scanner.next();
		System.out.println("ENter SecurityPin:");
		String Security_Pin=scanner.next();
		
		
		String query="insert into user(UserName,UserEmail,password) values(?,?,?)";
		
		PreparedStatement ps=connection.prepareStatement(query);
		
		ps.setString(1, Name);
		ps.setString(2, Email);
		ps.setString(3,Security_Pin);
		int affectedRows=ps.executeUpdate();
		if(affectedRows>0)
		{
			System.out.println("Registration Succesfull..!!");
		}
		else
		{
			System.out.println("Registration Failed..!");
		}
		
	}
	
	public String login() throws SQLException
	{
		System.out.println("ENter Email: ");
		String email=scanner.next();
		System.out.println("ENter Password: ");
		String pass=scanner.next();
		if(userExists(email))
		{
			System.out.println("User already Exists..!!");
		}
		
		String query="select * from user where useremail=? and password=?";
		PreparedStatement ps=connection.prepareStatement(query);
		ps.setString(1, email);
		ps.setString(2, pass);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
		{
			return email;
		}
		else
		{
			return null;
		}
		
	}
	
	
	
	public boolean userExists(String email) throws SQLException
	{  String query="select * from user where useremail=?";
	PreparedStatement ps=connection.prepareStatement(query);
		
	ps.setString(1, email);
      ResultSet rs=ps.executeQuery();
      if(rs.next())
      {
    	  return true;
      }
      else
      {
    	  return false;
      }
      
    }
	

}
