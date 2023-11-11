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


    //has quantity parameter
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
                //don't add return once found the product because addToCart method adds multiple identical products if quantity > 1. 
            }
        }
    }

    public void purchaseCart(Scanner scan) {
        for (Product product : cart) {
            if (product.getStock() < 1) {
                throw new IllegalArgumentException("Not Enough Stock!");
            }
            this.transactionHistory.add(product.toString2());
            this.transactionHistoryProducts.add(product);
            this.boughtProduct++;
            product.decrementStock();
            product.incrementSold();
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
    public String dashboardbySold() {
        String x = "";
        ArrayList<String> dashboard = new ArrayList<>();
        ArrayList<Store> stores = Storage.getStores();
        for (Store store : stores) {
            dashboard.add(String.format("Store name: %s Sold: %d", store.getStoreName(), store.getSoldProduct()));
        }
        for (int i = 0; i < dashboard.size(); i++) {
            x += dashboard.get(i) + "\n";
        }
        return x;
    }
}
