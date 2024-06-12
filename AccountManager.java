package Banking_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class AccountManager {
	
	private Connection connection;
	
	private Scanner scanner;
	
	public AccountManager(Connection connection,Scanner scanner)
	{
		this.connection=connection;
		this.scanner=scanner;
	}
	
	
	public void credit_Money(long account_NO) throws SQLException
	{
	  System.out.println("Enter Amount :");
	  
	  double ammount=scanner.nextDouble();
	  
	  System.out.println("ENter Security Pin :");
	  
	  String pin=scanner.next();
	  
	  connection.setAutoCommit(false);
	  if(account_NO!=0)
	  {
		  String Query="select * from accounts where account_No=? and security_Pin=?";
		  
		  PreparedStatement ps=connection.prepareStatement(Query);
		  
		   ps.setLong(1, account_NO);
		   
		   ps.setString(2, pin);
		   
		   ResultSet rs=ps.executeQuery();
		   
		   
	      if(rs.next())
	      {
	    	  String credit_Query="update accounts set balance=balance+? where account_No=?";
	    	  
	    	  PreparedStatement ps1=connection.prepareStatement(credit_Query);
	    	   
	    	  ps1.setDouble(1, ammount);
	    	
	    	  ps1.setLong(2, account_NO);
	    	  
	    	  int affectedRows=ps1.executeUpdate();
	    	  
	    	  if(affectedRows>0)
	    	  {
	    		  System.out.println("Rs. " + ammount + "Credited Succesfully ");
	    		  
	    		  connection.commit();
	    		  
	    		  connection.setAutoCommit(true);
	    		 
	    		  return;
	    		  
	    	  }
	    	  
	    	  else
	    	  {
	    		  System.out.println("Transaction failed..");
	    		  
	    		  connection.rollback();
	    		  
	    		  connection.setAutoCommit(true);
	    		  
	    	  }
	    }
	      
	      else
	    	  
	      {
	    	  System.out.println("Invalid Security Pin");
	      }
		   
		   
		  
	  }
		
		
	}
	public void debit_Money(long account_NO) throws SQLException
	{
		System.out.println("Enter Amount : ");
		double amount=scanner.nextDouble();
	    System.out.println("ENter SEcurity Pin : ");
	    String pin=scanner.next();
	    connection.setAutoCommit(false);
	    if(account_NO!=0)
	    {
	    	 String query="select * from accounts where account_No=? and security_Pin=? ";
	    	
	    	 PreparedStatement ps=connection.prepareStatement(query);
	    	
	    	ps.setLong(1, account_NO);
	    	
	    	ps.setString(2, pin);
	    	
	       ResultSet rs=ps.executeQuery();
	       
	       
	       if(rs.next())
	       {
	    	   
	    	   double currentBalance=rs.getDouble("balance");
	    	   
	    	   if(amount<=currentBalance)
	    	   {
	    		   String debit_query="update accounts set balance=balance-? where account_No=?";
	    		   
	    		   PreparedStatement ps1=connection.prepareStatement(debit_query);
	    		   
	    		   ps1.setDouble(1, amount);
                   
	    		   ps1.setLong(2, account_NO);
                   
                   int affectedRows=ps1.executeUpdate();
                   if(affectedRows>0)
                   {
                	   
                	   System.out.println("Rs. " + amount + "Debited Successfully..!!");
                	   
                	   connection.commit();
                	   
                	   connection.setAutoCommit(true);
                	   
                	   return;
                   }
                   
                   else
                   
                   {
                	
                	   System.out.println("Transaction Failed..");
                	   
                	   connection.rollback();
                	   
                	   connection.setAutoCommit(true);
                   }
               }
	    	   
	    	   
	       }
	       else
	    	   
	       {
	    	   System.out.println("Invalid Pin");
	       }
	    	
	    	
	    	
	    }
	 
	
	}
	
	public void getBalance(long account_No) throws SQLException
    {   
		 System.out.println("Enter Security Pin :  ");
		 
		 String pin=scanner.next();
		 
		 String query="select * from accounts where account_NO=? and security_Pin=?";
		
		 PreparedStatement ps=connection.prepareStatement(query);
		 
		 ps.setLong(1, account_No);
		 
		 ps.setString(2, pin);
		 
		 ResultSet rs=ps.executeQuery();
		 
		 if(rs.next())
		 {
			 double balance=rs.getDouble("balance");
			 System.out.println("Balannce: " + balance);
		 }
		 else
		 {
			 System.out.println("Invalid Pin..");
		 }
		 
		 
		 
		 
		 
	}
	
	
    public void transfer_Money(long sender_Account_NO) throws SQLException
	{
	  	System.out.println("Enter Recivers AccountNO :");
	  	
	  	long reciver_account_No=scanner.nextLong();
	  	
	  	System.out.println("Enter amount :");
	  	
	  	double amount=scanner.nextDouble();
	  	
	  	System.out.println("Enter Security Pin :");
	  	
	  	String pin=scanner.next();
	  	
	  	
	  	connection.setAutoCommit(false);
	  	
	  	if(sender_Account_NO!=0 && reciver_account_No!=0)
	  	{
	  		PreparedStatement ps=connection.prepareStatement("select * from accounts where account_No=? and Security_Pin=?");
	  		  
	  		ps.setLong(1, sender_Account_NO);
	  		
	  		ps.setString(2, pin);
	  		
	  		ResultSet rs=ps.executeQuery();
	  		
	  			
	  		if(rs.next())
	  		{
	  			double curr_Balance=rs.getDouble("balance");
	  			if(amount<=curr_Balance)
	  			{
	  				String debit_Query="update accounts set balance=balance-? where account_No=?";
	  				
	  				String credit_Query="update accounts set balance=balance+? where account_No=?";
	  				
	  				PreparedStatement cps=connection.prepareStatement(credit_Query);
	  				
	  				PreparedStatement dps=connection.prepareStatement(debit_Query);
	  				
	  				cps.setDouble(1, amount);
	  				cps.setLong(2, reciver_account_No);
	  				
	  				dps.setDouble(1, amount);
	  			    dps.setLong(2, sender_Account_NO);
	  			   
	  			   int affrows1=cps.executeUpdate();
	  			   int affrows2=dps.executeUpdate();
	  			   
	  			   if(affrows1>0 && affrows2>0)
	  			   {
	  				   System.out.println("Transaction Succesfull..!");
	  				   
	  				   System.out.println("Rs. " + amount + " Transfered successfuly");
	  				   
	  				   connection.commit();
	  				   
	  				   connection.setAutoCommit(true);
	  				   
	  				   return;
	  			   }
	  			   
	  			   else
	  			   {
	  				System.out.println("Transaction failed..!");
	  				
	  				connection.rollback();
	  				
	  				connection.setAutoCommit(true);
	  				
	  				
	  			   }
	  			  
	  				
	  				
	  				
	  			}
	  			else
	  			{
	  				System.out.println("Insufficient Balance..");
	  			}
	  		}
	  		
	  		
	  		
	  		
	  		
	  		
	  	}
	  	
	  	
	  	
	  	
	  	
	  	
	  	
	  	
	  	
	  	
	  	
	  	
	  	
	  	
	  	
	  	
	  	
	  	
    	
	}

}
