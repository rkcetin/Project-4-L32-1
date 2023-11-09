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

    public Customer(String name, String password, String salt) {
        super(name, password , salt);
    }

    public ArrayList<Product> getCart() {
        return cart;
    }

    public void addToCart(Store store, String name) {
        ArrayList<Product> products = Storage.getProducts();
        for (int i = 0; i < products.size(); i++) {
            if (store.equals(products.get(i).getStore()) && name.equals(products.get(i).getProductName())) {
                cart.add(products.get(i));
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
        System.out.printf("Purchase cart items for $%d?\n1. Confirm purchase\n2. Exit\n", this.calculatePrice());
        try {
            int choice = scan.nextInt();
            scan.nextLine();
            if (choice == 1) {
                //TODO
            } else if (choice == 2) {
                //TODO
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

    public int calculatePrice() {
        int price = 0;
        for (int i = 0; i < cart.size(); i++) {
            price += cart.get(i).getPrice();
        }
        return price;
    }
}
