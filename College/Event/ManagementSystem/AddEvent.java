package College.Event.ManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;

public class AddEvent extends JFrame implements ActionListener {
    JTextField textEventName, textLocation, textDate, textPrice;
    JButton addButton;

    public AddEvent() {
        setTitle("Add Event");
        setLayout(null);
        setSize(1540, 830);
        setLocationRelativeTo(null);
        setResizable(false);

        Font labelFont = new Font("Arial", Font.BOLD, 18);
        Font textFieldFont = new Font("Arial", Font.BOLD, 16);

        int formWidth = 800;
        int formHeight = 450;
        int leftMargin = (1540 - formWidth) / 2;
        int topMargin = (830 - formHeight) / 2;

        JLabel l1 = new JLabel("Event Name:");
        l1.setBounds(leftMargin, topMargin + 50, 200, 40);
        l1.setFont(labelFont);
        add(l1);

        textEventName = new JTextField();
        textEventName.setBounds(leftMargin + 200, topMargin + 50, 400, 40);
        textEventName.setFont(textFieldFont);
        add(textEventName);

        JLabel l2 = new JLabel("Location:");
        l2.setBounds(leftMargin, topMargin + 120, 200, 40);
        l2.setFont(labelFont);
        add(l2);

        textLocation = new JTextField();
        textLocation.setBounds(leftMargin + 200, topMargin + 120, 400, 40);
        textLocation.setFont(textFieldFont);
        add(textLocation);

        JLabel l3 = new JLabel("Date (YYYY-MM-DD):");
        l3.setBounds(leftMargin, topMargin + 190, 250, 40);
        l3.setFont(labelFont);
        add(l3);

        textDate = new JTextField();
        textDate.setBounds(leftMargin + 250, topMargin + 190, 350, 40);
        textDate.setFont(textFieldFont);
        add(textDate);

        JLabel l4 = new JLabel("Ticket Price:");
        l4.setBounds(leftMargin, topMargin + 260, 200, 40);
        l4.setFont(labelFont);
        add(l4);

        textPrice = new JTextField();
        textPrice.setBounds(leftMargin + 200, topMargin + 260, 400, 40);
        textPrice.setFont(textFieldFont);
        add(textPrice);

        addButton = new JButton("Add Event");
        addButton.setBounds(leftMargin + 200, topMargin + 350, 400, 50);
        addButton.setFont(new Font("Arial", Font.BOLD, 18));
        addButton.addActionListener(this);
        add(addButton);

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
        try {
            String name = textEventName.getText();
            String location = textLocation.getText();
            String date = textDate.getText();
            double price = Double.parseDouble(textPrice.getText());

            DBConnection db = new DBConnection();
            String query = "INSERT INTO event(event_name, location, event_date, ticket_price) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = db.getConnection().prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, location);
            ps.setString(3, date);
            ps.setDouble(4, price);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Event added successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AddEvent();
    }
}
