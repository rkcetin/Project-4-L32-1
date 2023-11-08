import java.util.ArrayList;

public class Seller extends User {

    private ArrayList<Store> stores;

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
    //returns a store with the given storename
    public Store getStore(String storename) {
        return Store.checkStore(storename, this.stores);

    }
    //removes a store of the given name
    public void removeStore(String storeName) {
        Store.removeStore(storeName, this.stores);
    }
    //returns an arraylist of stores
    public ArrayList<Store> getStores() {
        return stores;
    }
    //returns all of the store names accosiated with a given store
    public String[] getSellerStoreNames() {
        return Store.listStoreNames(this.getStores());
    }



}
