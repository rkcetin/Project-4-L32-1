
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
            // direct to customer menu using generateCustomerMenu
        } else if (workingUser instanceof Seller) {
            // direct to seller menu using generateSellerMenu
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
}
/*
    public void generateSellerMenu() {
        System.out.println("What would you like to do? Choose numbers 1-6.");
        System.out.println("1. View Stores \n2. Edit Account\n3.Delete Account\n4.Create Store\n5.Edit Store\n" +
                "6.Delete Store");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1 -> {
                //if seller is new, no stores are shown
            }
            case 2 -> {
                //edit account
            }
            case 3 -> {
                //delete account
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
*/

