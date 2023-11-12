import org.junit.*;
import java.io.*;
import java.util.ArrayList;
public class MainTest {
    private static final OutputStream outputStream = new ByteArrayOutputStream();
    private static final PrintStream printStream = new PrintStream(outputStream);
    public void input(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }
    @Before
    public void setup() {
        System.setOut(printStream);
    }
    @Test
    public void outTest() {
        System.out.print("hello");
        Assert.assertEquals("hello", outputStream.toString());
    }
    @Test
    public void invalidInputTestForLoginSequence() {

        TestRunner.setUp();
        String invalidInputSequence = String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n",
            "5",  // first invalid input
            "2", // goes to signup
            "3", // invalid email
            "password", //passwrod
            "4", //invalid role
            "3",//invalid exit
            "1", //exits signup
            "5",   //another invalid input
            "3" //final exit
        );

        input(invalidInputSequence);
        Main.main(new String[0]);
        String[] outputs = outputStream.toString().split("\n");

        Assert.assertEquals(outputs[4] , "Invalid input");
        Assert.assertEquals(outputs[14] , "input valid number for role");
        Assert.assertEquals(outputs[18] , "Input Valid option");
    }
    @After
    public void setDown() {
        System.setOut(System.out);

    }
}