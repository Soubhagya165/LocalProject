package College.Event.ManagementSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class BookTickets extends JFrame {

    JTable table;
    JTextField ticketField;
    JLabel priceLabel;

    public BookTickets() {
        super("Ticket Booking");

        // Table Setup
        String[] columnNames = {"Event ID", "Event Name", "Ticket Price"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 20, 800, 400);

        // Ticket input
        JLabel ticketLabel = new JLabel("No. of Tickets:");
        ticketLabel.setBounds(850, 50, 100, 30);
        ticketField = new JTextField();
        ticketField.setBounds(960, 50, 100, 30);

        // Calculate button
        JButton calcBtn = new JButton("Calculate Price");
        calcBtn.setBounds(850, 100, 210, 40);

        // Price display
        priceLabel = new JLabel("Total Price: ");
        priceLabel.setBounds(850, 160, 300, 30);

        // Load data from database
        try {
            DBConnection con = new DBConnection();
            String query = "SELECT * FROM event";
            PreparedStatement ps = con.connection.prepareStatement(query);
            ResultSet output = ps.executeQuery();

            while (output.next()) {
                int id = output.getInt("event_id");
                String name = output.getString("event_name");
                double price = output.getDouble("ticket_price");

                model.addRow(new Object[]{id, name, price});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Calculate Button Action
        calcBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select an event.");
                    return;
                }

                String ticketText = ticketField.getText();
                if (ticketText.isEmpty() || !ticketText.matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number of tickets.");
                    return;
                }

                int ticketCount = Integer.parseInt(ticketText);
                double price = (double) table.getValueAt(selectedRow, 2); // Get ticket price
                double total = price * ticketCount;

                priceLabel.setText("Total Price: ₹" + total);
            }
        });

        // Frame setup
        setLayout(null);
        add(scrollPane);
        add(ticketLabel);
        add(ticketField);
        add(calcBtn);
        add(priceLabel);

        setSize(1540, 830);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new BookTickets();
    }
}
