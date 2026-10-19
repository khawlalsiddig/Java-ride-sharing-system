public class SharedRide extends Ride implements ISharedRide{

 private LinkedList<IRider> participants;

   public SharedRide(String pickupLocation, IDateTime pickupTime,
                      IDateTime dropoffTime, String dropoffLocation,
                       IDriver driver) {
        super(pickupLocation, pickupTime, dropoffTime, dropoffLocation, driver);
        this.participants = new LinkedList<IRider>();
    }

    public LinkedList<IRider> getParticipants(){
    return participants;
   }

    public boolean addParticipant(IRider rider) {
        if (rider == null || hasRider(rider.getId()))
            return false;
        
        if (!participants.empty()) {          // move current to the last node first
            participants.findFirst();
            while (!participants.last())
                participants.findNext();
        }
        participants.insert(rider);          
        return true;
    }

    public boolean removeParticipantById(int riderId){
        if (participants.empty())
            return false;
        participants.findFirst();
        while (!participants.last()){
            if (participants.retrieve().getId() == riderId){
                participants.remove();
                return true;
            }
            participants.findNext();
        }
        if (participants.retrieve().getId() == riderId){
                participants.remove();
                return true;
            }
        return false;



    }


   public boolean hasRider(int riderId){
    if (participants.empty())
        return false;

    participants.findFirst();
    while (!participants.last()){
        if (participants.retrieve().getId() == riderId)
            return true;
        participants.findNext();
   }
   if (participants.retrieve().getId() == riderId)
            return true;

return false;
}

    public boolean isEmpty() {
        return participants.empty();
    }

    //With StringBuilder to avoid creating multiple String objects during concatenation
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("\nType: Shared");
        sb.append("\nParticipants: ");
        if (participants.empty()) {
            sb.append("None");
        } else {
            participants.findFirst();
            while (!participants.last()) {
                IRider rider = participants.retrieve();
                sb.append(rider.getName()).append(" (ID ").append(rider.getId()).append("), ");
                participants.findNext();
            }
            IRider lastRider = participants.retrieve();
            sb.append(lastRider.getName()).append(" (ID ").append(lastRider.getId()).append(")");
        }
        return sb.toString();
    }
    //With concatenation
// public String toString() {
//     String result = super.toString() + "\n" + "Type: Shared" + "\n" + "Riders: ";
//     if (participants.empty()) {
//         return result + "none";
//     }
//     participants.findFirst();
//     while (true) {
//         IRider r = participants.retrieve();
//         result = result + r.getName() + " (ID " + r.getId() + ")";
//         if (participants.last()) break;
//         result = result + ", ";        // comma between riders, none after the last
//         participants.findNext();
//     }
//     return result;
// }

}