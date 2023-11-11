import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Project 4 -- Seller Class
 *
 * Class represents a Seller and extends User. Contains methods relating to Seller permissions
 * such as editing their stores
 *
 * @author Steven Chang, Alexander Benson, Stephanie Sun, Chris Xu, Ramazan Cetin, L32
 *
 * @version November 6, 2023
 *
 */

public class Seller extends User {
    private static final int STORE_INDEX = 1;
    private static final int NAME_INDEX = 0;
    private static final int DESCRIPTION_INDEX = 2;
    private static final int STOCK_INDEX = 3;
    private static final int PRICE_INDEX = 4;
    private static final int SPECIFC_LENGTH = 5;
    private ArrayList<Store> stores;
    private double totalSales;

    //standard constructor that extends from the user class
    public Seller(String name, String password , String salt) {
        super(name, password , salt);
        stores = new ArrayList<>();
    }

    //creates a new store with the given store name
    public Store createStore(String storeName, ArrayList<Store> bigStores) {
        Store createdStore = new Store(storeName , this);
        stores.add(createdStore);
        bigStores.add(createdStore);
        return createdStore;
    }

    //returns a store with the given storeName
    public Store getStore(String storeName) {
        return Store.checkStore(storeName, this.stores);
    }

    //removes a store of the given name
    public void removeStore(String storeName) {
        Store.removeStore(storeName, this.stores);
    }

    //returns an arraylist of stores
    public ArrayList<Store> getStores() {
        return stores;
    }

    //returns all the store names associated with a given store
    public String[] getSellerStoreNames() {
        return Store.listStoreNames(this.getStores());
    }

    public double calculateTotalSales() {
        double sales = 0.0;
        for (int i = 0; i < this.stores.size(); i++) {
            sales += this.stores.get(i).getSales();
        }
        return sales;
    }

    public ArrayList<String> displayUnsortedStatistics() throws IOException {
        BufferedReader bfr = new BufferedReader(new FileReader("statistics.txt"));
        Map<String, Integer> productCounts = new HashMap<>();
        ArrayList<String> unsortedStats = new ArrayList<>();

        String line;

        while ((line = bfr.readLine()) != null) {
            for (Store store : stores) {
                if (line.split(",")[0].equals(store.getStoreName())) {
                    String key = line.trim();
                    productCounts.put(key, productCounts.getOrDefault(key, 0) + 1);
                }
            }
        }

        for (Map.Entry<String, Integer> entry : productCounts.entrySet()) {
            String product = entry.getKey();
            int count = entry.getValue();
            String countString;
            if (count > 1) {
                countString = count + "x ";
            } else {
                countString = "";
            }
            unsortedStats.add(countString + product.split(",")[0] + "," + product.split(",")[1] + ","
                    + product.split(",")[2]);
            System.out.println(countString + product);
        }
        return unsortedStats;
    }

    //has to run with displayUnsortedStatistics()
    public static String sortStatisticsBySales(String statisticsResult, boolean highestToLowest) {
        List<String> lines = Arrays.asList(statisticsResult.split("\n"));
        lines.sort((line1, line2) -> {
            double sales1 = Double.parseDouble(line1.split(",")[2]);
            double sales2 = Double.parseDouble(line2.split(",")[2]);
            return highestToLowest ? Double.compare(sales2, sales1) : Double.compare(sales1, sales2);
        });

        return String.join("\n", lines);
    }

    public void importProducts(String filepath, ArrayList<Product> products) throws Exception {
        if (filepath == null || products == null) {
            throw new NullPointerException();
        }
        if (!filepath.contains(".csv")) {
            throw new Exception("invalid file format");
        }
        ArrayList<Object[]> csvInputs = new ArrayList<>();
        try {
            File csv = new File(filepath);
            FileReader fr = new FileReader(csv);
            BufferedReader bfr = new BufferedReader(fr);
            String currentLine = bfr.readLine();
            while (currentLine != null) {
                String[] workingList = currentLine.split(",");
                if (workingList.length != SPECIFC_LENGTH) {
                    throw new Exception();
                }
                if (workingList[NAME_INDEX].isEmpty() || workingList[DESCRIPTION_INDEX].isEmpty()) {
                    throw new Exception();
                }
                if (Store.checkStore(workingList[STORE_INDEX], this.getStores()) == null) { // will throw exception if not in seller store list
                    throw new Exception();
                }
                Integer.parseInt(workingList[STOCK_INDEX]); // will throw exception if wrong format
                Double.parseDouble(workingList[PRICE_INDEX]);  // will throw exception if wrong format
                csvInputs.add(workingList);

                currentLine = bfr.readLine();
            }
            fr.close();
            bfr.close();
            for (Object[] line : csvInputs) {
                Store inputStore = Store.checkStore((String) line[STORE_INDEX], this.getStores());
                String inputName = (String) line[NAME_INDEX];
                String inputDesc = (String) line[DESCRIPTION_INDEX];
                int inputQuantity = Integer.parseInt((String) line[STOCK_INDEX]);
                double inputPrice = Double.parseDouble((String) line[PRICE_INDEX]);
                Product inputProduct = new Product(inputStore, inputName, inputDesc, inputQuantity, inputPrice);
                inputStore.addProduct(inputProduct);
                products.add(inputProduct);

            }

        } catch (Exception e) {
            throw new Exception("Problem reading file");
        }
    }

    public boolean equals(Object o) {
        if (o instanceof Seller) {
            Seller seller = (Seller) o;
            return (seller.getName().equals(this.getName()));
        } else {
            return false;
        }
    }

}