import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

/**
 * Project 4 -- Store Class
 *
 * Class holds information about a store which contains a list of products associated with that store.
 * Includes methods to edit the Store
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

    //helper function to remove a store from a list based on its name
    public static void removeStore(String storeName, ArrayList<Store> stores) {
        Store storeToRemove = checkStore(storeName, stores);
        if (storeToRemove == null) {
            throw new IllegalArgumentException();
        }
        stores.remove(storeToRemove);

    }

    //helper function to get names of list of stores
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

    public String getStoreName() {
        return storeName;
    }

    public void addProduct( String name, String description, int stock, double price , ArrayList<Product> product) {
        Product addedProduct = new Product(this , name , description, stock , price);
        this.products.add(addedProduct);
        product.add(addedProduct);
    }
    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(String name) {
        for (int i = 0; i < products.size(); i++) {
            if (name.equals(products.get(i).getProductName())) {
                products.remove(i);
                return;
            }
        }
    }

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

    public double getSales() {
        return sales;
    }

    public void incrementSales(double sales) {
        this.sales += sales;
    }

    public boolean equals(Object o) {
        if (o instanceof Store) {
            Store store = (Store) o;
            return (store.getStoreName().equals(this.getStoreName()) && store.seller.equals(this.seller));
        } else {
            return false;
        }
    }

    public String toString() {
        String itemList = "";
        for (int i = 0; i < products.size(); i++) {
            itemList += products.get(i).toString();
        }
        return String.format("Store name: %s\nItems: %s", this.storeName, itemList);
    }
}
