import java.util.ArrayList;

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

    public boolean equals(Object o) {
        if (o instanceof Seller seller) {
            return (seller.getName().equals(this.getName()));
        } else {
            return false;
        }
    }
}