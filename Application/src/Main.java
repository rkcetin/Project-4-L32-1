import java.util.*;

public class Main {
    private static final int USER_INDEX = 0;
    private static final int STORE_INDEX = 1;
    private static final int PRODUCT_INDEX = 2;

    public static void main(String[] args) {
        Object[] data = Storage.getData();

        ArrayList<User> users = (ArrayList<User>) data[USER_INDEX];

        ArrayList<Product> products = (ArrayList<Product>) data[PRODUCT_INDEX];
        ArrayList<Store> stores = (ArrayList<Store>) data[STORE_INDEX];

        Scanner scanner = new Scanner(System.in);
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

        System.out.println("Enter role:");
        System.out.println("1. Customer \n2. Seller");
        role = scanner.nextInt();
        if (scanner.hasNextInt()) {
            role = scanner.nextInt();
            if (role < 1 || role > 2) {
                System.out.println("Invalid role. Exiting.");
                return;
            }
        } else {
            String roleInput = scanner.next();
            if (roleInput.equals("1") || roleInput.equalsIgnoreCase("customer")) {
                role = 1;
            } else if (roleInput.equals("2") || roleInput.equalsIgnoreCase("seller")) {
                role = 2;
            }

            User workingUser = null;

            try {
                switch (logOrSign) {
                    case 1:
                        workingUser = User.login(email, password, users);
                        break;
                    case 2:
                        if (role == 1) {
                            workingUser = new Customer();
                        } else if (role == 2) {
                            workingUser = new Seller();
                        } else {
                            throw new Exception("Invalid Role for Signup");
                        }
                        users.add(workingUser);  // Assuming workingUser is added to the users list
                        break;
                    default:
                        throw new Exception("Invalid Option");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println(workingUser.getName());
            Storage.storeData(users, stores, products);

        }
        public void generateSellerMenu() {
            System.out.println("What would you like to do? Choose numbers 1-6.");
            System.out.println("1. View Stores \n2. Edit Account\n3.Delete Account\n4.Create Store\n5.Edit Store\n" +
                    "6.Delete Store");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> {
                    yield Seller.getStoreNames();
                    //if seller is new, no stores are shown
                }
                case 2 -> {
                    yield
                    //
                }
                case 3 -> {
                    //
                }
                case 4 -> {
                    //create store function
                }
                case 5 -> {
                    //edit store function
                }
                case 6 -> {
                    //delete store function
                }
                default -> {
                    throw new Exception("Invalid Number Choice")
                }
            }
        }
        public void generateCustomerMenu() {
            System.out.println("What would you like to do? Choose numbers 1-3.");
            System.out.println("1. View Stores \n2. Edit Account\n3.Delete Account");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> {
                    //list store names function
                }
                case 2 -> {
                    //edit account
                }
                case 3 -> {
                    //delete account
                }
                default -> {
                    throw new Exception("Invalid Number Choice")
                }
        }
    }
}
