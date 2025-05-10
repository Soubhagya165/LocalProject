package College.Event.ManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class ViewTickets extends JFrame implements ActionListener {
    JTable ticketsTable;
    JScrollPane scrollPane;
    JButton refreshButton;
    String regd_no;

    public ViewTickets(String regd_no) {
        this.regd_no = regd_no;

        setTitle("View Tickets");
        setLayout(null);

        setSize(1540, 830);
        setLocationRelativeTo(null);

        Font headerFont = new Font("Arial", Font.BOLD, 30);
        Font tableFont = new Font("Arial", Font.PLAIN, 18);
        Font buttonFont = new Font("Arial", Font.BOLD, 20);

        JLabel headerLabel = new JLabel("Your Booked Tickets");
        headerLabel.setFont(headerFont);
        headerLabel.setBounds(570, 20, 400, 40);
        add(headerLabel);

        String[] columnNames = {"Ticket ID", "Event Name", "No of Tickets", "Total Price"};
        Object[][] data = getTicketData();

        ticketsTable = new JTable(data, columnNames);
        ticketsTable.setFont(tableFont);
        ticketsTable.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(ticketsTable);
        scrollPane.setBounds(100, 100, 1340, 500);
        add(scrollPane);

        refreshButton = new JButton("Refresh");
        refreshButton.setFont(buttonFont);
        refreshButton.setBounds(670, 640, 200, 40);
        refreshButton.addActionListener(this);
        add(refreshButton);

        ImageIcon image1 = new ImageIcon("backbg.png");
        Image image2 = image1.getImage().getScaledInstance(1540, 830, Image.SCALE_DEFAULT);
        ImageIcon image3 = new ImageIcon(image2);
        JLabel image = new JLabel(image3);
        image.setBounds(0, 0, 1540, 830);
        add(image);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private Object[][] getTicketData() {
        ArrayList<Object[]> ticketList = new ArrayList<>();
        try {
            DBConnection db = new DBConnection();
            String query = "SELECT b.booking_id, e.event_name, b.no_of_tickets, b.total_price " +
                    "FROM booking b " +
                    "JOIN event e ON b.event_id = e.event_id " +
                    "WHERE b.regd_no = ?";
            PreparedStatement ps = db.getConnection().prepareStatement(query);
            ps.setString(1, regd_no);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int ticketId = rs.getInt("booking_id");
                String eventName = rs.getString("event_name");
                int noOfTickets = rs.getInt("no_of_tickets");
                double totalPrice = rs.getDouble("total_price");
                ticketList.add(new Object[]{ticketId, eventName, noOfTickets, totalPrice});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ticketList.toArray(new Object[0][]);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == refreshButton) {
            Object[][] newData = getTicketData();
            ticketsTable.setModel(new javax.swing.table.DefaultTableModel(
                    newData,
                    new String[] {"Ticket ID", "Event Name", "No of Tickets", "Total Price"}
            ));
        }
    }

    public static void main(String[] args) {
        new ViewTickets("240720100036");
    }
}
