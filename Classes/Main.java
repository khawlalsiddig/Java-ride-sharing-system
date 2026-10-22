
import java.io.File;
import java.util.Scanner;

public class Main {
private static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        RideSharingSystem system = new RideSharingSystem(); 

         if (!system.loadRidersFromCSV(new File("riders_100.csv").getAbsolutePath())) {
             System.out.println("Error loading riders data.");
         }
         if (!system.loadDriversFromCSV(new File("drivers_30.csv").getAbsolutePath())) {
             System.out.println("Error loading drivers data.");
         }
         if (!system.loadRidesFromCSV(new File("rides_40.csv").getAbsolutePath())) {
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

                    if (system.addRider(new Rider(riderId, riderName, riderHomeCity, phoneNumber, email)))
                    System.out.println("Rider added successfully.");
                    else
                    System.out.println("Falied adding the rider.");
                    break;

                case 5:
                    printList(system.getAllDrivers(), "No drivers found.");
                    break;

                case 6:
                    VehicleType vehicleType = readVehicleType();
                    printList(system.searchDriversByVehicleType(vehicleType), "No drivers found with that vehicle type.");
                    break;

                case 7:
                    int driverId = readInt("Driver ID: ");
                    String driverName = readText("Full name: ");
                    String driverPhoneNumber = readText("Phone number: ");
                    String vehiclePlate = readText("Vehicle plate: ");
                    VehicleType driverVehicleType = readVehicleType();

                    try {

                        Driver newDriver = new Driver(driverId, driverName, driverPhoneNumber, vehiclePlate, driverVehicleType);
                        
                        if(system.addDriver(newDriver)) {
                            System.out.println("Driver added successfully.");
                        } else {
                            System.out.println("Error adding driver");
                        }

                    } catch (IllegalArgumentException e) {
                        System.out.println("Error adding driver: " + e.getMessage());
                    }
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
                    String riderNameForRideSearch = readText("Rider's full name: ");
                    printList(system.searchRidesByRiderName(riderNameForRideSearch), "No rides found for that rider.");
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

                    if (pickupTime == null || dropoffTime == null) //Don't add
                         System.out.println("Invalid date/time. Ride not added.");
                    else {//Add
                        int driverIdForRideAdding = readInt("Driver ID: ");
                        String failMessage = "Failed to schedule the ride. Check that the rider(s) and driver exist, "
                                       +     "that pickup is before drop-off, and that there is no schedule conflict.";
                        String result;

                     if (kind == 1) {                                   // private ride
                        int riderIdForRideAdding = readInt("Rider ID: ");
                        boolean scheduled = system.schedulePrivateRide(pickupLocation, pickupTime,dropoffTime, dropoffLocation, riderIdForRideAdding, driverIdForRideAdding);
                        result = scheduled ? "Ride scheduled successfully." : failMessage; } 
                    else {                                            // shared ride
                        int[] riderIds = readRiderIds("Rider IDs separated by commas (at least 2): ");
                        if (riderIds == null || riderIds.length < 2) 
                            result = "A shared ride needs at least 2 valid rider IDs. Ride not added.";
        
                        else {
                            boolean scheduled = system.scheduleSharedRide(pickupLocation, pickupTime, dropoffTime, dropoffLocation, riderIds,driverIdForRideAdding);
                            result = scheduled ? "Ride scheduled successfully." : failMessage;}

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
    private static VehicleType readVehicleType() {
        while (true) {
            System.out.println("1. Sedan");
            System.out.println("2. Luxury sedan");
            System.out.println("3. SUV");
            System.out.println("4. Van");

            int choice = readInt("Vehicle type: ");

            switch (choice) {
                case 1:
                    return VehicleType.SEDAN;
                case 2:
                    return VehicleType.LUXURY_SEDAN;
                case 3:
                    return VehicleType.SUV;
                case 4:
                    return VehicleType.VAN;
                default:
                    System.out.println("Invalid choice. Enter a number from 1 to 4.");
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
