import java.io.*;
import java.util.*;

/**
 * Project 4 -- Storage Class
 *
 * Class provides means to read and write array lists to file for  persistent storage
 *
 * @author Steven Chang, Alexander Benson, Stephanie Sun, Chris Xu, Ramazan Cetin, L32
 *
 * @version November 6, 2023
 *
 */

public class Storage {
    /**
     * Writes an Arraylist of Users as an object to the Users.ser file
     *
     * @param uses ArrayList of Users to write to Users.ser file
     */
    public static void storeUsers(ArrayList<User> users) {
        if (users == null) {
            throw new NullPointerException();
        }
        File userFile = new File("Users.ser");

        try {
            userFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(userFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(users);
            oos.flush();
            oos.close();
            fos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Writes an Arraylist of Stores as an object to the Stores.ser file
     *
     * @param uses ArrayList of Stores to write to Store.ser file
     */
    public static void storeStores(ArrayList<Store> stores) {
        if(stores == null) {
            throw new NullPointerException();
        }
        File storeFile = new File("Stores.ser");

        try {
            storeFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(storeFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(stores);
            oos.flush();
            oos.close();
            fos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Writes an Arraylist of products as an object to the Products.ser file
     *
     * @param uses ArrayList of Products to write to Products.ser file
     */
    public static void storeProducts(ArrayList<Product> products) {
        if(products == null) {
            throw new NullPointerException();
        }
        File productFile = new File("Products.ser");

        try {
            productFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(productFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(products);
            oos.flush();
            oos.close();
            fos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Reads the ArrayList written to the Users.ser file
     *
     * @returns an ArrayList whihc is either a new Arraylist or the ArrayList<User> written to Users.ser
     */
    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            File userFile = new File("Users.ser");
            if (userFile.createNewFile()) {
                storeUsers(users);

            }
            FileInputStream fis = new FileInputStream(userFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            users = (ArrayList<User>) ois.readObject();
            fis.close();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Reads the ArrayList written to the Stores.ser file
     *
     * @returns an ArrayList whihc is either a new Arraylist or the ArrayList<Store> written to Stores.ser
     */
    public static ArrayList<Store> getStores() {
        ArrayList<Store> stores = new ArrayList<>();
        try {
            File storeFile = new File("Stores.ser");
            if (storeFile.createNewFile()) {
                storeStores(stores);

            }
            FileInputStream fis = new FileInputStream(storeFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            stores = (ArrayList<Store>) ois.readObject();
            fis.close();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return stores;
    }

    /**
     * Reads the ArrayList written to the Products.ser file
     *
     * @returns an ArrayList which is either a new Arraylist or the ArrayList<Product> written to Products.ser
     */
    public static ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        try {
            File productFile = new File("Products.ser");
            if (productFile.createNewFile()) {
                storeProducts(products);

            }
            FileInputStream fis = new FileInputStream(productFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            products = (ArrayList<Product>) ois.readObject();
            fis.close();
            ois.close();


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return products;
    }

}

