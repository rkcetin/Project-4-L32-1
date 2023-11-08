import java.io.Serializable;

/**
 * Project 4 -- Product Class
 *
 * Class holds information about a specific product that is being sold within the store
 * also checks for invalid input and has getter and setter methods
 *
 * @author Steven Chang Alexander Benson Stephanie Sun Chris Xu Ramazan Cetin , L32
 *
 * @version November 6, 2023
 *
 */
public class Product implements Serializable {
    private static final long serialVersionUID = 1234L;
    private Store store;
    private String productName;
    private String productDescription;
    private int stock;
    private double price;

    public Product(Store store, String productName, String productDescription, int stock, double price) {
        if (store == null || productName == null || productDescription == null) {
            throw new NullPointerException();
        }
        if (stock < 0 || price < 0) {
            throw new IllegalArgumentException();
        }
        this.store = store;
        this.productName = productName;
        this.productDescription = productDescription;
        this.stock = stock;
        this.price = price;
    }

    public void setProductName(String productName) {
        if (productName == null || productName.isEmpty()) {
            throw new NullPointerException();
        }
        this.productName = productName;
    }

    public void setProductDescription(String productDescription) {
        if (productDescription == null) {
            throw new NullPointerException();
        }
        this.productDescription = productDescription;
    }

    public void setStock(int stock) {
        if (stock < 0 ) {
            throw new IllegalArgumentException();
        }
        this.stock = stock;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException();
        }
        this.price = price;
    }

    public Store getStore() {
        return store;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public int getStock() {
        return stock;
    }

    public double getPrice() {
        return price;
    }

    public String toString() {
        return String.format("Product<Name: %s, Description: %s, Price: %.2f, Stock: %d, Store: %s>",
            this.getProductName(), this.getProductDescription(), this.getPrice(), this.getStock(),
                this.getStore().getStoreName());
    }

}
