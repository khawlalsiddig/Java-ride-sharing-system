
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        // RideSharingSystem system = new RideSharingSystem();

        // system.loadRidersFromCSV("riders 100.csv");
        // system.loadDriversFromCSV("drivers 30.csv");
        // system.loadRidesFromCSV("rides 40.csv");

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
                    // Call getAllRidesAlphabetically().
                    break;

                case 9:
                    // Read pickup location and search.
                    break;

                case 10:
                    // Read rider name and search.
                    break;

                case 11:
                    // Read pickup location and get participants.
                    break;

                case 12:
                    // Ask PRIVATE or SHARED.
                    // Read the appropriate ride information.
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
}
