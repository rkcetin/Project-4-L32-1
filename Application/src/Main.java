import java.net.SocketImpl;
import java.util.*;

public class Main {
    private static final int USER_INDEX = 0;
    private static final int STORE_INDEX = 1;
    private static final int PRODUCT_INDEX = 2;
    private static Scanner scanner = new Scanner(System.in);
    private static Object[] data = Storage.getData();
    private static ArrayList<User> users = (ArrayList<User>) data[USER_INDEX];
    private static ArrayList<Product> products = (ArrayList<Product>) data[PRODUCT_INDEX];
    private static ArrayList<Store> stores = (ArrayList<Store>) data[STORE_INDEX];

    public static void main(String[] args) throws Exception {
        System.out.println("1. Login \n2. Sign Up");
        String input = scanner.nextLine();
        int logOrSign;
        int role;

        if (input.equals("1") || input.equalsIgnoreCase("login")) {
            logOrSign = 1;
        } else if (input.equals("2") || input.equalsIgnoreCase("sign up")) {
            logOrSign = 2;
        } else {
            System.out.println("Invalid input. Exiting.");
            return;
        }

        System.out.print("Enter your email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User user = switch (logOrSign) {
            case 1:
            yield User.login(email, password, users);
            case 2:
            System.out.println("Enter role:");
            System.out.println("1. Customer \n2. Seller");
            role = scanner.nextInt();
            scanner.nextLine();
            yield User.signup(email, password, role, users);
            default:
            yield null;
        };
        if (user instanceof Seller) {
            int again;
            do {
                generateSellerMenu((Seller) user);
            } while ((again = Integer.parseInt("Press 1 if you'd like to see the menu again")) == 1);
        } else if (user instanceof Customer) {
            user = (Customer) user;
            int again;
            do {
                generateCustomerMenu((Customer) user);
            } while ((again = Integer.parseInt("Press 1 if you'd like to see the menu again")) == 1);
        }
    }
    public static void generateSellerMenu(Seller seller) throws Exception {
        System.out.println("What would you like to do? Choose numbers 1-6.");
        boolean sellerMain = true;
        do {
             System.out.println("1. View Stores\n2. Create Store\n3. Delete Store\n4. Import products\n5. Edit account\n6. Delete Account");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                System.out.println(seller.getStores().toString());
                break;
                case 2:
                System.out.println("Enter name of the store");
                String storeName = scanner.nextLine();
                seller.createStore(storeName, stores);
                System.out.println("Store created, would you like to add products right now? Choose between 1 or 2");
                boolean cont = true;
                while (cont) {
                    System.out.println("1 - Add Product\n2 - Exit");
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice) {
                    case 1:
                    System.out.println("Enter product name");
                    String name = scanner.nextLine();
                    System.out.println("Enter description for the product");
                    String desc = scanner.nextLine();
                    System.out.println("Enter stock");
                    int stock = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter price");
                    double price = Double.parseDouble(scanner.nextLine());
                    seller.getStore(storeName).addProduct(name, desc, stock, price, products);
                    System.out.println("Would you like to continue adding product? Type 1 for continue");
                    int con = Integer.parseInt(scanner.nextLine());
                    if (con != 1) {
                        cont = false;
                    }
                    }
                }
                break;
                case 3:
                System.out.println("Enter name of the store that you'd like to be deleted");
                String deleteStore = scanner.nextLine();
                seller.removeStore(deleteStore);
                break;
                case 4:
                System.out.println("Type the filepath of the .csv file");
                String csvImportPath = scanner.nextLine();
                seller.importProducts(csvImportPath, products);
                break;
                case 5:
                System.out.println("Enter new e-mail");
                String newEmail = scanner.nextLine();
                System.out.println("Enter new password");
                String newPassword = scanner.nextLine();
                seller.setName(newEmail);
                seller.setPassword(newPassword);
                break;
                case 6:
                seller.deleteUser();
                break;
            }
            System.out.println("Enter 1 if you want to go to main menu");
            int cont = Integer.parseInt(scanner.nextLine());
            if (cont != 1) {
                sellerMain = false;
            }
        } while(sellerMain);
    }
    public static void generateCustomerMenu(Customer customer) throws Exception {
        System.out.println("What would you like to do? Choose numbers 1-3.");
        boolean customerMain = true;
        do {
            System.out.println("1. View Stores \n2. Edit Account\n3.Delete Account");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> {
                    boolean mainCont = true;
                    do {
                        System.out.println("Enter the index of the store that you want to visit");
                        for (int i = 1; i <= stores.size(); i++) {
                            System.out.println(String.format("%d - %s", i, stores.get(i - 1).getStoreName()));
                            int index = Integer.parseInt(scanner.nextLine());
                            if (index > stores.size() || index < 1) {
                                System.out.println("Invalid input!");
                            } else {
                                boolean cont = true;
                                do {
                                    for (int j = 0; j < stores.get(index).getProducts().size(); j++) {
                                    System.out.println(String.format("%d - Name: %s\nDescription: %s\nPrice: %d\nStock: %d", stores.get(index).getProducts().get(j - 1).getProductName(),
                                                                                                                                    stores.get(index).getProducts().get(j - 1).getProductDescription(),
                                                                                                                                    stores.get(index).getProducts().get(j - 1).getPrice(),
                                                                                                                                    stores.get(index).getProducts().get(j - 1).getStock()));
                                }
                                System.out.println("Enter the index of the product you want to add to cart");
                                int index2 = Integer.parseInt(scanner.nextLine());
                                if (index > stores.get(index).getProducts().size() || index < 1) {
                                    System.out.println("Invalid input!");
                                } else {
                                    System.out.println("Enter quantity");
                                    int quantity = Integer.parseInt(scanner.nextLine());
                                    if (quantity < 0) {
                                        System.out.println("Invalid input!");
                                    } else {
                                        customer.addToCart(stores.get(index), stores.get(index).getProducts().get(index2).getProductName(), quantity, products);
                                        System.out.println("Added to cart!");
                                    }
                                }
                                System.out.println("Enter 1 if you want to add more products from this store");
                                int keep = Integer.parseInt(scanner.nextLine());
                                if (keep != 1) cont = false;
                                } while(cont);
                            }
                        }
                        System.out.println("Enter 1 if you want to keep searhing products from all stores");
                        int contt;
                        if ((contt = Integer.parseInt(scanner.nextLine())) != 1) mainCont = false;
                    } while (mainCont);
                }
                case 2 -> {
                    System.out.println("Enter new e-mail");
                    String newEmail = scanner.nextLine();
                    System.out.println("Enter new password");
                    String newPassword = scanner.nextLine();
                    customer.setName(newEmail);
                    customer.setPassword(newPassword);
                }
                case 3 -> {
                    customer.deleteUser();
                }
                default -> {
                    throw new Exception("Invalid Number Choice");
                }
            }   
        } while(customerMain);
    }
}
