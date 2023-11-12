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
 * @version November 6, 2023
 *
 */

public class Customer extends User {
    private ArrayList<Product> cart;
    private ArrayList<String> transactionHistory;
    private ArrayList<Product> transactionHistoryProducts;
    private int boughtProduct;

    public int getBoughtProduct() {
        return boughtProduct;
    }

    public void incrementBoughtProduct() {
        boughtProduct++;
    }

    public Customer(String name, String password, String salt, int boughtProduct) {
        super(name, password , salt);
    }

    public ArrayList<Product> getCart() {
        return cart;
    }

    public void singlePurchase(Store store, String name, int quantity, ArrayList<Product> products)
            throws IOException, IllegalArgumentException {
        PrintWriter pw = new PrintWriter(new FileWriter("statistics.txt", true));
        for (Product product : products) {
            if (store.equals(product.getStore()) && name.equals(product.getProductName())) {
                if (quantity < product.getStock()) {
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
    public void addToCart(Store store, String name, int quantity, ArrayList<Product> products) {
        for (Product product : products) {
            if (store.equals(product.getStore()) && name.equals(product.getProductName())) {
                for (int j = 0; j < quantity; j++) {
                    cart.add(product);
                }
                return;
            }
        }
    }


    public void removeFromCart(Store store, String name, int quantity) {
        for (int i = cart.size() - 1; i >= 0; i--) {
            for (int j = 0; j < quantity; j++) {
                if (cart.get(i).getProductName().equals(name) && cart.get(i).getStore() == store) {
                    cart.remove(i);
                }
            }
        }
    }

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

    public void purchaseCart(Scanner scan) throws IOException, IllegalArgumentException {
        PrintWriter pw = new PrintWriter(new FileWriter("statistics.txt", true));
        ArrayList<Integer> quantity = this.getProductOccurrences();

        System.out.printf("Purchase all cart items for $%.2f?\n1. Confirm purchase\n2. Exit\n", this.calculatePrice());
        int count = 0;
        try {
            int choice = scan.nextInt();
            scan.nextLine();
            if (choice == 1) {
                for (int i = 0; i < cart.size(); i++) {
                    if (cart.get(i).getStock() < quantity.get(i)) {
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
    }

    public void setCart(ArrayList<Product> cart) {
        this.cart = cart;
    }

    public String getTransactionHistory() {
        String history = "";
        for (String s : transactionHistory) {
            history += s + "\n";
        }
        return history;
    }

    public void setTransactionHistory(ArrayList<String> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    public double calculatePrice() {
        double price = 0;
        for (Product product : cart) {
            price += product.getPrice();
        }
        return price;
    }

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

    //Returns a string that contains a list of stores and the number of products they sold
    public String dashboardbySold(ArrayList<Store> stores) {
        String x = "";
        ArrayList<String> dashboard = new ArrayList<>();

        for (Store store : stores) {
            dashboard.add(String.format("Store name: %s Sold: %d", store.getStoreName(), store.getSoldProduct()));
        }
        for (int i = 0; i < dashboard.size(); i++) {
            x += dashboard.get(i) + "\n";
        }
        return x;
    }
}