import java.util.*;

// Reservation request
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
}

// Booking queue (FIFO)
class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.add(r);
        System.out.println("Request received from " + r.getGuestName());
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean hasRequests() {
        return !queue.isEmpty();
    }
}

// Inventory management
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
}

// Allocation service
class RoomAllocationService {

    private RoomInventory inventory;

    // track allocated room IDs
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    public RoomAllocationService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    private String generateRoomID(String roomType) {
        return roomType.substring(0, 2).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 4);
    }

    public void allocateRoom(Reservation r) {

        String type = r.getRoomType();

        if (inventory.getAvailability(type) > 0) {

            String roomID = generateRoomID(type);

            allocatedRooms.putIfAbsent(type, new HashSet<>());
            allocatedRooms.get(type).add(roomID);

            inventory.decreaseRoom(type);

            System.out.println("Reservation Confirmed for " + r.getGuestName());
            System.out.println("Room Type: " + type);
            System.out.println("Assigned Room ID: " + roomID);
            System.out.println();

        } else {
            System.out.println("Sorry " + r.getGuestName() + ", no " + type + " available.\n");
        }
    }
}

// Main class
public class UC_06 {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("     Book My Stay App - Version 6.0  ");
        System.out.println("=====================================");

        BookingRequestQueue queue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocator = new RoomAllocationService(inventory);

        // booking requests
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Double Room"));
        queue.addRequest(new Reservation("Charlie", "Suite Room"));
        queue.addRequest(new Reservation("David", "Suite Room"));

        System.out.println("\n--- Processing Requests ---\n");

        while (queue.hasRequests()) {
            Reservation r = queue.getNextRequest();
            allocator.allocateRoom(r);
        }

        System.out.println("All requests processed.");
    }
}