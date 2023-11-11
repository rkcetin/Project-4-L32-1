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
 * @version November 6, 2023
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
    public String getName() {
        return name;
    }
    //returns the password of the user
    public String getPassword() {
        return password;
    }
    //returns the information of the user in a formatted string
    public String toString() {
        return String.format("%s,%s,%s", name, password, salt);
    }

    //user auth methods

    public static boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }
    // Generate a random salt for password hashing
    // Function to generate a simple random salt
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
    private static String hashPassword(String password, String salt) {
        return password + salt;
    }
    public static User isEmailRegistered(String email , ArrayList<User> users) {
        if(email == null || users == null) {
            throw new NullPointerException();
        }
        ArrayList<User> filteredUsers = new ArrayList<User>(users
                .stream()
                .filter(user -> user.getName().equals(email) )
                .collect(Collectors.toList()));
        if(filteredUsers.isEmpty()) {
            return null;
        }
        return filteredUsers.get(0);
    }
    // Save user information to the file
    public static User saveUserToDatabase(String email, String hashedPassword, String salt, int role, ArrayList<User> users) {
        User newUser = null;
        switch(role) {
            case 1:
                newUser = new Seller(email, hashedPassword , salt);
                break;
            case 2:
                break;

        }

        users.add(newUser);
        return newUser;
    }
    public static User login(String email, String password, ArrayList<User> users) throws Exception{
        User currentUser = null;
        if (User.isValidEmail(email)) {
            // Check if the email is registered
            currentUser = User.isEmailRegistered(email , users);
            if (currentUser != null) {
                // Retrieve the stored hashed password and salt for the user
                String[] userData = currentUser.toString().split(",");

                if (userData != null) {
                    String storedHashedPassword = userData[1];
                    String salt = userData[2];

                    // Hash the provided password with the stored salt
                    String hashedPassword = hashPassword(password, salt);

                    // Compare the computed hash with the stored hash
                    if (storedHashedPassword.equals(hashedPassword)) {
                        return currentUser;
                    } else {
                        throw new Exception("login failed");
                    }
                } else {
                    throw new Exception("Missing data?");
                }
            } else {
                throw new Exception("Email not registered");
            }
        } else {
            throw new Exception("Incorrect Email Format");
        }
    }
    public static User signup(String email, String password , int role, ArrayList<User> users) throws Exception {
        // Check if the email is valid
        if (User.isValidEmail(email)) {
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
                throw new Exception("Email Already Registerd");
            }
        } else {
            throw new Exception("Invalid email format");
        }
    }

}
