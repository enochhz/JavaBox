import com.amazonaws.services.s3.AmazonS3;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BucketOperationTest {

    AmazonS3 s3 = CommonSources.s3;
    String bucketName = CommonSources.BUCKET_NAME;
    static String sourceFolderName = CommonSources.SOURCE_FOLDER_NAME;
    static String destinationFolderName = CommonSources.DESTINATION_FOLDER_NAME;
    BucketOperation bucketOperation = Mockito.mock(BucketOperation.class);

    @Test
    public void testUploadFile() {
        assertNotNull(bucketOperation.uploadFile(s3, bucketName, new File(sourceFolderName + "/test.txt"), destinationFolderName));
    }

    @Test
    public void testDeleteFile() {
        assertNotNull(bucketOperation.deleteFile(s3, bucketName, destinationFolderName));
    }

    @Test
    public void updateFile() {
//        assertNotNull(bucketOperation.updateFile(s3, bucketName, new File(sourceFolderName), destinationFolderName));
    }
}
