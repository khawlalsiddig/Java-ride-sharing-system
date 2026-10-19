error id: file:///C:/Users/rawan/OneDrive/Desktop/DS%20Project/Java-ride-sharing-system/Classes/RiderList.java:_empty_/IRider#
file:///C:/Users/rawan/OneDrive/Desktop/DS%20Project/Java-ride-sharing-system/Classes/RiderList.java
empty definition using pc, found symbol in pc: _empty_/IRider#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 1523
uri: file:///C:/Users/rawan/OneDrive/Desktop/DS%20Project/Java-ride-sharing-system/Classes/RiderList.java
text:
```scala
public class RiderList implements IRiderList {

    private LinkedList<IRider> riders;

    public RiderList() {
        riders = new LinkedList<IRider>();
    }

    // Inserts a rider into the list in sorted order by ID. If a rider with the same ID already exists, insertion fails.
    public boolean add(IRider rider) {
        if (rider == null) {
            return false;
        }
        // Empty list
        if (riders.empty()) {
            riders.insert(rider);
            return true;
        }
        riders.findFirst();
        // Move while current ID is smaller
        while (!riders.last() && riders.retrieve().getId() < rider.getId())
            riders.findNext();

        // Duplicate ID
        if (riders.retrieve().getId() == rider.getId())
            return false;
        // Insert after current
        riders.insert(rider);
        return true;
    }


    //Searches for a rider by ID.
    public IRider findById(int riderId) {
        riders.findFirst();
        while (!riders.last()) {
            if (riders.retrieve().getId() == riderId) {
                return riders.retrieve();
            }
            riders.findNext();
        }
        // Check last node
        if (riders.retrieve().getId() == riderId) {
            return riders.retrieve();
        }
        return null;
    }


    // Searches for all riders with the given full name.
    public LinkedList<IRider> findByName(String fullName) {
        LinkedList<IRid@@er> result = new LinkedList<IRider>();
        riders.findFirst();
        while (!riders.last()) {
            if (riders.retrieve().getName().equals(fullName)) {
                result.add(riders.retrieve());
            }
            riders.findNext();
        }
        // Check last node
        if (riders.retrieve().getName().equals(fullName)) {
            result.add(riders.retrieve());
        }
        return result;
    }
 

    //Searches for a rider by email address.
    public IRider findByEmail(String email) {
        riders.findFirst();
        while (!riders.last()) {
            if (riders.retrieve().getEmail().equals(email)) {
                return riders.retrieve();
            }
            riders.findNext();
        }
        // Check last node
        if (riders.retrieve().getEmail().equals(email))
            return riders.retrieve();
        return null;
    }

    
    //Returns all riders from the specified home city.
    public LinkedList<IRider> findByHomeCity(String homeCity) {
        LinkedList<IRider> result = new LinkedList<IRider>();
        riders.findFirst();
        while (!riders.last()) {
            if (riders.retrieve().getHomeCity().equals(homeCity)) {
                result.add(riders.retrieve());
            }
            riders.findNext();
        }
        // Check last node
        if (riders.retrieve().getHomeCity().equals(homeCity))
            result.add(riders.retrieve());
        return result;
    }

    
    //Returns all riders in the linked list.
    public LinkedList<IRider> getAll() {
        return riders;
    }


    //Removes a rider by ID. Returns true if a rider with that ID was found and removed; false otherwise.
    public boolean removeById(int riderId) {
        riders.findFirst();
        while (!riders.last()) {
            if (riders.retrieve().getId() == riderId) {
                riders.remove();
                return true;
            }
            riders.findNext();
        }
        if (riders.retrieve().getId() == riderId) {
                riders.remove();
                return true;
            }
        return false;
    }

    //Removes a rider by email address. Returns true if a rider with that email was found and removed; false otherwise.
    public boolean removeByEmail(String email) {
        riders.findFirst();
        while (!riders.last()) {
            if (riders.retrieve().getEmail().equals(email)) {
                riders.remove();
                return true;
            }
            riders.findNext();
        }
        if (riders.retrieve().getEmail().equals(email)) {
            riders.remove();
            return true;
        }
        return false;
    }

    /**
     * Name and home city are not unique, so more than one rider may match.
     * Removes every rider whose full name exactly matches the given name
     * (the same full-name equality used by findByName), not a first-name-only
     * or partial match.
     * Returns the number of riders removed (0 if none matched).
     */
    public int removeByName(String fullName) {
    int c = 0;
    if (riders.empty()) return 0;

    riders.findFirst();
    while (true) {
        if (riders.retrieve().getName().equals(fullName)) {
            riders.remove();
            c++;
            if (riders.empty()) break;
        } else {
            if (riders.last()) break;
            riders.findNext();
        }
    }
    return c;
}



    /**
     * Removes every rider from the specified home city.
     * Returns the number of riders removed (0 if none matched).
     */
    public int removeByHomeCity(String homeCity) {
    int c = 0;
    if (riders.empty()) return 0;

    riders.findFirst();
    while (true) {
        if (riders.retrieve().getHomeCity().equals(homeCity)) {
            riders.remove();
            c++;
            if (riders.empty()) break;
        } else {
            if (riders.last()) break;
            riders.findNext();
        }
    }
    return c;
}



    //return the total number of riders stored.
    public int size() {
    if (riders.empty()) return 0;

    int count = 1;
    riders.findFirst();
    while (!riders.last()) {
        count++;
        riders.findNext();
    }
    return count;
}

}



```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/IRider#