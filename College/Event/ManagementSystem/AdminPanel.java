package College.Event.ManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPanel extends JFrame implements ActionListener {
    JButton button1,button2,button3,button4,button5;
    public AdminPanel() {
        super("Admin Panel");

        button1 = new JButton("Home");
        button1.setBounds(20,20,100,40);
        button1.setFont(new Font("Algeria",Font.TRUETYPE_FONT,22));
        button1.setContentAreaFilled(false);
        button1.setBorderPainted(false);
        button1.addActionListener(this);
        add(button1);

        button2 = new JButton("Add Event");
        button2.setBounds(220,20,150,40);
        button2.setFont(new Font("Algeria",Font.TRUETYPE_FONT,22));
        button2.setContentAreaFilled(false);
        button2.setBorderPainted(false);
        button2.addActionListener(this);
        add(button2);

        button3 = new JButton("Modify Tickets");
        button3.setBounds(420,20,200,40);
        button3.setFont(new Font("Arial",Font.TRUETYPE_FONT,22));
        button3.setContentAreaFilled(false);
        button3.setBorderPainted(false);
        button3.addActionListener(this);
        add(button3);

        button4 = new JButton("View Tickets");
        button4.setBounds(620,20,200,40);
        button4.setFont(new Font("Algeria",Font.TRUETYPE_FONT,22));
        button4.setContentAreaFilled(false);
        button4.setBorderPainted(false);
        button4.addActionListener(this);
        add(button4);

        button5 = new JButton("Feedbacks");
        button5.setBounds(820,20,200,40);
        button5.setFont(new Font("Algeria",Font.TRUETYPE_FONT,22));
        button5.setContentAreaFilled(false);
        button5.setBorderPainted(false);
        button5.addActionListener(this);
        add(button5);

        JPanel top = new JPanel();
        top.setBackground(Color.red);
        top.setBounds(0,0,1540,80);
        add(top);

        ImageIcon image1 = new ImageIcon("Picture5.jpg");
        Image image2 = image1.getImage().getScaledInstance(1540,830,Image.SCALE_DEFAULT);
        ImageIcon image3 = new ImageIcon(image2);
        JLabel image = new JLabel(image3);
        image.setBounds(0,0,1540,830);
        add(image);

        setLayout(null);
        setSize(1540,830);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            if (e.getSource() == button2) {
                new AddEvent();
            }
            else if(e.getSource() == button1)
            {
                new Welcome();
            } else if (e.getSource() == button3) {
                new AdminUpdateTickets();
            } else if(e.getSource() == button4)
            {
                new AdminViewTickets();
            }
            else if(e.getSource() == button5)
            {
                new AdminShowFeedback();
            }
        }
        catch(Exception E)
        {
            E.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AdminPanel();
    }
}
