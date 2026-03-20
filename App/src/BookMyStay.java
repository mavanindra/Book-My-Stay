import java.util.*;

/**
 * Use Case 11 - Concurrent Booking Simulation
 * @version 11.0
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
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public int getAvailable(String type) {
        return availability.getOrDefault(type, 0);
    }

    public void decrease(String type) {
        availability.put(type, availability.get(type) - 1);
    }

    public Map<String, Integer> getAll() {
        return availability;
    }
}

class RoomAllocationService {
    private Set<String> allocatedIds = new HashSet<>();
    private Map<String, Integer> counter = new HashMap<>();

    public void allocateRoom(Reservation r, RoomInventory inventory) {

        String type = r.getRoomType();

        if (inventory.getAvailable(type) <= 0) {
            return;
        }

        int count = counter.getOrDefault(type, 0) + 1;
        String id = type + "-" + count;

        while (allocatedIds.contains(id)) {
            count++;
            id = type + "-" + count;
        }

        allocatedIds.add(id);
        counter.put(type, count);
        inventory.decrease(type);

        System.out.println("Booking confirmed for Guest: "
                + r.getGuestName() + ", Room ID: " + id);
    }
}

class ConcurrentBookingProcessor implements Runnable {

    private BookingRequestQueue queue;
    private RoomInventory inventory;
    private RoomAllocationService service;

    public ConcurrentBookingProcessor(
            BookingRequestQueue queue,
            RoomInventory inventory,
            RoomAllocationService service) {
        this.queue = queue;
        this.inventory = inventory;
        this.service = service;
    }

    @Override
    public void run() {

        while (true) {
            Reservation r;

            synchronized (queue) {
                if (!queue.hasRequests()) break;
                r = queue.next();
            }

            synchronized (inventory) {
                service.allocateRoom(r, inventory);
            }
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation");

        BookingRequestQueue queue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService service = new RoomAllocationService();

        queue.add(new Reservation("Abhi", "Single"));
        queue.add(new Reservation("Subha", "Single"));
        queue.add(new Reservation("Vanmathi", "Double"));
        queue.add(new Reservation("Kural", "Suite"));

        Thread t1 = new Thread(new ConcurrentBookingProcessor(queue, inventory, service));
        Thread t2 = new Thread(new ConcurrentBookingProcessor(queue, inventory, service));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }

        System.out.println("\nRemaining Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.getAll().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}