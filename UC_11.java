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

// Shared Inventory
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    // synchronized critical section
    public synchronized boolean allocateRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }

        return false;
    }

    public void showInventory() {
        System.out.println("\nRemaining Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}

// Booking Processor (Thread)
class BookingProcessor extends Thread {

    private Reservation reservation;
    private RoomInventory inventory;

    public BookingProcessor(Reservation reservation, RoomInventory inventory) {
        this.reservation = reservation;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        System.out.println(reservation.getGuestName() + " is attempting booking...");

        boolean success = inventory.allocateRoom(reservation.getRoomType());

        if (success) {

            System.out.println("Booking Confirmed → " +
                    reservation.getGuestName() +
                    " | Room: " + reservation.getRoomType() +
                    " | ReservationID: " + reservation.getReservationId());

        } else {

            System.out.println("Booking Failed → No " +
                    reservation.getRoomType() +
                    " available for " +
                    reservation.getGuestName());
        }
    }
}

// Main
public class UC_11 {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Book My Stay App - Version 11.0   ");
        System.out.println("=====================================");

        RoomInventory inventory = new RoomInventory();

        // simulate concurrent booking
        Reservation r1 = new Reservation("Alice", "Suite Room");
        Reservation r2 = new Reservation("Bob", "Suite Room");
        Reservation r3 = new Reservation("Charlie", "Single Room");
        Reservation r4 = new Reservation("David", "Single Room");

        BookingProcessor t1 = new BookingProcessor(r1, inventory);
        BookingProcessor t2 = new BookingProcessor(r2, inventory);
        BookingProcessor t3 = new BookingProcessor(r3, inventory);
        BookingProcessor t4 = new BookingProcessor(r4, inventory);

        // start threads
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        // wait for completion
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventory.showInventory();
    }
}
