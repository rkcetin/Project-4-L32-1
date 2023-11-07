import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1 - LogIn \n2 - SignUp");
        int logorsign = scanner.nextInt();
        scanner.nextLine();
        // Prompt the user for email and password
        System.out.print("Enter your email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        switch (logorsign) {
            case 1:
                if (isValidEmail(email)) {
                    // Check if the email is registered
                    if (isEmailRegistered(email)) {
                        // Retrieve the stored hashed password and salt for the user
                        String[] userData = getUserData(email);
        
                        if (userData != null) {
                            String storedHashedPassword = userData[0];
                            String salt = userData[1];
        
                            // Hash the provided password with the stored salt
                            String hashedPassword = hashPassword(password, salt);
        
                            // Compare the computed hash with the stored hash
                            if (storedHashedPassword.equals(hashedPassword)) {
                                System.out.println("Login successful. Welcome, " + email + "!");
                            } else {
                                System.out.println("Login failed. Incorrect password.");
                            }
                        } else {
                            System.out.println("User data is missing or corrupted.");
                        }
                    } else {
                        System.out.println("Email is not registered. Please sign up first.");
                    }
                } else {
                    System.out.println("Invalid email format.");
                }
                break;
            case 2:
                // Check if the email is valid
                if (isValidEmail(email)) {
                    // Check if the email is not already registered
                    if (!isEmailRegistered(email)) {
                        // Generate a random salt
                        String salt = generateSalt(16);

                        // Hash the password with the salt
                        String hashedPassword = hashPassword(password, salt);

                        // Store the email, hashed password, and salt in a file
                        if (saveUserToDatabase(email, hashedPassword, salt)) {
                            System.out.println("Signup successful. User information has been saved.");
                        } else {
                            System.out.println("Signup failed. Please try again later.");
                        }
                    } else {
                        System.out.println("Email is already registered.");
                    }
                } else {
                    System.out.println("Invalid email format.");
                }
        }
    }

    // Validate email format
    public static boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    // Check if the email is already registered in the file
    public static boolean isEmailRegistered(String email) {
        try {
            File database = new File("user_database.txt");
            Scanner fileScanner = new Scanner(database);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.contains(email)) {
                    fileScanner.close();
                    return true;
                }
            }

            fileScanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Generate a random salt for password hashing
    // Function to generate a simple random salt
    public static String generateSalt(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder salt = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            salt.append(characters.charAt(random.nextInt(characters.length())));
        }

        return salt.toString();
    }

    // Hash the password using a secure hashing algorithm
    public static String hashPassword(String password, String salt) {
        return password + salt;
    }

    // Save user information to the file
    public static boolean saveUserToDatabase(String email, String hashedPassword, String salt) {
        try {
            FileWriter writer = new FileWriter("user_database.txt", true);
            writer.write(email + "," + hashedPassword + "," + salt + "\n");
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Function to retrieve user data (hashed password and salt) based on email
    public static String[] getUserData(String email) {
        try {
            File database = new File("user_database.txt");
            Scanner fileScanner = new Scanner(database);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] userData = line.split(",");

                if (userData.length >= 3 && userData[0].equals(email)) {
                    fileScanner.close();
                    return new String[]{userData[1], userData[2]}; // [hashed password, salt]
                }
            }

            fileScanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // User not found or an error occurred
    }
}
