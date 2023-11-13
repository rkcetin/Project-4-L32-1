import org.junit.*;
import java.io.*;
import java.io.ByteArrayInputStream;
import java.util.*;
public class FileTest {
    @After
    public void reset() {
        String[] commands1 =  {"bash" , "-c" , "rm *.csv"};
        String[] commands2 = {"bash" , "-c" , "rm *.txt"};
        try {
            Runtime.getRuntime().exec(commands1);
            Runtime.getRuntime().exec(commands2);
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }
    @Test
    public void sellerImportExportTest() throws Exception {
        ArrayList<Store> stores = new ArrayList<>();
        ArrayList<Product> products = new ArrayList<>();
        Seller seller = new Seller("seller" , "pw" , "123");
        seller.createStore("store" , stores);
        Store store = Store.checkStore("store" , stores);
        store.addProduct("pd" , "des" , 5 , 10.1 , products);
        store.addProduct("pd1" , "des" , 5 , 10.1 , products);
        store.addProduct("pd2" , "des" , 5 , 10.1 , products);
        seller.exportProducts(store , "test");
        store.removeProduct("pd" , products);
        store.removeProduct("pd1" , products);
        store.removeProduct("pd2" , products);
        seller.importProducts("test.csv" , products);
        Assert.assertEquals(3 , seller.getStore("store").getProducts().size());
    }
    @Test
    public void customerOutputTest() throws Exception {
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
        String history = customer.getTransactionHistory();
        customer.extractTransactionHistory();
        File fr = new File(customer.getName() + "transactionHistory.txt");
        FileReader fileReader = new FileReader(fr);
        BufferedReader bfr = new BufferedReader(fileReader);
        String combinedLine = "";

        String currentLine = bfr.readLine();
        while (currentLine != null) {
            combinedLine += currentLine + "\n";
            currentLine = bfr.readLine();
        }
        Assert.assertEquals(combinedLine , history );

    }
    @Test
    public void invalidInputTest() throws Exception {
        ArrayList<Store> stores = new ArrayList<>();
        ArrayList<Product> products = new ArrayList<>();
        Seller seller = new Seller("seller" , "pw" , "123");
        seller.createStore("store" , stores);
        Store store = Store.checkStore("store" , stores);
        store.addProduct("pd" , "des" , 5 , 10.1 , products);
        store.addProduct("pd1" , "des" , 5 , 10.1 , products);
        store.addProduct("pd2" , "des" , 5 , 10.1 , products);
        org.junit.Assert.assertThrows(Exception.class, () -> {
            seller.exportProducts(store , "");
        });
        org.junit.Assert.assertThrows(Exception.class, () -> {
            seller.importProducts("test" , products);
        });
        org.junit.Assert.assertThrows(Exception.class, () -> {
            seller.importProducts("" , products);
        });


    }



}
