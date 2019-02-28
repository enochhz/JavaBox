import org.junit.Test;

import java.io.IOException;

public class AppTest {

    @Test(expected = NullPointerException.class)
    public void testAppWithIOException() throws IOException{
        App app = new App(null, null, null);
        app.start();
    }
}
