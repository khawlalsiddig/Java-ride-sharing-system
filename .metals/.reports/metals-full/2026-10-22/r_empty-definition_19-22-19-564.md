error id: file:///C:/Users/rawan/OneDrive/Desktop/DS%20Project/Java-ride-sharing-system/Classes/Rider.java:java/lang/String#
file:///C:/Users/rawan/OneDrive/Desktop/DS%20Project/Java-ride-sharing-system/Classes/Rider.java
empty definition using pc, found symbol in pc: java/lang/String#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 327
uri: file:///C:/Users/rawan/OneDrive/Desktop/DS%20Project/Java-ride-sharing-system/Classes/Rider.java
text:
```scala
public class Rider extends Person implements IRider {
    private String email;
    private String homeCity;

 public Rider(int id, String name, String phoneNumber, String email, String homeCity) {
        super(id, name, phoneNumber);
        this.email = email;
        this.homeCity = homeCity;
    }

    public S@@tring getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomeCity() {
        return homeCity;
    }

    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    public int compareTo(IRider other) {
        return Integer.compare(this.getId(), other.getId());
    }

    public String toString() {
        return super.toString() + ", email='" + email + '\'' +
                ", homeCity='" + homeCity + '\'';
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: java/lang/String#