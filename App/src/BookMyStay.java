import java.util.*;

/**
 * Use Case 6 - Room Allocation
 * @version 6.0
 */

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

class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void add(Reservation r) {
        queue.offer(r);
    }

    public Reservation next() {
        return queue.poll();
    }

    public boolean hasRequests() {
        return !queue.isEmpty();
    }
}

class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single", 2);
        availability.put("Double", 1);
        availability.put("Suite", 1);
    }

    public int getAvailable(String type) {
        return availability.getOrDefault(type, 0);
    }

    public void decrease(String type) {
        availability.put(type, availability.get(type) - 1);
    }
}

class RoomAllocationService {

    private Set<String> allocatedIds = new HashSet<>();
    private Map<String, Set<String>> assigned = new HashMap<>();

    public void allocate(Reservation r, RoomInventory inventory) {
        String type = r.getRoomType();

        if (inventory.getAvailable(type) <= 0) {
            System.out.println("No rooms available for " + type + " for Guest: " + r.getGuestName());
            return;
        }

        String roomId = generateId(type);

        allocatedIds.add(roomId);
        assigned.putIfAbsent(type, new HashSet<>());
        assigned.get(type).add(roomId);

        inventory.decrease(type);

        System.out.println("Booking confirmed for Guest: "
                + r.getGuestName() + ", Room ID: " + roomId);
    }

    private String generateId(String type) {
        int count = assigned.getOrDefault(type, new HashSet<>()).size() + 1;
        String id = type + "-" + count;

        while (allocatedIds.contains(id)) {
            count++;
            id = type + "-" + count;
        }
        return id;
    }
}

public class BookMyStay {
    public static void main(String[] args) {

        System.out.println("Room Allocation Processing");

        BookingRequestQueue queue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService service = new RoomAllocationService();

        queue.add(new Reservation("Abhi", "Single"));
        queue.add(new Reservation("Subha", "Single"));
        queue.add(new Reservation("Vanmathi", "Suite"));

        while (queue.hasRequests()) {
            Reservation r = queue.next();
            service.allocate(r, inventory);
        }
    }
}