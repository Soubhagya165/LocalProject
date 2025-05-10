package College.Event.ManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Feedback extends JFrame implements ActionListener {
    JLabel eventLabel, ratingLabel, feedbackLabel;
    JComboBox<String> eventDropdown;
    JTextArea feedbackArea;
    JButton submitButton;
    JComboBox<String> ratingDropdown;
    String regd_no;

    public Feedback(String regd_no) {
        this.regd_no = regd_no;

        setTitle("Feedback");
        setLayout(null);

        setSize(1540, 830);
        setLocationRelativeTo(null);

        Font headerFont = new Font("Arial", Font.BOLD, 40);
        Font labelFont = new Font("Arial", Font.PLAIN, 25);
        Font dropdownFont = new Font("Arial", Font.PLAIN, 20);
        Font textAreaFont = new Font("Arial", Font.PLAIN, 20);
        Font buttonFont = new Font("Arial", Font.BOLD, 30);

        JLabel headerLabel = new JLabel("Event Feedback");
        headerLabel.setFont(headerFont);
        headerLabel.setBounds(900, 10, 400, 50);
        add(headerLabel);

        eventLabel = new JLabel("Select Event:");
        eventLabel.setFont(labelFont);
        eventLabel.setBounds(50, 100, 200, 40);
        add(eventLabel);

        eventDropdown = new JComboBox<>();
        eventDropdown.setFont(dropdownFont);
        eventDropdown.setBounds(250, 100, 500, 40);
        add(eventDropdown);

        loadEvents();

        ratingLabel = new JLabel("Rating (1-5):");
        ratingLabel.setFont(labelFont);
        ratingLabel.setBounds(50, 170, 200, 40);
        add(ratingLabel);

        ratingDropdown = new JComboBox<>();
        for (int i = 1; i <= 5; i++) {
            ratingDropdown.addItem(String.valueOf(i));
        }
        ratingDropdown.setFont(dropdownFont);
        ratingDropdown.setBounds(250, 170, 100, 40);
        add(ratingDropdown);

        feedbackLabel = new JLabel("Your Feedback:");
        feedbackLabel.setFont(labelFont);
        feedbackLabel.setBounds(50, 240, 200, 40);
        add(feedbackLabel);

        feedbackArea = new JTextArea();
        feedbackArea.setFont(textAreaFont);
        feedbackArea.setBounds(250, 240, 500, 150);
        feedbackArea.setLineWrap(true);
        feedbackArea.setWrapStyleWord(true);
        add(feedbackArea);

        submitButton = new JButton("Submit Feedback");
        submitButton.setFont(buttonFont);
        submitButton.setBounds(570, 430, 400, 60);
        submitButton.addActionListener(this);
        add(submitButton);

        int verticalCenter = (getHeight() - 600) / 2;
        int horizontalCenter = (getWidth() - 600) / 2;

        headerLabel.setBounds(horizontalCenter, verticalCenter, 400, 50);
        eventLabel.setBounds(horizontalCenter - 200, verticalCenter + 70, 200, 40);
        eventDropdown.setBounds(horizontalCenter + 100, verticalCenter + 70, 500, 40);

        ratingLabel.setBounds(horizontalCenter - 200, verticalCenter + 140, 200, 40);
        ratingDropdown.setBounds(horizontalCenter + 100, verticalCenter + 140, 100, 40);

        feedbackLabel.setBounds(horizontalCenter - 200, verticalCenter + 210, 200, 40);
        feedbackArea.setBounds(horizontalCenter + 100, verticalCenter + 210, 500, 150);

        submitButton.setBounds(horizontalCenter + 100, verticalCenter + 380, 400, 60);

        ImageIcon image1 = new ImageIcon("backbg.png");
        Image image2 = image1.getImage().getScaledInstance(1540, 830, Image.SCALE_DEFAULT);
        ImageIcon image3 = new ImageIcon(image2);
        JLabel image = new JLabel(image3);
        image.setBounds(0, 0, 1540, 830);
        add(image);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void loadEvents() {
        try {
            DBConnection db = new DBConnection();
            String query = "SELECT event_id, event_name FROM event";
            ResultSet rs = db.statement.executeQuery(query);

            while (rs.next()) {
                String eventName = rs.getString("event_name");
                eventDropdown.addItem(eventName);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Failed to load events.");
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            try {
                String eventName = (String) eventDropdown.getSelectedItem();
                int rating = Integer.parseInt((String) ratingDropdown.getSelectedItem());
                String feedbackText = feedbackArea.getText();

                if (eventName == null || eventName.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please select an event.");
                    return;
                }

                if (feedbackText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please provide your feedback.");
                    return;
                }

                DBConnection db = new DBConnection();
                String eventQuery = "SELECT event_id FROM event WHERE event_name = ?";
                PreparedStatement ps = db.getConnection().prepareStatement(eventQuery);
                ps.setString(1, eventName);
                ResultSet rs = ps.executeQuery();

                int eventId = -1;
                if (rs.next()) {
                    eventId = rs.getInt("event_id");
                }

                if (eventId != -1) {
                    String insertQuery = "INSERT INTO feedback(regd_no, event_id, rating, feedback_text) VALUES(?, ?, ?, ?)";
                    PreparedStatement insertPS = db.getConnection().prepareStatement(insertQuery);
                    insertPS.setString(1, regd_no);
                    insertPS.setInt(2, eventId);
                    insertPS.setInt(3, rating);
                    insertPS.setString(4, feedbackText);
                    insertPS.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Feedback submitted successfully.");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Event not found.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error while submitting feedback.");
                ex.printStackTrace();
            }
        }
    }

    private void clearFields() {
        eventDropdown.setSelectedIndex(-1);
        ratingDropdown.setSelectedIndex(0);
        feedbackArea.setText("");
    }

    public static void main(String[] args) {
        new Feedback("240720100036");
    }
}
