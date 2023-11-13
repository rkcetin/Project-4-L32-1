import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.*;

public class ShoppingCartTest {
    @Test
    public void cartContentsPreserveTest() throws Exception {
        ArrayList<Object> input = TestRunner.setUp();
        ArrayList<User> users = (ArrayList<User> ) input.get(0);
        ArrayList<Store> stores = (ArrayList<Store> ) input.get(1);
        ArrayList<Product> products =  (ArrayList<Product> ) input.get(2);
        Customer customer = new Customer("email@email.com" , "password" ,"12" , 0);
        users.add(customer);
        customer.addToCart(stores.get(0) , stores.get(0).getProducts().get(0).getProductName() , 3 , products);
        Storage.storeData(users, stores, products);
        Object[] data = Storage.getData();
        ArrayList<User> newUser = (ArrayList<User>) data[0];
        ArrayList<Store> newStorage = (ArrayList<Store>) data[1];
        ArrayList<Product> newProduct = (ArrayList<Product>) data[2];
        Customer oldCustomer = (Customer) User.isEmailRegistered("email@email.com" , users);
        Assert.assertEquals(3 , oldCustomer.getCart().size());
        Assert.assertTrue(oldCustomer.getCart().get(0).getProductName().equals(stores.get(0).getProducts().get(0).getProductName()));

    }
    @Test
    public void sellerViewTest() throws Exception {
        ArrayList<Object> input = TestRunner.setUp();
        ArrayList<User> users = (ArrayList<User> ) input.get(0);
        ArrayList<Store> stores = (ArrayList<Store> ) input.get(1);
        ArrayList<Product> products =  (ArrayList<Product> ) input.get(2);
        Customer customer = new Customer("email@email.com" , "password" ,"12" , 0);
        users.add(customer);
        customer.addToCart(stores.get(0) , stores.get(0).getProducts().get(0).getProductName() , 3 , products);
        ArrayList<Product> cart = Customer.getCart("email@email.com" , users);
        Assert.assertEquals(3 , cart.size());
        Assert.assertTrue(cart.get(0).getProductName().equals(stores.get(0).getProducts().get(0).getProductName()));
    }
    @Test
    public void addRemoveTest()  throws Exception {
        ArrayList<Object> input = TestRunner.setUp();
        ArrayList<User> users = (ArrayList<User> ) input.get(0);
        ArrayList<Store> stores = (ArrayList<Store> ) input.get(1);
        ArrayList<Product> products =  (ArrayList<Product> ) input.get(2);
        Customer customer = new Customer("email@email.com" , "password" ,"12" , 0);
        users.add(customer);
        customer.addToCart(stores.get(0) , stores.get(0).getProducts().get(0).getProductName() , 3 , products);
        customer.removeFromCart(stores.get(0) , stores.get(0).getProducts().get(0).getProductName());
        Assert.assertEquals(0 , customer.getCart().size());
    }
    @Test
    public void cartPurchaseTest() throws Exception {
        ArrayList<Object> input = TestRunner.setUp();
        ArrayList<User> users = (ArrayList<User> ) input.get(0);
        ArrayList<Store> stores = (ArrayList<Store> ) input.get(1);
        ArrayList<Product> products =  (ArrayList<Product> ) input.get(2);
        Customer customer = new Customer("email@email.com" , "password" ,"12" , 0);
        users.add(customer);
        customer.addToCart(stores.get(0) , stores.get(0).getProducts().get(0).getProductName() , 1 , products);
        customer.addToCart(stores.get(1) , stores.get(1).getProducts().get(0).getProductName() , 1 , products);
        int initialStock = stores.get(0).getProducts().get(0).getStock() + stores.get(1).getProducts().get(0).getStock();
        ByteArrayInputStream testIn = new ByteArrayInputStream("1\n".getBytes());
        System.setIn(testIn);
        Scanner scan = new Scanner(System.in);
        customer.purchaseCart(scan);
        int finalStock = stores.get(0).getProducts().get(0).getStock() + stores.get(1).getProducts().get(0).getStock();
        Assert.assertEquals(initialStock - 2 , finalStock );


    }
    @Test
    public void invalidInputTest() throws Exception {
        ArrayList<Object> input = TestRunner.setUp();
        ArrayList<User> users = (ArrayList<User> ) input.get(0);
        ArrayList<Store> stores = (ArrayList<Store> ) input.get(1);
        ArrayList<Product> products =  (ArrayList<Product> ) input.get(2);
        Customer customer = new Customer("email@email.com" , "password" ,"12" , 0);
        users.add(customer);

        org.junit.Assert.assertThrows(Exception.class, () -> {
            customer.addToCart(stores.get(0) , stores.get(0).getProducts().get(0).getProductName() , -1 , products);
        });
    }

}
