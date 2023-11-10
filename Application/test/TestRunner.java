import org.junit.runner.*;
import org.junit.runner.Result;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(StorageTest.class);
        System.out.println(result.wasSuccessful());
    }
}
