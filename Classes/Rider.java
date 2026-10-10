public class Rider implements IRider extends Person {
     private String email; 
 private String homeCity;

 public String getEmail() {
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
    return 0;
}