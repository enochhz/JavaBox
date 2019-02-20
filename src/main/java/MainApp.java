import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.assertEquals;

public class MainApp {


    @Test
    public void test() {
        String a = "test1";
        String b = "test2";
        assertEquals(a, b);
    }

}
