import com.amazonaws.services.s3.AmazonS3;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.assertEquals;

public class MainApp {

    AmazonS3 s3;
    String bucketName;
    static String sourceFolderName;
    static String destinationFolderName;

    public static void main(String[] args) {
        AmazonS3 s3 = CommonSources.s3;
        String bucketName = CommonSources.BUCKET_NAME;
        String sourceFolderName = CommonSources.SOURCE_FOLDER_NAME;
        String destinationFolderName = CommonSources.DESTINATION_FOLDER_NAME;

        MainApp app = new MainApp(s3, bucketName, sourceFolderName, destinationFolderName);
        app.start(false);
    }

    public MainApp(AmazonS3 s3, String bucketName, String sourceFolderName, String destinationFolderName) {
        this.s3 = s3;
        this.bucketName = bucketName;
        this.sourceFolderName = sourceFolderName;
        this.destinationFolderName = destinationFolderName;
    }

    public boolean start(boolean testMode) {
        // reset the bucket
//        new AmazonBucket(s3, bucketName).deleteBucket();

        // Create the bucket first
//        new AmazonBucket(s3, bucketName).createBucket();

        // Start to watch the file
        try {
            FolderWatch folderWatch = new FolderWatch(s3, bucketName, sourceFolderName, destinationFolderName);
            folderWatch.startToWatch(testMode);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
