package lk.coursework.version3;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static lk.coursework.version3.main.config;

public class TicketCollection extends Configuration {
    private final Queue<lk.coursework.version3.Ticket> tickets = new LinkedList();
    private final int maxTicketCapacity;
    private int totalTickets;
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();
    private Logger logger;

    public TicketCollection(int totalTickets, int maxTicketCapacity, Logger logger) {
        this.totalTickets = totalTickets;
        this.maxTicketCapacity = maxTicketCapacity;
        this.logger = logger;
    }

    public boolean addTicket(Ticket ticket) {
        lock.lock();
        try {
            if(maxTicketCapacity < totalTickets) {
                while (tickets.size() >= maxTicketCapacity) {
                    System.out.println("Queue full. Vendor waiting..\n");
                    logger.log("Queue full. Vendor waiting..\n");
                    notFull.await();
                }

                // Ensure tickets are only added while there are tickets left
                if (totalTickets > 0) {
                    tickets.add(ticket);
                    totalTickets--; // Decrement remaining tickets
                    notEmpty.signal();
                    System.out.println("Ticket " + ticket.getticket_id() + " added by vendor.\n");
                    logger.log("Ticket " + ticket.getticket_id() + " added by vendor \n");
                    return true;
                }
            }else{
                System.out.println("The number of maximum tickets is smaller than total tickets entered");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
        return false;
    }

    public Ticket RemoveTicket() {
        lock.lock();
        try {
            while (tickets.isEmpty()) {
                System.out.println("Queue empty. Customer waiting..\n");
                notEmpty.await(); // Customers wait if the queue is empty
            }

            Ticket ticket = tickets.poll();
            notFull.signal(); // Notify vendors that there's space in the queue
            return ticket;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
        return null;
    }


    public void salesReport() {
        System.out.println("SALES REPORT: \n");
        int ticketsSold = config.getTotalTickets() - totalTickets; // Tickets sold = Initial - Remaining
        System.out.println("Total Tickets in the pool: " + config.getTotalTickets());
        System.out.println("Total Tickets sold: " + ticketsSold);
        System.out.println("Tickets remaining: " + totalTickets);

    }

}
