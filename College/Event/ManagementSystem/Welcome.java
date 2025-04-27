package College.Event.ManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Welcome extends JFrame implements ActionListener {

    JButton login;
    JButton signup;

    public Welcome()
    {
        super("Welcome");

        JLabel wel = new JLabel("Welcome To");
        wel.setBounds(500,200,1000,150);
        wel.setFont(new Font("Arial",Font.BOLD,90));
        add(wel);

        JLabel welc = new JLabel("Centurion University of Technology and Management");
        welc.setBounds(300,300,1100,150);
        welc.setFont(new Font("Arial",Font.BOLD,40));
        add(welc);

        login = new JButton("ADMIN");
        login.setBounds(650,440,100,40);
        login.addActionListener(this);
        add(login);

        signup = new JButton("USER");
        signup.setBounds(750,440,100,40);
        signup.addActionListener(this);
        add(signup);

        ImageIcon image1 = new ImageIcon("Picture3.png");
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
        if(e.getSource() == login)
        {
            new AdminLogin();
        }
        else if(e.getSource() == signup)
        {
            new Login();
        }
    }

    public static void main(String[] args) {
        new Welcome();
    }
}
