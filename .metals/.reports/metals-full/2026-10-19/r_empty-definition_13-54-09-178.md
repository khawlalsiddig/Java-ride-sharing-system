error id: file:///C:/Users/rawan/OneDrive/Desktop/DS%20Project/Java-ride-sharing-system/Classes/RideSharingSystem.java:
file:///C:/Users/rawan/OneDrive/Desktop/DS%20Project/Java-ride-sharing-system/Classes/RideSharingSystem.java
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 2635
uri: file:///C:/Users/rawan/OneDrive/Desktop/DS%20Project/Java-ride-sharing-system/Classes/RideSharingSystem.java
text:
```scala

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RideSharingSystem implements IRideSharingSystem {
    private LinkedList<IRider> riders;
    private LinkedList<IDriver> drivers;
    private LinkedList<IRide> rides;

    public RideSharingSystem() {
        riders = new LinkedList<>();
        drivers = new LinkedList<>();
        rides = new LinkedList<>();
    }
    
	// Loads riders from a CSV file.
	// Returns true if loading succeeds; false otherwise.
    public boolean loadRidersFromCSV(String ridersFilePath) {
    try {
        Scanner scanner = new Scanner(new File(ridersFilePath));

        // Skip the header
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();

            if (line.trim().isEmpty()) {
                continue;
            }

            String[] parts = line.split(",");

            int riderId = Integer.parseInt(parts[0]);
            String name = parts[1];
            String email = parts[2];
            String phoneNumber = parts[3];
            String homeCity = parts[4];

            Rider rider = new Rider(
                riderId,
                name,
                email,
                phoneNumber,
                homeCity
            );

            riders.add(rider);
        }

        scanner.close();
        return true;

    } catch (FileNotFoundException e) {
        System.out.println("File not found: " + ridersFilePath);
    } catch (NumberFormatException e) {
        System.out.println("Invalid rider ID in CSV file.");
    } catch (ArrayIndexOutOfBoundsException e) {
        System.out.println("Invalid rider CSV format.");
    }
    return false;
}

	// Loads drivers from a CSV file.
	// Returns true if loading succeeds; false otherwise.
    public boolean loadDriversFromCSV(String driversFilePath) {
        try{
            Scanner scanner = new Scanner(new File(driversFilePath));

        // Skip the header
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();

            if (line.trim().isEmpty()) {
                continue;
            }

            String[] parts = line.split(",");

            int riderId = Integer.parseInt(parts[0]);
            String name = parts[1];
            String email = parts[2];
            String plate = parts[3];
            String homeCity@@ = parts[4];

            Rider rider = new Rider(
                riderId,
                name,
                email,
                phoneNumber,
                homeCity
            );

            riders.add(rider);
        }

        scanner.close();
        return true;

    } catch (FileNotFoundException e) {
        System.out.println("File not found: " + ridersFilePath);
    } catch (NumberFormatException e) {
        System.out.println("Invalid rider ID in CSV file.");
    } catch (ArrayIndexOutOfBoundsException e) {
        System.out.println("Invalid rider CSV format.");
    }
    return false;

        }
        
    }

	// Loads rides from a CSV file. All referenced riders and the assigned driver must already exist. It must enforce conflict rules
	// Returns true if loading succeeds; false otherwise.
    public boolean loadRidesFromCSV(String ridesFilePath) {

    }

    

    //Adds a rider to the system after enforcing uniqueness by ID. Returns true if added; false if duplicate ID or invalid
    public boolean addRider(IRider rider) {
        if (searchRiderById(rider.getId()) != null) {
            return false;
        }
        riders.add(rider);
        return true;
    }

    /**
     * Adds a driver to the system after enforcing uniqueness by ID and by
     * vehicle plate number (no two drivers may share the same plate).
     * Returns true if added; false if duplicate ID, duplicate plate, or invalid
     */
    public boolean addDriver(IDriver driver) {
        if (searchDriverById(driver.getId()) != null || searchDriverByVehiclePlate(driver.getVehiclePlate()) != null) {
            return false;
        }
        drivers.add(driver);
        return true;
    }

    //Searches for a rider by ID.
    public IRider searchRiderById(int riderId) {
        return riders.findById(riderId);
    }

    //Searches for a rider by email.
    IRider searchRiderByEmail(String email);

    //Searches for all riders whose full name exactly matches the given name.
    LinkedList<IRider> searchRidersByName(String fullName);

    //Searches for all riders from the specified home city.
    LinkedList<IRider> searchRidersByHomeCity(String homeCity);

    //Returns all riders stored in the system.
    LinkedList<IRider> getAllRiders();

    //Searches for a driver by ID.
    IDriver searchDriverById(int driverId);

    //Searches for a driver by vehicle plate.
    IDriver searchDriverByVehiclePlate(String vehiclePlate);

    //Searches for all drivers with the specified vehicle type.
    LinkedList<IDriver> searchDriversByVehicleType(VehicleType vehicleType);

    //Returns all drivers stored in the system.
    LinkedList<IDriver> getAllDrivers();

    /**
     * Removes a rider and performs cascade deletion of associated rides:
     * - All private rides involving the rider are deleted.
     * - The rider is removed from any shared rides.
     * - Shared rides with no remaining participants are deleted.
     * It returns true if the rider was found and removed; false otherwise
     */
    boolean removeRider(int riderId);

    /**
     * Removes a driver and performs cascade deletion of their assigned rides:
     * - All private rides assigned to the driver are deleted.
     * - All shared rides assigned to the driver are deleted.
     * It returns true if the driver was found and removed; false otherwise
     */
    boolean removeDriver(int driverId);

    /**
     * Schedules a private ride for one rider with one driver.
     * Requirements:
     * - The rider must exist.
     * - The driver must exist.
     * - The ride must not conflict with the rider's current schedule.
     * - The ride must not conflict with the driver's current schedule.
     * It returns true if scheduled; false otherwise
     */
    boolean schedulePrivateRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, int riderId, int driverId);

    /**
     * Schedules a shared ride for multiple riders with one driver.
     * Requirements:
     * - All riders must exist.
     * - The driver must exist.
     * - The ride must not conflict with any listed rider's schedule.
     * - The ride must not conflict with the driver's current schedule.
     * It returns true if scheduled; false otherwise
     */
    boolean scheduleSharedRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, int[] riderIds, int driverId);

    //Searches for all rides whose pickup location matches the given location (may return multiple if pickup locations repeat).
    LinkedList<IRide> searchRidesByPickupLocation(String pickupLocation);

    //Searches for all rides that involve a rider with the given name.
    LinkedList<IRide> searchRidesByRiderName(String riderName);

    //Returns all riders registered on a shared ride identified by its pickup location.
    LinkedList<IRider> getSharedRideParticipants(String pickupLocation);

    //Returns all rides (private and shared) alphabetically ordered by pickup location.
    LinkedList<IRide> getAllRidesAlphabetically();
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 