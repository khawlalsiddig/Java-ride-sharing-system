abstract class Person implements IPerson {
    private int id;
    private String name;
    private String phoneNumber;
    private LinkedList<IRide> rideHistory;
    

    protected Person(int id, String name, String phoneNumber, LinkedList<IRide> rideHistory) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.rideHistory = new LinkedList<IRide>();
    }
    
    //return the unique ID of this person.
int getId(){
return id;
}
//return the full name of this person.
String getName(){
return name;
}
//Sets the full name of this person.
void setName(String name){
    this.name = name;
}
//return the phone number of this person.
String getPhoneNumber(){
return phoneNumber;
}
/**
* Sets the phone number of this person.
* The phone number must be exactly 10 digits (numeric characters only,
* e.g., "0551234567"). Implementations must validate the format and
* throw IllegalArgumentException if it does not match.
*/
void setPhoneNumber(String phoneNumber){
    this.phoneNumber = phoneNumber;
}
//return the list of rides associated with this person (as a rider, or as an assigned driver).
LinkedList<IRide> getRideHistory() {
    return null;
}
// Returns a formatted string describing this person.
@Override
 String toString();
}