
import java.util.Scanner;

public class Main {
private static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {

        RideSharingSystem system = new RideSharingSystem(); 
        // موضوع انها تدينا false لو فيه مشكلة في تحميل البيانات من الملفات
         system.loadRidersFromCSV("riders 100.csv");
         system.loadDriversFromCSV("drivers 30.csv");
         system.loadRidesFromCSV("rides 40.csv");

        int choice;

        do {
            System.out.println("\n=== Ride Sharing System ===");
            System.out.println("1. List all riders");
            System.out.println("2. Search riders by home city");
            System.out.println("3. Search riders by name");
            System.out.println("4. Add a rider");
            System.out.println("5. List all drivers");
            System.out.println("6. Search drivers by vehicle type");
            System.out.println("7. Add a driver");
            System.out.println("8. List all rides alphabetically");
            System.out.println("9. Search rides by pickup location");
            System.out.println("10. Search rides by rider name");
            System.out.println("11. List shared ride participants");
            System.out.println("12. Add a ride");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    // Call getAllRiders()
                    // Print the returned list.
                    break;

                case 2:
                    // Read a home city.
                    // Call searchRidersByHomeCity().
                    break;

                case 3:
                    // Read a full name.
                    // Call searchRidersByName().
                    break;

                case 4:
                    // Read rider fields, construct Rider,
                    // and call addRider().
                    break;

                case 5:
                    // Call getAllDrivers().
                    break;

                case 6:
                    // Read a VehicleType.
                    // Call searchDriversByVehicleType().
                    break;

                case 7:
                    // Read driver fields, construct Driver,
                    // and call addDriver().
                    break;

                case 8:
                    printList(system.getAllRidesAlphabetically(), "No rides found.");
                    break;

                case 9:
                    // Read pickup location and search.
                    String location = readText("Pickup location: ");
                    printList(system.searchRidesByPickupLocation(location), "No rides found at that pickup location.");
                    break;

                case 10:
                    // Read rider name and search.
                    String name = readText("Rider's full name: ");
                    printList(system.searchRidesByRiderName(name), "No rides found for that rider.");
                    break;

                case 11:
                    // Read pickup location and get participants.
                    String pickUpLocation = readText("Pickup location of the shared ride: ");
                    printList(system.getSharedRideParticipants(pickUpLocation),"No shared ride (or no riders) found at that pickup location.");
                    break;

                case 12:{
                    // Ask PRIVATE or SHARED.
                    // Read the appropriate ride information.
                    System.out.println("What kind of ride?");
                    System.out.println("1. Private (one rider)");
                    System.out.println("2. Shared (more than one rider)");
                    int kind = readInt("Choice: ");

                    if (kind != 1 && kind != 2) 
                     System.out.println("Invalid ride type. Ride not added.");
                    else {
                        String pickupLocation = readText("Pickup location: ");
                        IDateTime pickupTime = readDateTime("Pickup date/time (MM/DD/YYYY HH:MM): ");
                        IDateTime dropoffTime = readDateTime("Drop-off date/time (MM/DD/YYYY HH:MM): ");
                        String dropoffLocation = readText("Drop-off location: ");

                    if (pickupTime == null || dropoffTime == null) 
                         System.out.println("Invalid date/time. Ride not added.");
                    else {
                        int driverId = readInt("Driver ID: ");
                        String failMessage = "Failed to schedule the ride. Check that the rider(s) and driver exist, "
                                       +     "that pickup is before drop-off, and that there is no schedule conflict.";
                        String result;

                     if (kind == 1) {                                   // private ride
                        int riderId = readInt("Rider ID: ");
                        boolean scheduled = system.schedulePrivateRide(pickupLocation, pickupTime,dropoffTime, dropoffLocation, riderId, driverId);
                        result = scheduled ? "Ride scheduled successfully." : failMessage;
                    } 
                    else {                                           // shared ride
                        int[] riderIds = readRiderIds("Rider IDs separated by commas (at least 2): ");
                        if (riderIds == null || riderIds.length < 2) 
                        result = "A shared ride needs at least 2 valid rider IDs. Ride not added.";
                
                    else {
                    boolean scheduled = system.scheduleSharedRide(pickupLocation, pickupTime, dropoffTime, dropoffLocation, riderIds, driverId);
                    result = scheduled ? "Ride scheduled successfully." : failMessage;
                            }
                        }
                System.out.println(result);
                        }
                    }
                break;
                    }


                    

                case 0:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);

        input.close();
    }


    // =====================================================================
    //  SHARED HELPERS (input and printing)
    // =====================================================================
     /** Prints every element's toString(), or the given message if the list is empty. */
  private static <T> void printList(LinkedList<T> list, String emptyMessage) {
        if (list == null || list.empty()) {
            System.out.println(emptyMessage);
            return;
        }
        list.findFirst();
        while (true) {
            String text = list.retrieve().toString();
            System.out.println(text);
            if (text.contains("\n")) {
                System.out.println();               // blank line between multi-line items (rides)
            }
            if (list.last()) {
                break;
            }
            list.findNext();
        }
    }

     /** Prompts once and returns the trimmed text the user typed. */
    private static String readText(String prompt) {
        System.out.print(prompt);
        return input.nextLine().trim();
    }

     /** Prompts until the user enters a whole number. */
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = input.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    /** Reads "MM/DD/YYYY HH:MM" and returns a DateTime, or null if the text is invalid. */
    private static IDateTime readDateTime(String prompt) {
        String text = readText(prompt);
        try {
            String[] parts = text.split(" +");
            String[] date = parts[0].split("/");
            String[] time = parts[1].split(":");
            return new DateTime(Integer.parseInt(date[2]), Integer.parseInt(date[0]),
                                Integer.parseInt(date[1]), Integer.parseInt(time[0]),
                                Integer.parseInt(time[1]));
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    private static int[] readRiderIds(String prompt) {
        String text = readText(prompt);
        if (text.isEmpty()) {
            return null;
        }
        String[] pieces = text.split("[,\\s]+");
        int[] ids = new int[pieces.length];
        try {
            for (int i = 0; i < pieces.length; i++) {
                ids[i] = Integer.parseInt(pieces[i]);
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return ids;
    }

}
