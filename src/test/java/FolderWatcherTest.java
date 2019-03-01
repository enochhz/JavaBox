import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FolderWatcherTest {

    Thread t1 = new Thread() {
        public void run() {
            try {
                FolderWatcher folderWatcher = new FolderWatcher(null, null, "LocalFolder");
                folderWatcher.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    Thread t2 = new Thread() {
        public void run() {
            try {
                Thread.sleep(2000);
                // Create a new file
                File temp = new File("LocalFolder/temp1.txt");
                temp.createNewFile();
                writeOffer("Test content");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Test
    public void testFolderWatcher() throws Exception {
        t1.start();
        t2.start();
        // Run threads for 5 seconds
        long startTime = System.nanoTime();
        while ((System.nanoTime() - startTime) / 1000 < 5000000) {
        }
    }

    public static void writeOffer(String offer) {
        Path filePath = Paths.get("LocalFolder/temp1.txt");
        ByteBuffer bb = ByteBuffer.wrap(offer.getBytes());

        try (FileChannel fc = (FileChannel.open(filePath,StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING))) {
            bb.rewind();
            fc.write(bb);
        } catch (IOException ex) {
            System.out.println("exception in writing to offers file: "+ex);
        }
    }
}
