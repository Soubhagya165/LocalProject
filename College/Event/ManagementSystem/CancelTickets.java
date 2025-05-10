package College.Event.ManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CancelTickets extends JFrame implements ActionListener {
    JTextField bookingIdField;
    JButton cancelButton;
    JLabel resultLabel;
    String regd_no;

    public CancelTickets(String regd_no) {
        this.regd_no = regd_no;

        setTitle("Cancel Tickets");
        setLayout(null);

        setSize(1540, 830);
        setLocationRelativeTo(null);

        Font labelFont = new Font("Arial", Font.BOLD, 25);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 20);
        Font buttonFont = new Font("Arial", Font.BOLD, 30);
        Font resultFont = new Font("Arial", Font.PLAIN, 20);

        int frameWidth = 1540;
        int componentWidth = 400;
        int fieldWidth = 300;
        int buttonWidth = 350;

        int centerX = frameWidth / 2;

        JLabel bookingIdLabel = new JLabel("Enter Booking ID to Cancel:");
        bookingIdLabel.setFont(labelFont);
        bookingIdLabel.setBounds(centerX - 400, 250, componentWidth + 100, 40);
        add(bookingIdLabel);

        bookingIdField = new JTextField();
        bookingIdField.setFont(textFieldFont);
        bookingIdField.setBounds(centerX, 250, fieldWidth, 40);
        add(bookingIdField);

        cancelButton = new JButton("Cancel Ticket");
        cancelButton.setFont(buttonFont);
        cancelButton.setBounds(centerX - (buttonWidth / 2), 330, buttonWidth, 60);
        cancelButton.addActionListener(this);
        add(cancelButton);

        resultLabel = new JLabel("");
        resultLabel.setFont(resultFont);
        resultLabel.setForeground(Color.RED);
        resultLabel.setBounds(centerX - 250, 420, 500, 30);
        add(resultLabel);

        ImageIcon image1 = new ImageIcon("backbg.png");
        Image image2 = image1.getImage().getScaledInstance(1540, 830, Image.SCALE_DEFAULT);
        ImageIcon image3 = new ImageIcon(image2);
        JLabel image = new JLabel(image3);
        image.setBounds(0, 0, 1540, 830);
        add(image);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelButton) {
            String bookingId = bookingIdField.getText().trim();

            if (bookingId.isEmpty()) {
                resultLabel.setText("Please enter a booking ID.");
                return;
            }

            try {
                DBConnection db = new DBConnection();
                String query = "SELECT * FROM booking WHERE booking_id = ? AND regd_no = ?";
                PreparedStatement ps = db.getConnection().prepareStatement(query);
                ps.setString(1, bookingId);
                ps.setString(2, regd_no);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String deleteQuery = "DELETE FROM booking WHERE booking_id = ?";
                    PreparedStatement deletePs = db.getConnection().prepareStatement(deleteQuery);
                    deletePs.setString(1, bookingId);
                    int result = deletePs.executeUpdate();

                    if (result > 0) {
                        resultLabel.setText("Ticket successfully cancelled!");
                    } else {
                        resultLabel.setText("Failed to cancel ticket. Try again.");
                    }
                } else {
                    resultLabel.setText("No booking found for this ID.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                resultLabel.setText("Error during cancellation.");
            }
        }
    }

    public static void main(String[] args) {
        new CancelTickets("240720100036");
    }
}
