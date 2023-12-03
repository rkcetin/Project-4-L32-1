import java.io.*;
import java.net.*;
import java.util.*;

public class ClientThread extends Thread {
    final ObjectInputStream inputStream;
    final ObjectOutputStream outputStream;
    final Socket socket;
    final ArrayList<User> users;
    final ArrayList<Store> stores;
    final ArrayList<Product> products;

    final static Object userSync = new Object();
    final static Object storeSync = new Object();
    final static Object productsSync = new Object();


    boolean isSeller;
    User currentUser;


    public ClientThread(ArrayList<User> users , ArrayList<Store> stores , ArrayList<Product> products, Socket socket) throws Exception
    {

        this.inputStream = new ObjectInputStream(socket.getInputStream());
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.socket = socket;
        this.users = users;
        this.stores = stores;
        this.products = products;


    }
    public void run() {


        try {
            boolean registration = true;
            //first menu
            while(true) {
                int menuPoint = inputStream.readInt();



                // first menu
                if (menuPoint == 0) {
                    registration = inputStream.readBoolean();
                }

                if (menuPoint == 1) {
                    if (registration) {
                        String[] registrationInfo = (String[]) inputStream.readObject();
                        try {
                            System.out.println("here");
                            currentUser = User.signup(
                                    registrationInfo[0],
                                    registrationInfo[1],
                                    Integer.parseInt(registrationInfo[2]),
                                    users
                            );
                            System.out.println("reach4");
                            outputStream.writeBoolean(true);
                            outputStream.flush();
                            break;
                        } catch (Exception e) {
                            System.out.println("reach5");
                            outputStream.writeBoolean(false);
                            outputStream.flush();
                        }
                    } else {
                        String[] logInfo = (String[]) inputStream.readObject();
                        try {
                            currentUser = User.login(
                                    logInfo[0],
                                    logInfo[1],
                                    users
                            );
                            outputStream.writeBoolean(true);
                            outputStream.flush();
                            if (currentUser instanceof Seller) {
                                System.out.println("a");
                                outputStream.writeInt(1);
                            } else {
                                System.out.println("b");
                                outputStream.writeInt(2);
                            }
                            outputStream.flush();
                            break;
                        } catch (Exception e) {
                            outputStream.writeBoolean(false);
                            outputStream.flush();
                        }

                    }
                    Storage.storeData(users, stores, products);

                }

            }

            if (currentUser instanceof Customer ) {
                /*
                Customer Checklist
                  need to check for synchronicity all of this

                view stores | done

                view products  | done
                edit account | done
                delete account | done
                view cart | done
                add item to cart | not done need to handle synchronicity
                remover from cart | done
                purchase cart | not done
                single purchase | done check for synchronicity
                dashboard by bought | done
                dashboard by sold | done
                get trasnaction histiroy | serverside done
                view stores purchase dfrom | done\
                total purchases | done
                 */



                Customer currentCustomer = (Customer) currentUser;
                loop : while(true) {
                    int menuChoice = inputStream.readInt();
                    switch (menuChoice) {
                        case 100: { // view products
                            outputStream.writeObject(
                                products // sorting should happen client side + listing generation shoudl also happene clientside
                            );
                            outputStream.flush();
                            break;
                        }
                        case 101 : { //view stores
                            outputStream.writeObject(
                                stores
                            );
                            outputStream.flush();
                            break;
                        }

                        case 200: { //edit account
                            try{
                                String newEmail = (String) inputStream.readObject();
                                String newPassword = (String) inputStream.readObject();
                                synchronized (userSync) {
                                    User.isEmailRegistered(newEmail, users);
                                    currentCustomer.setName(newEmail, users);
                                }
                                currentCustomer.setPassword(newPassword);

                                outputStream.writeBoolean(true);
                                outputStream.flush();
                            } catch (Exception e) {
                                outputStream.writeBoolean(false);
                                outputStream.flush();
                            }
                            break;
                        }
                        case 300: {  // delete user
                            currentCustomer.deleteUser(users);
                            Storage.storeData(users, stores, products);
                            break loop;
                        }
                        case 400: { // view cart
                            outputStream.writeObject(
                                    currentCustomer.getCart()
                            );
                            outputStream.flush();
                            break;
                        }
                        case 401 : { // add item to cart
                            String[] cartActionInfo = (String[]) inputStream.readObject();
                            try {
                                int quantity = Integer.parseInt(cartActionInfo[1]); // 1 should be quantity index
                                synchronized (productsSync) {
                                    Product targetProduct = Product.checkProduct(cartActionInfo[0], products);  // what if they delete while purchasing
                                    currentCustomer.addToCart(
                                            targetProduct.getStore(),
                                            targetProduct.getProductName(),
                                            quantity,
                                            products
                                    );
                                }
                                outputStream.writeBoolean(true);
                            } catch (Exception e) {
                                outputStream.writeBoolean(false);
                            }
                            outputStream.flush();
                            break;
                        }
                        case 402 : { // purchase cart

                        }
                        case 403 : { // purchase item
                            String[] purchaseInfo = (String[]) inputStream.readObject();
                            try {
                                int quantity = Integer.parseInt(purchaseInfo[1]);
                                synchronized (productsSync) {
                                    Product targetProduct = Product.checkProduct(purchaseInfo[0], products);
                                    currentCustomer.singlePurchase(
                                            targetProduct.getStore(),
                                            targetProduct.getProductName(),
                                            quantity,
                                            products
                                    );
                                }
                                outputStream.writeBoolean(true);
                            } catch (Exception e ) {
                                outputStream.writeBoolean(false);
                            }
                            outputStream.flush();
                            break;
                        }
                        case 404  : { //remove from cart
                            String itemName = (String) inputStream.readObject();
                            currentCustomer.removeFromCart(itemName);
                            outputStream.writeBoolean(true);
                            outputStream.flush();
                            break;
                        }
                        case 501 : { //view dashboard by bought
                            outputStream.writeObject(
                                    currentCustomer.dashboardbyBought()
                            );
                            outputStream.flush();
                            break;
                        }
                        case 502 : { //view dashboard by sold
                            outputStream.writeObject(
                                    currentCustomer.dashboardBySold(stores)
                            );
                            outputStream.flush();
                            break;
                        }
                        case 600: { // extract transaction history local machine has to handle other part
                            outputStream.writeObject(currentCustomer.getTransactionHistoryList());
                            outputStream.flush();
                            break;
                        }
                        case 701: { //view statistics stores purchased from
                            outputStream.writeObject(
                                    currentCustomer.getPurchaseCounts()
                            );
                            outputStream.flush();
                            break;
                        }
                        case 702: { // total purhcases from stores
                            outputStream.writeObject(
                                    currentCustomer.countStoreOccurrences()
                            );
                            outputStream.flush();
                            break;
                        }
                        case 800 : {
                            continue loop;
                        }
                        case 900 : { // exit marketplace
                            Storage.storeData(users , stores, products);
                            break loop;
                        }

                    }
                }
                Storage.storeData(users, stores, products);
            }

            /*
            view customer cart | done
            view owned stores | done
            create store | done
            add prduct to store | done
            edit product  | done
            delete product  | not done
            bulk add products | done
            get products for export | done
            sales by store | not done



            edit account | done
            unsorted statistics | done
            delete user | done
            exit marketplace | done


             */
            if (currentUser instanceof Seller) {
                Seller currentSeller = (Seller) currentUser;
                loop : while (true) {
                    int processSelection = inputStream.readInt();
                    switch (processSelection) {
                        case 100: { //view stores
                            outputStream.writeObject(currentSeller.getStoresString(currentSeller.getStores()));
                            outputStream.flush();
                            break;
                        }
                        case 200 : { // create store
                            String storeName = (String) inputStream.readObject();
                            try {

                                //
                                    currentSeller.createStore(storeName, stores);
                                //}// synchronize maybe

                                outputStream.writeBoolean(true);
                                outputStream.flush();
                            } catch (Exception e) {
                                outputStream.writeBoolean(false);
                                outputStream.flush();
                            }
                            break;
                        }
                        case 201 : { // add product to store
                            String[] addProductInfo = (String[]) inputStream.readObject();
                            // [0] is store name
                            // [1] product name
                            // [2] description
                            // [3] stock
                            // [4] price

                            try {
                                //synchronized (storeSync) {
                                    //synchronized (productsSync) {
                                        Store targetStore = Store.checkStore(
                                                addProductInfo[0],
                                                stores
                                        );
                                        targetStore.addProduct(
                                                addProductInfo[1],
                                                addProductInfo[2],
                                                Integer.parseInt(addProductInfo[3]),
                                                Double.parseDouble(addProductInfo[4]),
                                                products
                                        );
                                    //}
                                //}
                                outputStream.writeBoolean(true);
                                outputStream.flush();
                            } catch (Exception e) {
                                outputStream.writeBoolean(false);
                                outputStream.flush();
                            }
                            Storage.storeData(users, stores, products);
                            continue;


                        }
                        case 202 : { // bulk add products
                            ArrayList<Object[]> csvInput =  (ArrayList<Object[]>) inputStream.readObject();
                            try {
                                synchronized ( productsSync){
                                    synchronized ( storeSync) {
                                        currentSeller.serverSideImport(csvInput, products);
                                    }
                                }
                                outputStream.writeBoolean(true);
                            } catch (Exception e) {
                                outputStream.writeBoolean(false);
                            }
                            outputStream.flush();
                            break;

                        }
                        case 203 : { //edit product
                            /*
                            0 name
                            1 description
                            2 stock
                            3 price
                            4 oldname

                            check for empty/ null < 0 should bed one client side
                            send null and -1 if they dont want to change something
                            ex
                            ["newname" , null , -1  , 10 , "oldname" ]
                             */
                            Object[] changeInput = (Object[]) inputStream.readObject();
                            synchronized (productsSync) {
                                if (User.isEmailRegistered((String) changeInput[0], users) != null) {
                                    outputStream.writeBoolean(false);
                                } else {
                                    Product workingProduct = Product.checkProduct(
                                            (String) changeInput[4],
                                            products
                                    );
                                    if (changeInput[0] != null) {
                                        workingProduct.setProductName(
                                                (String) changeInput[0],
                                                products
                                        );
                                    }
                                    if (changeInput[1] != null) {
                                        workingProduct.setProductDescription(
                                                (String) changeInput[1]
                                        );
                                    }
                                    if (changeInput[2] != null) {
                                        workingProduct.setStock(
                                                (Integer) changeInput[2]
                                        );
                                    }
                                    if (changeInput[3] != null) {
                                        workingProduct.setPrice(
                                                (Double) changeInput[3]
                                        );
                                    }
                                    outputStream.writeBoolean(true);
                                }
                            }
                            outputStream.flush();
                            break;


                        }
                        case 204 : {  // delete product
                            String targetProduct = (String) inputStream.readObject();

                            try {
                                synchronized (productsSync) {
                                    Product productObj = Product.checkProduct(targetProduct, products);
                                    if (productObj.getStore().getSeller() == currentSeller) {
                                        productObj.getStore().removeProduct(targetProduct, products);
                                        outputStream.writeBoolean(true);
                                    } else {
                                        throw new Exception();
                                    }
                                }
                            } catch (Exception e) {
                                outputStream.writeBoolean(false);
                            }
                            outputStream.flush();
                            break;
                        }
                        case 300 : { //edit account
                            try {
                                String newEmail = (String) inputStream.readObject();
                                String newPassword = (String) inputStream.readObject();
                                synchronized (userSync) {
                                    User.isEmailRegistered(newEmail, users);
                                    currentSeller.setName(newEmail, users);
                                    currentSeller.setPassword(newPassword);
                                }
                                outputStream.writeBoolean(true);

                            } catch (Exception e) {
                                outputStream.writeBoolean(false);
                            }
                            outputStream.flush();
                            break;
                        }
                        case 301 : { // unsorted statistics
                            outputStream.writeObject(
                                    currentSeller.displayUnsortedStatistics() // rest needs to happen clientside
                            );
                            break;
                        }

                        case 400 : { // delete user
                            currentSeller.deleteUser(users);
                            Storage.storeData(users , stores , products);
                            break loop;
                        }
                        case 500 : {  //exit marketplace
                            break loop;
                        }
                        case 600 : { // view customer cart
                            String targetUser = (String) inputStream.readObject();
                            synchronized (userSync) {
                                Customer targetCustomer = (Customer) User.isEmailRegistered(targetUser, users);
                                outputStream.writeObject(targetCustomer.getCart());
                            }
                            outputStream.flush();
                            break;
                        }
                        case 601 : { // get customers for cart thing
                            outputStream.writeObject(
                                    Customer.getCustomers(users)
                            );
                            outputStream.flush();
                            break;
                        }
                        case 700 : { //get all stores for the export of all products
                            outputStream.writeObject(currentSeller.getStores());
                            outputStream.flush();
                            break;
                        }
                        case 701 : { //get store for the product export
                            String targetStore = (String) inputStream.readObject();
                            Store sellerStore = currentSeller.getStore(targetStore);
                            outputStream.writeObject(sellerStore);
                            outputStream.flush();
                            break;
                        }
                        case 900 : { // exit marketplace
                            Storage.storeData(users , stores, products);
                            break loop;
                        }


                    }
                    Storage.storeData(users, stores, products);
                }
            }


            socket.close();
            inputStream.close();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
