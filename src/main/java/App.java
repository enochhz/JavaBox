import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class App {

    private String watchFolder;
    private String accessKeyId = "AKIAID6PVG6VZ5K2WY6A";
    private String accessSecKey = "Za49uL0EfiWQ2IX2BtFhEuJJSgjKA3WyuoCgrbO6";
    private String bucketName;
    private AmazonS3 s3;

    public final static void main(String[] args) {
        App app = new App("LocalFolder", "enochbucket0513");
        app.start();
    }

    public String getFolderName() {
        return watchFolder;
    }

    public AmazonS3 getAmazonS3() {
        return s3;
    }

    public String getBucketName() {
        return bucketName;
    }

    public App(String folder, String bucketName) {
        this.watchFolder = folder;
        this.bucketName = bucketName;
        AWSCredentials credentials = new BasicAWSCredentials(accessKeyId, accessSecKey);
        s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_WEST_1).build();
    }

    public void start() {
        try {
            FolderWatcher folderWatcher = new FolderWatcher(s3, bucketName, watchFolder);
            folderWatcher.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
