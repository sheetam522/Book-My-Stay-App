import java.io.*;
import java.util.*;

// Reservation class (Serializable)
class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

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

    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

// Inventory class (Serializable)
class RoomInventory implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<String,Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room",2);
        inventory.put("Double Room",2);
        inventory.put("Suite Room",1);
    }

    public boolean allocateRoom(String roomType) {

        int available = inventory.getOrDefault(roomType,0);

        if(available > 0){
            inventory.put(roomType, available - 1);
            return true;
        }

        return false;
    }

    public void showInventory(){

        System.out.println("\nInventory Status:");

        for(String type : inventory.keySet()){
            System.out.println(type + " : " + inventory.get(type));
        }
    }

    public Map<String,Integer> getInventory(){
        return inventory;
    }
}

// Booking System (Serializable)
class BookingSystem implements Serializable {

    private static final long serialVersionUID = 1L;

    private RoomInventory inventory;
    private List<Reservation> bookings;

    public BookingSystem(){
        inventory = new RoomInventory();
        bookings = new ArrayList<>();
    }

    public void bookRoom(String guestName,String roomType){

        if(inventory.allocateRoom(roomType)){

            Reservation r = new Reservation(guestName,roomType);
            bookings.add(r);

            System.out.println("Booking Confirmed → " + r);

        }else{

            System.out.println("Booking Failed → No " + roomType + " available");
        }
    }

    public void showBookings(){

        System.out.println("\nBooking History:");

        if(bookings.isEmpty()){
            System.out.println("No bookings yet.");
            return;
        }

        for(Reservation r : bookings){
            System.out.println(r);
        }
    }

    public RoomInventory getInventory(){
        return inventory;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "booking_data.ser";

    public static void saveSystem(BookingSystem system){

        try(ObjectOutputStream oos =
                new ObjectOutputStream(new FileOutputStream(FILE_NAME))){

            oos.writeObject(system);
            System.out.println("\nSystem state saved successfully.");

        }catch(IOException e){

            System.out.println("Error saving system state.");
        }
    }

    public static BookingSystem loadSystem(){

        try(ObjectInputStream ois =
                new ObjectInputStream(new FileInputStream(FILE_NAME))){

            BookingSystem system = (BookingSystem) ois.readObject();
            System.out.println("System state restored from file.");

            return system;

        }catch(Exception e){

            System.out.println("No previous data found. Starting fresh system.");
            return new BookingSystem();
        }
    }
}

// Main
public class UC_12 {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Book My Stay App - Version 12.0   ");
        System.out.println("=====================================");

        // restore system
        BookingSystem system = PersistenceService.loadSystem();

        // simulate bookings
        system.bookRoom("Alice","Single Room");
        system.bookRoom("Bob","Suite Room");

        system.showBookings();
        system.getInventory().showInventory();

        // save system before shutdown
        PersistenceService.saveSystem(system);
    }
}
