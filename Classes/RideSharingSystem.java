
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class RideSharingSystem implements IRideSharingSystem {
    private RiderList riders;
    private DriverList drivers;
    private RideList rides;

    public RideSharingSystem() {
        riders = new RiderList();
        drivers = new DriverList();
        rides = new RideList();
    }
    
	// Loads riders from a CSV file.
	// Returns true if loading succeeds; false otherwise.
    public boolean loadRidersFromCSV(String ridersFilePath) {
        try (Scanner scanner = new Scanner(new File(ridersFilePath))) {
            if (scanner.hasNextLine()) scanner.nextLine(); // header
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",", -1);
                if (parts.length < 5) return false;
                int riderId = Integer.parseInt(parts[0].trim());
                IRider rider = new Rider(riderId, parts[1].trim(), parts[2].trim(),
                        parts[3].trim(), parts[4].trim());
                if (!addRider(rider)) return false;
            }
            return true;
        } catch (FileNotFoundException | NumberFormatException e) {
            return false;
        }
    }

	// Loads drivers from a CSV file.
	// Returns true if loading succeeds; false otherwise.
    public boolean loadDriversFromCSV(String driversFilePath) {
    try (Scanner scanner = new Scanner(new File(driversFilePath))) {
        if (scanner.hasNextLine()) scanner.nextLine();   // header

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) continue;

            String[] parts = line.split(",", -1);
            if (parts.length < 5) return false;
            int driverId = Integer.parseInt(parts[0].trim());
            String name = parts[1].trim();
            String email = parts[2].trim();
            String plate = parts[3].trim();
            VehicleType type = VehicleType.valueOf(parts[4].trim().toUpperCase());

            IDriver driver = new Driver(driverId, name, email, plate, type);
            if (!addDriver(driver)) return false;   // duplicate ID or plate
        }
        return true;

    } catch (FileNotFoundException | NumberFormatException e) {
            return false;
        }
}
        

	// Loads rides from a CSV file. All referenced riders and the assigned driver must already exist. It must enforce conflict rules
	// Returns true if loading succeeds; false otherwise.
    public boolean loadRidesFromCSV(String ridesFilePath) {
        try (Scanner scanner = new Scanner(new File(ridesFilePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",", -1);
                if (parts.length < 6) return false;
                String pickupLocation = parts[0].trim();
                IDateTime pickupTime = new DateTime(parts[1].trim());
                IDateTime dropoffTime = new DateTime(parts[2].trim());
                String dropoffLocation = parts[3].trim();
                int riderId = Integer.parseInt(parts[4].trim());
                int driverId = Integer.parseInt(parts[5].trim());

                IRider rider = searchRiderById(riderId);
                IDriver driver = searchDriverById(driverId);
                if (rider == null || driver == null) return false;
// مشكلة ال constructor
                if (!schedulePrivateRide(pickupLocation, pickupTime, dropoffTime, dropoffLocation, riderId, driverId)) return false;
            }
            return true;
        } 
        catch (FileNotFoundException | NumberFormatException e) {
            return false;
        }
    }

    

    //Adds a rider to the system after enforcing uniqueness by ID. Returns true if added; false if duplicate ID or invalid
    public boolean addRider(IRider rider) {
        return riders.add(rider);
    }

    /**
     * Adds a driver to the system after enforcing uniqueness by ID and by
     * vehicle plate number (no two drivers may share the same plate).
     * Returns true if added; false if duplicate ID, duplicate plate, or invalid
     */
    public boolean addDriver(IDriver driver) {
        return drivers.add(driver);
    }    

    

    //Searches for a rider by ID.
    public IRider searchRiderById(int riderId) {
        return riders.findById(riderId);
    }

    //Searches for a rider by email.
    public IRider searchRiderByEmail(String email) {
        return riders.findByEmail(email);
    }

    //Searches for all riders whose full name exactly matches the given name.
    public LinkedList<IRider> searchRidersByName(String fullName) {
        return riders.findByName(fullName);
    }

    //Searches for all riders from the specified home city.
    public LinkedList<IRider> searchRidersByHomeCity(String homeCity) {
        return riders.findByHomeCity(homeCity);
    }

    //Returns all riders stored in the system.
    public LinkedList<IRider> getAllRiders() {
        return riders;
    }

    //Searches for a driver by ID.
    public IDriver searchDriverById(int driverId) {
        return drivers.findById(driverId);
    }

    //Searches for a driver by vehicle plate.
    public IDriver searchDriverByVehiclePlate(String vehiclePlate) {
        return drivers.findByVehiclePlate(vehiclePlate);

    }

    //Searches for all drivers with the specified vehicle type.
    public LinkedList<IDriver> searchDriversByVehicleType(VehicleType vehicleType) { return null; }

    //Returns all drivers stored in the system.
    public LinkedList<IDriver> getAllDrivers() {
        return drivers;
    }

    /**
     * Removes a rider and performs cascade deletion of associated rides:
     * - All private rides involving the rider are deleted.
     * - The rider is removed from any shared rides.
     * - Shared rides with no remaining participants are deleted.
     * It returns true if the rider was found and removed; false otherwise
     */
    public boolean removeRider(int riderId) { return false; }

    /**
     * Removes a driver and performs cascade deletion of their assigned rides:
     * - All private rides assigned to the driver are deleted.
     * - All shared rides assigned to the driver are deleted.
     * It returns true if the driver was found and removed; false otherwise
     */
    public boolean removeDriver(int driverId) { return false; }

    /**
     * Schedules a private ride for one rider with one driver.
     * Requirements:
     * - The rider must exist.
     * - The driver must exist.
     * - The ride must not conflict with the rider's current schedule.
     * - The ride must not conflict with the driver's current schedule.
     * It returns true if scheduled; false otherwise
     */
    public boolean schedulePrivateRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, int riderId, int driverId) { return false; }

    /**
     * Schedules a shared ride for multiple riders with one driver.
     * Requirements:
     * - All riders must exist.
     * - The driver must exist.
     * - The ride must not conflict with any listed rider's schedule.
     * - The ride must not conflict with the driver's current schedule.
     * It returns true if scheduled; false otherwise
     */
    public boolean scheduleSharedRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, int[] riderIds, int driverId);


    //Searches for all rides whose pickup location matches the given location (may return multiple if pickup locations repeat).
    public LinkedList<IRide> searchRidesByPickupLocation(String pickupLocation){
         return rides.findByPickupLocation(pickupLocation);
    }

    //Searches for all rides that involve a rider with the given name.
    public LinkedList<IRide> searchRidesByRiderName(String riderName){
        return rides.findByRiderName(riderName);
    }

    //Returns all riders registered on a shared ride identified by its pickup location.
    public LinkedList<IRider> getSharedRideParticipants(String pickupLocation){
        LinkedList<IRide> matches = rides.findByPickupLocation(pickupLocation);

         if (!matches.empty()) {
            matches.findFirst();
            while (true) {
                if (matches.retrieve() instanceof ISharedRide) {
                    return ((ISharedRide) matches.retrieve()).getParticipants();
                }
                if (matches.last()) 
                    break;
                matches.findNext();
            }
        }
        return new LinkedList<IRider>();           // nothing found
    
    }

    //Returns all rides (private and shared) alphabetically ordered by pickup location.
    LinkedList<IRide> getAllRidesAlphabetically(){
         return rides.getAllAlphabetically();
    }
}
