public class DriverList implements IDriverList {

    private final LinkedList<IDriver> drivers = new LinkedList<IDriver>();

    public boolean add(IDriver driver) {
        if (driver == null)
            return false;

        if (drivers.empty()) {
            drivers.insert(driver);
            return true;
        }
        drivers.findFirst();
        
        while (!drivers.last() && drivers.retrieve().getId() < driver.getId())
            drivers.findNext();
        int curId = drivers.retrieve().getId();
        if (curId == driver.getId()) return false;
        if (curId < driver.getId()) {
            // current is the last node and smaller: append
            drivers.insert(driver);
        } else {
            // current is bigger: put driver here, push old one after it
            IDriver old = drivers.retrieve();
            drivers.update(driver);
            drivers.insert(old);
        }
        return true;
    }
    public IDriver findById(int driverId) {
        if (drivers.empty())
            return null;

        drivers.findFirst();
        while (true) {
            IDriver driver = drivers.retrieve();
            if (driver.getId() == driverId)
                return driver;

            if (drivers.last())
                break;

            drivers.findNext();
        }

        return null;
    }

    public LinkedList<IDriver> findByName(String fullName) {
        LinkedList<IDriver> result = new LinkedList<IDriver>();

        if (drivers.empty())
            return result;

        drivers.findFirst();

        while (true) {
            if (drivers.retrieve().getName().equals(fullName))
                result.insert(drivers.retrieve());

            if (drivers.last())
                break;

            drivers.findNext();
        }

        return result;
    }

    public IDriver findByVehiclePlate(String vehiclePlate) {
        if (drivers.empty())
            return null;

        drivers.findFirst();

        while (true) {
            if (drivers.retrieve().getVehiclePlate().equals(vehiclePlate))
                return drivers.retrieve();

            if (drivers.last())
                break;

            drivers.findNext();
        }

        return null;
    }

    public LinkedList<IDriver> findByVehicleType(VehicleType vehicleType) {
        LinkedList<IDriver> result = new LinkedList<IDriver>();

        if (drivers.empty())
            return result;

        drivers.findFirst();

        while (true) {
            if (drivers.retrieve().getVehicleType() == vehicleType)
                result.insert(drivers.retrieve());

            if (drivers.last())
                break;

            drivers.findNext();
        }

        return result;
    }

    public LinkedList<IDriver> getAll() {
        // Return a copy of the list of drivers
        LinkedList<IDriver> copyDrivers = new LinkedList<IDriver>();
        if (!drivers.empty()) {
            drivers.findFirst();
            while (!drivers.last()) {
                copyDrivers.insert(drivers.retrieve());
                drivers.findNext();
            }
            copyDrivers.insert(this.drivers.retrieve());
        }
        return copyDrivers;
    }

    public boolean removeById(int driverId) {
        if (drivers.empty())
            return false;

        drivers.findFirst();

        while (true) {
            if (drivers.retrieve().getId() == driverId) {
                drivers.remove();
                return true;
            }

            if (drivers.last())
                break;

            drivers.findNext();
        }

        return false;
    }

    public boolean removeByVehiclePlate(String vehiclePlate) {
        if (drivers.empty())
            return false;

        drivers.findFirst();

        while (true) {
            if (drivers.retrieve().getVehiclePlate().equals(vehiclePlate)) {
                drivers.remove();
                return true;
            }

            if (drivers.last())
                break;

            drivers.findNext();
        }

        return false;
    }

    public int removeByName(String fullName) {
        int count = 0;

        if (drivers.empty())
            return 0;

        drivers.findFirst();

        while (true) {
            if (drivers.retrieve().getName().equals(fullName)) {
                drivers.remove();
                count++;

                if (drivers.empty())
                    break;
            } else {
                if (drivers.last())
                    break;

                drivers.findNext();
            }
        }

        return count;
    }

    public int removeByVehicleType(VehicleType vehicleType) {
        int count = 0;

        if (drivers.empty())
            return 0;

        drivers.findFirst();

        while (true) {
            if (drivers.retrieve().getVehicleType() == vehicleType) {
                drivers.remove();
                count++;

                if (drivers.empty())
                    break;
            } else {
                if (drivers.last())
                    break;

                drivers.findNext();
            }
        }

        return count;
    }

    // Return the number of drivers
    public int size() {
        int count = 0;

        if (drivers.empty())
            return 0;

        drivers.findFirst();

        while (true) {
            count++;

            if (drivers.last())
                break;

            drivers.findNext();
        }

        return count;
    }



}