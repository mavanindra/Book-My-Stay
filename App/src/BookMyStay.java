import java.util.HashMap;
import java.util.Map;

/**
 * Use Case 4 - Room Search
 * @version 4.0
 */

abstract class Room {
    protected int beds;
    protected int size;
    protected double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void display() {
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + price);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}

class RoomInventory {
    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public Map<String, Integer> getAvailability() {
        return availability;
    }
}

class RoomSearchService {

    public void searchAvailableRooms(
            RoomInventory inventory,
            Room single,
            Room doubleRoom,
            Room suite) {

        Map<String, Integer> data = inventory.getAvailability();

        System.out.println("Room Search\n");

        if (data.get("Single") > 0) {
            System.out.println("Single Room:");
            single.display();
            System.out.println("Available: " + data.get("Single") + "\n");
        }

        if (data.get("Double") > 0) {
            System.out.println("Double Room:");
            doubleRoom.display();
            System.out.println("Available: " + data.get("Double") + "\n");
        }

        if (data.get("Suite") > 0) {
            System.out.println("Suite Room:");
            suite.display();
            System.out.println("Available: " + data.get("Suite"));
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        RoomInventory inventory = new RoomInventory();
        RoomSearchService search = new RoomSearchService();

        search.searchAvailableRooms(inventory, single, doubleRoom, suite);
    }
}