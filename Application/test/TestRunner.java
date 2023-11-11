import org.junit.runner.*;
import org.junit.runner.Result;

import java.sql.Array;
import java.util.*;
public class TestRunner {

    public static ArrayList<Object> setUp() {
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();
        ArrayList<Store> stores = new ArrayList<>();
        Seller seller1 = new Seller("seller1" , "tacobell1" , "1234");
        Seller seller2 = new Seller("seller2" , "tacobell2" , "1234");
        Seller seller3 = new Seller("seller3" , "tacobell3" , "1234");
        users.add(seller1);
        users.add(seller2);
        users.add(seller3);
        seller1.createStore("store1" , stores);
        seller2.createStore("store2" , stores);
        seller3.createStore("store3" , stores);
        seller1.getStore("store1").addProduct("product1", "description" , 10 , 10.2 , products);
        seller2.getStore("store2").addProduct("product2", "description" , 10 , 10.2 , products);
        seller3.getStore("store3").addProduct("product3", "description" , 10 , 10.2 , products);
        ArrayList<Object> output = new ArrayList<>();
        output.add(users);
        output.add(stores);
        output.add(products);
        return output;
    }
    public static void main(String[] args) {
        Result resultStorage = JUnitCore.runClasses(StorageTest.class);
        Result resultProduct = JUnitCore.runClasses(ProductTest.class);
        Result resultUser = JUnitCore.runClasses(UserTest.class);
        System.out.println(resultStorage.wasSuccessful());
        System.out.println(resultProduct.wasSuccessful());
        System.out.println(resultUser.wasSuccessful());
    }
}
