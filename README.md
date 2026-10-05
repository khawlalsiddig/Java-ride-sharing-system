# Java-ride-sharing-system
A Java-based Ride-Sharing System that demonstrates custom data structure implementation, object-oriented design, searching, sorting, data validation, CSV data processing, and private/shared ride scheduling with conflict detection.

<img width="2720" height="2704" alt="ride_sharing_class_diagram_v2" src="https://github.com/user-attachments/assets/01e62811-e490-480b-9c00-01212510b744" />
<img width="2720" height="1896" alt="ride_sharing_system_architecture_v2" src="https://github.com/user-attachments/assets/bf8fc00b-8f35-409c-9b4e-6e8874d080ba" />

## Diagram Notes: What Is Not Shown

The diagrams show the main classes, their fields, and the relationships between them. They leave out some details on purpose so they stay readable. Everything below is still part of the design and the code.

### Not drawn

- **Interfaces:** `IPerson`, `IRider`, `IDriver`, `IDateTime`, `IRide`, `IPrivateRide`, `ISharedRide`, `IRiderList`, `IDriverList`, `IRideList` and `IRideSharingSystem` are not shown. Each class implements its interface (for example `Rider` implements `IRider`, and `RiderList` implements `IRiderList`). These "implements" arrows (dashed, with a hollow triangle) are omitted. Fields typed `IRider`, `IDriver` or `IRide` refer to the interface, not the concrete class.
- **Comparable relationships:** `Rider`, `Driver`, `DateTime` and `Ride` implement `Comparable`. Only `compareTo` for `Rider` and `Driver` is shown.
- **Custom `LinkedList` and its node class:** The diagrams show only a field of type `LinkedList`. The custom list and its internal node are not drawn.
- **`Person` to `Ride` link:** `rideHistory` in `Person` references rides, but the arrow is left out. `RideList` owns the rides, so this link is a plain association.

### Methods not listed

- **Getters and setters** for all classes, including the validated `setPhoneNumber` and `setVehiclePlate`, which throw `IllegalArgumentException`.
- **List methods** in `RiderList`, `DriverList` and `RideList`: `add`, `addRide`, the `findBy...` methods, and the `removeBy...` methods. `removeByName`, `removeByHomeCity` and `removeByVehicleType` must remove every match and return the count.
- **`RideSharingSystem` methods:**
  - CSV loading: `loadRidersFromCSV`, `loadDriversFromCSV`, `loadRidesFromCSV`
  - Add, remove and search methods, including the cascade deletes in `removeRider` and `removeDriver`
  - Scheduling: `schedulePrivateRide` and `scheduleSharedRide`, which include the time-overlap conflict checks
- **`Main`:** the menu loop and the 12 menu options.
- **Helpers and constructors:** the constructors and any extra helpers we add (for example parsing a `DateTime` from `MM/DD/YYYY HH:MM`, converting a CSV vehicle type like `LUXURY SEDAN` to `VehicleType.LUXURY_SEDAN`, or adding and removing rides in a person's history).

### Notation and modeling choices

- Composition (filled diamond) and aggregation (hollow diamond) follow the "do the parts die with the whole?" rule. For example, a `Ride` owns its two `DateTime` objects, but a `SharedRide` only references its riders.
- Cascade deletion is behavior in `removeRider` and `removeDriver`, not ownership, so it is drawn as an association and not as composition.
- Visibility is simplified: `-` for private fields and `+` for public methods.
- Multiplicities are shown only where they matter (`1`, `2`, `1..*`, `0..*`).

