import org.junit.Assert;
import org.junit.Test;
import java.util.*;

public class SharedRequirementTests {
    @Test
    public void testPersistantStorage() {
        ArrayList<Object> input = TestRunner.setUp();
        ArrayList<User> users = (ArrayList<User> ) input.get(0);
        ArrayList<Store> stores = (ArrayList<Store> ) input.get(1);
        ArrayList<Product> products =  (ArrayList<Product> ) input.get(2);
        Storage.storeData(users , stores , products);
        Object[] data = Storage.getData();
        ArrayList<User> newUser = (ArrayList<User>) data[0];
        ArrayList<Store> newStorage = (ArrayList<Store>) data[1];
        ArrayList<Product> newProduct = (ArrayList<Product>) data[2];
        org.junit.Assert.assertEquals(users.toString() , newUser.toString());
        org.junit.Assert.assertEquals(products.toString() , newProduct.toString());
        org.junit.Assert.assertEquals(stores.toString() , newStorage.toString());

        //checks if if values are the same
        StorageTest.reset();
        Storage.storeData(users, stores, products);
        data = Storage.getData();

        newUser = (ArrayList<User>) data[0];
        newStorage = (ArrayList<Store>) data[1];
        newProduct = (ArrayList<Product>) data[2];

        newProduct.get(0).setStock(101);
        org.junit.Assert.assertEquals(newProduct.get(0) , newStorage.get(0).getProducts().get(0));
        //checks if objects still cross reference
        StorageTest.reset();
        org.junit.Assert.assertThrows(NullPointerException.class, () -> {
            Storage.storeData(null, null , null);});
        org.junit.Assert.assertThrows(NullPointerException.class, () -> {
            Storage.storeData(new ArrayList<User>() , new ArrayList<Store>() , null);});
        org.junit.Assert.assertThrows(NullPointerException.class, () -> {
            Storage.storeData(null, null , new ArrayList<Product>());});
        StorageTest.reset();
        //check for invalid input
    }
    @Test
    public void testAccounts() throws Exception {
        ArrayList<User> users = new ArrayList<>();
        User seller = User.signup("email@email.com" , "password" , 1 , users);
        User customer = User.signup("bmail@email.com" , "password" , 2 , users);
        Assert.assertTrue(seller instanceof Seller);
        Assert.assertTrue(customer instanceof Customer); // check role signup
        String updatedUsername = "gmail@gmail.com";
        String updatedPassword = "new password";
        org.junit.Assert.assertThrows(Exception.class, () -> {
            User.signup("email@email.com" , "password" , 1 , users);
        });
        org.junit.Assert.assertThrows(Exception.class, () -> {
            User.signup("bmail@email.com" , "password" , 1 , users);
        });
        org.junit.Assert.assertThrows(Exception.class, () -> {
            User.signup("james" , "password" , 1 , users);
        });
        org.junit.Assert.assertThrows(Exception.class, () -> {
            User.signup("" , "password" , 1 , users);
        });
        org.junit.Assert.assertThrows(Exception.class, () -> {
            seller.setName("tacobell" , users);
        });
        org.junit.Assert.assertThrows(Exception.class, () -> {
            seller.setName("bmail@email.com" , users);
        });
        //tests invalid input

        seller.setName(updatedUsername , users);
        seller.setPassword(updatedPassword);
        Assert.assertTrue(updatedUsername.equals(seller.getName()));
        Assert.assertTrue(updatedPassword.equals(seller.getPassword()));

        //test changing information


        seller.deleteUser(users);
        Assert.assertFalse(users.toString().contains("gmail@gmail.com"));

        //checks delete user

    }
    @Test
    public void testStores() throws Exception{
        ArrayList<Store> stores = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();
        User seller = User.signup("email@email.com" , "password" , 1 , users);
        Seller sellerCasted = (Seller) seller;
        sellerCasted.createStore("1", stores);
        sellerCasted.createStore("2", stores);
        sellerCasted.createStore("3", stores);
        Assert.assertTrue(
                stores.toString().contains("1") &&
                        stores.toString().contains("2") &&
                        stores.toString().contains("3")
        );
        org.junit.Assert.assertThrows(Exception.class, () -> {
            sellerCasted.createStore("1" , stores);
        });
        org.junit.Assert.assertThrows(Exception.class, () -> {
            sellerCasted.createStore("" , stores);
        });



    }
}
