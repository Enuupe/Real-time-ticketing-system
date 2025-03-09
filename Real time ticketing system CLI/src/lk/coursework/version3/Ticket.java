package lk.coursework.version3;

public class Ticket {
    private final int ticket_id;
    private String EventName;
    private Double price;

    public Ticket(int ticket_id) {
        this.ticket_id = ticket_id;
        this.EventName = "Event :" + ticket_id;
        this.price = 100.00 + (ticket_id*5);
    }

    public int getticket_id() {
        return this.ticket_id;
    }

    public String getEventName() {
        return this.EventName;
    }

    public Double getprice() {
        return this.price;
    }
}
