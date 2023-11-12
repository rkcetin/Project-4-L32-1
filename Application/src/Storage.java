import java.io.*;
import java.util.*;

/**
 * Project 4 -- Storage Class
 *
 * Class provides means to read and write array lists to file for persistent storage.
 *
 * @author Steven Chang, Alexander Benson, Stephanie Sun, Chris Xu, Ramazan Cetin, L32
 *
 * @version November 6, 2023
 *
 */

public class Storage {
    private static final int USER_INDEX = 0;
    private static final int STORE_INDEX = 1 ;
    private static final int PRODUCT_INDEX = 2;
    public static void storeData(ArrayList<User> users , ArrayList<Store> stores , ArrayList<Product> products) {
        if (users == null || stores == null || products == null) {
            throw new NullPointerException();
        }
        Object[] data = {users , stores , products};
        File databaseFile = new File("Data.ser");
        try {
            databaseFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(databaseFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.flush();
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object[] getData() {
        Object[] data = null;
        try {
            File dataFile = new File("Data.ser");
            if (dataFile.createNewFile()) {
                storeData(new ArrayList<User>() , new ArrayList<Store>(), new ArrayList<Product> () );

            }
            FileInputStream fis = new FileInputStream(dataFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            data = (Object[]) ois.readObject();

            fis.close();
            ois.close();


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }
}