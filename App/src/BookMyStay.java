import java.io.*;
import java.util.*;

/**
 * Use Case 12 - Data Persistence & Recovery
 * @version 12.0
 */

class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public Map<String, Integer> getAll() {
        return availability;
    }

    public void set(String type, int count) {
        availability.put(type, count);
    }
}

class FilePersistenceService {

    public void saveInventory(RoomInventory inventory, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            for (Map.Entry<String, Integer> entry : inventory.getAll().entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }

            System.out.println("Inventory saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving inventory.");
        }
    }

    public void loadInventory(RoomInventory inventory, String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("No valid inventory data found. Starting fresh.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");

                if (parts.length == 2) {
                    String type = parts[0];
                    int count = Integer.parseInt(parts[1]);
                    inventory.set(type, count);
                }
            }

            System.out.println("Inventory loaded successfully.");

        } catch (Exception e) {
            System.out.println("Error loading inventory. Starting fresh.");
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {

        System.out.println("System Recovery");

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService service = new FilePersistenceService();

        String filePath = "inventory.txt";

        service.loadInventory(inventory, filePath);

        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.getAll().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        service.saveInventory(inventory, filePath);
    }
}