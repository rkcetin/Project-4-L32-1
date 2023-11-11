import org.junit.*;

import java.util.ArrayList;
public class UserTest {
    @Test
    public void testSignUpLogin() throws Exception {
        ArrayList<User> users = new ArrayList<>();
        User signUp = User.signup("email@email.com" , "password" , 1, users);
        User login = User.login("email@email.com" , "password" , users);
        User signUp1 = User.signup("email1@email.com" , "password" , 1, users);

        org.junit.Assert.assertEquals(signUp, login);
        org.junit.Assert.assertTrue( signUp instanceof Seller);




    }
    @Test
    public void testInvalid() throws Exception {
        ArrayList<User> users = new ArrayList<>();
        User signUp = User.signup("email@email.com" , "password" , 1, users);
        User login = User.login("email@email.com" , "password" , users);
        org.junit.Assert.assertThrows(Exception.class, () -> {
            User.signup("email@email.com" , "password" , 1 , users);
        });
        org.junit.Assert.assertThrows(Exception.class, () -> {
            User.login("w@email.com" , "password" , users);
        });
        org.junit.Assert.assertThrows(Exception.class, () -> {
            User.signup("non valid email" , "asdfasdf" ,1 ,  users);
        });

    }
}
