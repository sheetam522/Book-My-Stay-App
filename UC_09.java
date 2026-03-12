import java.util.*;

// Custom Exception for invalid booking
class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}

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

// Inventory Service
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public boolean isValidRoomType(String roomType) {
        return inventory.containsKey(roomType);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decreaseRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

// Booking Validator
class InvalidBookingValidator {

    public static void validateReservation(Reservation r, RoomInventory inventory)
            throws InvalidBookingException {

        if (r.getGuestName() == null || r.getGuestName().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (!inventory.isValidRoomType(r.getRoomType())) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        if (inventory.getAvailability(r.getRoomType()) <= 0) {
            throw new InvalidBookingException("Requested room is not available.");
        }
    }
}

// Booking Service
class BookingService {

    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void confirmBooking(Reservation r) {

        try {

            // validation step
            InvalidBookingValidator.validateReservation(r, inventory);

            // allocate room
            inventory.decreaseRoom(r.getRoomType());

            System.out.println("Booking confirmed for " + r.getGuestName());
            System.out.println("Reservation ID: " + r.getReservationId());
            System.out.println("Room Type: " + r.getRoomType());
            System.out.println();

        } catch (InvalidBookingException e) {

            System.out.println("Booking Failed: " + e.getMessage());
            System.out.println();
        }
    }
}

// Main Class
public class UC_09 {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("    Book My Stay App - Version 9.0   ");
        System.out.println("=====================================");

        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService(inventory);

        // Valid booking
        Reservation r1 = new Reservation("Alice", "Single Room");

        // Invalid room type
        Reservation r2 = new Reservation("Bob", "Luxury Room");

        // No room available case
        Reservation r3 = new Reservation("Charlie", "Suite Room");
        Reservation r4 = new Reservation("David", "Suite Room");

        bookingService.confirmBooking(r1);
        bookingService.confirmBooking(r2);
        bookingService.confirmBooking(r3);
        bookingService.confirmBooking(r4);
    }
}
