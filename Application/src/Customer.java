import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Project 4 -- Customer Class
 *
 * Class represents a Customer and extends User. Contains methods relating to Customer permissions
 * adding products to their cart and making purchases.
 *
 * @author Steven Chang, Alexander Benson, Stephanie Sun, Chris Xu, Ramazan Cetin, L32
 *
 * @version November 12, 2023
 *
 */

public class Customer extends User {
    private ArrayList<Product> cart = new ArrayList<>();
    private ArrayList<String> transactionHistory = new ArrayList<>();
    private ArrayList<Product> transactionHistoryProducts = new ArrayList<>();
    private int boughtProduct;
    public static void main(String[] args) {
    }
    public int getBoughtProduct() {
        return boughtProduct;
    }

    public void incrementBoughtProduct() {
        boughtProduct++;
    }

    public Customer(String name, String password, String salt, int boughtProduct) {
        super(name, password , salt);
    }

    /**
     * Adds a specific item to the users cart a particular number of times
     * pulls products from collection of products in arraylist
     *
     * @return returns an ArrayList of products representing the customers cart
     *
     */
    public ArrayList<Product> getCart() {
        return cart;
    }
    /**
     * returns the cart of the searched user
     * @param users arraylist of users to search within
     * @param name email of the customer to search for
     * @return returns an ArrayList of products representing the searched customers cart
     * @throws throws Exception when search user is a seller or when nothing is found
     */
    public static ArrayList<Product> getCart(String name , ArrayList<User> users) throws Exception {
        User searchedUser = User.isEmailRegistered(name , users);
        if (searchedUser == null || (searchedUser instanceof Seller)) {
            throw new Exception("invalid customer");
        }
        Customer castedUser = (Customer) searchedUser;
        return castedUser.getCart();
    }
    /**
     * Allows the customer to make a purchase without having them having to use the cart
     *
     *
     * @param store relates to the store the customer of the product the customer is buying
     * @param name name of the product the customer is purchasing
     * @param quantity the quantity of the item that the customer is adding
     * @param products the arraylist of products the program will get the products from
     * @throws IOException from use of print writer and files
     * @throws IllegalArgumentException when quantity purchased exceed item stock
     */
    public synchronized void singlePurchase(Store store, String name, int quantity, ArrayList<Product> products)
            throws IOException, IllegalArgumentException {
        PrintWriter pw = new PrintWriter(new FileWriter("statistics.txt", true));
        for (Product product : products) {
            if (store.equals(product.getStore()) && name.equals(product.getProductName())) {
                System.out.printf("Purchasing %d of these items for %.2f....Purchased\n",
                        quantity, product.getPrice() * quantity);
                if (quantity > product.getStock()) {
                    throw new IllegalArgumentException("Stock exceeded!");
                }
                for (int j = 0; j < quantity; j++) {
                    pw.println(String.format("%s,%s,%s,%.2f", product.getStore().getStoreName(), this.getName(),
                            product.getProductName(), product.getPrice()));
                    product.decrementStock();
                }
                return;
            }
        }
    }

    //has quantity parameter
    /**
     * Adds a specific item to the users cart a particular number of times
     * pulls products from collection of products in arraylist
     *
     * @param store relates to the store the customer of the product the customer to their cart
     * @param name name of the product the customer is purchasing
     * @param quantity the quantity of the item that the customer is adding
     * @param products the arraylist of products the program will get the products from
     *
     */
    public void addToCart(Store store, String name, int quantity, ArrayList<Product> products) throws Exception {
        if (quantity <= 0 ) {
            throw new Exception();
        }
        for (Product product : products) {
            if (store.equals(product.getStore()) && name.equals(product.getProductName())) {
                if (quantity <= product.getStock()) {
                    for (int j = 0; j < quantity; j++) {
                        cart.add(product);
                    }
                } else {
                    System.out.println("You cannot add more than there is stock for!");
                }
                return;
            }
        }
    }
    /**
     * removes an item from users cart
     *
     *
     * @param store relates to the store the customer of the product the customer to their cart
     * @param name name of the product the customer is purchasing
     *
     *
     */

    public void removeFromCart(Store store, String name) {
        while (Product.checkProduct(name, this.cart) != null) {
            cart.remove(Product.checkProduct(name, this.cart));
        }
    }

    /**
     * counts and returns occurrences of products within the cart
     *
     * @return returns an arraylist of the occurrences of particular product in the cart
     *
     */
    public ArrayList<Integer> getProductOccurrences() {
        Map<Product, Integer> productCountMap = new HashMap<>();

        for (Product product : cart) {
            productCountMap.put(product, productCountMap.getOrDefault(product, 0) + 1);
        }

        ArrayList<Integer> productOccurrencesList = new ArrayList<>();

        for (Product product : cart) {
            productOccurrencesList.add(productCountMap.get(product));
        }
        return productOccurrencesList;
    }
    /**
     * purchases cart and adds the information to statistics.txt
     *
     *
     * @param scan represents a scanner the user passes in to handle the input for the confirmation of the cart purchase
     * @throws IllegalArgumentException when input in invalid
     */

    public void purchaseCart(Scanner scan) throws IOException, IllegalArgumentException {
        PrintWriter pw = new PrintWriter(new FileWriter("statistics.txt", true));

        System.out.printf("Purchase all cart items for $%.2f?\n1. Confirm purchase\n2. Exit\n", this.calculatePrice());
        int count = 0;
        try {
            int choice = scan.nextInt();
            scan.nextLine();
            if (choice == 1) {
                for (Product value : cart) {
                    if (value.getStock() < 1) {
                        throw new IllegalArgumentException("Stock exceeded!");
                    }
                }
                for (Product product : cart) {
                    this.transactionHistory.add(product.toString2());
                    product.decrementStock();
                    count++;
                }
                for (int j = cart.size() - 1; j >= 0; j--) {
                    cart.get(j).getStore().incrementSales(cart.get(j).getPrice());
                    pw.println(String.format("%s,%s,%s,%.2f", cart.get(j).getStore().getStoreName(), this.getName(),
                            cart.get(j).getProductName(), cart.get(j).getPrice()));
                    cart.remove(j);
                }
                pw.flush();
                this.setTransactionHistory(transactionHistory);
            } else if (choice == 2) {
                System.out.println("You have exited the purchase screen.");
            } else {
                System.out.println("Invalid input, try again.");
            }
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input, try again.");
        }
        for (Product product : cart) {
            this.transactionHistory.add(product.toString2());
            this.transactionHistoryProducts.add(product);
            this.boughtProduct++;
            product.decrementStock();
            product.incrementSold();
            product.getStore().incrementSoldProduct();
            product.getStore().incrementSales(product.getPrice());
        }
        for (int j = cart.size() - 1; j >= 0; j--) {
            cart.get(j).getStore().incrementSales(cart.get(j).getPrice());
            cart.remove(j);
        }
        this.setTransactionHistory(transactionHistory);
    }
    /**
     * updates the cart based on a new arraylist of products as cart
     *
     *
     * @param cart arraylist of products representing cart

     *
     */
    public void setCart(ArrayList<Product> cart) {
        this.cart = cart;
    }
    public ArrayList<String> getTransactionHistoryList() {
        return transactionHistory;
    }
    public String getTransactionHistory() {
        String history = "";
        for (String s : transactionHistory) {
            history += s + "\n";
        }
        return history;
    }
    /**
     * updates the transaction history based off array list of strings provided
     * as the transaction history
     *
     * @param transactionHistory array list of the transaction history
     *
     */

    public void setTransactionHistory(ArrayList<String> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    /**
     * calculates price of products in cart
     *
     * @ return returns the price of the cart
     *
     */
    public double calculatePrice() {
        double price = 0;
        for (Product product : cart) {
            price += product.getPrice();
        }
        return price;
    }
    /**
     * generates a string representation of a dashboard the customer has purchased from before
     *
     * @return returns a string representation of the stores the customer has purchased from before
     */

    //Returns a string that contains a list of stores that the customer have purchased from before
    public String dashboardbyBought() {
        String x = "";
        ArrayList<String> dashboard = new ArrayList<>();
        ArrayList<String> y = new ArrayList<>();
        for (Product product : transactionHistoryProducts) {
            y.add(product.getStore().getStoreName());
        }
        for (String k : y) {
            if (!dashboard.contains(k)) {
                dashboard.add(k);
            }
        }
        for (int i = 0; i < dashboard.size(); i++) {
            x += dashboard.get(i) + "\n";
        }
        return x;
    }

    /**
     * generates a dashboard with an arraylist of stores by on the
     * quantity of products they have sold
     *
     * @param stores relates to the store the customer of the product the customer to their cart
     * @return returns a string representation of the dashboard of stores by how much each has sold
     */
    public String dashboardBySold(ArrayList<Store> stores) {

        String x = "";
        ArrayList<String> dashboard = new ArrayList<>();

        for (Store store : stores) {
            dashboard.add(String.format("Store name: %s Sold: %d", store.getStoreName(), store.getSoldProduct()));
        }
        for (int i = 0; i < dashboard.size(); i++) {
            x += dashboard.get(i) + "\n";
        }
        System.out.println(dashboard.toString());
        System.out.println(x);
        return x;
    }

    //THE BELOW METHOD IS OBSOLETE
    /*Sorts the dashboard according to the number after second ":"
    public String sortDashboard(String x) {
        String willReturn = "";
        if (x.indexOf(":") == -1) {
            throw new IllegalArgumentException("Dashboard can't be sorted!");
        } else {
            String[] lines = x.split("\r\n|\r|\n");

            for (int i = 0; i < lines.length - 1; i++) {
                int sold = Integer.parseInt(lines[i].split(":")[2].trim());
                int soldNext = Integer.parseInt(lines[i+1].split(":")[2].trim());
                if (soldNext > sold) {
                    lines[i] = lines[i + 1];
                    lines[i + 1] = lines[i];
                }
            }
            for (int i = 0; i < lines.length; i++) {
                willReturn += lines[i];
            }
            return willReturn;
        }
    }*/

    //Extracts transaction history as a file 
    public File extractTransactionHistory() throws Exception {
        File f = new File(this.getName() + "transactionHistory.txt");
        PrintWriter pw = new PrintWriter(f);
        f.createNewFile();
        for (int i = 0; i < this.transactionHistory.size(); i++) {
            pw.println(this.transactionHistory.get(i));
        }
        pw.flush();
        System.out.println("Extracted to -transactionHistory.txt-");
        return f;
    }

    public Map<String, Integer> getPurchaseCounts() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("statistics.txt"));
        Map<String, Integer> purchaseCounts = new HashMap<>();
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 4 && parts[1].equals(this.getName())) {
                String storeName = parts[0];
                purchaseCounts.put(storeName, purchaseCounts.getOrDefault(storeName, 0) + 1);
            }
        }

        reader.close();
        return purchaseCounts;
    }

    public String sortPurchaseCounts(Map<String, Integer> purchaseCounts, boolean highestToLowest) {
        return purchaseCounts.entrySet()
                .stream()
                .sorted((entry1, entry2) -> highestToLowest ?
                        Integer.compare(entry2.getValue(), entry1.getValue()) :
                        Integer.compare(entry1.getValue(), entry2.getValue()))
                .map(entry -> entry.getKey() + ": " + entry.getValue() + " purchases")
                .collect(Collectors.joining("\n"));
    }

    public Map<String, Integer> countStoreOccurrences() throws IOException {
        Map<String, Integer> storeCounts = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("statistics.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String storeName = line.split(",")[0].trim();
                storeCounts.put(storeName, storeCounts.getOrDefault(storeName, 0) + 1);
            }
        }

        return storeCounts;
    }

    public String sortStoreCounts(Map<String, Integer> storeCounts, boolean highestToLowest) {
        return storeCounts.entrySet().stream()
                .sorted((entry1, entry2) -> highestToLowest ?
                        Integer.compare(entry2.getValue(), entry1.getValue()) :
                        Integer.compare(entry1.getValue(), entry2.getValue()))
                .map(entry -> entry.getKey() + ": " + entry.getValue() + " purchases")
                .collect(Collectors.joining("\n"));
    }
}