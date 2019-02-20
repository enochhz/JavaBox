import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static junit.framework.TestCase.assertEquals;

public class MainTest {

    public static void main(String[] args) {
        System.out.println("Start Testing: ");
        Result result = JUnitCore.runClasses(MainApp.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
    }


    @Test
    public void test() {
        String a = "test1";
        String b = "test2";
        assertEquals(a, b);
    }
}
