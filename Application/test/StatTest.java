import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class StatTest {
    public static void main(String[] args) throws IOException {
        ArrayList<String> stores = new ArrayList<>();
        stores.add("store1");
        stores.add("store2");

        String statisticsResult = getStatistics(stores);
        String sortedResult = sortStatisticsBySales(statisticsResult, false);

        System.out.println(sortedResult);
        System.out.println();
        System.out.println(sortByOccurrence(stores, false));
    }

    public static String getStatistics(ArrayList<String> stores) throws IOException {
        BufferedReader bfr = new BufferedReader(new FileReader("statistics.txt"));
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
}
