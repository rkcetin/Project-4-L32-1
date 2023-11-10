# Project-4-L32-1



## Class Descriptions

### Storage.java
  Storage.java is the means of persistant storage for our project and stores the ArrayLists of objects that hold our product, store, and user information. Within the class are functions to read and write to the individual .ser files which these ArrayLists are stored and they are used within the Main.java class at the beginning and end of the programs to store the information. /// NEED TO ADD TESTING SECTION FOR THIS CLASS
  
### User.java
  User.java is the means of creating new instances of the User class, holding its information, and defining the variables associated with it. This class implements the Serializable interface. The variables associated with this class include the users name, their password, and their salt. The User class is used when creating a new account, and is used as the basis for both the Customer and Seller classes.
  //space for testing information
  
### Store.java
  Store.java is the means of creating new instances of the Store class, holding its information, and defining the variables associated with it. It implements the serializable interface. The variables associated with this class include the stores name, its products, the Seller associated with it, its sales, and its amount of sold products. Store has to ability make and remove stores, make and remove products, edit those products, and increment its own sales.
  //space for testing information

### Product.java
  Product.java is the means of holding information about a product that is being sold in a store. It implements the Serializable interface. The variables associated with this class include the store associated with the product, the products name, the products description, the number of the product in stock, the amount of products sold, and the price of the product. The Product class is used with the Store class, when making new products to be sold in the given store.
  //space for testing information
  
### Customer.java
  Customer.java is the means of creating new instances of the Customer class, holding its information, and defining the variables associated with it. It is an extends the User class. The variables associated with this class include the Customers cart, transaction history, the products associated with those transactions, and the number of products they have bought, as well as all of those already associated with the User class. A new instance of the Customer class is made when a user signs up and chooses to be a customer, instead of a seller. Customers are able to search for stores to purchase products from, as well as search for specific sellers.
  //space for testing information

### Seller.java
  Seller.java is the means of creating new instances of the Seller class, holding its information, and defining the variables associated with it. It is an extends the User class. The variables associated with this class include all of the stores the seller has made, as well as all of those already associated with the user class. A new instance of the Seller class is made when a user signs up and chooses to be a seller, instead of a Customer. Sellers are able to make new stores, as remove them and see the stores already created.
  //space for testing information

### Storage.java
  Storage.java provides a means of consistent storage for arraylists. This class has the ability to store information about users, stores, and products. This class reads information from other classes, and writes it to a .ser file. Depending on the type of information, it is stored to either the Users.ser file, the Stores.ser file, or the Products.ser file.
  //space for testing information
