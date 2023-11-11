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

    public  ArrayList<Product> getCart() {
        return cart;
    }


    //has quantity parameter
    public void addToCart(Store store, String name, int quantity , ArrayList<Product> products) {
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
                //don't add return once found the product because addToCart method adds multiple identical products if quantity > 1. 
            }
        }
    }

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
        System.out.println(dashboard.toString());
        System.out.println(x);
        return x;
    }
    //Sorts the dashboard according to the number after second ":"
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
    } 
}
