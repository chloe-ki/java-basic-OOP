import java.util.Scanner;

//  gathers user input and runs program
public class StageC {
	private static Activity[] activities;
	private static int activityCount = 0;
	int activityNum;
	private Scanner sc;

// executes startup menu
	public StageC() {
		sc = new Scanner(System.in);
		System.out.println("**STARTUP**");

		System.out.print("How many activities would you like to enter?: ");
//		user input to becomes class reference size
		activityNum = Integer.parseInt(sc.nextLine());
		activities = new Activity[activityNum];

//		once activity amount is specified, main menu is executed
		mainMenu();
	}

// displays main menu
	public void mainMenu() {
//		menu displaying available functions
		System.out.println("**MAIN MENU**\n");
		System.out.println("\n1: Enter details");
		System.out.println("2: View specific activity");
		System.out.println("3: View all activities");
		System.out.println("4: Process booking");
		System.out.println("5: Refund booking");
		System.out.println("6: Exit");

		System.out.print("\nEnter your choice: ");
		int menuChoice = Integer.parseInt(sc.nextLine());
//		prompts user to enter a valid choice if not already done
		while (menuChoice < 1 || menuChoice > 6) {
			System.out.print("Error! Invalid choice, please enter a valid choice: ");
			menuChoice = Integer.parseInt(sc.nextLine());
		}
		if (menuChoice > 0 && menuChoice < 6) {
//			calls appropriate method depending on user's choice
			switch (menuChoice) {
			case 1:
				enterDetails();
				break;
			case 2:
				viewActivity();
				break;
			case 3:
				viewAll();
				break;
			case 4:
				processBooking();
				break;
			case 5:
				refundBooking();
				break;
			case 6:
				exitProgram();
				break;
			default:
				break;
			}
		}
	}

//	creates new activity with user input
	public void enterDetails() {
		if (activityCount < activities.length) {
			String title, description, answer, activityType;
			double[][] prices;
			String[] ticketTypes, sessionNames;
			int ticketCount, sessionCount;
			Activity a, c;
			int[] capAmount;
//  enter multiple activities without returning to main menu first
			do {
				System.out.print("Enter activity type [capped/uncapped]: ");
				activityType = sc.nextLine();

				System.out.print("Enter activity title: ");
				title = sc.nextLine();

				System.out.print("Enter activity description: ");
				description = sc.nextLine();

				System.out.print("How many ticket types?: ");
				ticketCount = Integer.parseInt(sc.nextLine());

				System.out.print("How many sessions?: ");
				sessionCount = Integer.parseInt(sc.nextLine());

//				temp arrays that will be parameter values
				prices = new double[sessionCount][ticketCount];
				sessionNames = new String[sessionCount];
				ticketTypes = new String[ticketCount];
				capAmount = new int[sessionCount];

//				iteration through arrays
				for (int i = 0; i < ticketTypes.length; i++) {
					System.out.print("Enter ticket type name for element " + i + ": ");
					ticketTypes[i] = sc.nextLine();
				}

				for (int i = 0; i < sessionNames.length; i++) {
					System.out.print("Enter session name and time for element " + i + ": ");
					sessionNames[i] = sc.nextLine();
					if (activityType.equalsIgnoreCase("capped")) {
						System.out.print("Enter cap amount for session " + sessionNames[i] + ": ");
						capAmount[i] = Integer.parseInt(sc.nextLine());
					} else {
						capAmount[i] = 0;
					}
				}

				int counter = 0;
				do {
					for (int i = 0; i < sessionCount; i++) {
						System.out.print("Enter price for ticket type " + ticketTypes[counter] + 
								" at session " + sessionNames[i] + ": ");
						prices[i][counter] = Double.parseDouble(sc.nextLine());
					}
					counter++;
				} while (counter < ticketCount);
//				adding created activity to array depending on type (capped/uncapped)
				if (activityType.equals("capped")) {
					c = new CappedActivity(title, description, prices, ticketTypes, sessionNames, 
							activityType, capAmount);
					activities[activityCount] = c;
					activityCount++;
				} else {
					a = new Activity(title, description, prices, ticketTypes, sessionNames, 
							activityType, capAmount);
					activities[activityCount] = a;
					activityCount++;
				}
//				determining variable for do-while loop
				if (activityCount < activityNum) {
					System.out.print("Add another activity? (yes/no): ");
					answer = sc.nextLine();
				} else {
					answer = "no";
				}
			} while (answer.equalsIgnoreCase("yes"));
//			choice to return to main menu or exit program
			endMenu();

		} else {
			System.out.println("Error! Array full.");
			endMenu();
		}
	}

//	view specific activity based on search
	public void viewActivity() {
		String activity;
// determines whether search is successful, default false
		boolean found = false;
		System.out.print("Enter activity name to view: ");
		activity = sc.nextLine();
//		iterates through activities array searching for user input
		for (int i = 0; i < activities.length; i++) {
			String title = activities[i].getTitle();
			if (title.equalsIgnoreCase(activity)) {
				activities[i].displayDetails();
				found = true;
			}
		}
		if (found == false) {
			System.out.println("Error! " + activity + " was not found.");
		}
		endMenu();
	}

	public void viewAll() {
		for (int i = 0; i < activities.length; i++) {
			activities[i].displayDetails();
		}
		endMenu();
	}

// input values for ticket booking
	public void processBooking() {
//		determines search success, default false
		boolean foundTitle = false;
		boolean foundTicket = false;
		boolean foundSession = false;
		int ticketCount = 1;
		int ticketIndex;
		int sessionIndex;

		System.out.print("Enter title of activity for ticket purchase: ");
		String titleSearch = sc.nextLine();
		for (int i = 0; i < activities.length; i++) {
			String title = activities[i].getTitle();
			if (title.equalsIgnoreCase(titleSearch)) {
				System.out.print("Enter the name of the customer: ");
				String name = sc.nextLine();

				System.out.print("How many tickets are required?: ");
				int purchaseQuantity = Integer.parseInt(sc.nextLine());

				while (ticketCount <= purchaseQuantity) {
					System.out.print("Enter ticket type being purchased: ");
					String typeSearch = sc.nextLine();

					String[] type = activities[i].getTicketTypes();
					String[] session = activities[i].getSessionNames();
					for (int j = 0; j < type.length; j++) {
						if (type[j].equalsIgnoreCase(typeSearch)) {
							ticketIndex = j;
							System.out.print("Enter session for ticket " + type[j] + ": ");
							String sessionSearch = sc.nextLine();
							for (int k = 0; k < session.length; k++) {
								if (session[k].equalsIgnoreCase(sessionSearch)) {
									sessionIndex = k;
//									search successful, calls ticket print method
									if (activities[i].bookActivity(sessionIndex, 
											ticketIndex, name) == true) {
										System.out.println("\nBooking status: Success!");
									} else {
										System.out.println("\nBooking status: Fail! "
												+ "Cap amount for session "+ session[sessionIndex] + " reached.");
									}
									foundSession = true;
								}

							}
							if (foundSession == false) {
								System.out.println("Error! Session not found.");
								processBooking();
							}
							foundTicket = true;
							ticketCount++;
						}
					}
					if (foundTicket == false) {
						System.out.println("Error! Ticket type not found.");
						processBooking();
					}

				}
			}
			foundTitle = true;

		}

		if (foundTitle == false) {
			System.out.println("Error! " + titleSearch + " was not found.");
		}

		endMenu();
	}

//	gathers information for cancelling a booking
	public void refundBooking() {
		String activity;
		String[] sessions;
		String sessionSearch;
		System.out.println("Enter activity title of ticket:\n");
		for (int i = 0; i < activities.length; i++) {
			System.out.printf("%5s", activities[i].getTitle());
			System.out.println("\n");
		}
		System.out.print("Enter choice: ");
		String title = sc.nextLine();

		System.out.println("Enter session of ticket:\n");
		for (int i = 0; i < activities.length; i++) {
			activity = activities[i].getTitle();
			if (title.equalsIgnoreCase(activity)) {
				sessions = activities[i].getSessionNames();
				for (int j = 0; j < sessions.length; j++) {
					System.out.printf("%5s", sessions[j]);
					System.out.println("\n");
				}
			}
		}
		System.out.print("Enter choice: ");
		String session = sc.nextLine();

		System.out.print("Enter ticket holder name: ");
		String name = sc.nextLine();

		for (int i = 0; i < activities.length; i++) {
			activity = activities[i].getTitle();
			if (title.equalsIgnoreCase(activity)) {
				sessions = activities[i].getSessionNames();
				for (int j = 0; j < sessions.length; j++) {
					sessionSearch = sessions[j];
					if (session.equalsIgnoreCase(sessionSearch)) {
//						successful searches, refund method called
						activities[i].refundActivity(activity, session, name);
					}
				}
			}
		}

	}

//	appears after every menu function
	public void endMenu() {
//		choice to return to main menu or exit program
		System.out.print("\n\n1: Main Menu\n2: Exit\n\nEnter your choice: ");
		int choice = Integer.parseInt(sc.nextLine());
		while (choice < 1 || choice > 2) {
			System.out.print("Error! Invalid choice, please enter a valid choice: ");
			choice = Integer.parseInt(sc.nextLine());
		}
//		appropriate method called based on user input
		if (choice == 1 || choice == 2) {
			switch (choice) {
			case 1:
				mainMenu();
				break;
			case 2:
				exitProgram();
				break;
			default:
				break;
			}
		}
	}

// closes program
	public void exitProgram() {
		System.out.println("Program closing...");
		System.exit(0);
	}

	public static void main(String[] args) {
//		runs class StageC
		StageC run = new StageC();
	}

}
