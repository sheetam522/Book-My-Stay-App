import java.util.HashMap;
import java.util.Map;

// Abstract Room class
abstract class Room {

    protected String type;
    protected int beds;
    protected double size;
    protected double price;

    public Room(String type, int beds, double size, double price) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqm");
        System.out.println("Price: ₹" + price + " per night");
    }

    public String getType() {
        return type;
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 20.0, 1500);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 35.0, 2500);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 60.0, 5000);
    }
}

// Inventory class using HashMap
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();

        // Initial availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int newCount) {
        inventory.put(roomType, newCount);
    }

    public void displayInventory() {
        System.out.println("\n--- Current Room Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " Available: " + entry.getValue());
        }
    }
}

// Main class
public class UC_03 {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("     Book My Stay App - Version 3.0  ");
        System.out.println("=====================================");

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Display room details
        System.out.println("\n--- Room Details ---");
        single.displayDetails();
        System.out.println();
        doubleRoom.displayDetails();
        System.out.println();
        suite.displayDetails();

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Show inventory
        inventory.displayInventory();

        System.out.println("\nApplication finished.");
    }
}
