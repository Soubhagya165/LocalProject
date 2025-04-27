package College.Event.ManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEvent extends JFrame implements ActionListener {

    JTextField textevent,textname,textprice;
    JButton add;
    public AddEvent()
    {
        JLabel event = new JLabel("Event_id");
        event.setBounds(500,150,200,40);
        event.setFont(new Font("Arial",Font.BOLD,24));
        add(event);

        textevent = new JTextField();
        textevent.setBounds(700,150,400,40);
        textevent.setFont(new Font("Arial",Font.BOLD,24));
        add(textevent);

        JLabel eventn = new JLabel("Event_name");
        eventn.setBounds(500,250,200,40);
        eventn.setFont(new Font("Arial",Font.BOLD,24));
        add(eventn);

        textname = new JTextField();
        textname.setBounds(700,250,400,40);
        textname.setFont(new Font("Arial",Font.BOLD,24));
        add(textname);

        JLabel eventnew = new JLabel("Ticket_Price");
        eventnew.setBounds(500,350,200,40);
        eventnew.setFont(new Font("Arial",Font.BOLD,24));
        add(eventnew);

        textprice = new JTextField();
        textprice.setBounds(700,350,400,40);
        textprice.setFont(new Font("Arial",Font.BOLD,24));
        add(textprice);

        add = new JButton("ADD");
        add.setBounds(700,500,100,40);
        add.addActionListener(this);
        add(add);

        setLayout(null);
        setSize(1540,830);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String id = textevent.getText();
        String name = textname.getText();
        String price = textprice.getText();

        try
        {
            DBConnection obj = new DBConnection();
            String query = "insert into event values('"+id+"','"+name+"','"+price+"')";
            obj.statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null,"AddEvent Added");
        }
        catch(Exception E)
        {
            E.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AddEvent();
    }
}

