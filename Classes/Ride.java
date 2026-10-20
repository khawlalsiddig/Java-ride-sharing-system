public abstract class Ride implements IRide  {

    private static int nextRideId = 1;      // shared counter: next ID to hand out
     private final int rideId;               // unique internal ride ID (immutable)
    private String pickupLocation;
    private String dropoffLocation;
    private final IDateTime pickupTime;     // immutable
    private final IDateTime dropoffTime;    // immutable
    private IDriver driver;


    protected Ride(String pickupLocation, IDateTime pickupTime,
                   IDateTime dropoffTime, String dropoffLocation, IDriver driver) {
        checkLocation(pickupLocation, "Pickup location");
        checkLocation(dropoffLocation, "Drop-off location");
        if (pickupTime == null || dropoffTime == null) {
            throw new IllegalArgumentException("Pickup and drop-off times are required.");
        }
        if (driver == null) {
            throw new IllegalArgumentException("A ride must have a driver.");
        }
        this.rideId = nextRideId++ ;
        this.pickupLocation = pickupLocation;
        this.pickupTime = pickupTime;
        this.dropoffTime = dropoffTime;
        this.dropoffLocation = dropoffLocation;
        this.driver = driver;
    }

    // Throws IllegalArgumentException if the location is null or blank.
    private static void checkLocation(String location, String str) {
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException(str + " cannot be empty.");
        }
    }

//Getters
    
    public int getRideId() { return rideId; }

    
    public String getPickupLocation() { return pickupLocation; }

    public IDateTime getPickupTime() { return pickupTime; }

    public String getDropoffLocation() { return dropoffLocation; }
  
    public IDateTime getDropoffTime() { return dropoffTime; }

    public IDriver getDriver() { return driver; }
    
 //Setters

    public void setPickupLocation(String pickupLocation) {
        checkLocation(pickupLocation, "Pickup location");
        this.pickupLocation = pickupLocation;
    }

    public void setDropoffLocation(String dropoffLocation) {
        checkLocation(dropoffLocation, "Drop-off location");
        this.dropoffLocation = dropoffLocation;
    }
   
    
    public void setDriver(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("A ride must have a driver.");
        }
        this.driver = driver;
    }


    public abstract boolean hasRider(int riderId);

    public int compareTo(IRide other) {
        return pickupLocation.compareToIgnoreCase(other.getPickupLocation());
    }

    
    public String toString() {
        return "Ride #" + rideId
                + "\n" + pickupLocation + " (" + pickupTime.format() + ")"
                + " -> " + dropoffLocation + " (" + dropoffTime.format() + ")"
                + "\n" + "Driver: " + driver.getName() + " (ID " + driver.getId() + ")";
    }
}