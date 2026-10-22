public abstract class Person implements IPerson {
    private final int id;
    private String name;
    private String phoneNumber;
    private LinkedList<IRide> rideHistory;

    //Person constructor
    protected Person(int id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        setPhoneNumber(phoneNumber); //PHONE CHECKING HERE!!!!!
        this.rideHistory = new LinkedList<>();
    }
    
    //return the unique ID of this person.
    public int getId(){
        return id;
    }
    //return the full name of this person.
    public String getName(){
        return name;
    }
    //Sets the full name of this person.
    public void setName(String name){
    this.name = name;
}
    //return the phone number of this person.
    public String getPhoneNumber(){
        return phoneNumber;
    }
    /**
    * Sets the phone number of this person.
    * The phone number must be exactly 10 digits (numeric characters only,
    * e.g., "0551234567"). Implementations must validate the format and
    * throw IllegalArgumentException if it does not match.
    */
    public void setPhoneNumber(String phoneNumber) {
        if(phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
            throw new IllegalArgumentException("The phone number must be 10 digits!");
        }
        this.phoneNumber = phoneNumber;
    }
    //return the list of rides associated with this person (as a rider, or as an assigned driver).
     public LinkedList<IRide> getRideHistory() {
        return rideHistory;
    }

    // Returns a formatted string describing this person.
    @Override
    public String toString() {
        return "Person ID =" + id + ", Name='" + name + "', PhoneNumber='" + phoneNumber + "'";
    }
}