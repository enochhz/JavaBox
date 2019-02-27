import com.amazonaws.services.s3.AmazonS3;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.TestCase.assertTrue;

public class MainAppTest {

    AmazonS3 s3 = CommonSources.s3;
    String bucketName = CommonSources.BUCKET_NAME;
    String sourceFolderName = CommonSources.SOURCE_FOLDER_NAME;
    String destinationFolderName = CommonSources.DESTINATION_FOLDER_NAME;

    @Test
    public void testAllMethods() {
        MainApp app = new MainApp(s3, bucketName, sourceFolderName, destinationFolderName);
        assertTrue(app.start(true));
    }
}
