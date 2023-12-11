import java.io.*;
import java.util.*;
import java.net.*;

public class Server {
    private static ArrayList<User> users;
    private static ArrayList<Product> products;
    private static ArrayList<Store> stores;
    public static void main(String[] args) throws IOException {
        Object[] data = Storage.getData();
        users = (ArrayList<User>) data[0];
        stores = (ArrayList<Store>) data[1];
        products = (ArrayList<Product>) data[2];


        try {
            ServerSocket socket = new ServerSocket(9000);


            while (true) {
                Socket clientSocket = socket.accept();
                ClientThread t = new ClientThread(users, stores , products, clientSocket);
                t.start();

            }
        } catch (Exception e) {
            Storage.storeData(users , stores, products);
        }
    }

}
