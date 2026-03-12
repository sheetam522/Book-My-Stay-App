import java.util.*;

// Reservation
class Reservation {

    private String reservationId;
    private String guestName;

    public Reservation(String guestName) {
        this.reservationId = "RES-" + UUID.randomUUID().toString().substring(0,5);
        this.guestName = guestName;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }
}

// Add-On Service
class AddOnService {

    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

// Manager for Add-On Services
class AddOnServiceManager {

    private Map<String, List<AddOnService>> reservationServices = new HashMap<>();

    // attach service to reservation
    public void addService(String reservationId, AddOnService service) {

        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);

        System.out.println(service.getServiceName() + " added to reservation " + reservationId);
    }

    // calculate cost
    public double calculateTotalCost(String reservationId) {

        double total = 0;

        List<AddOnService> services =
                reservationServices.getOrDefault(reservationId, new ArrayList<>());

        for (AddOnService s : services) {
            total += s.getCost();
        }

        return total;
    }

    // display services
    public void displayServices(String reservationId) {

        List<AddOnService> services =
                reservationServices.getOrDefault(reservationId, new ArrayList<>());

        System.out.println("\nAdd-On Services for Reservation: " + reservationId);

        if (services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (AddOnService s : services) {
            System.out.println("- " + s.getServiceName() + " ($" + s.getCost() + ")");
        }

        System.out.println("Total Add-On Cost: $" + calculateTotalCost(reservationId));
    }
}

// Main Class
public class UC_07 {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("    Book My Stay App - Version 7.0   ");
        System.out.println("=====================================");

        // create reservation
        Reservation reservation = new Reservation("Alice");

        AddOnServiceManager manager = new AddOnServiceManager();

        // guest selects services
        manager.addService(reservation.getReservationId(),
                new AddOnService("Breakfast", 15));

        manager.addService(reservation.getReservationId(),
                new AddOnService("Airport Pickup", 30));

        manager.addService(reservation.getReservationId(),
                new AddOnService("Spa Access", 50));

        // show services
        manager.displayServices(reservation.getReservationId());
    }
}