import com.amazonaws.services.s3.AmazonS3;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;

public class FolderWatchTest {

    static AmazonS3 s3 = CommonSources.s3;
    static String bucketName = CommonSources.BUCKET_NAME;

    @Test
    public void verifyWatchFunction() {
        try {
            FolderWatch folderWatch = new FolderWatch(s3, bucketName, MainApp.sourceFolderName, MainApp.destinationFolderName);
            long timeStarted = System.nanoTime();
            while ((System.nanoTime() - timeStarted) / 1000 <= 5000000) {
                File mockedFile = Mockito.mock(File.class);
                assertTrue(folderWatch.eventProcess());
                assertTrue(folderWatch.eventProcess());
                assertTrue(folderWatch.eventProcess());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
