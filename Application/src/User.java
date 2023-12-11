import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Project 4 -- User Class
 *
 * Parent class of Seller and Customer. Contains the basic information common to all users
 * including their name and password. Contains methods pertaining to logging in and signing up
 *
 * @author Steven Chang, Alexander Benson, Stephanie Sun, Chris Xu, Ramazan Cetin, L32
 *
 * @version November 12, 2023
 *
 */

public class User implements Serializable {
    private static final long serialVersionUID = 106L;

    private String name;
    private String password;
    private String salt;

    //standard constructor for the User class
    public User(String name, String password, String salt) {

        this.name = name;
        this.password = password;
        this.salt = salt;
    }

    //returns the name of the user
    /**
     * returns the name of the seller
     * @return returns the name of the user
     *
     */
    public String getName() {
        return name;
    }
    //returns the password of the user
    /**
     * returns the password of the user
     * @return returns the password of the user
     *
     */
    public String getPassword() {
        return password;
    }

    /**
     * changes the users name
     * @param paramName updates the username
     * @throws Exception when invalid email
     */

    public void setName(String paramName, ArrayList<User> users) throws Exception {
        if (!User.isValidEmail(paramName) || User.isEmailRegistered(paramName, users) != null) {
            throw new Exception("invalid email");
        }
        this.name = paramName;
    }
    /**
     * updates the password
     * @param password updates the user password

     */
    public void setPassword(String password) {
        this.password = password + this.salt;
    }


    //returns the information of the user in a formatted string
    /**
     * returns string representation of the user
     * @return String representation of the user

     */
    public String toString() {
        return String.format("%s,%s,%s", name, password, salt);
    }

    //user auth methods
    /**
     * returns if email is a valid email
     * @return boolean if email is a valid email

     */
    public static boolean isValidEmail(String email) {
        if (email.isEmpty()) {
            return false;
        }
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }
    // Generate a random salt for password hashing
    // Function to generate a simple random salt
    /**
     * @param length length to use to generate salt
     * @return expression to help hash the password

     */
    private static String generateSalt(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder salt = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            salt.append(characters.charAt(random.nextInt(characters.length())));
        }

        return salt.toString();
    }

    // Hash the password using a secure hashing algorithm
    /**
     * generates a hashed password
     * @param password the password to be hashed
     * @param salt the password to salt with
     * @return the hashed password

     */
    private static String hashPassword(String password, String salt) {
        return password + salt;
    }
    /**
     * checks if user is already registered within an arraylist of users
     * @param email email to search for
     * @param users an arraylist of users to search
     * @return expression to help hash the password
     * return user if its registered

     */
    public static User isEmailRegistered(String email , ArrayList<User> users) {
        if (email == null || users == null) {
            throw new NullPointerException();
        }
        ArrayList<User> filteredUsers = new ArrayList<User>(users
                .stream()
                .filter(user -> user.getName().equals(email) )
                .collect(Collectors.toList()));
        if (filteredUsers.isEmpty()) {
            return null;
        }
        return filteredUsers.get(0);
    }

    // Save user information to the file
    /**
     * saves a user to an arraylist of users and returns the user
     * @param email email
     * @param hashedPassword password
     * @param salt salt to help authentication
     * @param role role to assign to the new user
     * @param users arraylist to save the new user to
     * @return expression to help hash the password
     * return user after saving user to the database

     */
    public static User saveUserToDatabase(String email, String hashedPassword, String salt, int role, ArrayList<User>
            users) {
        User newUser = null;
        switch(role) {
            case 1:
                newUser = new Seller(email, hashedPassword , salt);
                break;
            case 2:
                newUser = new Customer(email , hashedPassword , salt, 0);
                break;

        }
        users.add(newUser);
        return newUser;
    }
    /**
     * logs the user in if information is correct returns object for their account
     * @param email email to identify user
     * @param password password to verify
     * @param users arraylist to search for email and password
     * @return User if correctly logged in
     * @throws Exception if invalid information
     * returns the object for a user if they are logged in

     */
    public synchronized static User login(String email, String password, ArrayList<User> users) throws Exception {
        User currentUser = null;

        // Check if the email is registered
        currentUser = User.isEmailRegistered(email , users);
        if (currentUser != null) {
            // Retrieve the stored hashed password and salt for the user
            String[] userData = currentUser.toString().split(",");


            String storedHashedPassword = userData[1];
            String salt = userData[2];

            // Hash the provided password with the stored salt
            String hashedPassword = hashPassword(password, salt);

            // Compare the computed hash with the stored hash
            if (storedHashedPassword.equals(hashedPassword)) {
                return currentUser;
            } else {
                throw new Exception("Login failed");
            }

        } else {
            throw new Exception("Email not registered");
        }

    }
    /**
     * signs the user up with the email and password and role. if the information is correct
     * returns object for the user
     * @param email email to identify user
     * @param password password to verify
     * @param users arraylist to serach for email and password
     * @param role role to assign to the user
     * @return User if correctly signed up
     * @throws Exception if invalid information
     *

     */
    public synchronized static User signup(String email, String password, int role, ArrayList<User> users) throws Exception {
        // Check if the email is valid
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format!");
        }

        // Check if the email is not already registered
        User checkUser = User.isEmailRegistered(email, users);
        if (null == checkUser) {
            // Generate a random salt
            String salt = generateSalt(16);   ///// 16??????

            // Hash the password with the salt
            String hashedPassword = hashPassword(password, salt);

            User endUser = User.saveUserToDatabase(email, hashedPassword, salt, role, users);

            return endUser;
            // Store the email, hashed password, and salt in a file
        } else {
            throw new IllegalArgumentException("Email already registered!");
        }

    }


    public void deleteUser(ArrayList<User> users) {
        users.remove(this);
        this.name = null;
        this.password = null;
        this.salt = null;
    }
}
