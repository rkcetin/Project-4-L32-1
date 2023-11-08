import java.util.ArrayList;

public class Seller extends User {
    private ArrayList<Store> stores;

    public Seller(String name, String password , String salt) {
        super(name, password , salt);
    }


    public void createStore(String storeName) {
        stores.add(new Store(storeName));
    }

    public void removeStore(String storeName) {
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getStoreName().equals(storeName)) {
                stores.remove(i);
                return;
            }
        }
    }

}
