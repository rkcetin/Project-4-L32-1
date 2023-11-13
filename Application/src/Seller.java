import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Project 4 -- Seller Class
 *
 * Class represents a Seller and extends User. Contains methods relating to Seller permissions
 * such as editing their stores. Contains method for importing products in addition to dashboard
 * methods for organizing and generate seller dashboards
 *
 * @author Steven Chang, Alexander Benson, Stephanie Sun, Chris Xu, Ramazan Cetin, L32
 *
 * @version November 12, 2023
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
    /**
     * creates a store and adds it to the provided list of stores in addition
     * to the seller's list of stores
     * @param storeName the new store name
     * @param bigStores the arraylist of stores to add to
     * @return returns the object of the created store
     */
    public Store createStore(String storeName, ArrayList<Store> bigStores) {
        Store createdStore = new Store(storeName , this);
        stores.add(createdStore);
        bigStores.add(createdStore);
        return createdStore;
    }
    /**
     * returns the object corresponding to a store name from the sellers owned stores
     * @param storeName the new store name
     * @return returns the store object of a particular store name
     */
    //returns a store with the given storeName

    public Store getStore(String storeName) {
        return Store.checkStore(storeName, this.stores);
    }

    //removes a store of the given name
    /**
     * removes the store from the seller's list and the larger bank of stores
     * @param storeName the target store name
     * @param stores large bank of stores to remove from
     */
    public void removeStore(String storeName , ArrayList<Store> stores) {
        Store.removeStore(storeName, this.stores);
        Store.removeStore(storeName, stores);
    }

    //returns an arraylist of stores
    /**
     * returns the seller's list of stores
     * @return returns the arraylist of a sellers stores
     */
    public ArrayList<Store> getStores() {
        return stores;
    }

    public ArrayList<String> getStoreNames() {
        ArrayList<String> storeNames = new ArrayList<>();
        for (int i = 0; i < stores.size(); i++) {
            storeNames.add(stores.get(i).getStoreName());
        }
        return storeNames;
    }

    public String getStoresString(ArrayList<Store> stores) {
        String list = "";
        for (int i = 0; i < stores.size(); i++) {
            list += stores.get(i).toString() + "\n";
        }
        return list;
    }

    //returns all the store names associated with a given store
    /**
     * gets all the names of the sellers stores

     * @return returns the string representation all the seller's store names
     */
    public String[] getSellerStoreNames() {
        return Store.listStoreNames(this.getStores());
    }

    /**
     * calulates the total sales for a store
     *
     * @return returns a double of all the sales of all the stores
     */
    public double calculateTotalSales() {
        double sales = 0.0;
        for (int i = 0; i < this.stores.size(); i++) {
            sales += this.stores.get(i).getSales();
        }
        return sales;
    }
    /**
     * returns the statistics for a particular sellers stores
     *
     * @throws IOException from invalid reading of files
     * @return returns the statistics for the sellers stores
     */
    public String displayUnsortedStatistics() throws IOException {
        BufferedReader bfr = new BufferedReader(new FileReader("statistics.txt"));
        Map<String, Integer> productCounts = new HashMap<>();
        String a = "";
        String line;

        while ((line = bfr.readLine()) != null) {
            for (int i = 0; i < stores.size(); i++) {
                if (line.split(",")[0].equals(stores.get(i).getStoreName())) {
                    String key = line.trim();  // Assuming leading/trailing whitespaces don't matter
                    productCounts.put(key, productCounts.getOrDefault(key, 0) + 1);
                }
            }
        }

        for (Map.Entry<String, Integer> entry : productCounts.entrySet()) {
            String product = entry.getKey();
            int count = entry.getValue();
            String countString = (count > 1) ? count + "x " : "1x ";
            a += String.format("%s%s,%s,%.2f\n",countString , product.split(",")[0], product.split(",")[2], (Double.parseDouble(product.split(",")[3]) * count));
        }
        return a;
    }

    //has to run with displayUnsortedStatistics()
    /**
     * returns a sorted representation of the statistics based upon sales either high or low
     * @param statisticsResult the string of unsorted statistics
     * @param highestToLowest a boolean determining whether statistics sorted starting high or low
     * @return returns the store object of a particular store name
     */
    public String sortStatisticsBySales(String statisticsResult, boolean highestToLowest) {
        List<String> lines = Arrays.asList(statisticsResult.split("\n"));
        lines.sort((line1, line2) -> {
            double sales1 = Double.parseDouble(line1.split(",")[2]);
            double sales2 = Double.parseDouble(line2.split(",")[2]);
            return highestToLowest ? Double.compare(sales2, sales1) : Double.compare(sales1, sales2);
        });

        return String.join("\n", lines);
    }
    /**
     * returns a sorted representation of the statistics based upon quantity of sales high or low
     * @param stores arraylist of stores to reference
     * @param highestToLowest a boolean determining whether statistics sorted starting high or low
     * @return returns a String of store statistics sorted based upon of quantity of sales
     */
    public String sortByOccurrences(ArrayList<String> stores, boolean highestToLowest) throws IOException {
        BufferedReader bfr = new BufferedReader(new FileReader("statistics.txt"));
        Map<String, Integer> storeOccurrences = new HashMap<>();
        String a = "";
        String line;

        while ((line = bfr.readLine()) != null) {
            for (int i = 0; i < stores.size(); i++) {
                if (line.split(",")[0].equals(stores.get(i))) {
                    String key = stores.get(i);
                    storeOccurrences.put(key, storeOccurrences.getOrDefault(key, 0) + 1);
                }
            }
        }

        List<Map.Entry<String, Integer>> sortedEntries = storeOccurrences.entrySet()
                .stream()
                .sorted((entry1, entry2) -> highestToLowest ?
                        Integer.compare(entry2.getValue(), entry1.getValue()) :
                        Integer.compare(entry1.getValue(), entry2.getValue()))
                .collect(Collectors.toList());

        for (Map.Entry<String, Integer> entry : sortedEntries) {
            String storeName = entry.getKey();
            int totalOccurrences = entry.getValue();
            a += totalOccurrences + " purchases from " + storeName + "\n";
        }
        return a;
    }
    /**
     * imports products from a csv file only imports if all customers are valid
     * @param filepath string representation of filepath arraylist of stores to reference
     * @param products arraylist of products to import the products too
     * @throws NullPointerException when null input
     * @throws Exception when invalid file format or trouble reading file
     */
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
                int inputQuantity = Integer.parseInt( (String) line[STOCK_INDEX]);
                double inputPrice = Double.parseDouble( (String) line[PRICE_INDEX]) ;

                Product inputProduct = new Product(inputStore , inputName , inputDesc , inputQuantity , inputPrice);
                inputStore.addProduct(inputProduct , products);
            }

        } catch (Exception e) {
            throw new Exception("Problem reading file");
        }
    }
    /**
     * checks if 2 sellers are equal based upon their name
     * @param o object for comparison
     * @return returns if the object has equal name to the seller

     */
    public boolean equals(Object o) {
        if (o instanceof Seller) {
            Seller seller = (Seller) o;
            return (seller.getName().equals(this.getName()));
        } else {
            return false;
        }
    }

    //THE METHOD BELOW IS OBSOLETE
    /*/**
     * returns dashboard of products for all the stores of a seller showing product name and quantity sold

     * @return returns a dashboard of the products for a seller showing product sold and product name
     *
    
    public String dashboardProducts() {
        String x = "";
        ArrayList<String> dashboard = new ArrayList<>();
        for (Store store : stores) {
            for (Product product : store.getProducts()) {
                dashboard.add(String.format("Product name: %s Sold: %d", product.getProductName(), product.getSold()));
            }
        }
        for (int i = 0; i < dashboard.size(); i++) {
            x += dashboard.get(i);
        }
        return x;
    }*/

    public List<String> customersOfStores(ArrayList<String> storeNames) {
        Map<String, Set<String>> storeToUserMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("statistics.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String storeName = parts[0].trim();
                    String userName = parts[1].trim();

                    if (storeNames.contains(storeName)) {
                        storeToUserMap.computeIfAbsent(storeName, k -> new HashSet<>()).add(userName);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> result = new ArrayList<>();
        for (String storeName : storeNames) {
            Set<String> users = storeToUserMap.getOrDefault(storeName, new HashSet<>());
            result.add(String.join(", ", users));
        }

        return result;
    }

    public ArrayList<String> viewTransactionHistory(ArrayList<String> inputStoreNames) throws IOException {
        ArrayList<String> filteredLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("statistics.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String storeName = parts[0].trim();
                    if (inputStoreNames.contains(storeName)) {
                        filteredLines.add(line);
                    }
                }
            }
        }

        return filteredLines;
    }
}