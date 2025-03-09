package lk.coursework.version3;

public class Customer implements Runnable {
    private TicketCollection ticketCollection;
    private int customerRetrievalRate;
    private static int customerIDCounter = 1;
    private int customerId = 1;
    private Logger logger;

    public Customer(TicketCollection ticketCollection, int customerRetrievalRate, Logger logger) {
        this.ticketCollection = ticketCollection;
        this.customerRetrievalRate = customerRetrievalRate;
        this.logger = logger;
        this.customerId = customerIDCounter++;

    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Ticket ticket = ticketCollection.RemoveTicket();

                if (ticket != null) {
                    logger.log("Customer " + customerId + " bought Ticket " + ticket.getticket_id());
                    System.out.println("Customer " + customerId + " bought Ticket " + ticket.getticket_id());
                }

                Thread.sleep(customerRetrievalRate * 1000L); // Simulate time to buy another ticket
            }
        } catch (InterruptedException e) {
            logger.log("Customer " + customerId + " interrupted.");
            Thread.currentThread().interrupt();
        }
    }

}

