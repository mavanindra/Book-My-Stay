import java.util.*;

/**
 * Use Case 10 - Booking Cancellation & Rollback
 * @version 10.0
 */

class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public void increase(String type) {
        availability.put(type, availability.get(type) + 1);
    }

    public int getAvailable(String type) {
        return availability.getOrDefault(type, 0);
    }
}

class CancellationService {

    private Stack<String> releasedIds;
    private Map<String, String> reservationMap;

    public CancellationService() {
        releasedIds = new Stack<>();
        reservationMap = new HashMap<>();
    }

    public void registerBooking(String reservationId, String roomType) {
        reservationMap.put(reservationId, roomType);
    }

    public void cancelBooking(String reservationId, RoomInventory inventory) {

        if (!reservationMap.containsKey(reservationId)) {
            System.out.println("Invalid cancellation request.");
            return;
        }

        String type = reservationMap.get(reservationId);

        releasedIds.push(reservationId);
        inventory.increase(type);

        reservationMap.remove(reservationId);

        System.out.println("Booking cancelled successfully. Inventory restored for room type: " + type);
    }

    public void showRollbackHistory() {
        System.out.println("\nRollback History (Most Recent First):");

        Stack<String> temp = (Stack<String>) releasedIds.clone();

        while (!temp.isEmpty()) {
            System.out.println("Released Reservation ID: " + temp.pop());
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {

        System.out.println("Booking Cancellation");

        RoomInventory inventory = new RoomInventory();
        CancellationService service = new CancellationService();

        String reservationId = "Single-1";

        service.registerBooking(reservationId, "Single");

        service.cancelBooking(reservationId, inventory);

        service.showRollbackHistory();

        System.out.println("\nUpdated Single Room Availability: "
                + inventory.getAvailable("Single"));
    }
}