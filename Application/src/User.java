import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 106L;
    private final String name;
    private final String password;
    //standard constructor for the User class
    public User(String name, String password) {
        this.name = name;
        this.password = password;
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
        return String.format("%s, %s", name, password);
    }
}
