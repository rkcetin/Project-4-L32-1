import java.io.*;
import java.net.*;
import java.util.*;
public class ClientThread extends Thread {
    final ObjectInputStream inputStream;
    final ObjectOutputStream outputStream;
    final Socket socket;
    final ArrayList<User> users;
    final ArrayList<Store> stores;
    final ArrayList<Product> products;

    public ClientThread(ArrayList<User> users , ArrayList<Store> stores , ArrayList<Product> products, Socket socket) throws Exception
    {

        this.inputStream = new ObjectInputStream(socket.getInputStream());
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.socket = socket;
        this.users = users;
        this.stores = stores;
        this.products = products;

    }
    public void run() {
        try {
            boolean registration = inputStream.readBoolean();
            User currentUser = null;
            if (registration) {
                String[] registrationInfo = (String[]) inputStream.readObject();
                try {
                    currentUser = User.signup(
                            registrationInfo[0],
                            registrationInfo[1],
                            Integer.parseInt(registrationInfo[2]),
                            users
                    );
                    outputStream.writeBoolean(true);
                } catch (Exception e) {
                    outputStream.writeBoolean(false);
                }
            } else {
                String[] logInfo = (String[]) inputStream.readObject();
                try {
                    currentUser = User.login(
                            logInfo[0],
                            logInfo[1],
                            users
                    );
                    outputStream.writeBoolean(true);
                } catch (Exception e) {
                    outputStream.writeBoolean(false);
                }

            }



            socket.close();
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
