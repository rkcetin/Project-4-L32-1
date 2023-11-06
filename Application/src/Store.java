import java.util.*;

public class Store {
    private final String storeName;
    private ArrayList<Product> products;

    public Store(String storeName, ArrayList<Product> products) {
        this.storeName = storeName;
        this.products = products;
    }

    public Store(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void addProduct(String name, String description, int stock, double price) {
        products.add(new Product(name, description, stock, price));
    }

    public void removeProduct(String name) {
        for (int i = 0; i < products.size(); i++) {
            if (name.equals(products.get(i).getName())) {
                products.remove(i);
                return;
            }
        }
    }

    public void editProduct(String name, Scanner scan) {
        for (Product product : products) {
            if (name.equals(product.getName())) {
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
                                product.setName(newName);
                            }
                            case 2 -> {
                                System.out.println("Enter the new product description.");
                                String newDesc = scan.nextLine();
                                product.setDescription(newDesc);
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


    public String toString() {
        String itemList = "";
        for (int i = 0; i < products.size(); i++) {
            itemList += products.get(i).toString();
        }
        return String.format("Store name: %s\nItems: %s", this.storeName, itemList);
    }
}
