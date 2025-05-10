package College.Event.ManagementSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class AdminUpdateTickets extends JFrame {

    JTable ticketsTable;
    JScrollPane scrollPane;
    JButton updateButton, refreshButton;
    DefaultTableModel tableModel;

    DBConnection db;

    public AdminUpdateTickets() {
        setTitle("Admin - Update Booked Tickets");
        setLayout(null);

        db = new DBConnection();

        setSize(1540, 830);
        setLocationRelativeTo(null);
        setResizable(false);

        Font headerFont = new Font("Arial", Font.BOLD, 24);
        Font tableFont = new Font("Arial", Font.PLAIN, 16);
        Font buttonFont = new Font("Arial", Font.PLAIN, 18);

        JLabel headerLabel = new JLabel("Update Booked Tickets");
        headerLabel.setFont(headerFont);
        headerLabel.setBounds(600, 30, 400, 40);
        add(headerLabel);

        String[] columnNames = {"Booking ID", "Regd No", "Event ID", "Event Name", "No of Tickets", "Total Price", "Booking Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        ticketsTable = new JTable(tableModel);
        ticketsTable.setFont(tableFont);
        ticketsTable.setRowHeight(30);

        scrollPane = new JScrollPane(ticketsTable);
        scrollPane.setBounds(170, 100, 1200, 500);
        add(scrollPane);

        updateButton = new JButton("Update Selected Ticket");
        updateButton.setFont(buttonFont);
        updateButton.setBounds(650, 650, 250, 40);
        add(updateButton);

        refreshButton = new JButton("Refresh");
        refreshButton.setFont(buttonFont);
        refreshButton.setBounds(920, 650, 120, 40);
        add(refreshButton);

        updateButton.addActionListener(e -> updateSelectedTicket());
        refreshButton.addActionListener(e -> loadBookings());

        loadBookings();

        ImageIcon image1 = new ImageIcon("backbg.png");
        Image image2 = image1.getImage().getScaledInstance(1540, 830, Image.SCALE_DEFAULT);
        ImageIcon image3 = new ImageIcon(image2);
        JLabel image = new JLabel(image3);
        image.setBounds(0, 0, 1540, 830);
        add(image);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void loadBookings() {
        tableModel.setRowCount(0);
        try {
            String query = "SELECT b.booking_id, b.regd_no, b.event_id, e.event_name, b.no_of_tickets, b.total_price, b.booking_date " +
                    "FROM booking b JOIN event e ON b.event_id = e.event_id";
            ResultSet rs = db.statement.executeQuery(query);

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("booking_id"),
                        rs.getString("regd_no"),
                        rs.getInt("event_id"),
                        rs.getString("event_name"),
                        rs.getInt("no_of_tickets"),
                        rs.getDouble("total_price"),
                        rs.getDate("booking_date")
                };
                tableModel.addRow(row);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading bookings.");
            ex.printStackTrace();
        }
    }

    private void updateSelectedTicket() {
        int selectedRow = ticketsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a ticket to update.");
            return;
        }

        int bookingId = (int) tableModel.getValueAt(selectedRow, 0);
        int eventId = (int) tableModel.getValueAt(selectedRow, 2);

        String newTicketsStr = JOptionPane.showInputDialog(this, "Enter new number of tickets:");
        if (newTicketsStr == null || newTicketsStr.trim().isEmpty()) return;

        try {
            int newTickets = Integer.parseInt(newTicketsStr);

            String priceQuery = "SELECT ticket_price FROM event WHERE event_id = " + eventId;
            ResultSet rs = db.statement.executeQuery(priceQuery);
            double pricePerTicket = 0;
            if (rs.next()) {
                pricePerTicket = rs.getDouble("ticket_price");
            }

            double newTotalPrice = pricePerTicket * newTickets;

            String updateQuery = "UPDATE booking SET no_of_tickets = ?, total_price = ? WHERE booking_id = ?";
            PreparedStatement ps = db.getConnection().prepareStatement(updateQuery);
            ps.setInt(1, newTickets);
            ps.setDouble(2, newTotalPrice);
            ps.setInt(3, bookingId);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Booking updated successfully.");
                loadBookings();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update booking.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating booking.");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AdminUpdateTickets();
    }
}
