package College.Event.ManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class BookTickets extends JFrame implements ActionListener {
    JComboBox<String> eventDropdown;
    JLabel ticketPriceLabel, totalLabel;
    JTextField ticketCountField;
    JButton calculateButton, bookButton;

    Map<String, Integer> eventMap = new HashMap<>();
    Map<Integer, Double> priceMap = new HashMap<>();

    int selectedEventId = -1;
    String regd_no;

    public BookTickets(String regd_no) {
        this.regd_no = regd_no;

        setTitle("Book Tickets");
        setLayout(null);
        setSize(1540, 830);
        setLocationRelativeTo(null);

        Font font = new Font("Arial", Font.BOLD, 20);

        ImageIcon bgIcon = new ImageIcon("backbg.png");
        Image bgImage = bgIcon.getImage().getScaledInstance(1540, 830, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(bgImage);
        JLabel background = new JLabel(scaledIcon);
        background.setBounds(0, 0, 1540, 830);
        background.setLayout(null);
        add(background);

        JLabel eventLabel = new JLabel("Select Event:");
        eventLabel.setFont(font);
        eventLabel.setBounds(620, 100, 200, 30);
        background.add(eventLabel);

        eventDropdown = new JComboBox<>();
        eventDropdown.setFont(font);
        eventDropdown.setBounds(820, 100, 300, 30);
        eventDropdown.addActionListener(e -> updatePrice());
        background.add(eventDropdown);

        JLabel priceLabel = new JLabel("Ticket Price:");
        priceLabel.setFont(font);
        priceLabel.setBounds(620, 150, 200, 30);
        background.add(priceLabel);

        ticketPriceLabel = new JLabel("₹0.0");
        ticketPriceLabel.setFont(new Font("Arial", Font.BOLD, 22));
        ticketPriceLabel.setBounds(820, 150, 150, 30);
        background.add(ticketPriceLabel);

        JLabel ticketCountLabel = new JLabel("No of Tickets (1–10):");
        ticketCountLabel.setFont(font);
        ticketCountLabel.setBounds(620, 200, 250, 30);
        background.add(ticketCountLabel);

        ticketCountField = new JTextField();
        ticketCountField.setFont(font);
        ticketCountField.setBounds(880, 200, 50, 30);
        background.add(ticketCountField);

        calculateButton = new JButton("Calculate Price");
        calculateButton.setFont(new Font("Arial", Font.BOLD, 20));
        calculateButton.setBounds(620, 260, 220, 40);
        calculateButton.addActionListener(this);
        background.add(calculateButton);

        totalLabel = new JLabel("Total: ₹0.0");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 22));
        totalLabel.setBounds(860, 260, 200, 40);
        background.add(totalLabel);

        bookButton = new JButton("Book Now");
        bookButton.setFont(new Font("Arial", Font.BOLD, 22));
        bookButton.setBounds(710, 320, 180, 40);
        bookButton.addActionListener(this);
        background.add(bookButton);

        setVisible(true);
        loadEvents();
    }

    private void loadEvents() {
        try {
            DBConnection db = new DBConnection();
            String query = "SELECT event_id, event_name, ticket_price FROM event";
            ResultSet rs = db.statement.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("event_id");
                String name = rs.getString("event_name");
                double price = rs.getDouble("ticket_price");

                eventDropdown.addItem(name);
                eventMap.put(name, id);
                priceMap.put(id, price);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Failed to load events.");
            ex.printStackTrace();
        }
    }

    private void updatePrice() {
        String selected = (String) eventDropdown.getSelectedItem();
        if (selected != null && eventMap.containsKey(selected)) {
            selectedEventId = eventMap.get(selected);
            double price = priceMap.get(selectedEventId);
            ticketPriceLabel.setText("₹" + price);
        } else {
            ticketPriceLabel.setText("₹0.0");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int tickets = Integer.parseInt(ticketCountField.getText());

            if (tickets < 1 || tickets > 10) {
                JOptionPane.showMessageDialog(null, "Please select 1 to 10 tickets only.");
                return;
            }

            if (selectedEventId == -1) {
                JOptionPane.showMessageDialog(null, "Please select an event.");
                return;
            }

            double price = priceMap.get(selectedEventId);
            double total = price * tickets;
            totalLabel.setText("Total: ₹" + total);

            if (e.getSource() == bookButton) {
                DBConnection db = new DBConnection();
                String query = "INSERT INTO booking(regd_no, event_id, no_of_tickets, total_price, booking_date) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement ps = db.getConnection().prepareStatement(query);
                ps.setString(1, regd_no);
                ps.setInt(2, selectedEventId);
                ps.setInt(3, tickets);
                ps.setDouble(4, total);
                ps.setDate(5, new java.sql.Date(System.currentTimeMillis()));

                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Booking Successful!");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Enter a valid number of tickets.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error while booking tickets.");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new BookTickets("240720100036");
    }
}
