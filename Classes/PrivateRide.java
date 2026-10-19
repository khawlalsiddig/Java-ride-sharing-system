public class PrivateRide extends Ride implements IPrivateRide {
  private IRider rider;

    public PrivateRide(String pickupLocation, IDateTime pickupTime, 
                    IDateTime dropoffTime, String dropoffLocation,
                    IDriver driver, IRider rider) {

        super(pickupLocation, pickupTime, dropoffTime, dropoffLocation, driver);

        if (rider == null) {
            throw new IllegalArgumentException("A private ride must have a rider.");
        }
        this.rider = rider;
    }

    public IRider getRider() { return rider; }

     public void setRider(IRider rider) {
        if (rider == null) {
            throw new IllegalArgumentException("A private ride must have a rider.");
        }
        this.rider = rider;
    }

     public boolean hasRider(int riderId) {
        return rider.getId() == riderId;
    }

    public String toString() {
    return super.toString()
            + "\n" + "Type: Private"
            + "\n" + "Rider: " + rider.getName() + " (ID " + rider.getId() + ")";
    }
}