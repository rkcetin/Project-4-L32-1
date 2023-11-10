import org.junit.*;

import java.util.ArrayList;

public class StorageTest {
    private static Seller seller1 = new Seller("bob" , "123","123");
    private static Store store1 = new Store("store" , seller1);
    private static Product product1 = new Product(store1, "test1" , "desc" , 5 ,2.1);

    private static Seller seller2 = new Seller("rob" , "123","123");
    private static Store store2 = new Store("bare" , seller2);
    private static Product product2 = new Product(store2, "test2" , "desc" , 5 ,2.1);

    private static Seller seller3 = new Seller("mob" , "123","123");
    private static Store store3 = new Store("stare" , seller3);
    private static Product product3 = new Product(store3, "test3" , "desc" , 5 ,2.1);
    @After
    public void reset() {
        String[] commands =  {"bash" , "-c" , "rm *.ser"};
        try {
            Runtime.getRuntime().exec(commands);
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }
    @Test
    public void testUser() {
        ArrayList<User> users = new ArrayList<>();

        users.add(seller1);
        users.add(seller2);
        users.add(seller3);


        Storage.storeUsers(users);


        ArrayList<User> pulledUser = Storage.getUsers();

        org.junit.Assert.assertEquals(pulledUser , users);
        org.junit.Assert.assertEquals(pulledUser.size() , users.size());
        org.junit.Assert.assertEquals(pulledUser.toString() , users.toString());
        org.junit.Assert.assertTrue(pulledUser.contains(seller1));
        org.junit.Assert.assertTrue(users.contains(seller1));
        org.junit.Assert.assertEquals(pulledUser.get(0), users.get(0));
        reset();
    }
    @Test
    public void testProduct() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        Storage.storeProducts(products);
        ArrayList<Product> pulledProducts = Storage.getProducts();
        org.junit.Assert.assertEquals(pulledProducts.size() , products.size());
        org.junit.Assert.assertEquals(pulledProducts.toString() , products.toString());
        org.junit.Assert.assertEquals(pulledProducts.get(0).getStore(), products.get(0).getStore());

        reset();
    }
    @Test
    public void testStore() {
        ArrayList<Store> stores = new ArrayList<>();
        stores.add(store1);
        stores.add(store2);
        stores.add(store3);
        Storage.storeStores(stores);
        ArrayList<Store> pulledStores = Storage.getStores();
        org.junit.Assert.assertEquals(pulledStores.size() , stores.size());
        org.junit.Assert.assertEquals(pulledStores.toString() , stores.toString());
        org.junit.Assert.assertTrue(pulledStores.equals(stores));

        reset();
    }
    @Test
    public void testStartGet() {
        ArrayList<User> users = Storage.getUsers();
        users.add(seller1);
        users.add(seller2);
        users.add(seller3);
        Storage.storeUsers(users);
        ArrayList<User> pulledUser = Storage.getUsers();
        org.junit.Assert.assertEquals(pulledUser , users);
        reset();

    }
    @Test
    public void invalidInputTest() {
        org.junit.Assert.assertThrows(NullPointerException.class , () -> {
            Storage.storeUsers(null);
        });
        org.junit.Assert.assertThrows(NullPointerException.class , () -> {
            Storage.storeProducts(null);
        });
        org.junit.Assert.assertThrows(NullPointerException.class , () -> {
            Storage.storeStores(null);
        });
        reset();
    }


}
