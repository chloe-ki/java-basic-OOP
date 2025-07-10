// stores and process all activity related information
public class Activity {
	private double[][] prices;
	private String[] ticketTypes, sessionNames;
	private String title, description;
	private int numberSold;
	int[] capAmount;
	private int[] numSold;

// processes user input from StageC class
	public Activity(String title, String description, double[][] prices, String[] ticketTypes, 
			String[] sessionNames, String activityType, int[] capAmount) {
		this.prices = prices;
		this.ticketTypes = ticketTypes;
		this.title = title;
		this.description = description;
		this.sessionNames = sessionNames;
		this.numSold = new int[sessionNames.length];

	}

// getter method for activity title
	public String getTitle() {
		return title;
	}

//	getter method for activity description
	public String getDescription() {
		return description;
	}

//	getter method for activity prices
	public double[][] getPrice() {
		return prices;
	}

//	getter method for activity ticket types
	public String[] getTicketTypes() {
		return ticketTypes.clone();
	}

//	getter method for activity sessions
	public String[] getSessionNames() {
		return sessionNames.clone();
	}

//	getter method for activity tickets sold
	public int getNumberSold() {
		return numberSold;
	}

//	getter method for activity session tickets sold
	public int[] getNumSold() {
		return numSold.clone();
	}

// displays relevant activity details
	public void displayDetails() {
		int counter = 0;

		System.out.println("\n**ACTIVITY DETAILS**\n\nTitle: " + this.title + 
				"\n\nDescription: " + this.description);
		System.out.println("\n");
		for (int i = 0; i < numSold.length; i++) {
			System.out.println("Number sold for " + sessionNames[i] + ": " + numSold[i]);
			System.out.println("\n");
		}

		System.out.println("Ticket types, sessions and prices:");
		System.out.printf("%-25s", " ");
// 		iteration for arrays to print all information 
		for (int i = 0; i < ticketTypes.length; i++) {
			System.out.printf("%-25s", ticketTypes[i]);
		}
		System.out.println("\n");

		do {
			System.out.printf("%-25s", sessionNames[counter]);
			for (int i = 0; i < ticketTypes.length; i++) {
				System.out.printf("%-25.2f", prices[counter][i]);
			}
			System.out.println("\n");
			counter++;
		} while (counter < sessionNames.length);
	}

// prints ticket for booking
	public boolean bookActivity(int session, int ticketType, String name) {
		System.out.println("- - - - - - - - - - - - - -");
		System.out.println("Customer name: " + name + "\nTicket type: " + ticketTypes[ticketType]
				+ "\nSession: " + sessionNames[session] + "\nPrice: $" + prices[session][ticketType]);
		numSold[session]++;
		numberSold++;
// ticket print successful
		return true;
	}

// refunds cancelled booking
	public void refundActivity(String activity, String session, String name) {

	}

}
