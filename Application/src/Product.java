import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 106L;
    private Store store;
    private String productName;
    private String productDescription;
    private int quantity;
    private double price;

    public Product(String productName , String productDescription , int quantity, int price , Store store) {
        if( store == null || productName == null || productDescription == null ) {
            throw new NullPointerException();
        }
        if (quantity < 0 || price < 0) {
            throw new IllegalArgumentException();
        }
        this.productName = productName;
        this.productDescription = productDescription;
        this.quantity = quantity;
        this.price = price;
        this.store = store;
    }

    public void setProductName(String productName) {
        if (productName == null) {
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

    public void setQuantity(int quantity) {
        if (quantity < 0 ) {
            throw new IllegalArgumentException();
        }
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String toString() {
        return String.format("Product<Name: %s, Description: %s, Price: %.2f, Stock: %d, Store: %s>" , productName, productDescription, price, quantity, store.getName());
    }

}
