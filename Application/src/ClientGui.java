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
    JButton editProductButton;
    JButton editAccountButtonS;
    JButton deleteAccountButtonS;
    JButton returnToMenuButtonS;
    JButton exitS;

    //customer buttons
    JButton viewProductsButton;
    JButton viewCartButton;
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
                    } else {
                        JOptionPane.showMessageDialog(null, "Store creation failed, store already exists.",
                                "Create Store", JOptionPane.PLAIN_MESSAGE);
                    }
                    output.writeInt(800);
                    output.flush();
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
            if (e.getSource() == editProductButton) {
                System.out.println("a");
                String[] toSend = new String[5];
                ArrayList<Product> sellerProducts = new ArrayList<>();
                String sellerProductsString = "";
                try {
                    output.writeInt(203);
                    output.flush();
                    System.out.println("b");
                    sellerProducts = (ArrayList<Product>) input.readObject();
                    System.out.println("C");
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                for (int i = 0; i < sellerProducts.size(); i++) {
                    sellerProductsString += (i + 1) + " " + sellerProducts.get(i).toString();
                }
                String[] choices = {"Edit a product", "Exit"};
                String choice = (String) JOptionPane.showInputDialog(null, "Choose action:", "Choose action",
                        JOptionPane.PLAIN_MESSAGE, null, choices, choices[0]);
                System.out.println("D");
                boolean temp = true;
                boolean temp2 = true;
                while (temp) {
                    try {
                        if (choice.equals("Edit a product")) {
                            int count = 0;
                            String oldName = JOptionPane.showInputDialog(null, sellerProductsString +
                                            "\nEnter the name of the product you want to edit", "Edit Product",
                                    JOptionPane.QUESTION_MESSAGE);
                            System.out.println("e");
                            for (int i = 0; i < sellerProducts.size(); i++) {
                                if (oldName.equals(sellerProducts.get(i).getProductName())) {
                                    count++;
                                }
                            }
                            if (count == 0) {
                                throw new IllegalArgumentException();
                            }
                            toSend[4] = oldName;
                            temp = false;
                            toSend[0] = JOptionPane.showInputDialog(null, sellerProductsString +
                                            "\nEnter a new name for your product. Type \"null\" to keep the same name", "Edit Product",
                                    JOptionPane.QUESTION_MESSAGE);
                            toSend[1] = JOptionPane.showInputDialog(null, sellerProductsString +
                                            "\nEnter a new description for your product. Type \"null\" to keep the same description", "Edit Product",
                                    JOptionPane.QUESTION_MESSAGE);
                            while (temp2) {
                                try {
                                    int newStock = Integer.parseInt(JOptionPane.showInputDialog(null, sellerProductsString +
                                                    "\nEnter a new stock for your product. Type \"-1\" to keep the same stock", "Edit Product",
                                            JOptionPane.QUESTION_MESSAGE));
                                    double newPrice = Double.parseDouble(JOptionPane.showInputDialog(null, sellerProductsString +
                                                    "\nEnter a new price for your product. Type \"-1\" to keep the same price", "Edit Product",
                                            JOptionPane.QUESTION_MESSAGE));
                                    temp2 = false;
                                    toSend[2] = String.valueOf(newStock);
                                    toSend[3] = String.valueOf(newPrice);
                                } catch (NumberFormatException nfe) {
                                    JOptionPane.showMessageDialog(null, "Invalid input! Try again.",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            System.out.println("e2");
                            output.writeInt(-2);
                            output.flush();
                            output.writeObject(toSend);
                            output.flush();
                            System.out.println("f");
                            if (input.readBoolean()) {
                                JOptionPane.showMessageDialog(null, "Product edited successfully!",
                                        "Edit Account", JOptionPane.PLAIN_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "Editing product failed.",
                                        "Error", JOptionPane.PLAIN_MESSAGE);
                            }
                            System.out.println("g");
                        } else if (choice.equals("Exit")) {
                            output.writeInt(-1);
                            output.flush();
                            return;
                        }
                    } catch (IllegalArgumentException iae) {
                        JOptionPane.showMessageDialog(null, "No product with such name!",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            if (e.getSource() == editAccountButtonS) {
                try {
                    output.writeInt(300);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    String newEmail = JOptionPane.showInputDialog(null, "Enter your new e-mail.", "Edit Account",
                            JOptionPane.QUESTION_MESSAGE);
                    output.writeObject(newEmail);
                    output.flush();
                    String newPass = JOptionPane.showInputDialog(null, "Enter your new password.", "Edit Account",
                            JOptionPane.QUESTION_MESSAGE);
                    output.writeObject(newPass);
                    output.flush();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    if (input.readBoolean()) {
                        JOptionPane.showMessageDialog(null, "Account edited successfully!",
                                "Edit Account", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Account editing failed, invalid e-mail format!",
                                "Error", JOptionPane.PLAIN_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource() == returnToMenuButtonS) {
                cardLayout.show(cardPanel, "seller");
                try {
                    output.writeInt(800);
                    output.flush();
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
                StringBuilder list = new StringBuilder();
                boolean temp = true;
                int index = -1;
                ArrayList<Product> productsList = null;
                try {
                    output.writeInt(100);
                    output.flush();
                    productsList = (ArrayList<Product>) input.readObject();
                    for (Product product : productsList) {
                        System.out.println(product.getStock());
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

                String[] choices = {"Sort by price", "Sort by stock"};
                String[] order = {"Sort from highest to lowest", "Sort from lowest to highest"};
                String choice = (String) JOptionPane.showInputDialog(null, "Select role", "Sign Up",
                        JOptionPane.PLAIN_MESSAGE, null, choices, choices[0]);

                if (choice.equals("Sort by price")) {
                    String sort = (String) JOptionPane.showInputDialog(null, "Sort by price", "View products",
                            JOptionPane.PLAIN_MESSAGE, null, order, order[1]);
                    if (sort.equals("Sort from lowest to highest")) {
                        Product.sortPrice(true, productsList);
                        for (int i = 1; i <= productsList.size(); i++) {
                            list.append(i).append(". ").append(productsList.get(i - 1));
                        }
                        while (temp) {
                            try {
                                index = Integer.parseInt(JOptionPane.showInputDialog(null, list + "\nEnter the index of the product you want to know " +
                                        "more about:", "Price from lowest to highest", JOptionPane.QUESTION_MESSAGE));
                                temp = false;
                            } catch (NumberFormatException nfe) {
                                JOptionPane.showMessageDialog(null, "Invalid input! Index must be an Integer.",
                                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {
                        Product.sortPrice(false, productsList);
                        for (int i = 1; i <= productsList.size(); i++) {
                            list.append(i).append(". ").append(productsList.get(i - 1));
                        }
                        while (temp) {
                            try {
                                index = Integer.parseInt(JOptionPane.showInputDialog(null, list + "\nEnter the index of the product you want to know " +
                                        "more about:", "Price from highest to lowest", JOptionPane.QUESTION_MESSAGE));
                                temp = false;
                            } catch (NumberFormatException nfe) {
                                JOptionPane.showMessageDialog(null, "Invalid input! Index must be an Integer.",
                                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                } else if (choice.equals("Sort by stock")) {
                    String sort = (String) JOptionPane.showInputDialog(null, "Sort by stock", "View products",
                            JOptionPane.PLAIN_MESSAGE, null, order, order[0]);
                    if (sort.equals("Sort from lowest to highest")) {
                        Product.sortStock(true, productsList);
                        for (int i = 1; i <= productsList.size(); i++) {
                            list.append(i).append(". ").append(productsList.get(i - 1));
                        }
                        while (temp) {
                            try {
                                index = Integer.parseInt(JOptionPane.showInputDialog(null, list + "\nEnter the index of the product you want to know " +
                                        "more about:", "Stock from lowest to highest", JOptionPane.QUESTION_MESSAGE));
                                temp = false;
                            } catch (NumberFormatException nfe) {
                                JOptionPane.showMessageDialog(null, "Invalid input! Index must be an Integer.",
                                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {
                        Product.sortStock(false, productsList);
                        for (int i = 1; i <= productsList.size(); i++) {
                            list.append(i).append(". ").append(productsList.get(i - 1));
                        }
                        while (temp) {
                            try {
                                index = Integer.parseInt(JOptionPane.showInputDialog(null, list + "\nEnter the index of the product you want to know " +
                                        "more about:", "Stock from highest to lowest", JOptionPane.QUESTION_MESSAGE));
                                temp = false;
                            } catch (NumberFormatException nfe) {
                                JOptionPane.showMessageDialog(null, "Invalid input! Index must be an Integer.",
                                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
                String product = String.format("%d - Store: %s | Product: %s | Price: %.2f | " +
                                "Remaining Stock: %d\n", index,
                        productsList.get(index - 1).getStore().getStoreName(),
                        productsList.get(index - 1).getProductName(), productsList.get(index - 1).getPrice(),
                        productsList.get(index - 1).getStock());
                String[] actions = {"Purchase this item individually", "Add this item to cart", "Exit"};
                String choice2 = (String) JOptionPane.showInputDialog(null, product + "Choose action:", "Choose action",
                        JOptionPane.PLAIN_MESSAGE, null, actions, actions[0]);
                temp = true;

                String[] toPurchase = new String[2];
                switch (choice2) {
                    case "Purchase this item individually" -> {
                        /*
                         VALUE DOES NOT UPDATE IMMEDIATELY NEEDS FIX
                        */
                        try {
                            output.writeInt(403);
                            output.flush();
                            while (temp) {
                                try {
                                    int quantity = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the quantity you would like to purchase",
                                            "Purchase item", JOptionPane.QUESTION_MESSAGE));
                                    toPurchase[0] = productsList.get(index - 1).getProductName();
                                    toPurchase[1] = String.valueOf(quantity);
                                    output.writeObject(toPurchase);
                                    output.flush();
                                    temp = false;
                                } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                                    JOptionPane.showMessageDialog(null, "Invalid input! Index must be a valid integer.",
                                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    case "Add this item to cart" -> {
                        try {
                            output.writeInt(401);
                            output.flush();
                            while (temp) {
                                try {
                                    int quantity = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the quantity you would like to add to cart",
                                            "Add to cart", JOptionPane.QUESTION_MESSAGE));
                                    toPurchase[0] = productsList.get(index - 1).getProductName();
                                    toPurchase[1] = String.valueOf(quantity);
                                    output.writeObject(toPurchase);
                                    output.flush();
                                    temp = false;
                                } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                                    JOptionPane.showMessageDialog(null, "Invalid input! Index must be a valid integer.",
                                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    case "Exit" -> {
                        try {
                            output.writeInt(800);
                            output.flush();
                            return;
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                try {
                    if (input.readBoolean()) {
                        JOptionPane.showMessageDialog(null, "Item purchased successfully!",
                                "Purchase item", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Item purchase failed.",
                                "Error", JOptionPane.PLAIN_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource() == viewCartButton) {
                ArrayList<Product> cart = null;
                StringBuilder cartList = new StringBuilder();
                try {
                    output.writeInt(400);
                    output.flush();
                    cart = (ArrayList<Product>) input.readObject();
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                for (int i = 0; i < cart.size(); i++) {
                    cartList.append(cart.get(i).toString2());
                }
                /*
                 VALUE DOES NOT UPDATE IMMEDIATELY NEEDS FIX
                */
                JOptionPane.showMessageDialog(null, cartList.toString(), "View Cart", JOptionPane.PLAIN_MESSAGE);
                String[] actions = {"Purchase all items in this cart", "Exit"};
                String choice = (String) JOptionPane.showInputDialog(null, cartList + "\nChoose action:", "Choose action",
                        JOptionPane.PLAIN_MESSAGE, null, actions, actions[1]);
                if (choice.equals("Purchase all items in this cart")) {
                    double cost = 0;
                    try {
                        output.writeInt(402);
                        output.flush();
                        cost = input.readDouble();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    JOptionPane.showMessageDialog(null, String.format("Purchasing all items in this cart for $%.2f", cost),
                            "Purchase cart", JOptionPane.PLAIN_MESSAGE);
                    try {
                        if (input.readBoolean()) {
                            JOptionPane.showMessageDialog(null, "Cart purchased successfully!",
                                    "Purchase cart", JOptionPane.PLAIN_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Cart could not be purchased!",
                                    "Error", JOptionPane.PLAIN_MESSAGE);
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (choice.equals("Exit")) {
                    return;
                }
            }
            if (e.getSource() == returnToMenuButtonC) {
                cardLayout.show(cardPanel, "customer");
                try {
                    output.writeInt(800);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            if (e.getSource() == exitC) {
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

        //seller main panel
        JPanel sellerPanel = new JPanel();
        viewStoresButton = new JButton("View Stores");
        createStoresButton = new JButton("Create Store");
        addProductButton = new JButton("Add Product");
        editProductButton = new JButton("Edit Product");
        editAccountButtonS = new JButton("Edit Account");
        deleteAccountButtonS = new JButton("Delete Account");
        returnToMenuButtonS = new JButton("Return to menu");
        exitS = new JButton("Exit Application");

        sellerPanel.add(viewStoresButton);
        sellerPanel.add(createStoresButton);
        sellerPanel.add(addProductButton);
        sellerPanel.add(editProductButton);
        sellerPanel.add(editAccountButtonS);
        sellerPanel.add(returnToMenuButtonS);
        sellerPanel.add(exitS);

        viewStoresButton.addActionListener(actionListener);
        returnToMenuButtonS.addActionListener(actionListener);
        createStoresButton.addActionListener(actionListener);
        editAccountButtonS.addActionListener(actionListener);
        deleteAccountButtonS.addActionListener(actionListener);
        addProductButton.addActionListener(actionListener);
        exitS.addActionListener(actionListener);
        editProductButton.addActionListener(actionListener);

        //customer main panel
        JPanel customerPanel = new JPanel();
        viewProductsButton = new JButton("View Products");
        viewCartButton = new JButton("View Cart");
        returnToMenuButtonC = new JButton("Return to menu");
        exitC = new JButton("Exit Application");

        customerPanel.add(viewProductsButton);
        customerPanel.add(viewCartButton);
        customerPanel.add(returnToMenuButtonC);
        customerPanel.add(exitC);

        viewProductsButton.addActionListener(actionListener);
        viewCartButton.addActionListener(actionListener);
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
/*seller cases
100 - completed
200 - completed
201 - completed
202 - NOT completed
203 - completed BUT bugged
204 - NOT completed
300 - completed BUT bugged
301 - not used?
400 - NOT completed
500 - completed
601 - NOT completed
700 - NOT completed
701 - NOT completed
900 - completed
 */

/*customer cases
100 - completed BUT bugged
200 - completed BUT bugged
300 - NOT completed
400 - completed BUT bugged
401 - completed
402 - completed
403 - completed
404 - NOT completed
501 - NOT completed
600 - NOT completed
701 - NOT completed
702 - NOT completed
800 - completed
900 - completed
 */