import java.util.*;

// Reservation
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.reservationId = "RES-" + UUID.randomUUID().toString().substring(0,5);
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Inventory
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decreaseRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void increaseRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}

// Booking Service
class BookingService {

    private RoomInventory inventory;

    // reservationId → reservation
    private Map<String, Reservation> confirmedBookings = new HashMap<>();

    // rollback structure
    private Stack<String> releasedRoomStack = new Stack<>();

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    // confirm booking
    public void confirmBooking(Reservation r) {

        if (inventory.getAvailability(r.getRoomType()) <= 0) {
            System.out.println("No rooms available for " + r.getRoomType());
            return;
        }

        inventory.decreaseRoom(r.getRoomType());
        confirmedBookings.put(r.getReservationId(), r);

        System.out.println("Booking Confirmed");
        System.out.println("Guest: " + r.getGuestName());
        System.out.println("Reservation ID: " + r.getReservationId());
        System.out.println("Room Type: " + r.getRoomType());
        System.out.println();
    }

    // cancel booking
    public void cancelBooking(String reservationId) {

        if (!confirmedBookings.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Reservation not found.");
            return;
        }

        Reservation r = confirmedBookings.remove(reservationId);

        // rollback operations
        inventory.increaseRoom(r.getRoomType());
        releasedRoomStack.push(reservationId);

        System.out.println("Booking Cancelled Successfully");
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Room Type Restored: " + r.getRoomType());
        System.out.println();
    }

    public void showRollbackHistory() {

        System.out.println("\nRollback Stack (Recently Cancelled):");

        if (releasedRoomStack.isEmpty()) {
            System.out.println("No cancellations yet.");
            return;
        }

        for (String id : releasedRoomStack) {
            System.out.println(id);
        }
    }
}

// Main
public class UC_10 {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("    Book My Stay App - Version 10.0  ");
        System.out.println("=====================================");

        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService(inventory);

        // confirm bookings
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Suite Room");

        bookingService.confirmBooking(r1);
        bookingService.confirmBooking(r2);

        inventory.displayInventory();

        // cancel booking
        bookingService.cancelBooking(r2.getReservationId());

        inventory.displayInventory();

        bookingService.showRollbackHistory();
    }
}
