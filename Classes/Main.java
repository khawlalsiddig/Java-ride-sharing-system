
import java.util.Scanner;

public class Main {
private static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {

        RideSharingSystem system = new RideSharingSystem(); 
        // موضوع انها تدينا false لو فيه مشكلة في تحميل البيانات من الملفات
         if (!system.loadRidersFromCSV("riders 100.csv")) {
             System.out.println("Error loading riders data.");
         }
         if (!system.loadDriversFromCSV("drivers 30.csv")) {
             System.out.println("Error loading drivers data.");
         }
         if (!system.loadRidesFromCSV("rides 40.csv")) {
             System.out.println("Error loading rides data.");
         }

        int choice;

        do {
            System.out.println("\n===== Ride Sharing System =====");
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
                    printList(system.getAllRiders(), "No riders found.");
                    break;

                case 2:
                    System.out.print("Home city: ");
                    String homeCity = input.nextLine();
                    printList(system.searchRidersByHomeCity(homeCity), "No riders found in that city.");
                    break;

                case 3:
                    System.out.print("Rider's full name: ");
                    String name = input.nextLine();
                    printList(system.searchRidersByName(name), "No riders found with that name.");
                    break;

                case 4:
                    
                    String riderHomeCity = readText("Home city: ");
                    String riderName = readText("Full name: ");
                    String phoneNumber = readText("Phone number: ");
                    String email = readText("Email: ");
                    int riderId = readInt("Rider ID: ");

                    system.addRider(new Rider(riderId, riderName, riderHomeCity, phoneNumber, email));
                    System.out.println("Rider added successfully.");
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

                case 12:
                    // Ask PRIVATE or SHARED.
                    // Read the appropriate ride information.
                     System.out.println("What kind of ride?");
                    System.out.println("1. Private (one rider)");
                    System.out.println("2. Shared (more than one rider)");
                    int kind = readInt("Choice: ");

                    while (kind != 1 || kind != 2){
                     System.out.println("Invalid ride type. Ride not added.");
                     kind = readInt("Choice: ");
                    }


                    break;

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
}
