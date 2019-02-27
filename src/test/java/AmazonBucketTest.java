import com.amazonaws.services.s3.AmazonS3;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

public class AmazonBucketTest {

    AmazonS3 s3 = CommonSources.s3;
    String bucketName = CommonSources.TEST_BUCKET_NAME;
    AmazonBucket mocketAmazonBucket = new AmazonBucket(s3, bucketName);

//    @Test
//    public void testCreate() {
//        assertNotNull(mocketAmazonBucket.createBucket());
//    }

//    @Test
//    public void testDelete() {
//        assertTrue(mocketAmazonBucket.deleteBucket());
//    }
}
