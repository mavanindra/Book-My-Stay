import java.util.*;

/**
 * Use Case 8 - Booking History & Reporting
 * @version 8.0
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

class BookingHistory {
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    public void add(Reservation r) {
        history.add(r);
    }

    public List<Reservation> getAll() {
        return history;
    }
}

class BookingReportService {

    public void generateReport(BookingHistory history) {

        System.out.println("Booking History Report");

        for (Reservation r : history.getAll()) {
            System.out.println("Guest: " + r.getGuestName()
                    + ", Room Type: " + r.getRoomType());
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {

        System.out.println("Booking History and Reporting\n");

        BookingHistory history = new BookingHistory();

        history.add(new Reservation("Abhi", "Single"));
        history.add(new Reservation("Subha", "Double"));
        history.add(new Reservation("Vanmathi", "Suite"));

        BookingReportService report = new BookingReportService();
        report.generateReport(history);
    }
}