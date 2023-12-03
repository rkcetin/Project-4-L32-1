import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class ClientGui extends JComponent implements Runnable {

    static Socket socket;
    static ObjectOutputStream output;
    static ObjectInputStream input;

    JButton loginButton;
    JButton signupButton;

    //seller buttons
    JButton viewStoresButton;
    JButton createStoresButton;
    JButton addProductButton;
    JButton returnToMenuButtonS;
    JButton exitS;

    //customer buttons
    JButton viewProductsButton;
    JButton returnToMenuButtonC;
    JButton exitC;

    JPanel cardPanel;
    CardLayout cardLayout;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginButton) {
                try {
                    output.writeInt(0);
                    output.flush();
                    output.writeBoolean(false);
                    output.flush();
                    output.writeInt(1);
                    output.flush();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                String[] userInfo = new String[2];
                userInfo[0] = JOptionPane.showInputDialog(null, "Enter your e-mail:", "Login",
                        JOptionPane.QUESTION_MESSAGE);
                userInfo[1] = JOptionPane.showInputDialog(null, "Enter your password:", "Login",
                        JOptionPane.QUESTION_MESSAGE);
                try {
                    output.writeObject(userInfo);
                    output.flush();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    if (input.readBoolean()) {
                        System.out.println("a");
                        int choice = input.readInt();
                        if (choice == 1) {
                            System.out.println("b");
                            cardLayout.show(cardPanel, "seller");
                        } else if (choice == 2) {
                            cardLayout.show(cardPanel, "customer");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect password or E-mail not registered.",
                                "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            if (e.getSource() == signupButton) {
                try {
                    output.writeInt(0);
                    output.flush();
                    output.writeBoolean(true);
                    output.flush();
                    output.writeInt(1);
                    output.flush();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                String[] userInfo = new String[3];
                userInfo[0] = JOptionPane.showInputDialog(null, "Enter your sign-up e-mail:", "Sign Up",
                        JOptionPane.QUESTION_MESSAGE);
                userInfo[1] = JOptionPane.showInputDialog(null, "Enter your sign-up password:", "Sign Up",
                        JOptionPane.QUESTION_MESSAGE);
                String[] roles = {"Seller", "Customer"};
                String choice = (String) JOptionPane.showInputDialog(null, "Select role", "Sign Up",
                        JOptionPane.PLAIN_MESSAGE, null, roles, roles[0]);
                userInfo[2] = "1";
                if (choice.equals("Customer")) {
                    userInfo[2] = "2";
                }
                try {
                    output.writeObject(userInfo);
                    output.flush();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    if (input.readBoolean()) {
                        if (userInfo[2].equals("1")) {
                            cardLayout.show(cardPanel, "seller");
                        } else {
                            cardLayout.show(cardPanel, "customer");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid e-mail format or e-mail already registered",
                                "Sign Up Failed", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            //seller logic
            if (e.getSource() == viewStoresButton) {
                try {
                    output.writeInt(100);
                    output.flush();
                    String storeList = (String) input.readObject();

                    JOptionPane.showMessageDialog(null, storeList, "Store List", JOptionPane.PLAIN_MESSAGE);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            if (e.getSource() == createStoresButton) {
                try {
                    output.writeInt(200);
                    output.flush();
                    String storeName = JOptionPane.showInputDialog(null, "Enter the name of the store.", "Create Store",
                            JOptionPane.QUESTION_MESSAGE);
                    output.writeObject(storeName);
                    output.flush();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                try {
                    System.out.println("a");
                    if (input.readBoolean()) {
                        JOptionPane.showMessageDialog(null, "Store created successfully!",
                                "Create Store", JOptionPane.PLAIN_MESSAGE);
                        output.writeInt(800);
                        output.flush();
                    } else {
                        JOptionPane.showMessageDialog(null, "Store creation failed, store already exists.",
                                "Create Store", JOptionPane.PLAIN_MESSAGE);
                        output.writeInt(800);
                        output.flush();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource() == addProductButton) {
                try {
                    output.writeInt(201);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                boolean temp = true;
                String[] productInfo = new String[5];
                productInfo[0] = JOptionPane.showInputDialog(null, "Enter the name of the store you want to add your product to", "Add Products",
                        JOptionPane.QUESTION_MESSAGE);
                productInfo[1] = JOptionPane.showInputDialog(null, "Enter the name of the product.", "Add Products",
                        JOptionPane.QUESTION_MESSAGE);
                productInfo[2] = JOptionPane.showInputDialog(null, "Enter a description of the product.", "Add Products",
                        JOptionPane.QUESTION_MESSAGE);
                while (temp) {
                    try {
                        int a = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the product stock.", "Add Products",
                                JOptionPane.QUESTION_MESSAGE));
                        productInfo[3] = String.valueOf(a);
                        temp = false;
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(null, "Invalid input! Stock must be an Integer.",
                                "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                }
                temp = true;
                while (temp) {
                    try {
                        double b = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter the price of the product", "Add Products",
                                JOptionPane.QUESTION_MESSAGE));
                        productInfo[4] = String.valueOf(String.format("%.2f", b));
                        temp = false;
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(null, "Invalid input! Price must be a number",
                                "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                }
                try {
                    output.writeObject(productInfo);
                    output.flush();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                try {
                    boolean a = input.readBoolean();
                    System.out.println(a);
                    if (a) {
                        JOptionPane.showMessageDialog(null, "Product successfully added!",
                                "Add Product", JOptionPane.PLAIN_MESSAGE);
                        output.writeInt(800);
                        output.flush();
                    } else {
                        JOptionPane.showMessageDialog(null, "Adding the product failed, store does not exist.",
                                "Error!", JOptionPane.ERROR_MESSAGE);
                        output.writeInt(800);
                        output.flush();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource() == returnToMenuButtonS) {
                cardLayout.show(cardPanel, "seller");
                try {
                    output.writeInt(800);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            if (e.getSource() == exitS) {
                try {
                    output.writeInt(900);
                    output.close();
                    input.close();
                    socket.close();
                    System.exit(0);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            //customer logic
            if (e.getSource() == viewProductsButton) {
                try {
                    output.writeInt(100);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    ArrayList<Product> productsList = (ArrayList<Product>) input.readObject();
                    for (int i = 0; i < productsList.size(); i++) {
                        System.out.println(productsList.get(i));
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            if (e.getSource() == returnToMenuButtonC) {
                cardLayout.show(cardPanel, "customer");
                try {
                    output.writeInt(8);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            if (e.getSource() == exitC) {
                try {
                    output.writeInt(9);
                    output.close();
                    input.close();
                    socket.close();
                    System.exit(0);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    };

    public static void main(String[] args) throws Exception {
        socket = new Socket("localhost" , 9000);
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());

        SwingUtilities.invokeLater(() -> {
            try {
                new ClientGui().run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void run() {
        JFrame frame = new JFrame("MarketPlace");

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        //login main panel
        JPanel loginPanel = new JPanel(new GridLayout(3, 1, 0, 10));
        loginButton = new JButton("Login");
        signupButton = new JButton("Signup");

        JLabel titleLabel = new JLabel("Welcome to the Marketplace!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        loginPanel.add(titleLabel);

        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginPanel.add(loginButton);
        signupButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginPanel.add(signupButton);

        loginButton.addActionListener(actionListener);
        signupButton.addActionListener(actionListener);

        //seller panel
        JPanel sellerPanel = new JPanel();
        viewStoresButton = new JButton("View Stores");
        createStoresButton = new JButton("Create Store");
        addProductButton = new JButton("Add Product");
        returnToMenuButtonS = new JButton("Return to menu");
        exitS = new JButton("Exit Application");

        sellerPanel.add(viewStoresButton);
        sellerPanel.add(createStoresButton);
        sellerPanel.add(addProductButton);
        sellerPanel.add(returnToMenuButtonS);
        sellerPanel.add(exitS);

        viewStoresButton.addActionListener(actionListener);
        returnToMenuButtonS.addActionListener(actionListener);
        createStoresButton.addActionListener(actionListener);
        addProductButton.addActionListener(actionListener);
        exitS.addActionListener(actionListener);

        //customer main panel
        JPanel customerPanel = new JPanel();
        viewProductsButton = new JButton("View Products");
        returnToMenuButtonC = new JButton("Return to menu");
        exitC = new JButton("Exit Application");

        customerPanel.add(viewProductsButton);
        customerPanel.add(returnToMenuButtonC);
        customerPanel.add(exitC);

        viewProductsButton.addActionListener(actionListener);
        returnToMenuButtonC.addActionListener(actionListener);
        exitC.addActionListener(actionListener);


        cardPanel.add(loginPanel, "login");
        cardPanel.add(customerPanel, "customer");
        cardPanel.add(sellerPanel, "seller");



        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(cardPanel);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
