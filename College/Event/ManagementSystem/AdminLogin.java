package College.Event.ManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminLogin extends JFrame implements ActionListener
{

    JTextField textname;
    JPasswordField textpassword;
    JButton button1,button2,button3;
    public AdminLogin()
    {
        super("College AddEvent Management System");


        JLabel label1 = new JLabel("COLLEGE EVENT MANAGEMENT SYSTEM");
        label1.setFont(new Font("Algeria",Font.BOLD,50));
        label1.setBounds(250,120,1100,90);
        label1.setForeground(Color.WHITE);
        add(label1);

        JLabel name = new JLabel("UserPanel ID : ");
        name.setBounds(400,260,300,40);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Algeria",Font.BOLD,38));
        add(name);

        textname = new JTextField();
        textname.setFont(new Font("Algeria",Font.BOLD,20));
        textname.setBounds(700,260,400,40);
        add(textname);

        JLabel password = new JLabel("Password : ");
        password.setBounds(400,320,300,40);
        password.setForeground(Color.WHITE);
        password.setFont(new Font("Algeria",Font.BOLD,38));
        add(password);

        textpassword = new JPasswordField();
        textpassword.setFont(new Font("Algeria",Font.BOLD,20));
        textpassword.setBounds(700,320,400,40);
        add(textpassword);

        button1 = new JButton("Login");
        button1.setBounds(700,410,150,40);
        button1.setFont(new Font("Algeria",Font.BOLD,22));
        button1.addActionListener(this);
        add(button1);

        button3 = new JButton("CLEAR");
        button3.setBounds(900,410,150,40);
        button3.setFont(new Font("Algeria",Font.BOLD,22));
        button3.addActionListener(this);
        add(button3);

        ImageIcon image1 = new ImageIcon("backbg.png");
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
    public void actionPerformed(ActionEvent e)
    {
        try {
            if (e.getSource() == button1)
            {
                String regd_no = textname.getText();
                String Password = textpassword.getText();

                DBConnection db = new DBConnection();
                Connection conn = db.getConnection();

                String query = "SELECT * FROM admin WHERE regd_no=? AND Password=?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, regd_no);
                ps.setString(2, Password);

                ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }
                new AdminPanel();

                rs.close();
                ps.close();
                conn.close();
            }

            else if(e.getSource() == button2)
            {
//                new Signup();
                setVisible(false);
            }
            else if(e.getSource() == button3)
            {
                textname.setText("");
                textpassword.setText("");
            }

        }
        catch(Exception E)
        {
            E.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AdminLogin();
    }
}

