
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
                if (rider == null || driver == null) 
                    return false;
// مشكلة ال constructor
                if (!schedulePrivateRide(pickupLocation, pickupTime, dropoffTime, dropoffLocation, riderId, driverId)) 
                    return false;
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
        return riders.getAll();
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
        return drivers.getAll();
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
    public boolean schedulePrivateRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, int riderId, int driverId) {
           //pickupTime before dropoffTime
        if(pickupTime == null && dropoffTime == null && pickupTime.compareTo(dropoffTime) > 0)
            return false;
           //rider and driver existence checks frist
        IRider rider = riders.findById(riderId);       
        IDriver driver = drivers.findById(driverId);
        if (rider == null || driver == null) 
            return false;
            //The ride must not conflict with the rider's current schedule.
            // The ride must not conflict with the driver's current schedule.
        if (hasPrivateConflict(riderId,driverId,pickupTime,dropoffTime))
            return false;

        PrivateRide ride;
        try {   // the constructor rejects blank locations, before an ID is used
            ride = new PrivateRide(pickupLocation, pickupTime, dropoffTime,dropoffLocation, driver, rider);
        } catch (IllegalArgumentException e) {
            return false;
        }

        rides.addRide(ride);
        //Sholud add to the History of driver or rider but I don't know how!!!!
        return true;
    
    }
    /**
     * Schedules a shared ride for multiple riders with one driver.
     * Requirements:
     * - All riders must exist.
     * - The driver must exist.
     * - The ride must not conflict with any listed rider's schedule.
     * - The ride must not conflict with the driver's current schedule.
     * It returns true if scheduled; false otherwise
     */
    public boolean scheduleSharedRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, int[] riderIds, int driverId){
           //pickupTime before dropoffTime
        if(riderIds == null || riderIds.length <=1 || pickupTime == null && dropoffTime == null && pickupTime.compareTo(dropoffTime) > 0)
            return false;

           //riders and driver existence checks frist
        IRider[] ridersList = new IRider[riderIds];

        for(int i=0; i<riderIds.length; i++){
        IRider rider = riders.findById(riderIds[i]);
            if (rider == null) //All riders must exist
                return false;
            ridersList[i] =rider;

            for (int j = 0; j < i; j++) {
            if (riderIds[j] == riderIds[i]) //Same rider listed twice
                return false; }
          }       
      
        if (ridersList.length<=1) //One rider only is not allowed
            return false;

        IDriver driver = drivers.findById(driverId);
        if ( driver == null) //Driver must exist
            return false;

        // The ride must not conflict with any listed rider's schedule..
        // The ride must not conflict with the driver's current schedule.
        if (hasSharedConflict(riderIds, driverId,pickupTime, dropoffTime)) 
            return false;

        SharedRide ride;
        try {
            ride = new SharedRide(pickupLocation, pickupTime, dropoffTime, dropoffLocation, driver);
        } catch (IllegalArgumentException e) {
            return false;
        }
        for (int i = 0; i < ridersList.length; i++) {
            ride.addParticipant(ridersList[i]);
            //Should I add to history?
        }
        rides.addRide(ride);
        //Should I add to history?
        return true;

    }

    //Usefull private methods for Conflict detection 
    //---------------------------------------------------------------------------
    private boolean overlaps(IDateTime pickOld, IDateTime dropOld, IDateTime pickNew, IDateTime dropNew) {
        return pickOld.compareTo(dropNew) < 0 && PickNew.compareTo(dropOld)< 0;
    }

    private boolean hasPrivateConflict(int riderId, int driverId,IDateTime pickup, IDateTime dropoff) {
    LinkedList<IRide> all = rides.getAllAlphabetically();
    if (all.empty()) 
        return false;
    
    all.findFirst();

    while (true) {
        IRide existing = all.retrieve();

        if (overlaps(pickup, dropoff,existing.getPickupTime(),existing.getDropoffTime())) {
            if (existing.getDriver().getId() == driverId || existing.hasRider(riderId))
                return true;
        }

        if (all.last()) 
            break;

        all.findNext();
    }

    return false;
}

private boolean hasSharedConflict(int[] riderIds, int driverId,IDateTime pickup, IDateTime dropoff){
LinkedList<IRide> all = rides.getAllAlphabetically();

    if (all.empty()) 
        return false;

    all.findFirst();
    while (true) { //Going through the Rides to check for conflicts
        IRide existing = all.retrieve();

            if (overlaps(pickup, dropoff,existing.getPickupTime(),existing.getDropoffTime())) {
                if (existing.getDriver().getId() == driverId )
                    return true;

                for (int i =0; i<riderIds.length;i++){//Going through the riderIds
                    if (existing.hasRider(riderIds[i]))
                        return true;
                }
            }

            if (all.last()) 
            break;

        all.findNext();
     }
    

    return false;
}

//---------------------------------------------------------------------------

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
    public LinkedList<IRide> getAllRidesAlphabetically(){
         return rides.getAllAlphabetically();
    }
}
