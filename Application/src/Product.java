import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

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

    // SETTER METHODS

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

    // Setter methods

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

    // Helper methods
    //check/get product method by string
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
    public static ArrayList<Product> search(String search, ArrayList<Product> products) {
        if(search == null || products == null) {
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
    // helper functino that sorts the products list by stock high or low
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

    public String toString() {
        return String.format("Product<Name: %s, Description: %s, Price: %.2f, Stock: %d, Store: %s>",
            this.getProductName(), this.getProductDescription(), this.getPrice(), this.getStock(),
                this.getStore().getStoreName());
    }
    public String toStringCsvFormat(boolean hasCommas) {
        if (hasCommas) {
            return String.format("%s,%s,$%.2f,%s",
                    this.getProductName(),
                    this.getProductDescription(),
                    this.getPrice(),
                    this.getStore().getStoreName());
        } else {
            return String.format("%s%s$%.2f%s",
                    this.getProductName(),
                    this.getProductDescription(),
                    this.getPrice(),
                    this.getStore().getStoreName());
        }
    }

}
