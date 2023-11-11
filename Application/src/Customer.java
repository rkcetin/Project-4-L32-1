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
    public Customer(String name, String password, String salt) {
        super(name, password , salt);
    }

    public ArrayList<Product> getCart() {
        return cart;
    }

    //might not be necessary without "int quantity"
    public void addToCart(Store store, String name) {
        ArrayList<Product> products = Storage.getProducts();
        for (Product product : products) {
            if (store.equals(product.getStore()) && name.equals(product.getProductName())) {
                cart.add(product);
                return;
            }
        }
    }

    //overloaded
    public void addToCart(Store store, String name, int quantity) {
        ArrayList<Product> products = Storage.getProducts();
        for (Product product : products) {
            if (store.equals(product.getStore()) && name.equals(product.getProductName())) {
                for (int j = 0; j < quantity; j++) {
                    cart.add(product);
                }
                return;
            }
        }
    }

    public void removeFromCart(Store store, String name) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getProductName().equals(name) && cart.get(i).getStore() == store) {
                cart.remove(i);
                return;
            }
        }
    }

    public void purchaseCart(Scanner scan) throws IOException, IllegalArgumentException {
        PrintWriter pw = new PrintWriter(new FileWriter("statistics.txt", false));

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

    public static String sortByOccurrences(ArrayList<String> stores, boolean highestToLowest) throws IOException {
        BufferedReader bfr = new BufferedReader(new FileReader("statistics.txt"));
        Map<String, Integer> storeOccurrences = new HashMap<>();
        String a = "";
        String line;

        while ((line = bfr.readLine()) != null) {
            for (int i = 0; i < stores.size(); i++) {
                if (line.split(",")[0].equals(stores.get(i))) {
                    String key = stores.get(i);
                    storeOccurrences.put(key, storeOccurrences.getOrDefault(key, 0) + 1);
                }
            }
        }

        List<Map.Entry<String, Integer>> sortedEntries = storeOccurrences.entrySet()
                .stream()
                .sorted((entry1, entry2) -> highestToLowest ?
                        Integer.compare(entry2.getValue(), entry1.getValue()) :
                        Integer.compare(entry1.getValue(), entry2.getValue()))
                .collect(Collectors.toList());

        for (Map.Entry<String, Integer> entry : sortedEntries) {
            String storeName = entry.getKey();
            int totalOccurrences = entry.getValue();
            a += totalOccurrences + " purchases from " + storeName + "\n";
        }
        return a;
    }

    public double calculatePrice() {
        double price = 0;
        for (Product product : cart) {
            price += product.getPrice();
        }
        return price;
    }
}
