import java.io.*;
import java.util.*;
/**
 * Project 4 -- Storage Class
 *
 * Class provides means to read and write array lists to file for  peristant storage
 *
 * @author Steven Chang Alexander Benson Stephanie Sun Chris Xu Ramazan Cetin, lab l32
 *
 * @version November 6, 2023
 *
 */
public class Storage {
    public static void storeUsers(ArrayList<User> users) {
        if(users == null) {
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
    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            File userFile = new File("Users.ser");
            if (userFile.createNewFile()) {
                throw new FileNotFoundException("User.ser no values");
            }
            FileInputStream fis = new FileInputStream(userFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            users = (ArrayList<User>) ois.readObject();
            fis.close();
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }
    public static ArrayList<Store> getStores() {
        ArrayList<Store> stores = new ArrayList<>();
        try {
            File storeFile = new File("Stores.ser");
            if (storeFile.createNewFile()) {
                throw new FileNotFoundException("Stores.ser no values");
            }
            FileInputStream fis = new FileInputStream(storeFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            stores = (ArrayList<Store>) ois.readObject();
            fis.close();
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return stores;
    }
    public static ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        try {
            File productFile = new File("Products.ser");
            if (productFile.createNewFile()) {
                throw new FileNotFoundException("Products.ser no values");
            }
            FileInputStream fis = new FileInputStream(productFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            products = (ArrayList<Product>) ois.readObject();
            fis.close();
            ois.close();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return products;
    }


}

