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

// Booking History (stores confirmed reservations)
class BookingHistory {

    private List<Reservation> history = new ArrayList<>();

    // add confirmed booking
    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    // get all bookings
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// Reporting Service
class BookingReportService {

    public void generateReport(List<Reservation> reservations) {

        System.out.println("\n===== Booking History Report =====");

        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : reservations) {
            System.out.println(
                "Reservation ID: " + r.getReservationId() +
                " | Guest: " + r.getGuestName() +
                " | Room Type: " + r.getRoomType()
            );
        }

        System.out.println("\nTotal Bookings: " + reservations.size());
    }
}

// Main Class
public class UC_08 {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("    Book My Stay App - Version 8.0   ");
        System.out.println("=====================================");

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // simulate confirmed reservations
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        // store in history
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // admin generates report
        reportService.generateReport(history.getAllReservations());
    }
}
