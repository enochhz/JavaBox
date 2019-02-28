import com.amazonaws.services.s3.AmazonS3;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class BucketHelperTest {

    App app;
    AmazonS3 s3;
    BucketHelper bucketOperation;
    String bucketName, localFolder, fileName;

    @Before
    public void tesOperationMethods() {
        app = new App("LocalFolder", "enochbucket0513");
        s3 = app.getAmazonS3();
        bucketName = app.getBucketName();
        localFolder = app.getFolderName();
        fileName = "test1.txt";
        bucketOperation = new BucketHelper();
        try {
            File temp = new File(localFolder + "/" + fileName);
            temp.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpload() {
        assertNotNull(bucketOperation.uploadFile(s3, bucketName, new File(localFolder + "/" + fileName), fileName));

    }

    @Test
    public void testDelete() {
        assertNotNull(bucketOperation.deleteFile(s3, bucketName, fileName));
    }

    @Test
    public void testUpdate() {
        assertNotNull(bucketOperation.updateFile(s3, bucketName, new File(localFolder + "/" + fileName), fileName));
    }

}
