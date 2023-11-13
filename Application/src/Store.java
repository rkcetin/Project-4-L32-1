import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

/**
 * Project 4 -- Store Class
 *
 * Class holds information about a store which contains a list of products associated with that store.
 * Includes methods to edit the Store. Also functions to generate dashboards and assist
 * with sales information.
 *
 * @author Steven Chang, Alexander Benson, Stephanie Sun, Chris Xu, Ramazan Cetin, L32
 *
 * @version November 6, 2023
 *
 */

public class Store implements Serializable {
    private static final long serialVersionUID = 118L;
    private final String storeName;
    private ArrayList<Product> products;
    private Seller seller;
    private double sales;
    private int soldProduct;
    private int productsInCart;

    public Store(String storeName, ArrayList<Product> products , Seller seller) {
        if (storeName == null || products == null || seller == null) {
            throw new NullPointerException();
        }
        if (storeName.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.storeName = storeName;
        this.products = products;
        this.seller = seller;
    }

    public Store(String storeName, Seller seller) {
        if (storeName == null ||  seller == null) {
            throw new NullPointerException();
        }
        if (storeName.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.storeName = storeName;
        products = new ArrayList<>();
        this.seller = seller;

    }

    public int getProductsInCart() {
        return productsInCart;
    }

    public void setProductsInCart(int productsInCart) {
        this.productsInCart = productsInCart;
    }

    /**
     * searches provided list for a store with the provided storename and returns the object if it is found
     * @param storeName storeName to look for
     * @param stores arraylist of stores to look within
     * @return returns the object for the store with corresponding storeName null if otherwise
     * @throws NullPointerException when null input
     *
     */
    //helper function for determining to determine if a store exists within a list of stores; returns null if it doesn't
    public static Store checkStore(String storeName, ArrayList<Store> stores) {
      if (stores == null || storeName == null) {
          throw new NullPointerException();
      }
      ArrayList<Store> filteredStores = new ArrayList<Store>(stores
              .stream()
              .filter(store -> store.getStoreName().equalsIgnoreCase(storeName) )
              .collect(Collectors.toList()));
      if (filteredStores.isEmpty()) {
          return null;
      }
      return filteredStores.get(0);
    }

    /**
     * removes store from list of store
     * @param storeName storeName to look for
     * @param stores arraylist of stores to look within
     * @throws NullPointerException when null input or if invalid store
     *
     */
    //helper function to remove a store from a list based on its name
    public static void removeStore(String storeName, ArrayList<Store> stores) throws IllegalArgumentException {
        Store storeToRemove = checkStore(storeName, stores);
        if (storeToRemove == null) {
            throw new IllegalArgumentException();
        }
        stores.remove(storeToRemove);

    }

    //helper function to get names of list of stores
    /**
     * creates a list of store names from a ArrayList of stores
     * @param stores arraylist of stores to list from
     * @throws NullPointerException when null input or if invalid store
     * @return returns String[] or store names
     */
    public static String[] listStoreNames(ArrayList<Store> stores) {
        if (stores == null) {
            throw new NullPointerException();
        }
        if (stores.isEmpty()) {
            return new String[0];
        }
        List<String> output = stores
                .stream()
                .map(Store::getStoreName)
                .collect(Collectors.toList());
        String[] storeList = new String[output.size()];
        output.toArray(storeList);
        return storeList;
    }
    /**
     * gets the store name

     * @return returns storename
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     *  creates a product in a store if it does not already exist in the store
     * @param name name of the product
     * @param description description of the product
     * @param stock stock of the product
     * @param price price of the product
     * @param product arraylist of products to add to
     *
     */
    public void addProduct(String name, String description, int stock, double price , ArrayList<Product> product) throws Exception {
        if (Product.checkProduct(name , this.products) != null) {
            throw new Exception("Already Exists");
        }
        Product addedProduct = new Product(this , name , description, stock , price);
        this.products.add(addedProduct);
        product.add(addedProduct);
    }
    /**
     *  creates a product in a store if it does not already exist in the store
     * @param product to add to the store
     * @param products arraylist of products to add to
     *
     */
    public void addProduct(Product product , ArrayList<Product> products) throws Exception {
        if (Product.checkProduct(product.getProductName() , products) != null) {
            throw new Exception();
        }
        this.products.add(product);
        products.add(product);
    }
    /**
     * removes a product from the store
     * @param name name of the product to remove
     * @param bigProducts arraylist to remove from
     *
     *
     */
    public void removeProduct(String name , ArrayList<Product> bigProducts) {
        Product productToRemove = Product.checkProduct(name , this.getProducts());
        if (productToRemove == null) {
            throw new IllegalArgumentException();
        }
        products.remove(productToRemove);
        bigProducts.remove(productToRemove);
    }
    /**
     * edits the information about a product
     * @param name name of to edit
     * @param scan scanner to read user input
     *
     */
    public void editProduct(String name, Scanner scan) {
        for (Product product : products) {
            if (name.equals(product.getProductName())) {
                System.out.println(product + "\n1. Modify Name\n2. Modify Description\n" +
                        "3. Modify Stock\n4. Modify Price");

                int choice = -1;
                while (choice < 1 || choice > 4) {
                    try {
                        choice = scan.nextInt();
                        scan.nextLine();

                        switch (choice) {
                            case 1 -> {
                                System.out.println("Enter the new product name.");
                                String newName = scan.nextLine();
                                product.setProductName(newName);
                            }
                            case 2 -> {
                                System.out.println("Enter the new product description.");
                                String newDesc = scan.nextLine();
                                product.setProductDescription(newDesc);
                            }
                            case 3 -> {
                                System.out.println("Enter the new stock.");
                                int newStock = scan.nextInt();
                                product.setStock(newStock);
                            }
                            case 4 -> {
                                System.out.println("Enter the new product price");
                                double newPrice = scan.nextDouble();
                                product.setPrice(newPrice);
                            }
                            default -> System.out.println("Invalid input, try again.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input, try again.");
                        scan.next();
                    }
                }
            }
        }
    }

    public int getSoldProduct() {
        return soldProduct;
    }

    public void incrementSoldProduct() {
        soldProduct++;
    }

    public double getSales() {
        return sales;
    }

    public void incrementSales(double sales) {
        this.sales += sales;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public String getProductsString(ArrayList<Product> products) {
        String productsList = "";
        for (int i = 0; i < products.size(); i++) {
            productsList += (i + 1) + ". " + products.get(i).toString2() + "\n";
        }
        return productsList;
    }
    /**
     * sees if two objects are equivalent in storename and seller
     * @param o object of comparison
     * @return boolean value seeing if the stores are equal
     *
     */
    public boolean equals(Object o) {
        if (o instanceof Store) {
            Store store = (Store) o;
            return (store.getStoreName().equals(this.getStoreName()) && store.seller.equals(this.seller));
        } else {
            return false;
        }
    }
    /**
     * generates to string for store
     *
     * @return returns string representation of store
     *
     */
    public String toString() {
        String itemList = "";
        for (int i = 0; i < products.size(); i++) {
            itemList += products.get(i).toString();
        }
        return String.format("Store name: %s\nItems:\n%s", this.storeName, itemList);
    }
}