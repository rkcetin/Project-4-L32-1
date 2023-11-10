import java.util.*;

public class Main {
    private static final int USER_INDEX = 0;
    private static final int STORE_INDEX = 1;
    private static final int PRODUCT_INDEX = 2;

    public static void main(String[] args) {
        Object[] data = Storage.getData();

        ArrayList<User> users = (ArrayList<User> ) data[USER_INDEX];

        ArrayList<Product> products =   (ArrayList<Product> ) data[PRODUCT_INDEX];
        ArrayList<Store> stores =(ArrayList<Store> ) data[STORE_INDEX];

        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Login \n2. Sign Up");
        int logOrSign = scanner.nextInt();
        scanner.nextLine();

        // Prompt the user for email and password
        System.out.print("Enter your email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        System.out.println("Enter role");
        System.out.println("1. Customer \n2. Seller");
        int role = scanner.nextInt();
        scanner.nextLine();

        User workingUser = null;
        try {
            workingUser = switch (logOrSign) {
                case 1 -> {
                    yield User.login(email, password, users);
                }
                case 2 -> {
                    yield User.signup(email, password, role, users);
                }
                default -> throw new Exception("Invalid Option");
            };
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(workingUser.getName());
        Storage.storeData(users , stores , products);
    }

}