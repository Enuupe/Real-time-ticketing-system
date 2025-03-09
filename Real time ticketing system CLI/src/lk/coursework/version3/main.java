package lk.coursework.version3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {
    private static TicketCollection ticketCollection;
    static Configuration config = new Configuration();
    static boolean isRunning = false;
    private static Logger logger;
    private static Thread vendorT, customerT;
    private static int vendor_count;
    private static int customer_count;
    private static final int[] count = new int[2];
    static List<Thread> vendorThreads = new ArrayList<>();
    static List<Thread> customerThreads = new ArrayList<>();

    public main() {
    }

    public static void input() {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter total amount of tickets: ");
        config.setTotalTickets(Configuration.validation(in));

        System.out.print("Enter the ticket release rate (in seconds): ");
        config.setTicketReleaseRate(Configuration.validation(in));

        System.out.print("Enter the ticket retrieval rate of customers (in seconds): ");
        config.setCustomerRetrievalRate(Configuration.validation(in));

        System.out.print("Enter the maximum ticket capacity: ");
        config.setMaxTicketCapacity(Configuration.validation(in));

        while (true) {
            System.out.print("Enter the amount of vendors (minimum 1): ");
            vendor_count = in.nextInt();
            if (vendor_count > 0) {
                count[0] = vendor_count;
                break;
            } else {
                System.out.println("Invalid number of vendors. Please enter a value greater than 0.");
            }
        }

        // Validate customer count
        while (true) {
            System.out.print("Enter the amount of customers (minimum 1): ");
            customer_count = in.nextInt();
            if (customer_count > 0) {
                count[1] = customer_count;
                break;
            } else {
                System.out.println("Invalid number of customers. Please enter a value greater than 0.");
            }
        }
    }

    public static void commands() {
        Scanner scanner = new Scanner(System.in);  // Scanner for reading commands
        System.out.println("Enter a command (Start, Exit): ");

        // Create a separate scanner and thread to monitor ENTER key press
        boolean[] stopListener = {false}; // Shared flag

        Thread listenerThread = new Thread(() -> {
            try (Scanner enterScanner = new Scanner(System.in)) {
                while (!stopListener[0]) {
                    String input = enterScanner.nextLine().trim();
                    if (input.isEmpty()) {
                        if (isRunning) {
                            isRunning = false;
                            vendorThreads.forEach(Thread::interrupt);
                            customerThreads.forEach(Thread::interrupt);
                            ticketCollection.salesReport();
                            System.out.println("The threads are stopped due to ENTER key press.");
                            stopListener[0] = true;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        listenerThread.start();


        // Command loop for starting or exiting the program
        while (true) {
            String command = scanner.nextLine().trim().toLowerCase();
            switch (command) {
                case "start":
                    if (!isRunning) {
                        isRunning = true;
                        for (int i = 0; i < vendor_count; i++) {
                            vendorT = new Thread(new Vendor(ticketCollection, config.getTicketReleaseRate(), config.getTotalTickets(), count, logger));
                            vendorThreads.add(vendorT);

                        }
                        for (int i = 0; i < customer_count; i++) {
                            customerT = new Thread(new Customer(ticketCollection, ticketCollection.getCustomerRetrievalRate(), logger));
                            customerThreads.add(customerT);
                        }

                        for (Thread thread : vendorThreads) {
                            thread.start();
                        }
                        for (Thread thread : customerThreads) {
                            thread.start();
                        }
                        System.out.println("Threads are starting.");
                    } else {
                        System.out.println("Threads are already running.");
                    }
                    break;

                case "exit":
                    if (isRunning) {
                        isRunning = false;

                        // Interrupt vendor and customer threads
                        vendorThreads.forEach(Thread::interrupt);
                        customerThreads.forEach(Thread::interrupt);

                        vendorThreads.clear();
                        customerThreads.clear();
                    }

                    ticketCollection.salesReport();
                    System.out.println("Exiting the program.");
                    stopListener[0] = true; // Stop listener thread
                    return;
                default:
                    System.out.println("Invalid command. Please enter 'Start' or 'Exit'.");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("git testing");
        logger = new Logger("event_log.txt");
        input();
//        config.connect_db();
        ticketCollection = new TicketCollection(config.getTotalTickets(), config.getMaxTicketCapacity(), logger);

        commands();
    }
}
