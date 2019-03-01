import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class App {

    private String watchFolder;
    private AmazonS3 s3;
    private String bucketName;

    public final static void main(String[] args) {
        App app = new App("LocalFolder", generatedAmazonS3(), "enochbucket0513");
        app.start();
    }

    public static AmazonS3 generatedAmazonS3() {
        // Get accessKeyId and accessSecKey info frim .s3_info file
        File file = new File(".s3_info");
        String accessKeyId = "";
        String accessSecKey = "";
        try {
            Scanner scanner = new Scanner(file);
            accessKeyId = scanner.nextLine();
            accessSecKey = scanner.nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(accessKeyId);
        System.out.println(accessSecKey);
        AWSCredentials credentials = new BasicAWSCredentials(accessKeyId, accessSecKey);
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_WEST_1).build();
        return s3;
    }

    public App(String folder, AmazonS3 s3, String bucketName) {
        this.watchFolder = folder;
        this.s3 = s3;
        this.bucketName = bucketName;
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

    public void start() throws NullPointerException {
        try {
            FolderWatcher folderWatcher = new FolderWatcher(s3, bucketName, watchFolder);
            folderWatcher.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
