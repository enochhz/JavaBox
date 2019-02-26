import com.amazonaws.services.s3.AmazonS3;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.assertEquals;

public class MainApp {

    static AmazonS3 s3 = CommonSources.s3;
    static String bucketName = CommonSources.BUCKET_NAME;
    static String sourceFolderName = CommonSources.SOURCE_FOLDER_NAME;
    static String destinationFolderName = CommonSources.DESTINATION_FOLDER_NAME;

    public static void main(String[] args) {
        reset();
        start();
    }

    public static void reset() {
        new AmazonBucket(s3, bucketName).deleteBucket();
    }

    @Test
    public static void start() {
        // Create the bucket first
        new AmazonBucket(s3, bucketName).createBucket();

        // Start to watch the file
        FolderWatch folderWatch = new FolderWatch(s3, bucketName, sourceFolderName, destinationFolderName);
        folderWatch.startToWatch();
    }
}
