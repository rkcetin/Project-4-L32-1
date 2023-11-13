import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class StatTest {
    public static void main(String[] args) throws IOException {
        ArrayList<String> stores = new ArrayList<>();
        stores.add("Meal");
        stores.add("Drink");

        //testing the basic unsorted statistics
        String statisticsResult = getStatistics(stores);
        System.out.print("Test 1 Running....");
        if (statisticsResult.equals("1x Drink,Lemonade,2.0\n1x Meal,Burger,8.0\n2x Meal,Burger,16.0\n2x Drink,Lemonade,4.0\n")) {
            System.out.println("Test 1 passed");
        } else {
            System.out.println("Test 1 failed");
        }

        //testing statistics sorted by sales
        String sortedResult = sortStatisticsBySales(statisticsResult, true);
        String sortedResult2 = sortStatisticsBySales(statisticsResult, false);

        System.out.print("Test 2 Running....");
        if (sortedResult.equals("2x Meal,Burger,16.0\n1x Meal,Burger,8.0\n2x Drink,Lemonade,4.0\n1x Drink,Lemonade,2.0") && sortedResult2.equals("1x Drink,Lemonade,2.0\n2x Drink,Lemonade,4.0\n1x Meal,Burger,8.0\n2x Meal,Burger,16.0")) {
            System.out.println("Test 2 passed");
        } else {
            System.out.println("Test 2 failed");
        }

        String occurrence = sortByOccurrence(stores, true);
        String occurrence2 = sortByOccurrence(stores, false);
        System.out.print("Test 3 Running....");
        if (occurrence.equals("4 purchases from Drink\n") && occurrence2.equals("4 purchases from Drink\n")) {
            System.out.println("Test 3 passed");
        } else {
            System.out.println("Test 3 failed");
        }

        String username = "User1";
        Map<String, Integer> purchaseCounts = getPurchaseCounts("statTests.txt", username);
        String sortedResults = sortPurchaseCounts(purchaseCounts, true);
        String sortedResults2 = sortPurchaseCounts(purchaseCounts, false);
        System.out.print("Test 4 Running....");
        if (sortedResults.equals("Meal: 2 purchases\nDrink: 2 purchases\nSnack: 2 purchases") && sortedResults2.equals("Meal: 2 purchases\nDrink: 2 purchases\nSnack: 2 purchases")) {
            System.out.println("Test 4 passed");
        } else {
            System.out.println("Test 4 failed");
        }


        Map<String, Integer> storeCounts = countStoreOccurrences();
        String sortedResults3 = sortStoreCounts(storeCounts, true);
        String sortedResults4 = sortStoreCounts(storeCounts, false);
        System.out.print("Test 5 Running....");
        if (sortedResults3.equals("Snack: 4 purchases\nMeal: 3 purchases\nDrink: 3 purchases") && sortedResults4.equals("Meal: 3 purchases\nDrink: 3 purchases\nSnack: 4 purchases")) {
            System.out.println("Test 5 passed");
        } else {
            System.out.println("Test 5 failed");
        }


        ArrayList<String> inputStoreNames = new ArrayList<>();
        inputStoreNames.add("Meal");
        inputStoreNames.add("Snack");

        List<String> result = analyzeTransactionHistory("statTests.txt", inputStoreNames);
        String customerFromStore = "";
        for (int i = 0; i < inputStoreNames.size(); i++) {
            String storeName = inputStoreNames.get(i);
            customerFromStore += "Users who purchased from " + storeName + ": " + result.get(i) + "\n";
        }
        System.out.print("Test 6 running....");
        if (customerFromStore.equals("Users who purchased from Meal: User2, User1\n" +
                "Users who purchased from Snack: User1, User4, User3\n")) {
            System.out.println("Test 6 passed");
        } else {
            System.out.println("Test 6 failed");
        }
    }

    public static String getStatistics(ArrayList<String> stores) throws IOException {
        BufferedReader bfr = new BufferedReader(new FileReader("statTests.txt"));
        Map<String, Integer> productCounts = new HashMap<>();
        String a = "";
        String line;

        while ((line = bfr.readLine()) != null) {
            for (int i = 0; i < stores.size(); i++) {
                if (line.split(",")[0].equals(stores.get(i))) {
                    String key = line.trim();
                    productCounts.put(key, productCounts.getOrDefault(key, 0) + 1);
                }
            }
        }

        for (Map.Entry<String, Integer> entry : productCounts.entrySet()) {
            String product = entry.getKey();
            int count = entry.getValue();
            String countString = (count > 1) ? count + "x " : "1x ";
            a += countString + product.split(",")[0] + "," + product.split(",")[2] + "," +
                    (Double.parseDouble(product.split(",")[3]) * count) + "\n";
        }
        return a;
    }

    public static String sortStatisticsBySales(String statisticsResult, boolean highestToLowest) {
        List<String> lines = Arrays.asList(statisticsResult.split("\n"));
        lines.sort((line1, line2) -> {
            double sales1 = Double.parseDouble(line1.split(",")[2]);
            double sales2 = Double.parseDouble(line2.split(",")[2]);
            return highestToLowest ? Double.compare(sales2, sales1) : Double.compare(sales1, sales2);
        });

        return String.join("\n", lines);
    }

    public static String sortByOccurrence(ArrayList<String> stores, boolean highestToLowest) throws IOException {
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

    public static Map<String, Integer> getPurchaseCounts(String fileName, String username) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        Map<String, Integer> purchaseCounts = new HashMap<>();
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 4 && parts[1].equals(username)) {
                String storeName = parts[0];
                purchaseCounts.put(storeName, purchaseCounts.getOrDefault(storeName, 0) + 1);
            }
        }

        reader.close();
        return purchaseCounts;
    }

    public static String sortPurchaseCounts(Map<String, Integer> purchaseCounts, boolean highestToLowest) {
        return purchaseCounts.entrySet()
                .stream()
                .sorted((entry1, entry2) -> highestToLowest ?
                        Integer.compare(entry2.getValue(), entry1.getValue()) :
                        Integer.compare(entry1.getValue(), entry2.getValue()))
                .map(entry -> entry.getKey() + ": " + entry.getValue() + " purchases")
                .collect(Collectors.joining("\n"));
    }

    private static Map<String, Integer> countStoreOccurrences() throws IOException {
        Map<String, Integer> storeCounts = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("statTests.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String storeName = line.split(",")[0].trim();
                storeCounts.put(storeName, storeCounts.getOrDefault(storeName, 0) + 1);
            }
        }

        return storeCounts;
    }

    private static String sortStoreCounts(Map<String, Integer> storeCounts, boolean highestToLowest) {
        return storeCounts.entrySet().stream()
                .sorted((entry1, entry2) -> highestToLowest ?
                        Integer.compare(entry2.getValue(), entry1.getValue()) :
                        Integer.compare(entry1.getValue(), entry2.getValue()))
                .map(entry -> entry.getKey() + ": " + entry.getValue() + " purchases")
                .collect(Collectors.joining("\n"));
    }

    public static List<String> analyzeTransactionHistory(String filePath, ArrayList<String> inputStoreNames) {
        Map<String, Set<String>> storeToUserMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String storeName = parts[0].trim();
                    String userName = parts[1].trim();

                    if (inputStoreNames.contains(storeName)) {
                        storeToUserMap.computeIfAbsent(storeName, k -> new HashSet<>()).add(userName);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> result = new ArrayList<>();
        for (String storeName : inputStoreNames) {
            Set<String> users = storeToUserMap.getOrDefault(storeName, new HashSet<>());
            result.add(String.join(", ", users));
        }

        return result;
    }
}