public class RideList implements IRideList {
  private final LinkedList<IRide> rides = new LinkedList<IRide>();
  private int count = 0;
  

    public boolean addRide (IRide ride){
   
    if (ride == null || hasRide(ride.getRideId()))
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

    


}



