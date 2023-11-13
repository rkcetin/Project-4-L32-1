import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Project 4 -- Product Class
 *
 * Class holds information about a specific product that is being sold within the store
 * also checks for invalid input and has getter and setter methods Has helpful methods to generate
 * listing of products in addition to sorting lists of products
 *
 * @author Steven Chang, Alexander Benson, Stephanie Sun, Chris Xu, Ramazan Cetin, L32
 *
 * @version November 12, 2023
 *
 */

public class Product implements Serializable {
    private static final long serialVersionUID = 1234L;
    private Store store;//the store a product is associated with

    private String productName; //the description of the product
    private String productDescription;//the description of the product
    private int stock;//the number of items in stock
    private double price;//the price of the item
    private int sold;

    public int getSold() {
        return sold;
    }

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
        this.sold = 0;
    }

    // SETTER METHODS

    //sets Product name to given name, throws null pointer exception if null
    /**
     * updates product name
     *
     *
     * @param productName new product name of the product
     * @throws NullPointerException when null is provided
     * @throws IllegalArgumentException when productName is empty
     */
    public void setProductName(String productName) {
        if (productName == null ) {
            throw new NullPointerException();
        }
        if (productName.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.productName = productName;
    }

    //sets the Product description, throws null pointer exception if null
    /**
     * updates product description
     *
     *
     * @param productDescription new product description
     * @throws NullPointerException when null is provided
     * @throws IllegalArgumentException when productDescription is empty
     */
    public void setProductDescription(String productDescription) {
        if (productDescription == null) {
            throw new NullPointerException();
        }
        this.productDescription = productDescription;
    }

    //set the stock to the given stock, throws Illegal argument exception if stock is less than zero
    /**
     * updates stock
     *
     *
     * @param stock new stock
     * @throws IllegalArgumentException throws when stock < 0
     */
    public void setStock(int stock) {
        if (stock < 0 ) {
            throw new IllegalArgumentException();
        }
        this.stock = stock;
    }

    //sets the price to the given price, throws illegal argument exception if less than zero
    /**
     * updates product description
     *
     *
     * @param price new price as a double
     * @throws IllegalArgumentException throws when price < 0
     */

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException();
        }
        this.price = price;
    }

    //returns the name of the store
    /**
     * returns store of product
     *
     *
     * @return Store returns the store of the product
     */
    public Store getStore() {
        return store;
    }

    //returns the name of the product
    /**
     * returns name of the product
     *
     *
     * @return String name of the product
     */
    public String getProductName() {
        return productName;
    }

    //returns the description of the product
    /**
     * returns product description
     *
     *
     * @return String returns string of product description
     */
    public String getProductDescription() {
        return productDescription;
    }

    //returns the number of items in stock
    /**
     * returns the stock
     *
     *
     * @return int returns the stock
     */
    public int getStock() {
        return stock;
    }

    //returns the price
    /**
     * returns the price of the product
     *
     *
     * @return double the price of the product
     */
    public double getPrice() {
        return price;
    }
    /**
     * decrements the stock
     *
     * @throws IllegalArgumentException if stock is already zero
     */

    public void decrementStock() throws IllegalArgumentException {
        int newStock = this.stock--;
        if (newStock < 0) {
            throw new IllegalArgumentException();
        }
    }
    /**
     * increments the stock
     *
     *
     */
    public void incrementStock() {
        this.stock++;
    }
    /**
     * increments the amount sold of a product
     *
     */
    public void incrementSold() {
        sold++;
    }

    // Helper methods
    //check/get product method by string
    /**
     * checks if a product is within an arraylist and returns it if it is
     * @param productName string name of the product looking for
     * @param products list of products to search for the name
     * @throws NullPointerException when input is null
     * @return Product if the value exists within products null if it doesnt
     */
    public static Product checkProduct(String productName, ArrayList<Product> products) {
        if(productName == null || products == null) {
            throw new NullPointerException();
        }
        ArrayList<Product> filteredProduct = new ArrayList<Product>(products
                .stream()
                .filter(product -> product.getProductName().equalsIgnoreCase(productName))
                .collect(Collectors.toCollection(ArrayList::new)));
        if(filteredProduct.isEmpty()) {
            return null;
        }
        return filteredProduct.get(0);
    }

    //search method
    /**
     * searches a list of products for a string returns matching products
     * @param search string to search the product list for
     * @param products list of products to search in
     * @throws NullPointerException when input is null
     * @return an arraylist of products containing the search string
     */
    public static ArrayList<Product> search(String search, ArrayList<Product> products) {
        if (search == null || products == null) {
            throw new NullPointerException();
        }
        if (search.isEmpty()) {
            return products;
        }
        ArrayList<Product> output = products
                .stream()
                .filter(product -> product.toStringCsvFormat(false).toLowerCase().contains(search.toLowerCase()))
                .collect(Collectors.toCollection(ArrayList::new));
        return output;

    }

    //generate listings from list
    /**
     * generates the listing of a list of products
     * @param products list of products to generate the listing from
     * @throws NullPointerException when input is null
     * @return Arraylist of String representing the products
     */
    public static ArrayList<String> generateListing(ArrayList<Product> products) {
        if (products == null) {
            throw new NullPointerException();
        }
        ArrayList<String> output = new ArrayList<>();
        for(Product product : products) {
            String listing = String.format("Store: %s, Name: %s , Price: $%.2f" ,
                    product.getStore().getStoreName() ,
                    product.getProductName() ,
                    product.getPrice());

            output.add(listing);
        }
        return output;

    }

    // helper function that sorts the products list by stock high or low
    /**
     * sorts an arraylists of products by the stock available either starting low or high
     * @param products list of products to sort
     * @throws NullPointerException when input is null

     */
    public static void sortStock(boolean startLow , ArrayList<Product> products) {
        if (products == null) {
            throw new NullPointerException();
        }
        if (startLow) {
            products.sort(Comparator.comparing(Product::getStock));
        } else {
            products.sort(Comparator.comparing(Product::getStock).reversed());
        }

    }
    /**
     * sorts an arraylist of products by price starting either high or low
     * @param products list of products to sort
     * @throws NullPointerException when input is null
     */
    // helper function that sort the products list by price high or low
    public static void sortPrice(boolean startLow , ArrayList<Product>  products) {
        if (products == null) {
            throw new NullPointerException();
        }
        if (startLow) {
            products.sort(Comparator.comparing(Product::getPrice));
        } else {
            products.sort(Comparator.comparing(Product::getPrice).reversed());
        }

    }



   //formats all the information into a string and returns it
    /**
     * generates + returns string representation of the product
     * @return returns a string representation of the product
     */
    public String toString() {
        return String.format("Product<Name:%s, Description:%s, Price: %.2f, Stock: %d, Store: %s>\n",
            this.getProductName(), this.getProductDescription(), this.getPrice(), this.getStock(),
                this.getStore().getStoreName());
    }

    /**
     * generates + returns string representation of the product in different format without stock
     * @return returns a string representation of the product
     */
    public String toString2() {
        return String.format("Product<Name: %s, Description: %s, Price: %.2f, Store: %s>",
                this.getProductName(), this.getProductDescription(), this.getPrice(), this.getStore().getStoreName());
    }
    /**
     * generates simple representation of product with or without commas
     * @return returns a string representation of the product in csv format or without commas
     */
    public String toStringCsvFormat(boolean hasCommas) {
        if (hasCommas) {
            return String.format("%s,%s,%.2f,%s",
                    this.getProductName(),
                    this.getProductDescription(),
                    this.getPrice(),
                    this.getStore().getStoreName());
        } else {
            return String.format("%s%s%.2f%s",
                    this.getProductName(),
                    this.getProductDescription(),
                    this.getPrice(),
                    this.getStore().getStoreName());
        }
    }

}