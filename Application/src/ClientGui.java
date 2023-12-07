import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    JButton viewStatisticsButtonS;
    JButton returnToMenuButtonS;
    JButton exitS;
    JButton deleteAccountButtonS;

    //customer buttons
    JButton viewProductsButton;
    JButton viewCartButton;
    JButton viewTransactionsButton;
    JButton viewStatisticsButtonC;
    JButton returnToMenuButtonC;
    JButton deleteAccountButtonC;
    JButton exitC;

    JPanel cardPanel;
    CardLayout cardLayout;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginButton) {
                login();
            }
            if (e.getSource() == signupButton) {
                signup();
            }

            //seller logic
            if (e.getSource() == viewStoresButton) {
                viewStores();
            }
            if (e.getSource() == createStoresButton) {
                createStore();
            }
            if (e.getSource() == addProductButton) {
                addProduct();
            }
            if (e.getSource() == editProductButton) {
                editProduct();
            }
            if (e.getSource() == editAccountButtonS) {
                editAccount();
            }
            if (e.getSource() == viewStatisticsButtonS) {
                viewStatistics();
            }
            if (e.getSource() == returnToMenuButtonS) {
                returnToSellerMenu();
            }
            if (e.getSource() == exitS) {
                exit();
            }
            if (e.getSource() == deleteAccountButtonS) {
                deleteSeller();
            }

            //customer logic
            if (e.getSource() == viewProductsButton) {
                viewProducts();
            }
            if (e.getSource() == viewCartButton) {
                viewCart();
            }
            if (e.getSource() == returnToMenuButtonC) {
                returnToCustomerMenu();
            }
            if (e.getSource() == viewTransactionsButton) {
                viewCustomerTransactionHistory();
            }
            if (e.getSource() == viewStatisticsButtonC) {
                viewCustomerStatistics();
            }
            if (e.getSource() == exitC) {
                exit();
            }
            if (e.getSource() == deleteAccountButtonC) {
                deleteCustomer();
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
        viewStatisticsButtonS = new JButton("View Statistics");
        deleteAccountButtonS = new JButton("Delete Account");
        returnToMenuButtonS = new JButton("Return to menu");
        exitS = new JButton("Exit Application");

        sellerPanel.add(viewStoresButton);
        sellerPanel.add(createStoresButton);
        sellerPanel.add(addProductButton);
        sellerPanel.add(editProductButton);
        sellerPanel.add(editAccountButtonS);
        sellerPanel.add(viewStatisticsButtonS);
        sellerPanel.add(returnToMenuButtonS);
        sellerPanel.add(exitS);
        sellerPanel.add(deleteAccountButtonS);

        viewStoresButton.addActionListener(actionListener);
        createStoresButton.addActionListener(actionListener);
        addProductButton.addActionListener(actionListener);
        editProductButton.addActionListener(actionListener);
        editAccountButtonS.addActionListener(actionListener);
        viewStatisticsButtonS.addActionListener(actionListener);
        returnToMenuButtonS.addActionListener(actionListener);
        exitS.addActionListener(actionListener);
        deleteAccountButtonS.addActionListener(actionListener);


        //customer main panel
        JPanel customerPanel = new JPanel();
        viewProductsButton = new JButton("View Products");
        viewCartButton = new JButton("View Cart");
        viewTransactionsButton = new JButton("View/Extract Transaction History");
        viewStatisticsButtonC = new JButton("View statistics");
        returnToMenuButtonC = new JButton("Return to menu");
        exitC = new JButton("Exit Application");
        deleteAccountButtonC = new JButton("Delete Account");

        customerPanel.add(viewProductsButton);
        customerPanel.add(viewCartButton);
        customerPanel.add(viewTransactionsButton);
        customerPanel.add(viewStatisticsButtonC);
        customerPanel.add(returnToMenuButtonC);
        customerPanel.add(exitC);
        customerPanel.add(deleteAccountButtonC);

        viewProductsButton.addActionListener(actionListener);
        viewCartButton.addActionListener(actionListener);
        viewTransactionsButton.addActionListener(actionListener);
        viewStatisticsButtonC.addActionListener(actionListener);
        returnToMenuButtonC.addActionListener(actionListener);
        exitC.addActionListener(actionListener);
        deleteAccountButtonC.addActionListener(actionListener);


        cardPanel.add(loginPanel, "login");
        cardPanel.add(customerPanel, "customer");
        cardPanel.add(sellerPanel, "seller");


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(cardPanel);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void login() {
        try {
            output.writeInt(0);
            output.flush();
            output.writeBoolean(false);
            output.flush();
            output.writeInt(1);
            output.flush();

            String[] userInfo = new String[2];
            userInfo[0] = JOptionPane.showInputDialog(null, "Enter your e-mail:", "Login", JOptionPane.QUESTION_MESSAGE);
            userInfo[1] = JOptionPane.showInputDialog(null, "Enter your password:", "Login", JOptionPane.QUESTION_MESSAGE);

            output.writeObject(userInfo);
            output.flush();

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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void signup() {
        try {
            output.writeInt(0);
            output.flush();
            output.writeBoolean(true);
            output.flush();
            output.writeInt(1);
            output.flush();

            String[] userInfo = new String[3];
            userInfo[0] = JOptionPane.showInputDialog(null, "Enter your sign-up e-mail:", "Sign Up", JOptionPane.QUESTION_MESSAGE);
            userInfo[1] = JOptionPane.showInputDialog(null, "Enter your sign-up password:", "Sign Up", JOptionPane.QUESTION_MESSAGE);
            String[] roles = {"Seller", "Customer"};
            String choice = (String) JOptionPane.showInputDialog(null, "Select role", "Sign Up",
                    JOptionPane.PLAIN_MESSAGE, null, roles, roles[0]);

            String role = "2";
            if (choice.equals("Seller")) {
                role = "1";
            }
            userInfo[2] = role;

            output.writeObject(userInfo);
            output.flush();

            if (input.readBoolean()) {
                if (role.equals("1")) {
                    cardLayout.show(cardPanel, "seller");
                } else {
                    cardLayout.show(cardPanel, "customer");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid e-mail format or e-mail already registered",
                        "Sign Up Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void viewStores() {
        try {
            output.writeInt(100);
            output.flush();
            String storeList = (String) input.readObject();

            JOptionPane.showMessageDialog(null, storeList, "Store List", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void createStore() {
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
                        "Create Store", JOptionPane.ERROR_MESSAGE);
            }
            output.writeInt(800);
            output.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addProduct() {
        try {
            output.writeInt(201);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        String[] prompts = {
                "Enter the name of the store you want to add your product to",
                "Enter the name of the product.",
                "Enter a description of the product.",
                "Enter the product stock.",
                "Enter the price of the product"
        };

        boolean temp = true;
        String[] productInfo = new String[5];

        for (int i = 0; i < prompts.length; i++) {
            while (temp) {
                try {
                    if (i == 3) {
                        int stock = Integer.parseInt(JOptionPane.showInputDialog(null, prompts[i], "Add Products",
                                JOptionPane.QUESTION_MESSAGE));
                        productInfo[i] = String.valueOf(stock);
                    } else if (i == 4) {
                        double price = Double.parseDouble(JOptionPane.showInputDialog(null, prompts[i], "Add Products",
                                JOptionPane.QUESTION_MESSAGE));
                        productInfo[i] = String.format("%.2f", price);
                    } else {
                        productInfo[i] = JOptionPane.showInputDialog(null, prompts[i], "Add Products",
                                JOptionPane.QUESTION_MESSAGE);
                    }
                    temp = false;
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Invalid input! " + (i == 3 ? "Stock" : "Price") + " must be a number.",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
            temp = true;
        }

        try {
            output.writeObject(productInfo);
            output.flush();

            if (input.readBoolean()) {
                JOptionPane.showMessageDialog(null, "Product successfully added!",
                        "Add Product", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Adding the product failed, store does not exist.",
                        "Error!", JOptionPane.ERROR_MESSAGE);
            }

            output.writeInt(800);
            output.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void editProduct() {
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
                                "Error", JOptionPane.ERROR_MESSAGE);
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

    private void editAccount() {
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
                        "Edit Account", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Account editing failed, invalid e-mail format!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void viewStatistics() {
        String unsortedStats;
        ArrayList<String> storeParam;
        try {
            output.writeInt(301);
            output.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        try {
            unsortedStats = (String) input.readObject();
            storeParam = (ArrayList<String>) input.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        String[] choices = {"View unsorted statistics", "View statistics sorted by sales",
                "View statistics sorted by number of purchases made from each store",
                "View who has purchased from your stores", "View transaction history"};
        String choice = (String) JOptionPane.showInputDialog(null, "Choose action:", "View Statistics",
                JOptionPane.PLAIN_MESSAGE, null, choices, choices[0]);
        switch (choice) {
            case "View unsorted statistics" ->
                    JOptionPane.showMessageDialog(null, "Unsorted Statistics:\n" + unsortedStats,
                            "Edit Account", JOptionPane.PLAIN_MESSAGE);
            case "View statistics sorted by sales" -> {
                String[] highLow = {"Sort sales from highest to lowest", "Sort sales from lowest to highest"};
                String direction = (String) JOptionPane.showInputDialog(null, "Choose action:", "Sort by sales",
                        JOptionPane.PLAIN_MESSAGE, null, highLow, highLow[0]);
                if (direction.equals("Sort sales from highest to lowest")) {
                    JOptionPane.showMessageDialog(null, Seller.sortStatisticsBySales(unsortedStats, true),
                            "Sorted by Sales", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, Seller.sortStatisticsBySales(unsortedStats, false),
                            "Sorted by Sales", JOptionPane.PLAIN_MESSAGE);
                }
            }
            case "View statistics sorted by number of purchases made from each store" -> {
                String[] highLow = {"Sort from highest to lowest", "Sort from lowest to highest"};
                String direction2 = (String) JOptionPane.showInputDialog(null, "Choose action:", "Sort by sales",
                        JOptionPane.PLAIN_MESSAGE, null, highLow, highLow[0]);
                if (direction2.equals("Sort sales from highest to lowest")) {
                    try {
                        JOptionPane.showMessageDialog(null, Seller.sortByOccurrences(storeParam, true),
                                "Sorted by number of purchases", JOptionPane.PLAIN_MESSAGE);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    try {
                        JOptionPane.showMessageDialog(null, Seller.sortByOccurrences(storeParam, false),
                                "Sorted by number of purchases", JOptionPane.PLAIN_MESSAGE);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            case "View who has purchased from your stores" -> {
                List<String> result = Seller.customersOfStores(storeParam);
                String users = "";
                for (int i = 0; i < storeParam.size(); i++) {
                    String storeName = storeParam.get(i);
                    users += "Users who purchased from " + storeName + ": " + result.get(i) + "\n";
                    JOptionPane.showMessageDialog(null, users, "Users who have purchased from your stores",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
            case "View transaction history" -> {
                ArrayList<String> result = null;
                String history = "";
                try {
                    result = Seller.viewTransactionHistory(storeParam);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                for (int i = 0; i < result.size(); i++) {
                    history += result.get(i) + "\n";
                }
                JOptionPane.showMessageDialog(null, "Your transaction history:\n" + history,
                        "View transaction history", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    private void returnToSellerMenu() {
        cardLayout.show(cardPanel, "seller");
        try {
            output.writeInt(800);
            output.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void deleteSeller() {
        int deleteAccount = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete your account?", "Delete Account",
                JOptionPane.YES_NO_OPTION);
        if (deleteAccount == JOptionPane.YES_OPTION) {
            try {
                output.writeInt(400);
                output.close();
                input.close();
                socket.close();
                System.exit(0);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void viewProducts() {
        StringBuilder list = new StringBuilder();
        boolean temp = true;
        int index = -1;
        ArrayList<Product> productsList = null;
        try {
            output.writeInt(100);
            output.flush();
            productsList = (ArrayList<Product>) input.readObject();
            System.out.println(productsList);
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
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void viewCart() {
        ArrayList<Product> cart = null;
        try {
            output.writeInt(400);
            output.flush();
            cart = (ArrayList<Product>) input.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        StringBuilder cartList = new StringBuilder();
        cart.forEach(product -> cartList.append(product.toString2()));

        JOptionPane.showMessageDialog(null, cartList.toString(), "View Cart", JOptionPane.PLAIN_MESSAGE);

        String[] actions = {"Purchase all items in this cart", "Exit"};
        String choice = (String) JOptionPane.showInputDialog(null, cartList + "\nChoose action:", "Choose action",
                JOptionPane.PLAIN_MESSAGE, null, actions, actions[1]
        );

        if (choice.equals("Purchase all items in this cart")) {
            try {
                output.writeInt(402);
                output.flush();
                double cost = input.readDouble();

                JOptionPane.showMessageDialog(null, String.format("Purchasing all items in this cart for $%.2f", cost),
                        "Purchase cart", JOptionPane.PLAIN_MESSAGE
                );

                boolean purchaseSuccess = input.readBoolean();
                JOptionPane.showMessageDialog(null, (purchaseSuccess) ?
                        "Cart purchased successfully!" : "Cart could not be purchased!", "Purchase cart",
                        (purchaseSuccess) ? JOptionPane.PLAIN_MESSAGE : JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void viewCustomerTransactionHistory() {
        try {
            output.writeInt(600);
            output.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        String[] actions = {"View transaction history", "Extract transaction history"};
        String choice = (String) JOptionPane.showInputDialog(null, "Choose action:", "Choose action",
                JOptionPane.PLAIN_MESSAGE, null, actions, actions[0]);

        String transactionHist;
        try {
            transactionHist = (String) input.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        String title = "Transaction History";
        String message = (choice.equals("View transaction history")) ? "Your transaction history:\n" + transactionHist
                : "Extracting transaction history....";
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);

    }

    private void viewCustomerStatistics() {
        String[] actions = {"View what stores you have purchased from", "View total purchases from stores"};
        String choice = (String) JOptionPane.showInputDialog(null, "Choose action:", "Choose action",
                JOptionPane.PLAIN_MESSAGE, null, actions, actions[0]);

        int requestCode = (choice.equals("View what stores you have purchased from")) ? 701 : 702;

        Map<String, Integer> counts;
        try {
            output.writeInt(requestCode);
            output.flush();
            counts = (Map<String, Integer>) input.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        String direction = (String) JOptionPane.showInputDialog(null, "Choose action:", "Sort by sales",
                JOptionPane.PLAIN_MESSAGE, null, new String[]{"Sort from highest to lowest",
                        "Sort from lowest to highest"}, "Sort from highest to lowest");

        String sorted = Customer.sortPurchaseCounts(counts, direction.equals("Sort from highest to lowest"));
        JOptionPane.showMessageDialog(null, sorted, "Purchase Statistics", JOptionPane.PLAIN_MESSAGE);
    }

    private void returnToCustomerMenu() {
        cardLayout.show(cardPanel, "customer");
        try {
            output.writeInt(800);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void deleteCustomer() {
        int deleteAccount = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete your account?",
                "Delete Account", JOptionPane.YES_NO_OPTION);
        if (deleteAccount == JOptionPane.YES_OPTION) {
            try {
                output.writeInt(300);
                output.close();
                input.close();
                socket.close();
                System.exit(0);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void exit() {
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
/*seller cases
100 - completed
200 - completed
201 - completed
202 - NOT completed
203 - completed
204 - NOT completed
300 - completed
301 - completed
400 - completed
500 - completed
601 - NOT completed
700 - NOT completed
701 - NOT completed
900 - completed
 */

/*customer cases
100 - completed
200 - completed
300 - completed
400 - completed
401 - completed
402 - completed
403 - completed
404 - NOT completed
501 - NOT completed
600 - NOT completed
701 - completed
702 - completed
800 - completed
900 - completed
 */
