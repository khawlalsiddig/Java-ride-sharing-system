public class RideList implements IRideList {
  private final LinkedList<IRide> rides = new LinkedList<IRide>();
  private int count = 0;
  

    public boolean addRide (IRide ride){
   
    if (ride == null || ride.hasRider(ride.getRideId()))
        return false;
    
    if (rides.empty()){
        rides.insert(ride);
        count++;
        return true;
    } 
    
    while (true) {
        if (ride.compareTo(rides.retrieve()) < 0) { //If the new should be before the current Swap the current with the new and insert the old after it
            IRide old = rides.retrieve();
            rides.update(ride);
            rides.insert(old);
            count++;
            return true;}

        if (rides.last()) 
            break;

        rides.findNext(); //Keep looking for one that the new ride should be before
        }
        rides.insert(ride);          // new ride is >= everything: append at the end
        count++;
        return true;
    }
 
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

    public int size() {
        return count;
    }
}



