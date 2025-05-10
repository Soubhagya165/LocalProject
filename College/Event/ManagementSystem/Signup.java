package College.Event.ManagementSystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Signup extends JFrame implements ActionListener {
    JTextField textname, textemail, textpassword, textcpassword, textaddress;
    JRadioButton gender1, gender2, gender3;
    JButton next;
    JComboBox newbranch;

    public Signup() {
        super("Sign Up");

        JLabel label1 = new JLabel("SIGN UP");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("Algeria", Font.BOLD, 50));
        label1.setBounds(620, 40, 500, 80);
        add(label1);

        JLabel name = new JLabel("Name : ");
        name.setForeground(Color.WHITE);
        name.setBounds(400, 160, 100, 40);
        name.setFont(new Font("Algeria", Font.BOLD, 28));
        add(name);

        textname = new JTextField();
        textname.setFont(new Font("Algeria", Font.BOLD, 20));
        textname.setBounds(600, 160, 600, 40);
        add(textname);

        JLabel email = new JLabel("Email : ");
        email.setForeground(Color.WHITE);
        email.setBounds(400, 220, 100, 40);
        email.setFont(new Font("Algeria", Font.BOLD, 28));
        add(email);

        textemail = new JTextField();
        textemail.setFont(new Font("Algeria", Font.BOLD, 20));
        textemail.setBounds(600, 220, 600, 40);
        add(textemail);

        JLabel password = new JLabel("Regd. no : ");
        password.setForeground(Color.WHITE);
        password.setBounds(400, 280, 200, 40);
        password.setFont(new Font("Algeria", Font.BOLD, 28));
        add(password);

        textpassword = new JTextField();
        textpassword.setFont(new Font("Algeria", Font.BOLD, 20));
        textpassword.setBounds(600, 280, 600, 40);
        add(textpassword);

        JLabel cpassword = new JLabel("Password : ");
        cpassword.setForeground(Color.WHITE);
        cpassword.setBounds(400, 340, 400, 40);
        cpassword.setFont(new Font("Algeria", Font.BOLD, 28));
        add(cpassword);

        textcpassword = new JTextField();
        textcpassword.setFont(new Font("Algeria", Font.BOLD, 20));
        textcpassword.setBounds(600, 340, 600, 40);
        add(textcpassword);

        JLabel gender = new JLabel("Gender : ");
        gender.setForeground(Color.WHITE);
        gender.setBounds(400, 400, 200, 40);
        gender.setFont(new Font("Algeria", Font.BOLD, 28));
        add(gender);

        gender1 = new JRadioButton("Male");
        gender1.setBounds(600, 400, 130, 40);
        gender1.setFont(new Font("Algeria", Font.BOLD, 24));
        add(gender1);

        gender2 = new JRadioButton("Female");
        gender2.setBounds(750, 400, 130, 40);
        gender2.setFont(new Font("Algeria", Font.BOLD, 24));
        add(gender2);

        gender3 = new JRadioButton("Other");
        gender3.setBounds(900, 400, 130, 40);
        gender3.setFont(new Font("Algeria", Font.BOLD, 24));
        add(gender3);

        ButtonGroup button = new ButtonGroup();
        button.add(gender1);
        button.add(gender2);
        button.add(gender3);

        JLabel contact = new JLabel("Branch. : ");
        contact.setForeground(Color.WHITE);
        contact.setBounds(400, 460, 200, 40);
        contact.setFont(new Font("Algeria", Font.BOLD, 28));
        add(contact);

        String[] branch = {"MCA","Btech","BCA","BPharma","MBA"};
        newbranch = new JComboBox(branch);
        newbranch.setBounds(600,460,600,40);
        newbranch.setFont(new Font("Algeria",Font.BOLD,20));
        add(newbranch);

        JLabel address = new JLabel("Contact no. : ");
        address.setForeground(Color.WHITE);
        address.setBounds(400, 520, 200, 40);
        address.setFont(new Font("Algeria", Font.BOLD, 28));
        add(address);

        textaddress = new JTextField();
        textaddress.setFont(new Font("Algeria", Font.BOLD, 20));
        textaddress.setBounds(600, 520, 600, 40);
        add(textaddress);

        next = new JButton("Next");
        next.setBounds(650, 700, 150, 30);
        next.setFont(new Font("Algeria", Font.BOLD, 26));
        next.addActionListener(this);
        add(next);

        ImageIcon image1 = new ImageIcon("Picture2.jpg");
        Image image2 = image1.getImage().getScaledInstance(1540,830,Image.SCALE_DEFAULT);
        ImageIcon image3 = new ImageIcon(image2);
        JLabel image = new JLabel(image3);
        image.setBounds(0,0,1540,830);
        add(image);

        setLayout(null);
        setSize(1540, 830);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String Name = textname.getText();
        String Email = textemail.getText();
        String regdno = textpassword.getText();
        String Password = textcpassword.getText();
        String Gender = null;
        if (gender1.isSelected()) {
            Gender = "Male";
        } else if (gender2.isSelected()) {
            Gender = "Female";
        } else if (gender3.isSelected()) {
            Gender = "Other";
        }

        String branch = newbranch.getSelectedItem().toString();
        String contact = textaddress.getText();

        try {
            if (textpassword.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Fill All the Fields");
            } else {
                DBConnection connect = new DBConnection();
                String query = "insert into users values('" + Name + "','" + Email + "','" + regdno + "','" + Password + "','" + Gender + "','" + branch + "','" + contact + "')";
                connect.statement.executeUpdate(query);
                new AdminPanel();
                setVisible(false);
            }
        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Signup();
    }
}
