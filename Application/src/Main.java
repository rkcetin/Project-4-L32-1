import java.util.*;

public class Main {
    private static final int USER_INDEX = 0;
    private static final int STORE_INDEX = 1;
    private static final int PRODUCT_INDEX = 2;

    public static void main(String[] args) throws Exception {
        Object[] data = Storage.getData();
        ArrayList<User> users = (ArrayList<User>) data[USER_INDEX];
        ArrayList<Product> products = (ArrayList<Product>) data[PRODUCT_INDEX];
        ArrayList<Store> stores = (ArrayList<Store>) data[STORE_INDEX];
        Scanner scanner = new Scanner(System.in);
        User workingUser = null;
        boolean restart = true;

        outerLoop:
        while (workingUser == null) {
            System.out.println("Welcome to the Marketplace\nInput desired action as number.");
            System.out.println("1. Login \n2. Sign Up \n3. Exit");
            String input = scanner.nextLine();

            if (input.equals("1") || input.equalsIgnoreCase("login")) {
                while (workingUser == null) {
                    try {

                        System.out.print("Enter your email: ");
                        String email = scanner.nextLine().trim();
                        System.out.print("Enter your password: ");
                        String password = scanner.nextLine();
                        workingUser = User.login(email, password, users);
                        break;

                    }  catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    int exitCommand = 0;
                    System.out.println("Do you want to Exit Login\n 1. Yes \n 2. No");
                    while (exitCommand == 0) {
                        try {

                            String exitInput = scanner.nextLine();
                            exitCommand = binaryInputHandler(exitInput);

                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input, try again.");
                        }
                    }
                    if (exitCommand == 1) {
                        break;
                    }
                }
            } else if (input.equals("2") || input.equalsIgnoreCase("Sign Up")) {
                while (workingUser == null) {
                    try {
                        System.out.println("Enter a sign up email: ");
                        String email = scanner.nextLine().trim();
                        System.out.println("Enter a sign up password: ");
                        String password = scanner.nextLine();
                        System.out.println("Choose a role:\n1. Seller \n2. Customer");
                        int role = binaryInputHandler(scanner.nextLine());
                        workingUser = User.signup(email, password, role, users);

                        System.out.println("1. Login now\n2. Exit");
                        int x = scanner.nextInt();
                        scanner.nextLine();
                        if (x == 1) {
                            workingUser = null;
                            Storage.storeData(users, stores, products);
                            continue outerLoop;
                        } else if (x == 2) {
                            Storage.storeData(users, stores, products);
                            return;
                        }
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input, pick either 1 or 2.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    int exitCommand = 0;
                    while (exitCommand == 0) {
                        System.out.println("Do you want to Exit Signup\n 1. Yes \n 2. No");
                        try {
                            String exitInput = scanner.nextLine();
                            exitCommand = binaryInputHandler(exitInput);
                        } catch (NumberFormatException e) {
                            System.out.println("Input valid option");
                        }
                    }
                    if (exitCommand == 1) {
                        break;
                    }
                }
            } else if (input.equals("3") || input.equalsIgnoreCase("Exit")) {
                break;
            } else {
                System.out.println("Invalid input, try again.");

            }
        }

        if (workingUser instanceof Customer) {
            // direct to customer menu using generateCustomerMenu
            Customer currentUser = (Customer) workingUser;
            Main.generateCustomerMenu(scanner, currentUser,users, stores, products);
        } else if (workingUser instanceof Seller) {
            // direct to seller menu using generateSellerMenu
            Seller currentUser = (Seller) workingUser;
            Main.generateSellerMenu(scanner, currentUser, users,  stores, products);
        }


        Storage.storeData(users, stores, products);
    }

    public static int binaryInputHandler(String input) throws NumberFormatException {
        int numInput = Integer.parseInt(input);
        if (numInput != 1 && numInput != 2) {
            throw new NumberFormatException();
        }
        return numInput;
    }

    public static int numberInputHandler(String input, int numOptions) throws NumberFormatException {
        int numInput = Integer.parseInt(input);
        int[] numberArr = new int[numOptions];
        for (int i = 1; i <= numOptions; i++) {
            numberArr[i - 1] = i;
        }
        if (!Arrays.asList(numberArr).contains(numInput)) {
            throw new NumberFormatException();
        }
        return numInput;
    }

    public static void generateSellerMenu(Scanner scanner, Seller seller, ArrayList<User> users,  ArrayList<Store> stores, ArrayList<Product> products) throws Exception {
        System.out.println("What would you like to do? Choose numbers 1-6.");
        boolean sellerMain = true;
        do {
            System.out.println("1. View Stores\n2. Create Store\n3. Delete Store\n4. Import products\n5. Edit account\n6. Delete Account");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> System.out.println(seller.getStores().toString());
                case 2 -> {
                    System.out.println("Enter name of the store");
                    String storeName = scanner.nextLine();
                    seller.createStore(storeName, stores);
                    System.out.println("Store created, would you like to add products right now? Choose between 1 or 2");
                    boolean cont = true;
                    while (cont) {
                        System.out.println("1 - Add Product\n2 - Exit");
                        choice = scanner.nextInt();
                        scanner.nextLine();
                        if (choice == 1) {
                            System.out.println("Enter product name");
                            String name = scanner.nextLine();
                            System.out.println("Enter description for the product");
                            String desc = scanner.nextLine();
                            System.out.println("Enter stock to add");
                            int stock = Integer.parseInt(scanner.nextLine());
                            System.out.println("Enter product price");
                            double price = Double.parseDouble(scanner.nextLine());
                            seller.getStore(storeName).addProduct(name, desc, stock, price, products);
                            System.out.println("Would you like to continue adding product? Type 1 for continue");
                            int con = Integer.parseInt(scanner.nextLine());
                            if (con != 1) {
                                cont = false;
                            }
                        }
                    }
                }
                case 3 -> {
                    System.out.println("Enter name of the store that you'd like to be deleted");
                    String deleteStore = scanner.nextLine();
                    seller.removeStore(deleteStore , stores);
                }
                case 4 -> {
                    System.out.println("Type the filepath of the .csv file");
                    String csvImportPath = scanner.nextLine();
                    seller.importProducts(csvImportPath, products);
                }
                case 5 -> {
                    System.out.println("Enter new e-mail");
                    String newEmail = scanner.nextLine();
                    System.out.println("Enter new password");
                    String newPassword = scanner.nextLine();
                    seller.setName(newEmail , users);
                    seller.setPassword(newPassword);
                }
                case 6 -> System.out.println("placeholder");//seller.deleteUser();
            }
            System.out.println("Enter 1 if you want to go to main menu");
            int cont = Integer.parseInt(scanner.nextLine());
            if (cont != 1) {
                sellerMain = false;
            }
        } while(sellerMain);
    }
    public static void generateCustomerMenu(Scanner scanner, Customer customer, ArrayList<User> users, ArrayList<Store> stores, ArrayList<Product> products) throws Exception {
        System.out.println("What would you like to do? Choose numbers 1-3.");
        boolean customerMain = true;
        do {
            System.out.println("1. View Stores \n2. Edit Account\n3. Delete Account");
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
                    customer.setName(newEmail, users);
                    customer.setPassword(newPassword);
                }
                case 3 -> {
                    //customer.deleteUser();
                    System.out.println("placeholder");
                }
                default -> {
                    throw new Exception("Invalid Number Choice");
                }
            }
        } while(customerMain);
    }
}
