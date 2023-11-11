import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomerTest {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        ArrayList<Double[]> cart = new ArrayList<>();
        cart.add(new Double[] {15.50, 2.0});
        cart.add(new Double[] {16.50, 1.0});
        cart.add(new Double[] {17.50, 2.0});
        cart.add(new Double[] {12.50, 2.0});
        cart.add(new Double[] {11.51, 2.0});

        double cost = CustomerTest.calculatePrice(cart);
        CustomerTest.purchaseCart(scan, cart, cost);

        //cart should be empty after purchase
        int count = 0;
        for (int i = 0; i < cart.size(); i++) {
            System.out.println(cart.get(i)[0]);
            count++;
        }
        System.out.println(count); //should be 0
    }

    public static void purchaseCart(Scanner scan, ArrayList<Double[]> cart, double cost) throws IOException, IllegalArgumentException {
        PrintWriter pw = new PrintWriter(new FileWriter("test1.txt", true));
        String transactionHistory = "";
        double sales = 0.0;

        System.out.printf("Purchase all cart items for $%.2f?\n1. Confirm purchase\n2. Exit\n", cost);
        try {
            int choice = scan.nextInt();
            scan.nextLine();
            if (choice == 1) {
                for (int i = 0; i < cart.size(); i++) {
                    if (cart.get(i)[1] < 1) {
                        throw new IllegalArgumentException("Stock exceeded!");
                    }
                }
                for (int i = 0; i < cart.size(); i++) {
                    transactionHistory += "bought for " + cart.get(i)[0] + "\n";
                    cart.get(i)[0] = cart.get(i)[0] - 1;
                }
                for (int j = cart.size() - 1; j >= 0; j--) {
                    sales += cart.get(j)[0];
                    pw.println(cart.get(j)[0] + "-" + cart.get(j)[1]);
                    cart.remove(j);
                }
                pw.flush();
            } else if (choice == 2) {
                System.out.println("You have exited the purchase screen.");
                return;
            } else {
                System.out.println("Invalid input, try again.");
            }
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input, try again.");
        }
        System.out.printf("%.2f\n", sales);
        System.out.println();
        System.out.println(transactionHistory);
    }

    public static double calculatePrice(ArrayList<Double[]> cart) {
        double price = 0;
        for (int i = 0; i < cart.size(); i++) {
            price += cart.get(i)[0];
        }
        return price;
    }
}
