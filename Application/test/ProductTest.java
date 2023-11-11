import org.junit.*;

import java.util.ArrayList;
public class ProductTest {
    @Test
    public void testInvalidInput() {
        ArrayList<Object> input = TestRunner.setUp();
        ArrayList<User> users = (ArrayList<User> ) input.get(0);
        ArrayList<Store> stores = (ArrayList<Store> ) input.get(1);
        ArrayList<Product> products =  (ArrayList<Product> ) input.get(2);
        Product testProduct = products.get(0);
        org.junit.Assert.assertThrows(IllegalArgumentException.class, () -> {
            testProduct.setPrice(-1);
        });
        org.junit.Assert.assertThrows(IllegalArgumentException.class, () -> {
            testProduct.setStock(-10);
        });
        org.junit.Assert.assertThrows(NullPointerException.class, () -> {
            testProduct.setProductName(null);
        });
        org.junit.Assert.assertThrows(IllegalArgumentException.class, () -> {
            testProduct.setProductName("");
        });
        org.junit.Assert.assertThrows(NullPointerException.class, () -> {
            testProduct.setProductDescription(null);
        });
        org.junit.Assert.assertThrows(NullPointerException.class, () -> {
           Product.checkProduct(null , null);
        });
        org.junit.Assert.assertThrows(NullPointerException.class, () -> {
            Product.search(null , null);
        });
        org.junit.Assert.assertThrows(NullPointerException.class, () -> {
            Product.generateListing(null);
        });
        org.junit.Assert.assertThrows(NullPointerException.class, () -> {
            Product.generateListing(null);
        });
        org.junit.Assert.assertThrows(NullPointerException.class, () -> {
            Product.sortStock(false , null);
        });
        org.junit.Assert.assertThrows(NullPointerException.class, () -> {
            Product.sortPrice(false , null);
        });


    }
    @Test
    public void testSetAndGet() {
        ArrayList<Object> input = TestRunner.setUp();
        ArrayList<User> users = (ArrayList<User> ) input.get(0);
        ArrayList<Store> stores = (ArrayList<Store> ) input.get(1);
        ArrayList<Product> products =  (ArrayList<Product> ) input.get(2);
        stores.get(0).addProduct("storeName" , "description", 23 , 10.23 ,products);
        Product testProduct = Product.checkProduct("storeName" , products);
        testProduct.setProductName("changedName");
        testProduct.setStock(232323);
        testProduct.setPrice(12.34);
        testProduct.setProductDescription("a description");
        org.junit.Assert.assertEquals("changedName" , testProduct.getProductName());
        org.junit.Assert.assertEquals("a description" , testProduct.getProductDescription());
        org.junit.Assert.assertEquals(12.34 , testProduct.getPrice() , .0002);
        org.junit.Assert.assertEquals( 232323 , testProduct.getStock());
    }
    @Test
    public void testHelperFunctions() {
        ArrayList<Object> input = TestRunner.setUp();
        ArrayList<User> users = (ArrayList<User> ) input.get(0);
        ArrayList<Store> stores = (ArrayList<Store> ) input.get(1);
        ArrayList<Product> products =  (ArrayList<Product> ) input.get(2);
        stores.get(0).addProduct("storeName" , "description", 23 , 10.23 ,products);
        Product testProduct = Product.checkProduct("storeName" , products);
        org.junit.Assert.assertEquals(testProduct, stores.get(0).getProducts().get(1));
        org.junit.Assert.assertTrue(Product.search("product1", products).toString().contains("product1"));
        org.junit.Assert.assertFalse(Product.search("product1", products).toString().contains("storeName"));


    }
    @Test
    public void listingRelatedTesting() {
        ArrayList<Object> input = TestRunner.setUp();
        ArrayList<User> users = (ArrayList<User> ) input.get(0);
        ArrayList<Store> stores = (ArrayList<Store> ) input.get(1);
        ArrayList<Product> products =  (ArrayList<Product> ) input.get(2);

        stores.get(0).addProduct("prodHighQ" , "description", 100 , 0.0 ,products);
        stores.get(0).addProduct("prodLowQ" , "description", 0 , 100.00 ,products);
        org.junit.Assert.assertTrue( Product.generateListing(stores.get(0).getProducts()).toString().contains("prodHighQ")
                && Product.generateListing(stores.get(0).getProducts()).toString().contains("prodLowQ"));
        Product.sortStock(false ,stores.get(0).getProducts());
        org.junit.Assert.assertEquals( stores.get(0).getProducts().get(0).getStock() , 100);

        Product.sortStock(true ,stores.get(0).getProducts());
        org.junit.Assert.assertEquals( stores.get(0).getProducts().get(0).getStock() , 0);

        Product.sortPrice(false ,stores.get(0).getProducts());
        org.junit.Assert.assertEquals( stores.get(0).getProducts().get(0).getPrice() , 100.00 , .001);

        Product.sortPrice(true ,stores.get(0).getProducts());
        org.junit.Assert.assertEquals( stores.get(0).getProducts().get(0).getPrice() , 0.0 , .001);


    }

}
