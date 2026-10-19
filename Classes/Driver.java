public class Driver extends Person implements IDriver {
    private String vehiclePlate;
    private VehicleType vehicleType;

    public Driver(int id, String name, String phoneNumber, String vehiclePlate, VehicleType vehicleType) {
        super(id, name, phoneNumber);
        this.vehiclePlate = vehiclePlate;
        this.vehicleType = vehicleType;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }
    public void setVehiclePlate(String vehiclePlate) {
        if(vehiclePlate == null || !vehiclePlate.matches("[A-Z]{3}[0-9]{4}")) {
            throw new IllegalArgumentException("The vehicle plate must be in the format of 3 uppercase letters followed by 4 digits!");
        }
        this.vehiclePlate = vehiclePlate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }
    public void setVehicleType(VehicleType vehicleType) {
        if(vehicleType == null) {
            throw new IllegalArgumentException("Vehicle type cannot be null.");
        }
        this.vehicleType = vehicleType;
    }
    

    public int compareTo(IDriver other) {
        return Integer.compare(this.getId(), other.getId());
    }
    
}
