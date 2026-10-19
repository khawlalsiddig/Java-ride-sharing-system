
public class RiderList implements IRiderList {

    private LinkedList<IRider> riders;

    public RiderList() {
        riders = new LinkedList<IRider>();
    }


    // Add rider in ascending order by ID
    public boolean add(IRider rider) {
    if (rider == null) return false;
    if (riders.empty()) {
        riders.insert(rider);
        return true;
    }
    riders.findFirst();
    while (!riders.last() && riders.retrieve().getId() < rider.getId())
        riders.findNext();
    int curId = riders.retrieve().getId();
    if (curId == rider.getId()) return false;
    if (curId < rider.getId()) {
        // current is the last node and smaller: append
        riders.insert(rider);
    } else {
        // current is bigger: put rider here, push old one after it
        IRider old = riders.retrieve();
        riders.update(rider);
        riders.insert(old);
    }
    return true;
}

    // Find all riders with the given name
    public LinkedList<IRider> findByName(String fullName) {
        LinkedList<IRider> result = new LinkedList<IRider>();

        if (riders.empty())
            return result;

        riders.findFirst();

        while (true) {
            if (riders.retrieve().getName().equals(fullName))
                result.add(riders.retrieve());

            if (riders.last())
                break;

            riders.findNext();
        }

        return result;
    }

    // Find rider by email
    public IRider findByEmail(String email) {
        if (riders.empty())
            return null;

        riders.findFirst();

        while (true) {
            if (riders.retrieve().getEmail().equals(email))
                return riders.retrieve();

            if (riders.last())
                break;

            riders.findNext();
        }

        return null;
    }

    // Find rider by ID
    public IRider findById(int riderId) {
        if (riders.empty())
            return null;

        riders.findFirst();
        while (true) {
            IRider rider = riders.retrieve();
            if (rider.getId() == riderId)
                return rider;

            if (riders.last())
                break;

            riders.findNext();
        }

        return null;
    }

    // Find all riders from a home city
    public LinkedList<IRider> findByHomeCity(String homeCity) {
        LinkedList<IRider> result = new LinkedList<IRider>();

        if (riders.empty())
            return result;

        riders.findFirst();

        while (true) {
            if (riders.retrieve().getHomeCity().equals(homeCity))
                result.add(riders.retrieve());

            if (riders.last())
                break;

            riders.findNext();
        }

        return result;
    }

    // Return all riders
    public LinkedList<IRider> getAll() {
        return riders;
    }

    // Remove rider by ID
    public boolean removeById(int riderId) {
        if (riders.empty())
            return false;

        riders.findFirst();

        while (true) {
            if (riders.retrieve().getId() == riderId) {
                riders.remove();
                return true;
            }

            if (riders.last())
                break;

            riders.findNext();
        }

        return false;
    }

    // Remove rider by email
    public boolean removeByEmail(String email) {
        if (riders.empty())
            return false;

        riders.findFirst();

        while (true) {
            if (riders.retrieve().getEmail().equals(email)) {
                riders.remove();
                return true;
            }

            if (riders.last())
                break;

            riders.findNext();
        }

        return false;
    }

    // Remove all riders with the given name
    public int removeByName(String fullName) {
    int count = 0;

    if (riders.empty())
        return 0;

    riders.findFirst();

    while (true) {
        if (riders.retrieve().getName().equals(fullName)) {
            riders.remove();
            count++;

            if (riders.empty())
                break;
        } else {
            if (riders.last())
                break;

            riders.findNext();
        }
    }

    return count;
}

public int removeByHomeCity(String homeCity) {
    int count = 0;

    if (riders.empty())
        return 0;

    riders.findFirst();

    while (true) {
        if (riders.retrieve().getHomeCity().equals(homeCity)) {
            riders.remove();
            count++;

            if (riders.empty())
                break;
        } else {
            if (riders.last())
                break;

            riders.findNext();
        }
    }

    return count;
}

    // Return the number of riders
    public int size() {
        int count = 0;

        if (riders.empty())
            return 0;

        riders.findFirst();

        while (true) {
            count++;

            if (riders.last())
                break;

            riders.findNext();
        }

        return count;
    }
}
