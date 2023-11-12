/*import java.util.*;

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
                        if (role == 1 || role == 2) {
                            workingUser = User.signup(email, password, role, users);
                            users.add(workingUser);  // Assuming workingUser is added to the users list
                        } else {
                            throw new Exception("Invalid Role for Signup");
                        }
                        break;
                    default:
                        throw new Exception("Invalid Option");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            if (workingUser instanceof Customer) {
                // direct to customer menu using generateCustomerMenu
            } else if (workingUser instanceof Seller) {
                // direct to seller menu using generateSellerMenu
            }

            System.out.println(workingUser.getName());
            Storage.storeData(users, stores, products);
        }
    }
}
*/

