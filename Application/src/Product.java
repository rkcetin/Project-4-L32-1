 
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
public class Product {
    private Store store; //the store a product is associated with
    private String productName; //the name of the product
    private String productDescription; //the description of the product
    private int stock; //the number of items in stock
    private double price; //the price of the item

     //basic constructor for Product class
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
//sets Product name to given name, throws null pointer exception if null
    public void setProductName(String productName) {
        if (productName == null || productName.isEmpty()) {
            throw new NullPointerException();
        }
        this.productName = productName;
    }
//sets the Product description, throws null pointer exception if null
    public void setProductDescription(String productDescription) {
        if (productDescription == null) {
            throw new NullPointerException();
        }
        this.productDescription = productDescription;
    }
//set the stock to the given stock, throws Illegal argument exception if stock is less than zero
    public void setStock(int stock) {
        if (stock < 0 ) {
            throw new IllegalArgumentException();
        }
        this.stock = stock;
    }
//sets the price to the given price, throws illegal argument exception if less than zero
    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException();
        }
        this.price = price;
    }
//returns the name of the store
    public Store getStore() {
        return store;
    }
//returns the name of the product
    public String getProductName() {
        return productName;
    }
//returns the description of the product
    public String getProductDescription() {
        return productDescription;
    }
//returns the number of items in stock
    public int getStock() {
        return stock;
    }
//returns the price
    public double getPrice() {
        return price;
    }
//formats all of the information into a string and returns it
    public String toString() {
        return String.format("Product<Name: %s, Description: %s, Price: %.2f, Stock: %d, Store: %s>",
            this.getProductName(), this.getProductDescription(), this.getPrice(), this.getStock(),
                this.getStore().getStoreName());
    }

}
