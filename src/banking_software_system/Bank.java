package banking_software_system;

import java.text.ParseException;
import java.util.Scanner;

public class Bank {

	static String rDashLine = "----------------------------";
	static String dashLine = "---------------------------------------------";
	static Scanner scan = new Scanner(System.in);
	static int count = 0;

	public static void main(String[] args) throws ParseException {
		final int MAX_CUSTOMERS = 1000;
		Customer[] customers = new Customer[MAX_CUSTOMERS];
		String input;

		while (true) {
		
			scan.nextLine();
			input = mainMenu();

			switch (input) {

			case "1": atm(customers);  break;
			case "2": desk(customers); break;
			default: System.out.println("No such choice ;_; \n");

			}
		}
	}

	private static void desk(Customer[] customers) throws ParseException {
		String input = "";
		String pin;
		System.out.println(dashLine);
		System.out.println("Hello! Do you have an account with us?");
		input = scan.nextLine();

		if (input.contentEquals("yes")) {
			System.out.println("Oh, welcome back! What is your account number?");
			String accNum = scan.nextLine();
			Customer foundCustomer = findByAccountNumber(customers, count, accNum);

			while (true) {

				if (foundCustomer.password.contains("DELETED")) {
					System.out.println("I'm sorry, but this account has been deleted.");
					break;
				}
				System.out.print("Enter your pin number on the dPad: ");
				pin = scan.nextLine();

				if (pin.equals(foundCustomer.password)) {

					makeChanges(foundCustomer);
					break;

				} else {
					System.out.println("Sorry, but the details you provided are wrong...");
					System.out.println("Could you please try again? \n");
				}
			}

		} else if (input.contentEquals("no")) {
			Customer customer = createAccount();
			customers[count++] = customer;
			
			System.out.println("Thank you " + customer.firstName + " " + customer.lastName + "!");
			System.out.println("Here is your receipt for your initial deposit.");
			showReceipt(customer, count);
		} else {
			System.out.println("Excuse me?");
		}
	}

	private static void atm(Customer[] customers) throws ParseException {
		int input;
		System.out.println(dashLine);
		System.out.println("     Welcome to Bread Bank™!");
		System.out.println("       Would you like to: ");
		System.out.println(" (1) Login     (2) Create Account");
		input = scan.nextInt();

		if (input == 1) {
			System.out.println(dashLine);
			System.out.println("Welcome back!");
			System.out.print("Please enter your details below");
			scan.nextLine();

			while (true) {
				System.out.print("\nAccount Number: ");
				String accNum = scan.nextLine();

				Customer foundCustomer = findByAccountNumber(customers, count, accNum);

				if (foundCustomer.password.contains("DELETED")) {
					System.out.println("This account has been deleted.");
					break;
				}
				System.out.print("PIN: ");
				String pin = scan.nextLine();

				if (pin.equals(foundCustomer.password)) {

					makeChangesATM(foundCustomer);
					break;

				} else {
					System.out.println("Sorry, the credentials you provided are invalid");
					System.out.println("Please Try Again \n");
				}
			}
		} else if (input == 2) {
			System.out.println("Please approach the counter to create your account");

		}
	}

	public static Customer findByAccountNumber(Customer[] customerArray, int customerCounter, String accountNumber) {
		for (int i = 0; i < customerCounter; i++) {
			if (customerArray[i].getAccountNumber().contentEquals(accountNumber)) {
				return customerArray[i];
			}
		}
		return null;
	}

	public static void showReceipt(Customer c, int accountNumber) {
		header();
		System.out.println("Date: " + c.getShortDateAccountCreated());
		System.out.println("Client Name: " + c.firstName + " " + c.lastName);
		System.out.println("Account Number: " + c.accountNumber);
		System.out.println(rDashLine);
		System.out.printf("%-8S%20.2f\n", "Deposit", c.balance);
		System.out.printf("%-8S%20.2f\n", "Balance", c.balance);
		System.out.println(rDashLine);
	}

	public static Customer createAccount() {

		System.out.print("Answer the following questions" + "\nto set up your account with us." + "\n");
		scan.nextLine();
		System.out.println("What is your first name? ");
		String fName = scan.nextLine();

		System.out.println("Last name? ");
		String lName = scan.nextLine();

		System.out.println("What is your phone number? ");
		String phone = scan.nextLine();

		System.out.println("What account number would you like to have? ");
		String accountNumber = scan.nextLine();

		System.out.println("Enter a new PIN number: ");
		String pin = scan.nextLine();

		System.out.println("What will your initial deposit be? ");
		double balance = Double.parseDouble(scan.nextLine());

		Customer c = new Customer(fName, lName, phone, accountNumber, balance, pin);

		return c;
	}

	public static void makeChanges(Customer customer) throws ParseException {
		System.out.println(dashLine);
		System.out.println("      Welcome " + customer.firstName + " " + customer.lastName + "!");
		int input = 0;
		String wordInput = "";
		double amount = 0;

		while (true) {
			System.out.println("       Would you like to: ");
			System.out.println("  (1) Deposit        (2) Withdraw");
			System.out.println("  (3) Write Check    (4) Show Interest");
			System.out.println("  (5) Delete Account (6) Exit");
			input = scan.nextInt();
			
			if (input == 1) {
				System.out.println(dashLine);
				System.out.println("How much would you like to deposit?");
				amount = scan.nextInt();
				scan.nextLine();
				customer.deposit(amount);

			} else if (input == 2) {
				System.out.println(dashLine);
				System.out.println("How much would you like to withdraw?");
				amount = scan.nextInt();
				
				scan.nextLine();
				customer.withdraw(amount);
				
				if(amount > customer.balance)
					amount = 00.00;
				
			} else if (input == 3) {
				scan.nextLine();
				createCheck();
				
			} else if (input == 5) {
				System.out.println("Are you sure?");
				scan.nextLine();
				scan.nextLine();
				showChangesReceipt(customer, input, amount);
				break;
			} else if(input == 4) {
				System.out.println("When do you want to calculate the days till? (mm/dd/yyyy)");
				scan.nextLine();
				String date2 = scan.nextLine();
				int nOfDays = Customer.betweenDays(date2);
				double interestEarned = Customer.getInterestEarnedByDuration(nOfDays);
				
				System.out.printf("%-10s%1.2f%2s%5s","You would've earned $", interestEarned , " by ", date2);
				System.out.println();
			}else if(input == 6) {
			System.out.println("Thank you for using our service!");
				break;
			}	
			
			showChangesReceipt(customer, input, amount);
			
			System.out.println("Are you done for today?");
			wordInput = scan.nextLine();
			if (wordInput.contentEquals("yes")) {
				break;
			} else if(wordInput.contentEquals("no")) {
				continue;
				
				}
			
			}
		}
	
	public static void makeChangesATM(Customer customer) throws ParseException {
		System.out.println(dashLine);
		System.out.println("      Welcome " + customer.firstName + " " + customer.lastName + "!");
		int input = 0;
		String wordInput = "";
		double amount = 0;

		while (true) {
			System.out.println("       Would you like to: ");
			System.out.println("  (1) Deposit        (2) Withdraw");
			System.out.println("            (3) Exit             ");
			input = scan.nextInt();
			
			if (input == 1) {
				System.out.println(dashLine);
				System.out.println("How much would you like to deposit?");
				amount = scan.nextInt();
				scan.nextLine();
				customer.deposit(amount);

			} else if (input == 2) {
				System.out.println(dashLine);
				System.out.println("How much would you like to withdraw?");
				amount = scan.nextInt();
				
				scan.nextLine();
				customer.withdraw(amount);
				
				if(amount > customer.balance)
					amount = 00.00;
				
			}else if(input == 3) {
			System.out.println("Thank you for using our service!");
				System.exit(1);
				break;
			}
			showChangesReceipt(customer, input, amount);
			System.out.println("Are you done for today?");

			wordInput = scan.nextLine();
			if (wordInput.contentEquals("yes")) {
				break;
			} else if(wordInput.contentEquals("no")) {
				continue;
			}

		}

	}
	private static void createCheck() {
		String payTo = "";
		double amount;
		System.out.println("Who is this check for?");
		payTo = scan.nextLine();
		System.out.println("What would the amount be on the check?");
		amount = scan.nextDouble();

		scan.nextLine();
		Customer.check(amount, payTo);
	}

	public static void showChangesReceipt(Customer customer, int input, double amount) {

		if (input == 1 || input == 2) {
			header();
			System.out.println("Client Name: " + customer.firstName + " " + customer.lastName);
			System.out.println("Account Number: " + (customer.accountNumber));
			System.out.println(rDashLine);
			if (input == 1)
				System.out.printf("%-8S%20.2f\n", "Deposit", amount);

			else if (input == 2)
				System.out.printf("%-8S%20.2f\n", "Withdraw", amount);

			System.out.printf("%-8S%20.2f\n", "Balance", customer.balance);
			System.out.println(rDashLine);


		} else if (input == 5) {
			header();
			System.out.println("Client Name: " + customer.firstName + " " + customer.lastName);
			System.out.println("Account Number: " + (customer.accountNumber));
			System.out.println(rDashLine);
			System.out.printf("%-8S%20.2f\n", "Withdraw", customer.balance);
			System.out.println(rDashLine);
			System.out.println("YOUR ACCOUNT HAS BEEN DELETED");
			customer.deleteAccount();
	
		}
			
	}

	public static String mainMenu() {
		System.out.println("-------Which service would you prefer?-------");
		System.out.println("	  (1) ATM      (2) DESK \n");
		String input = scan.nextLine();
		return input;
	}

	public static int greeting() {

		System.out.println("    Welcome to Bread Bank™!");
		System.out.println("      Would you like to: ");
		System.out.println("  (1)Login      (2) Sign Up");
		int input = scan.nextInt();
		return input;

	}

	public static void header() {

		System.out.println(rDashLine);
		System.out.println("         Bread Bank™");
		System.out.println("      132 Main Street");
		System.out.println("      Selden, NY 11784");
		System.out.println("       (631)123-4567");
		System.out.println(rDashLine);

	}

}
