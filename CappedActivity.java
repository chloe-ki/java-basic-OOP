// extension of class Activity, process capped activity information
public class CappedActivity extends Activity {
	private String[][] bookings;
	private int numberSold, capTotal;
	private String[] sessionNames;
	private int[] numSold, capAmount;
	private double[][] prices;
// inherits activity details
	public CappedActivity(String title, String description, double[][] prices, 
			String[] ticketTypes, String[] sessionNames, String activityType, int[] capAmount) {
		super(title, description, prices, ticketTypes, sessionNames, activityType, capAmount);
		for (int i = 0; i < capAmount.length; i++) {
			capTotal = capTotal + capAmount[i];
		}
		bookings = new String[capTotal][sessionNames.length];
		this.numSold = new int[sessionNames.length];

		this.sessionNames = sessionNames;
		this.capAmount = capAmount;
		this.prices = prices;
	}
//	getter for capped activity bookings
	public String[][] getBookings() {
		return bookings.clone();
	}
// overrides Activity method displayDetails()
	@Override
	public void displayDetails() {
// calls Activity method displayDetails() for code reuse
		super.displayDetails();

//		prints bookings made for specific (capped) activity
		for (int i = 0; i < sessionNames.length; i++) {
			System.out.printf("%-50s", "Session: " + sessionNames[i]);
			System.out.println("\n");
			if (numSold[i] > 0) {
				for (int j = 0; j < numSold[i]; j++) {
					System.out.printf("%-50s", "     " + bookings[j][i]);
					System.out.println("\n");
				}

			} else {
				System.out.printf("%-50s", " ");
				System.out.println("\n");
			}
		}
	}
// overrides Activity method bookActivity()
	@Override
	public boolean bookActivity(int session, int ticketType, String name) {
// 		calls Activity method bookActivity() for code reuse
		super.bookActivity(session, ticketType, name);
		numSold[session]++;
// 		fills up bookings array
		if (numSold[session] <= capAmount[session]) {
			String bookingInfo = String.format("%s %.2f", "Name: " + name + ", Ticket price: $",
					prices[session][ticketType]);
			bookings[numSold[session] - 1][session] = bookingInfo;

			for (int i = 0; i < numSold.length; i++) {
				numberSold = numberSold + numSold[i];
			}
			return true;
		} else {
			System.out.println("Error! Booking exceeds cap amount.");
			numberSold = numberSold - 1;
			numSold[session] = numSold[session] - 1;
			return false;
		}
	}
// 	overrides Activity method refundActivity
	@Override
	public void refundActivity(String activity, String session, String name) {
		super.refundActivity(activity, session, name);
		String index;
//		searches for match to user input in StageC class
		String[][] newBookings = new String[capTotal - 1][sessionNames.length];
		for (int i = 0; i < bookings.length; i++) {
			for (int j = 0; j < bookings[i].length; j++) {
				if (bookings[i][j].contains(name)) {
					index = bookings[i][j];
//					creates copy array without the refunded booking
					for (int k = 0; k < bookings.length; k++) {
						for (int l = 0; l < bookings[k].length; l++) {
							if (bookings[k][l].equals(index)) {
								continue;
							} else if (bookings[k][l].equals(null)){
								newBookings[k][l] = bookings[k][l];
							} else {
								newBookings[k][l] = bookings[k][l];
							}
						}
					}
					bookings = newBookings;
				}
			}
		}
	}
}
