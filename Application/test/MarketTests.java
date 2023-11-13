import org.junit.*;
import java.util.*;
public class MarketTests {
    @Test
    public void marketTests() throws Exception {
        ArrayList<Object> input = TestRunner.setUp();
        ArrayList<User> users = (ArrayList<User> ) input.get(0);
        ArrayList<Store> stores = (ArrayList<Store> ) input.get(1);
        ArrayList<Product> products =  (ArrayList<Product> ) input.get(2);

        stores.get(0).addProduct("prodHighQ" , "description", 100 , 0.0 ,products);
        stores.get(0).addProduct("prodLowQ" , "description", 0 , 100.00 ,products);
        String output = "";
        for (int i = 1; i <= products.size(); i++) {
            output += String.format("%d - Store: %s | Product: %s | Price: %.2f | Remaining Stock: %d\n", i, products.get(i - 1).getStore().getStoreName(), products.get(i - 1).getProductName(), products.get(i - 1).getPrice(), products.get(i - 1).getStock());
        }
        Assert.assertTrue(output.contains("Product: prodHighQ | Price: 0.00 | Remaining Stock: 100"));  // shows description

        Product targetProd = Product.checkProduct("prodHighQ" , products);

        Customer customer = new Customer("name" , "pw" , "123" , 0);
        customer.singlePurchase(stores.get(0) , "prodHighQ" , 10, products);
        Assert.assertEquals(90 ,
                Product.checkProduct("prodHighQ" , stores.get(0).getProducts())  // checking decreasing stock
                        .getStock()
        );



    }
    @Test
    public void sellerTests() throws Exception {
        ArrayList<Object> input = TestRunner.setUp();
        ArrayList<User> users = (ArrayList<User> ) input.get(0);
        ArrayList<Store> stores = (ArrayList<Store> ) input.get(1);
        ArrayList<Product> products =  (ArrayList<Product> ) input.get(2);
        stores.get(0).addProduct("storeName" , "description", 23 , 10.23 ,products); //testing adding methods
        Product testProduct = Product.checkProduct("storeName" , products);
        testProduct.setProductName("changedName" , products);
        testProduct.setStock(232323);
        testProduct.setPrice(12.34);
        testProduct.setProductDescription("a description");
        org.junit.Assert.assertEquals("changedName" , testProduct.getProductName());
        org.junit.Assert.assertEquals("a description" , testProduct.getProductDescription());
        org.junit.Assert.assertEquals(12.34 , testProduct.getPrice() , .0002);
        org.junit.Assert.assertEquals( 232323 , testProduct.getStock());     //testing edit methods
        stores.get(0).removeProduct( "changedName" , products);
        Assert.assertTrue(Product.checkProduct("changedName" , products) == null); //testing removing methods
    }
    @Test
    public void sellerInputErrors() throws Exception {
        ArrayList<Object> input = TestRunner.setUp();
        ArrayList<User> users = (ArrayList<User> ) input.get(0);
        ArrayList<Store> stores = (ArrayList<Store> ) input.get(1);
        ArrayList<Product> products =  (ArrayList<Product> ) input.get(2);
        stores.get(0).addProduct("storeName" , "description", 23 , 10.23 ,products); //testing adding methods
        Product testProduct = Product.checkProduct("storeName" , products);
        org.junit.Assert.assertThrows(Exception.class, () -> {
            testProduct.setProductName("" , products);
        });
        org.junit.Assert.assertThrows(Exception.class, () -> {
            testProduct.setStock(-1);
        });
        org.junit.Assert.assertThrows(Exception.class, () -> {
            testProduct.setPrice(-1.00);
        });
        org.junit.Assert.assertThrows(Exception.class, () -> {
            testProduct.setProductName("store1" , products);
        });


    }
    @Test
    public void customerTests() throws Exception {
        ArrayList<Object> input = TestRunner.setUp();
        ArrayList<User> users = (ArrayList<User> ) input.get(0);
        ArrayList<Store> stores = (ArrayList<Store> ) input.get(1);
        ArrayList<Product> products =  (ArrayList<Product> ) input.get(2);

        stores.get(0).addProduct("prodHighQ" , "description", 100 , 0.0 ,products);
        stores.get(0).addProduct("prodLowQ" , "description", 0 , 100.00 ,products);
        org.junit.Assert.assertTrue( Product.generateListing(stores.get(0).getProducts()).toString().contains("prodHighQ")
                && Product.generateListing(stores.get(0).getProducts()).toString().contains("prodLowQ")); //checking listing function

        Product.sortStock(false ,stores.get(0).getProducts());
        org.junit.Assert.assertEquals( stores.get(0).getProducts().get(0).getStock() , 100);

        Product.sortStock(true ,stores.get(0).getProducts());
        org.junit.Assert.assertEquals( stores.get(0).getProducts().get(0).getStock() , 0);

        Product.sortPrice(false ,stores.get(0).getProducts());
        org.junit.Assert.assertEquals( stores.get(0).getProducts().get(0).getPrice() , 100.00 , .001);

        Product.sortPrice(true ,stores.get(0).getProducts());
        org.junit.Assert.assertEquals( stores.get(0).getProducts().get(0).getPrice() , 0.0 , .001);  //checking sorting functions

        input = TestRunner.setUp();
        users = (ArrayList<User> ) input.get(0);
        stores = (ArrayList<Store> ) input.get(1);
        products =  (ArrayList<Product> ) input.get(2);
        stores.get(0).addProduct("storeName" , "description", 23 , 10.23 ,products);
        Product testProduct = Product.checkProduct("storeName" , products);
        org.junit.Assert.assertEquals(testProduct, stores.get(0).getProducts().get(1));
        org.junit.Assert.assertTrue(Product.search("product1", products).toString().contains("product1"));  //checks search
        org.junit.Assert.assertFalse(Product.search("product1", products).toString().contains("storeName")); ///checks serach





    }
    @Test
    public void consumerNullTestCases() {
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
            testProduct.setProductName(null , products);
        });
        org.junit.Assert.assertThrows(IllegalArgumentException.class, () -> {
            testProduct.setProductName("" , products);
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
}
