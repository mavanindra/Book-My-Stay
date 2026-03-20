import java.util.*;

/**
 * Use Case 7 - Add-On Services
 * @version 7.0
 */

class AddOnService {
    private String name;
    private double cost;

    public AddOnService(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }
}

class AddOnServiceManager {
    private Map<String, List<AddOnService>> servicesMap;

    public AddOnServiceManager() {
        servicesMap = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service) {
        servicesMap.putIfAbsent(reservationId, new ArrayList<>());
        servicesMap.get(reservationId).add(service);
    }

    public double getTotalCost(String reservationId) {
        double total = 0;
        List<AddOnService> list = servicesMap.get(reservationId);

        if (list != null) {
            for (AddOnService s : list) {
                total += s.getCost();
            }
        }
        return total;
    }
}

public class BookMyStay {
    public static void main(String[] args) {

        System.out.println("Add-On Service Selection");

        String reservationId = "Single-1";

        AddOnServiceManager manager = new AddOnServiceManager();

        manager.addService(reservationId, new AddOnService("Breakfast", 500));
        manager.addService(reservationId, new AddOnService("Spa", 1000));

        double totalCost = manager.getTotalCost(reservationId);

        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Total Add-On Cost: " + totalCost);
    }
}