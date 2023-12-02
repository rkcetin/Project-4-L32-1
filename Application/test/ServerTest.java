import org.junit.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerTest {
    @Test
    public void LoginTest() throws Exception {
        Socket socket = new Socket("localhost" , 9000);
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        output.writeBoolean(true);
        output.flush();
        String[] inputResponse = {"email@email.com" , "pw" , "1"};
        output.writeObject(inputResponse);
        output.flush();
        System.out.println("reach3");
        Assert.assertTrue(input.readBoolean());

    }
}