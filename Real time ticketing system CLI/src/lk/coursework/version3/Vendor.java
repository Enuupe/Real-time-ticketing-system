package lk.coursework.version3;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Vendor implements Runnable {
    private static final AtomicInteger ticketID = new AtomicInteger(1);
    private TicketCollection ticketCollection;
    private int ticketReleaseRate;
    private int totalTickets;
    private int vendorCount;
    private Logger logger;
    private int vendorID;

    public Vendor(TicketCollection ticketCollection, int ticketReleaseRate, int totalTickets, int[] count, Logger logger) {
        this.ticketCollection = ticketCollection;
        this.ticketReleaseRate = ticketReleaseRate;
        this.totalTickets = totalTickets / count[0];  // Divide totalTickets here if you want to
        this.vendorCount = count[0];
        this.logger = logger;
    }


    @Override
    public void run() {
        try {
            while(!Thread.currentThread().isInterrupted()){

                int currentTicketID = ticketID.getAndIncrement();
                Ticket ticket = new Ticket(currentTicketID);
                ticketCollection.addTicket(ticket);

                logger.log("Vendor added Ticket " + ticket.getticket_id());

                Thread.sleep(ticketReleaseRate * 1000L);
            }
        } catch (InterruptedException e) {
            logger.log("Vendor interrupted.");
            Thread.currentThread().interrupt();
        }
    }
}
