import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
public class Main {
    public static void main(String[] args) {
        ArrayList<User> users = Storage.getUsers();
        ArrayList<Product> products = Storage.getProducts();
        ArrayList<Store> stores = Storage.getStores();

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
                default -> {
                    throw new Exception("Invalid Option");
                }

            };

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }







}
