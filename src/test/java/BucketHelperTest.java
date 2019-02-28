import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
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

    AWSCredentials credentials = new BasicAWSCredentials("fake", "fake");
    AmazonS3 bads3 = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.US_WEST_1).build();

    @Before
    public void tesOperationMethods() {
        app = new App("LocalFolder", App.generatedAmazonS3(),"enochbucket0513");
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
    public void testBucketMethods() {
        bucketOperation.uploadFile(s3, bucketName, new File(localFolder + "/" + fileName), fileName);
        bucketOperation.deleteFile(s3, bucketName, fileName);
        bucketOperation.uploadFile(s3, bucketName, new File(localFolder + "/" + fileName), fileName);
    }

    @Test(expected = NullPointerException.class)
    public void testBucketMethodsWithNull() {
        bucketOperation.uploadFile(null, bucketName, new File(localFolder + "/" + fileName), fileName);
        bucketOperation.deleteFile(null, bucketName, fileName);
        bucketOperation.uploadFile(null, bucketName, new File(localFolder + "/" + fileName), fileName);
    }

    @Test(expected = AmazonServiceException.class)
    public void testBucketMethodsWithServiceException() {
        bucketOperation.uploadFile(bads3, bucketName, new File(localFolder + "/" + fileName), fileName);
        bucketOperation.deleteFile(bads3, bucketName, fileName);
        bucketOperation.uploadFile(bads3, bucketName, new File(localFolder + "/" + fileName), fileName);
    }

}
