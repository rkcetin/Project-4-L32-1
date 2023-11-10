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
    private String transactionHistory = "";

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

    public void purchase(Scanner scan) {
        String transactionHistory = "";
        System.out.printf("Purchase cart items for $%.2f?\n1. Confirm purchase\n2. Exit\n", this.calculatePrice());
        try {
            int choice = scan.nextInt();
            scan.nextLine();
            if (choice == 1) {
                for (Product product : cart) {
                    transactionHistory += product.toString2();
                    product.decrementStock();
                }
                for (int j = cart.size() - 1; j >= 0; j--) {
                    cart.get(j).getStore().incrementSales(cart.get(j).getPrice());
                    cart.remove(j);
                }
                this.setTransactionHistory(transactionHistory);
            } else if (choice == 2) {
                System.out.println("You have exited the purchase screen.");
            } else {
                System.out.println("Invalid input, try again.");
            }
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input, try again.");
        } catch (IllegalArgumentException iae) {
            System.out.println("Cannot purchase all items, stock exceeded.");
        }
    }

    public void setCart(ArrayList<Product> cart) {
        this.cart = cart;
    }

    public void setTransactionHistory(String transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    public String getTransactionHistory() {
        return this.transactionHistory;
    }

    public double calculatePrice() {
        double price = 0;
        for (Product product : cart) {
            price += product.getPrice();
        }
        return price;
    }
}
