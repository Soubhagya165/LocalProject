package College.Event.ManagementSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class AdminViewTickets extends JFrame {

    JTable ticketsTable;
    JScrollPane scrollPane;
    JButton refreshButton;
    DefaultTableModel tableModel;

    public AdminViewTickets() {
        setTitle("View All Booked Tickets");
        setLayout(null);

        setSize(1540, 830);
        setLocationRelativeTo(null);
        setResizable(false);

        Font headerFont = new Font("Arial", Font.BOLD, 24);
        Font tableFont = new Font("Arial", Font.PLAIN, 16);
        Font buttonFont = new Font("Arial", Font.PLAIN, 18);

        JLabel headerLabel = new JLabel("All Booked Tickets");
        headerLabel.setFont(headerFont);
        headerLabel.setBounds(600, 30, 400, 40);
        add(headerLabel);

        String[] columnNames = {"Booking ID", "Regd No", "Event Name", "No of Tickets", "Total Price", "Booking Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        ticketsTable = new JTable(tableModel);
        ticketsTable.setFont(tableFont);
        ticketsTable.setRowHeight(30);

        scrollPane = new JScrollPane(ticketsTable);
        scrollPane.setBounds(170, 100, 1200, 500);
        add(scrollPane);

        refreshButton = new JButton("Refresh");
        refreshButton.setFont(buttonFont);
        refreshButton.setBounds(920, 650, 120, 40);
        add(refreshButton);

        refreshButton.addActionListener(e -> loadBookings());

        loadBookings();

        ImageIcon image1 = new ImageIcon("backbg.png");
        Image image2 = image1.getImage().getScaledInstance(1540,830,Image.SCALE_DEFAULT);
        ImageIcon image3 = new ImageIcon(image2);
        JLabel image = new JLabel(image3);
        image.setBounds(0,0,1540,830);
        add(image);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void loadBookings() {
        tableModel.setRowCount(0);
        try {
            DBConnection db = new DBConnection();
            String query = "SELECT b.booking_id, b.regd_no, e.event_name, b.no_of_tickets, b.total_price, b.booking_date " +
                    "FROM booking b JOIN event e ON b.event_id = e.event_id";
            ResultSet rs = db.statement.executeQuery(query);

            while (rs.next()) {
                int bookingId = rs.getInt("booking_id");
                String regdNo = rs.getString("regd_no");
                String eventName = rs.getString("event_name");
                int noOfTickets = rs.getInt("no_of_tickets");
                double totalPrice = rs.getDouble("total_price");
                Date bookingDate = rs.getDate("booking_date");

                String formattedDate = "N/A";
                if (bookingDate != null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    formattedDate = dateFormat.format(bookingDate);
                }

                Object[] row = {bookingId, regdNo, eventName, noOfTickets, totalPrice, formattedDate};
                tableModel.addRow(row);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error while loading bookings.");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AdminViewTickets();
    }
}
