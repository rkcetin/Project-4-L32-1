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
        User workingUser = null;
        while (workingUser == null) {
            System.out.println("Input desired Action as number");
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
                            System.out.println("Input Valid option");
                        }
                    }
                    if (exitCommand == 1) {
                        break;
                    }
                }
            } else if (input.equals("2") || input.equalsIgnoreCase("sign up")) {
                while (workingUser == null) {

                    try {
                        System.out.println("Enter your email: ");
                        String email = scanner.nextLine().trim();
                        System.out.println("Enter your password: ");
                        String password = scanner.nextLine();
                        System.out.println("Enter role:");
                        System.out.println("1. Seller \n2. Customer");
                        int role = binaryInputHandler(scanner.nextLine());
                        workingUser = User.signup(email, password, role, users);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("input valid number for role");
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
                            System.out.println("Input Valid option");
                        }
                    }
                    if (exitCommand == 1) {

                        break;
                    }


                }
            } else if (input.equals("3") || input.equalsIgnoreCase("Exit")) {
                break;
            } else {
                System.out.println("Invalid input");

            }
        }


        if (workingUser instanceof Customer) {
            Customer customer = (Customer) workingUser;
            generateCustomerMenu(customer);
        } else if (workingUser instanceof Seller) {
            Seller seller = (Seller) workingUser;
            generateSellerMenu(seller);
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
    public static void generateSellerMenu(Seller seller) throws Exception {
        System.out.println("What would you like to do? Choose numbers 1-6.");
        boolean sellerMain = true;
        do {
             System.out.println("1. View Stores\n2. Create Store\n3. Delete Store\n4. Import products\n5. Edit account\n6. Delete Account");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> {
                    System.out.println(seller.getStores().toString());
                }
                case 2 -> {
                    boolean cont = true;
                    do {
                        System.out.println("Enter name of the store");
                        String storeName = scanner.nextLine();
                        seller.createStore(storeName, stores);
                        System.out.println("Store created, would you like to add products right now? Choose between 1 or 2");
                        boolean cont2 = true;
                        do {
                            System.out.println("1 - Add Product\n2 - Exit");
                            choice = scanner.nextInt();
                            scanner.nextLine();
                            switch (choice) {
                            case 1 -> {
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
                                    cont2 = false;
                                }
                            }
                            case 2 -> {
                                cont2 = false;
                            }
                            }
                        } while (cont2);
                        System.out.println("Would you like to keep adding stores? Enter 1 for yes");
                        if (Integer.parseInt(scanner.nextLine()) != 1) {
                            cont = false;
                        }
                    } while (cont);
                }
                case 3 -> {
                    System.out.println("Enter name of the store that you'd like to be deleted");
                    String deleteStore = scanner.nextLine();
                    seller.removeStore(deleteStore);
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
                    seller.setName(newEmail);
                    seller.setPassword(newPassword);
                }
                case 6 -> {
                    seller.deleteUser();
                }                
            }
            System.out.println("Enter 1 if you want to go to main menu");
            int cont = Integer.parseInt(scanner.nextLine());
            if (cont != 1) {
                sellerMain = false;
            }
        } while(sellerMain);
    }
    public static void generateCustomerMenu(Customer customer) throws Exception {
        System.out.println("What would you like to do? Choose numbers 1-6.");
        boolean customerMain = true;
        do {
            System.out.println("1. View Products \n2. Edit Account\n3.Delete Account\n4. See Cart\n5. See Dashboard\n6. Extract Transaction History");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> {
                    boolean mainCont = true;
                    do {
                        System.out.println("Enter the index of the product that you want to see more of.");
                        for (int i = 1 ; i <= products.size(); i++) {
                            System.out.println(String.format("%d - Store: %s - Product name: %s - Price: %2.1f", i ,products.get(i - 1).getStore().getStoreName(), products.get(i- 1).getProductName(), products.get(i- 1).getPrice()));
                        }
                        int x = Integer.parseInt(scanner.nextLine());
                        if (x < 1 || x > products.size()) {
                            System.out.println("Invalid input!");
                            break;
                        }
                        System.out.println(String.format("Description: %s - Stock: %d", products.get(x - 1).getProductDescription(), products.get(x - 1).getStock()));
                        System.out.println("Do you want to add this product to cart? If so, enter 1");
                        if (Integer.parseInt(scanner.nextLine()) == 1) {
                            System.out.println("Enter quantity");
                            int quantity = Integer.parseInt(scanner.nextLine());
                            customer.addToCart(products.get(x - 1).getStore(), products.get(x - 1).getProductName(), quantity, products);
                        }
                        System.out.println("Enter 1 if  you want to keep searching for products");
                        if (Integer.parseInt(scanner.nextLine()) != 1) {
                            mainCont = false;
                        }
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
                case 4 -> {
                    if (customer.getCart() != null) {
                        System.out.println("If you want to purchase the cart, enter 0. If you want to remove the product from the cart, enter the product index. Enter -1 to exit");
                        ArrayList<Product> newList = new ArrayList<>();
                        for (Product product : customer.getCart()) {
                            if (!newList.contains(product)) {
                                newList.add(product);
                            }
                        }
                        for (int i = 1; i <= newList.size(); i++) {
                            System.out.println(String.format("%d - %s", i, newList.get(i - 1).toString2()));
                        }
                        int index = Integer.parseInt(scanner.nextLine());
                        if (index < -1 || index > newList.size()) {
                            System.out.println("Invalid index!");
                        } else if (index == -1) {

                        } else if (index == 0) {
                            customer.purchaseCart(scanner);
                        }
                    } else {
                        System.out.println("Cart is empty!");
                    }
                }
                case 5 -> {
                    System.out.println(customer.dashboardbyBought());
                    System.out.println("\n\n");
                    System.out.println(customer.dashboardbySold(stores));
                }
                case 6 -> {
                    System.out.println("Choose between 1 and 2.");
                    System.out.println("1 - See Transaction History\n2 - Extract Transaction History");
                    int x = Integer.parseInt(scanner.nextLine());
                    if (x != 1 && x != 2) {
                        System.out.println("Invalid input!");
                        break;
                    } else if (x == 1) {
                        System.out.println(customer.getTransactionHistory());
                    } else if (x == 2) {
                        customer.extractTransactionHistory();
                    }
                }
                default -> {
                    System.out.println("Invalid number choice");
                }
            }
            System.out.println("Enter 1 if you'd like to see the main menu again");
            if (Integer.parseInt(scanner.nextLine()) != 1) {
                customerMain = false;
            }
        } while(customerMain);
    }
}
