import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
public class ClientGui extends JComponent implements Runnable {
    JTextField usernameField;
    JTextField passwordField;
    JButton loginButton;
    JButton signupButton;
    JButton startBackButton;
    JComboBox roleDropdown;
    JButton enterButton;
    static JPanel panel;
    static MenuState state;
    static Socket socket;
    static ObjectOutputStream output;
    static ObjectInputStream input;
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginButton) {
                state = MenuState.Login;
                panel.removeAll();
                panel.repaint();

            }
            if (e.getSource() == signupButton) {
                state = MenuState.Signup;
                panel.removeAll();
                panel.repaint();
            }


        }
    };
    public static void main(String[] args) throws Exception {
        state = MenuState.Initial;
        socket = new Socket("localhost" , 9000);
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());



        SwingUtilities.invokeLater(new ClientGui());

        output.close();
        input.close();
        socket.close();
    }
    public void run() {
        JFrame frame = new JFrame("MarketPlace");
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        loginButton = new JButton("Login");
        signupButton = new JButton("Signup");
        loginButton.addActionListener(actionListener);
        loginButton.addActionListener(actionListener);

        panel = new JPanel();
        panel.add(loginButton);
        panel.add(signupButton);

        content.add(panel, BorderLayout.CENTER);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
