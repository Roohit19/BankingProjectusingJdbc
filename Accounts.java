package Banking_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Accounts {
	
	
	private Connection 	connection;

	private Scanner scanner;



	  public Accounts(Connection conncetion,Scanner scanner)
	{
		
		this.connection=conncetion;
		this.scanner=scanner;
		
	}
	
	  public  long openAccount(String email) throws SQLException
	  { 
		  
		  if(!account_Exists(email))
	{
		  
		  System.out.println("ENter Account_Holder NAme: ");
		  
		  String name=scanner.next();
		  
		  System.out.println("ENter Iniitial amount: ");
		  
		  double balance=scanner.nextDouble();
		  
		  System.out.println("ENter Security Pin :");
		  
		  String Pin=scanner.next();
		  
		  long accountN0=generateAccount_Number();
		  
		  String query="insert into accounts( account_NO,acc_name,acc_email,balance,Security_Pin) values(?,?,?,?,?)";
		  PreparedStatement ps=connection.prepareStatement(query);
		  ps.setLong(1, accountN0);
		  ps.setString(2, name);
		  ps.setString(3, email); 
		  ps.setDouble(4, balance);
		  ps.setString(5,Pin);
		  
		  int affectedRows=ps.executeUpdate();
		  if(affectedRows>0)
		  {
			  return accountN0;
		  }
		  else
		  {
			  throw new RuntimeException("Account Creation Failed..>!");
		  }
	}
		  throw new RuntimeException("Account ALREADY eXISTS");
		  
		  
		  
		  
		  
		  
		  
		  
		  
	  }
	  
	  public long getAccount_Number(String email) throws SQLException
	  {
		 String query="select account_NO from accounts where acc_email=?";
		 
		 PreparedStatement ps=connection.prepareStatement(query);
		 
		 
		 ps.setString(1, email);
		 ResultSet rs=ps.executeQuery();
		 if(rs.next())
		 {
			 return rs.getLong("account_NO");
		 }
		 else
		 {
			 throw new RuntimeException("Account Number not exusts..!!");
		 }
		 
		 
		 
		  
	  }
	  
	  private long generateAccount_Number() throws SQLException
	  {
		String query="select * from accounts order by account_No desc limit 1";
		PreparedStatement ps=connection.prepareStatement(query);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
		{
			long lastAccount_No=rs.getLong(1);
		  
			return lastAccount_No+1;
		}
		else
		{
			return 10000100;
		}
		  
		  
		  
		  
	  }
	  public boolean account_Exists(String email) throws SQLException
	  {
		  String query="select * from accounts where acc_email=?";
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
