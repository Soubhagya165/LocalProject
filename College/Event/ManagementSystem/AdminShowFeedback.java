package College.Event.ManagementSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminShowFeedback extends JFrame {
    JTable feedbackTable;
    JScrollPane scrollPane;
    DefaultTableModel model;

    public AdminShowFeedback() {
        setTitle("Admin - View Feedback");
        setLayout(null);
        setSize(1540, 830);
        setLocationRelativeTo(null);
        setResizable(false);

        Font labelFont = new Font("Arial", Font.PLAIN, 18);
        Font tableFont = new Font("Arial", Font.PLAIN, 16);

        int formWidth = 1200;
        int formHeight = 500;
        int leftMargin = (1540 - formWidth) / 2;
        int topMargin = (830 - formHeight) / 2;

        String[] columns = {"Feedback ID", "Registration No", "Event ID", "Rating", "Feedback Text", "Timestamp"};
        model = new DefaultTableModel(columns, 0);

        feedbackTable = new JTable(model);
        feedbackTable.setFont(tableFont);
        feedbackTable.setRowHeight(30);

        scrollPane = new JScrollPane(feedbackTable);
        scrollPane.setBounds(leftMargin, topMargin, formWidth, formHeight);
        add(scrollPane);

        loadFeedback();

        ImageIcon image1 = new ImageIcon("backbg.png");
        Image image2 = image1.getImage().getScaledInstance(1540, 830, Image.SCALE_DEFAULT);
        ImageIcon image3 = new ImageIcon(image2);
        JLabel image = new JLabel(image3);
        image.setBounds(0, 0, 1540, 830);
        add(image);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void loadFeedback() {
        try {
            DBConnection db = new DBConnection();
            String query = "SELECT feedback_id, regd_no, event_id, rating, feedback_text, timestamp FROM feedback ORDER BY timestamp DESC";
            ResultSet rs = db.statement.executeQuery(query);

            while (rs.next()) {
                int feedbackId = rs.getInt("feedback_id");
                String regdNo = rs.getString("regd_no");
                int eventId = rs.getInt("event_id");
                int rating = rs.getInt("rating");
                String feedbackText = rs.getString("feedback_text");
                Timestamp timestamp = rs.getTimestamp("timestamp");

                model.addRow(new Object[]{feedbackId, regdNo, eventId, rating, feedbackText, timestamp});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error while loading feedback data.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AdminShowFeedback();
    }
}
