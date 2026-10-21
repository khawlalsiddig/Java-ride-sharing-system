
import java.io.File;
import java.io.FileNotFoundException;
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
                if (!addRider(rider)) continue; // skip duplicates or invalid entries
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
            if (!addDriver(driver)) continue;   // skip duplicates or invalid entries
        }
        return true;

    } catch (FileNotFoundException | NumberFormatException e) {
            return false;
        }
}
        

public boolean loadRidesFromCSV(String ridesFilePath) {
    try (Scanner scanner = new Scanner(new File(ridesFilePath))) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split(",", -1);
            if (parts.length < 7) return false;

            String type = parts[0].trim().toUpperCase();
            String pickupLocation = parts[1].trim();
            IDateTime pickupTime = new DateTime(parts[2].trim());// يا بارسينغ يا كونستركتر
            IDateTime dropoffTime = new DateTime(parts[3].trim());
            String dropoffLocation = parts[4].trim();
            int driverId = Integer.parseInt(parts[5].trim());

            if (type.equals("PRIVATE")) {
                // parts[6] is a single rider id
                int riderId = Integer.parseInt(parts[6].trim());
                schedulePrivateRide(pickupLocation, pickupTime, dropoffTime,
                        dropoffLocation, riderId, driverId);
            } else if (type.equals("SHARED")) {
                // parts[6] is several ids separated by ';'
                String[] idsStr = parts[6].trim().split(";");
                int[] riderIds = new int[idsStr.length];
                for (int i = 0; i < idsStr.length; i++)
                    riderIds[i] = Integer.parseInt(idsStr[i].trim());
                scheduleSharedRide(pickupLocation, pickupTime, dropoffTime,
                        dropoffLocation, riderIds, driverId);
            } else {
                return false; // invalid ride type
            }

        }
        return true;
    } catch (FileNotFoundException | RuntimeException e) {
        return false;
    }
}

    
    public boolean addRider(IRider rider) {
        return riders.add(rider);
    }

    
    public boolean addDriver(IDriver driver) {
        return drivers.add(driver);
    }    

    
    public IRider searchRiderById(int riderId) {
        return riders.findById(riderId);
    }


    public IRider searchRiderByEmail(String email) {
        return riders.findByEmail(email);
    }


    public LinkedList<IRider> searchRidersByName(String fullName) {
        return riders.findByName(fullName);
    }


    public LinkedList<IRider> searchRidersByHomeCity(String homeCity) {
        return riders.findByHomeCity(homeCity);
    }


    public LinkedList<IRider> getAllRiders() {
        return riders.getAll();
    }


    public IDriver searchDriverById(int driverId) {
        return drivers.findById(driverId);
    }


    public IDriver searchDriverByVehiclePlate(String vehiclePlate) {
        return drivers.findByVehiclePlate(vehiclePlate);
    }


    public LinkedList<IDriver> searchDriversByVehicleType(VehicleType vehicleType) { 
        return drivers.findByVehicleType(vehicleType);
     }


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
    public boolean removeRider(int riderId) {
    IRider rider = riders.findById(riderId);
    if (rider == null) return false;

    // copy the rider's history first, because we edit the lists while looping
    LinkedList<IRide> history = rider.getRideHistory();
    if (!history.empty()) {
        history.findFirst();
        while (true) {
            IRide ride = history.retrieve();

            if (ride instanceof ISharedRide) {
                ISharedRide shared = (ISharedRide) ride;
                shared.removeParticipantById(riderId);          // rider leaves the shared ride
                if (shared.isEmpty()) {                         // nobody left -> delete the ride
                    removeFromHistory(shared.getDriver(), shared.getRideId());
                    rides.removeRideById(shared.getRideId());
                }
            } else {                                            // private ride -> delete it
                removeFromHistory(ride.getDriver(), ride.getRideId());
                rides.removeRideById(ride.getRideId());
            }

            if (history.last()) break;
            history.findNext();
        }
    }
    return riders.removeById(riderId);
}
// Removes the ride with the given ID from a person's ride history (ride IDs are unique)
    private void removeFromHistory(IPerson person, int rideId) {
        LinkedList<IRide> history = person.getRideHistory();
        if (history.empty()) return;

        history.findFirst();
        while (true) {
            if (history.retrieve().getRideId() == rideId) {
                history.remove();
                return;
            }
            if (history.last()) return;
            history.findNext();
        }
    }



    /**
     * Removes a driver and performs cascade deletion of their assigned rides:
     * - All private rides assigned to the driver are deleted.
     * - All shared rides assigned to the driver are deleted.
     * It returns true if the driver was found and removed; false otherwise
     */
    public boolean removeDriver(int driverId) { return false;     }



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
        if(pickupTime == null || dropoffTime == null || pickupTime.compareTo(dropoffTime) > 0)
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
        addToHistory(rider, ride);
        addToHistory(driver, ride);
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
        if(riderIds == null || riderIds.length ==0 || pickupTime == null || dropoffTime == null || pickupTime.compareTo(dropoffTime) > 0)
            return false;

           //riders and driver existence checks frist
        IRider[] ridersList = new IRider[riderIds.length];

        for(int i=0; i<riderIds.length; i++){
        IRider rider = riders.findById(riderIds[i]);
            if (rider == null) //All riders must exist
                return false;
            ridersList[i] =rider;

            for (int j = 0; j < i; j++) {// لي ؟
            if (riderIds[j] == riderIds[i]) //Same rider listed twice
                return false; }
          }       
      
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
            addToHistory(ridersList[i], ride);
        }
        rides.addRide(ride);
        addToHistory(driver, ride);
        return true;

    }

    //Usefull private methods for Conflict detection 
    //---------------------------------------------------------------------------
    private boolean overlaps(IDateTime pickOld, IDateTime dropOld, IDateTime pickNew, IDateTime dropNew) {
        return pickOld.compareTo(dropNew) < 0 && pickNew.compareTo(dropOld)< 0;
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

private void addToHistory(IPerson person, IRide ride) {
        LinkedList<IRide> history = person.getRideHistory();
        if (!history.empty()) {
            history.findFirst();
            while (!history.last()) {
                history.findNext();
            }
        }
        history.insert(ride);
    }



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
