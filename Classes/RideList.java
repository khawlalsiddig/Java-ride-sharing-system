public class RideList implements IRideList {
  private final LinkedList<IRide> rides = new LinkedList<IRide>();
  private int count = 0;
  
    //Adds a ride and maintains alphabetical ordering by pickup location.
    public boolean addRide(IRide ride) {
    if (ride == null)
        return false;

    if (rides.empty()) {
        rides.insert(ride);
        count++;
        return true;
    }

    rides.findFirst();                       

    while (true) {
        if (ride.compareTo(rides.retrieve()) < 0) {  // new ride goes before this one
            IRide old = rides.retrieve();
            rides.update(ride);
            rides.insert(old);
            count++;
            return true;
        }
        if (rides.last())
            break;
        rides.findNext();
    }
    rides.insert(ride);                      // new ride is >= everything: append
    count++;
    return true;
}

    //Removes a ride by its unique ride ID.
    public boolean removeRideById(int rideId) {
        if (rides.empty())
            return false;
        
        rides.findFirst();
        while (true) {
            if (rides.retrieve().getRideId() == rideId) {
                rides.remove();
                count--;
                return true;
            }
            if (rides.last()) 
                break;
            rides.findNext();
        }
        return false;
    }


    //Returns all rides alphabetically ordered by pickup location.
    public LinkedList<IRide> getAllAlphabetically() {
        LinkedList<IRide> list = new LinkedList<IRide>(); //Return a copy of the LinkedList rides
        if (rides.empty())
            return list;

        rides.findFirst();
        while (true) {
            list.insert(rides.retrieve());   // insert after current
            if (rides.last()) 
                break;
            rides.findNext();
            }
        
        return list;
    }

     //Returns all rides whose pickup location matches the given location.
    public LinkedList<IRide> findByPickupLocation(String pickupLocation) {
        LinkedList<IRide> list = new LinkedList<IRide>();
        if (pickupLocation == null || rides.empty()) 
            return list;
        

        rides.findFirst();
        while (true) {
            IRide ride = rides.retrieve();
            if (ride.getPickupLocation().equalsIgnoreCase(pickupLocation)) {
                list.insert(ride);
            }
            if (rides.last()) 
                break;
            rides.findNext();
        }
        return list;
    }

    //Returns all rides that involve a rider with the given full name.
    public LinkedList<IRide> findByRiderName(String riderFullName) {
        LinkedList<IRide> list = new LinkedList<IRide>();
        if (riderFullName == null || rides.empty())
            return list;
        
        rides.findFirst();
        while (true) {
            IRide ride = rides.retrieve();
            if (hasRiderNamed(ride, riderFullName)) { //hasRiderNamed is a Private method that checks
                list.insert(ride);
            }
            if (rides.last()) 
                break;
            rides.findNext();
        }
        return list;
    }

   //Private method that return true if the Rider exits in Ride
    private boolean hasRiderNamed(IRide ride, String fullName) {
        if (ride instanceof IPrivateRide) { //For private Ride
            return ((IPrivateRide) ride).getRider().getName().equals(fullName);
        }
        
        if (ride instanceof SharedRide){  //For Shared Ride
        LinkedList<IRider> riders = ((ISharedRide) ride).getParticipants();
        if (riders.empty())
            return false;


        riders.findFirst();
        while (true) {
            if (riders.retrieve().getName().equals(fullName)) 
                return true;
                
            if (riders.last()) 
                break;
            
            riders.findNext();
            }
        }
        return false;
    
    }

    //return number of rides stored.
    public int size() {
        return count;
    }
}



