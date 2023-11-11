import java.util.*;

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
        Seller seller1 = new Seller("bob" , "123","123");
        Store store1 = new Store("store" , seller1);
        Product product1 = new Product(store1, "test1" , "desc" , 5 ,2.1);

        Seller seller2 = new Seller("rob" , "123","123");
        Store store2 = new Store("bare" , seller2);
        Product product2 = new Product(store2, "test2" , "desc" , 5 ,2.1);

        Seller seller3 = new Seller("mob" , "123","123");
        Store store3 = new Store("stare" , seller3);
        Product product3 = new Product(store3, "test3" , "desc" , 5 ,2.1);
        ArrayList<Product> testProducts = new ArrayList<>();
        testProducts.add(product3);
        testProducts.add(product2);
        testProducts.add(product1);
        Storage.storeProducts(testProducts);
        ArrayList<Store> testStores = new ArrayList<>();
        testStores.add(store3);
        testStores.add(store2);
        testStores.add(store1);
        Storage.storeStores(testStores);
        Customer customer = new Customer("abc", "1232353215234", "1234", 0);
        customer.addToCart(store3, "test3", 2);
        customer.addToCart(store2, "test2", 5);
        customer.addToCart(store1, "test1", 1);
        System.out.println(customer.getCart().toString());
        customer.purchaseCart();
        System.out.println("\n\n\n");
        System.out.println("BOUGHT STORES ARE BELOW");
        System.out.println(customer.dashboardbyBought());
        System.out.println(customer.dashboardbySold());
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

    public void purchaseCart() {
        for (Product product: cart) {
            if (product.getStock() < 1) {
                throw new IllegalArgumentException("Not Enough Stock!");
            }
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
        for (int i  = 0; i < transactionHistory.size(); i++) {
            history += transactionHistory.get(i) + "\n";
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
