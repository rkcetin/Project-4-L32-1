import java.util.ArrayList;

public class Seller extends User {
    private ArrayList<Store> stores;


    public Seller(String name, String password , String salt) {
        super(name, password , salt);
        stores = new ArrayList<>();
    }


    public Store createStore(String storeName, ArrayList<Store> bigStores) {
        Store createdStore = new Store(storeName , this);
        stores.add(createdStore);
        bigStores.add(createdStore);
        return createdStore;
    }
    public Store getStore(String storename) {
        return Store.checkStore(storename, this.stores);

    }

    public void removeStore(String storeName) {
        Store.removeStore(storeName, this.stores);
    }
    public ArrayList<Store> getStores() {
        return stores;
    }
    public String[] getSellerStoreNames() {
        return Store.listStoreNames(this.getStores());
    }



}
