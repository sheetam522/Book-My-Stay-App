import java.util.LinkedList;
import java.util.Queue;

// Reservation class (represents a booking request)
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayRequest() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

// Booking Request Queue
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // Display queued requests
    public void displayQueue() {

        System.out.println("\n--- Booking Request Queue (FIFO Order) ---");

        for (Reservation r : queue) {
            r.displayRequest();
        }
    }
}

// Main Class
public class UC_05 {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("     Book My Stay App - Version 5.0  ");
        System.out.println("=====================================");

        BookingRequestQueue requestQueue = new BookingRequestQueue();

        // Guests submit booking requests
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        requestQueue.addRequest(r1);
        requestQueue.addRequest(r2);
        requestQueue.addRequest(r3);

        // Show queued requests
        requestQueue.displayQueue();

        System.out.println("\nRequests stored successfully (No allocation yet).");
    }
}