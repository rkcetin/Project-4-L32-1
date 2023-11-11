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
    public void testCrossObjectRelation() {
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<Store> stores = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();

        users.add(seller1);
        users.add(seller2);
        users.add(seller3);

        stores.add(store1);
        stores.add(store2);
        stores.add(store3);

        store1.addProduct(product1 , products);
        store2.addProduct(product2 , products);
        store3.addProduct(product3 , products);



        Storage.storeData(users, stores, products);
        Object[] data = Storage.getData();

        ArrayList<User> newUser = (ArrayList<User>) data[0];
        ArrayList<Store> newStorage = (ArrayList<Store>) data[1];
        ArrayList<Product> newProduct = (ArrayList<Product>) data[2];

        newProduct.get(0).setStock(101);
        newProduct.get(0).setPrice(10.1123);
        org.junit.Assert.assertEquals(newProduct.get(0) , newStorage.get(0).getProducts().get(0));


        ;
        reset();
    }
    @Test
    public void storageRetrievalTest() {
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<Store> stores = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();

        users.add(seller1);
        users.add(seller2);
        users.add(seller3);

        stores.add(store1);
        stores.add(store2);
        stores.add(store3);

        store1.addProduct(product1 , products);
        store2.addProduct(product2 , products);
        store3.addProduct(product3 , products);



        Storage.storeData(users, stores, products);
        Object[] data = Storage.getData();

        ArrayList<User> newUser = (ArrayList<User>) data[0];
        ArrayList<Store> newStorage = (ArrayList<Store>) data[1];
        ArrayList<Product> newProduct = (ArrayList<Product>) data[2];
        org.junit.Assert.assertEquals(users.toString() , newUser.toString());
        org.junit.Assert.assertEquals(products.toString() , newProduct.toString());
        org.junit.Assert.assertEquals(stores.toString() , newStorage.toString());
        reset();
    }

    @Test
    public void invalidInputTest() {
        org.junit.Assert.assertThrows(NullPointerException.class, () -> {
            Storage.storeData(null, null , null);});
        org.junit.Assert.assertThrows(NullPointerException.class, () -> {
            Storage.storeData(new ArrayList<User>() , new ArrayList<Store>() , null);});
        org.junit.Assert.assertThrows(NullPointerException.class, () -> {
            Storage.storeData(null, null , new ArrayList<Product>());});
        reset();
    }


}
