package Banking_System;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class BankingApp {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		System.out.println("Driver registerd");
		
		String url="jdbc:mysql://localhost:3306/banking_System";
		
		Connection con=DriverManager.getConnection(url,"root","root");
		
		System.out.println("Connection Establisshed");
		
		Scanner sc=new Scanner(System.in);
		
		User user=new User(con,sc);
		
		Accounts accounts=new Accounts(con, sc);
		
		AccountManager accountManager=new AccountManager(con, sc);
		
		String email;
		
		long account_Number;
		
		
		
		while(true)
		{
			System.out.println("!!..WELCOME TO BANKING SYSTEM..!!");
			System.out.println();
			System.out.println("1. REGISTER");
			System.out.println("2.LOGIN");
			System.out.println("3.EXIT");
			System.out.println("ENTER YOUR CHOICE :");
			int choice1=sc.nextInt();
			switch(choice1)
			{
			  
			case 1:
				user.register();
			      break;
			      
			case 2:
				email=user.login();
				System.out.println("Welcome " + email);
				if(email!=null)
				{
					System.out.println();
					System.out.println("User LOgged IN..");
					 if(!accounts.account_Exists(email))
					 {
						 System.out.println();
						 
						 System.out.println("1.OPEN A NEW ACCOUNT");
						 
						 System.out.println("2.EXIT");
						 if(sc.nextInt()==1)
						 {
							 account_Number=accounts.openAccount(email);
							 System.out.println("Account created Successfully..");
							 System.out.println("Your Account Number is " + account_Number);
						 }
						 else
						 {
							 break;
						 }
						 
						 
					 }
					 
					 account_Number=accounts.getAccount_Number(email);
					 int choice2= 0;
					 
					 while(choice2!=5)
					 {
						 System.out.println();
						 System.out.println("1.Debit Money");
						 System.out.println("2.Credit Money");
						 System.out.println("3.Transfer Money");
						 System.out.println("4.Cheack Balance");
						 System.out.println("5.LogOut");
						 System.out.println("Enter Your Choice ..");
						 choice2=sc.nextInt();
						 switch(choice2)
						 {
						 case 1:
							 accountManager.debit_Money(account_Number);
							 break;
						 case 2:
							 accountManager.credit_Money(account_Number);
							 break;
						 case 3:
							 accountManager.transfer_Money(account_Number);
							 break;
						 case 4:
							 accountManager.getBalance(account_Number);
							 break;
						 case 5:
							 break;
							 default:
								 System.out.println("Enter valid Choice..");
								 break;
						 }
						 
					 }
					 
					 
					 
					
					
				}
				else
				{
					System.out.println("Incorrect EMail or Password..");
				}
				
			case 3:
				System.out.println("THANK YOU FOR USING BANKING SYSTEM>>!!");
				System.out.println("EXITING SYSTEM");
				return;
				
				default :
					System.out.println("Enter valid choice");
					break;
			
			
			
			
			
			}
			
		}
		
		
		
		
		
		

	}

}
