package banking_software_system;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Customer {
	private static final double INTEREST_RATE = 0.015;
	String lastName;
	String firstName;
	String phoneNumber;
	String accountNumber;
	static double balance;
	String password;
	private Date dateAccountCreated;
	static private String shortDateAccountCreated;
	
	
	public Customer(String firstName, String lastName, String phoneNumber, String accountNumber, double balance, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.password = password;
		this.dateAccountCreated = new Date();
		this.shortDateAccountCreated = Utils.dateConverter(dateAccountCreated);
	}
	public void deposit(double amount) {
		balance += amount;
	}
	public static void check(double amount,String payTo ) {

		String dashline = "-------------------------------------------------";
		String border = "|						|";
		
		System.out.println(dashline);
		System.out.println("|			  Date: " + shortDateAccountCreated + " 	|");
		System.out.println(border);
		System.out.println("|PAY TO THE					|");
		System.out.println("|ORDER OF: " + payTo + "	       $" + amount + "		|");
		System.out.println(border);
		System.out.println("|" + Utils.convertCurrency(amount) + "        	 	DOLLARS |");
		System.out.println(border);
		System.out.println(dashline);

	}
	public void withdraw(double amount) {
		if (amount > balance) {
		
			System.out.println("Over draft");
		} else {
			balance -= amount;
		}
	}
	public static double getInterestEarnedByDuration(int numberOfDays) {
		double interestEarned = balance * (INTEREST_RATE / 365) * numberOfDays;
		return interestEarned;

	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public String getShortDateAccountCreated() {
		return shortDateAccountCreated;
	}
	public static int betweenDays(String date2) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date myDate1 = dateFormat.parse(shortDateAccountCreated);
		Date myDate2 = dateFormat.parse(date2);
		long diff = Math.abs(myDate1.getTime() - myDate2.getTime())/(1000 * 60 * 60 * 24);
		return (int) diff;
	}
	public void deleteAccount() {
		this.firstName = "";
		this.lastName = "";
		this.phoneNumber = "";
		this.balance = 0;
		this.password = "DELETED";
		
	}
	
	public String toString(){
		return accountNumber + " " + firstName + " " + lastName + " " + phoneNumber + " " + balance;

	}
	
}

