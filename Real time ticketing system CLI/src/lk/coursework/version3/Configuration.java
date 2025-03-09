package lk.coursework.version3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Configuration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    public Configuration() {
    }

    public int getTotalTickets() {
        return this.totalTickets;
    }

    public int getTicketReleaseRate() {
        return this.ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return this.customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return this.maxTicketCapacity;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    static int validation(Scanner in) {
        while(true) {
            if (in.hasNextInt()) {
                int value = in.nextInt();
                if (value > 0) {
                    return value;
                }
            } else {
                in.next();
            }
            System.out.println("Input is invalid. Please enter a positive number.");
        }
    }

//    void connect_db(){
//        String url = "jdbc:mysql://localhost:3306/customer_vendor_pattern?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
//        String user = "root";
//        String password = "Enuthi@DB123";
//
//        String insertQuery = "INSERT INTO configuration (total_tickets, ticket_release_rate, customer_retrieval_rate, max_ticket_capacity) VALUES (?,?,?,?)";
//
//        try{
//            Connection connection = DriverManager.getConnection(url, user, password);
//
//            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
//
//            preparedStatement.setInt(1, this.totalTickets);
//            preparedStatement.setInt(2, this.ticketReleaseRate);
//            preparedStatement.setInt(3, this.customerRetrievalRate);
//            preparedStatement.setInt(4, this.maxTicketCapacity);
//
//            int rowsInserted = preparedStatement.executeUpdate();
//            if(rowsInserted > 0){
//                System.out.println("data successfully inserted to the database");
//            }
//        }
//        catch(SQLException e){
//            System.err.println("Couldnt insert to database: " + e.getMessage());
//        }
//    }
}
