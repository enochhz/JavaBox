import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import java.io.File;

public class BucketHelper {

    public static void uploadFile(AmazonS3 s3, String bucketName, File sourceFile, String keyName)
            throws NullPointerException, AmazonServiceException{
        s3.putObject(bucketName, keyName, sourceFile);
        System.out.println("Upload File: " + keyName + " in bucket: " + bucketName + " - Done!\n");
    }

    public static void deleteFile(AmazonS3 s3, String bucketName, String keyName)
            throws NullPointerException, AmazonServiceException {
        s3.deleteObject(bucketName, keyName);
        System.out.println("Delete File: " + keyName + " in bucket: " + bucketName + " - Done!\n");
    }

    public static void updateFile(AmazonS3 s3, String bucketName, File sourceFile, String keyName)
            throws NullPointerException, AmazonServiceException {
        deleteFile(s3, bucketName, keyName);
        uploadFile(s3, bucketName, sourceFile, keyName);
        System.out.println("Update File: " + keyName + " in bucket: " + bucketName + " - Done!\n");
    }
}
