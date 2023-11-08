import java.util.ArrayList;

public class Customer extends User {
    private ArrayList<Product> cart;

    public Customer(String name, String password, String salt) {
        super(name, password , salt);
    }

    public ArrayList<Product> getCart() {
        return cart;
    }

    public void addToCart(Store store, String name) {

    }

    public void removeFromCart(Store store, String name) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getProductName().equals(name) && cart.get(i).getStore() == store) {
                cart.remove(i);
                return;
            }
        }
    }

    public void purchase() {

    }

    public void setCart(ArrayList<Product> cart) {
        this.cart = cart;
    }
}
