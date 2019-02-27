import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;

import java.io.File;
import java.io.IOException;

public class BucketOperation {

    public static boolean uploadFile(AmazonS3 s3, String bucketName, File sourceFile, String keyName) {
        try {
            s3.putObject(bucketName, keyName, sourceFile);
        } catch (AmazonServiceException e) {
            System.out.println(e.getErrorMessage());
            return false;
        }
        System.out.println("Upload File: " + keyName + " in bucket: " + bucketName + " - Done!\n");
        return true;
    }

    public static boolean deleteFile(AmazonS3 s3, String bucketName, String keyName) {
        try {
            s3.deleteObject(bucketName, keyName);
        } catch (AmazonServiceException e) {
            System.out.println(e.getErrorMessage());
            return false;
        }
        System.out.println("Delete File: " + keyName + " in bucket: " + bucketName + " - Done!\n");
        return true;
    }

    public static boolean updateFile(AmazonS3 s3, String bucketName, File sourceFile, String keyName) {
        if (deleteFile(s3, bucketName, keyName) && uploadFile(s3, bucketName, sourceFile, keyName)) {
            System.out.println("Update File: " + keyName + " in bucket: " + bucketName + " - Done!\n");
            return true;
        }
        return false;
    }
}
