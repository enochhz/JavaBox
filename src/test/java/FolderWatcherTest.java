import org.junit.Test;

import java.io.File;

import static junit.framework.TestCase.assertEquals;

public class FolderWatcherTest {

    @Test
    public void testFolderWatcher() throws Exception {
        FolderWatcher folderWatcher = new FolderWatcher(null, null, "LocalFolder");
        folderWatcher.start();
        long startTime = System.nanoTime();
        createTemporaryFile();
        while ((System.nanoTime() - startTime) / 1000 < 5000000) {
        }
        assertEquals(0, folderWatcher.getEventCounter());
        folderWatcher.stop();
    }

    public void createTemporaryFile() throws Exception{
        File temp = new File("LocalFolder/testForTestCase.txt");
        temp.createNewFile();
    }
}
