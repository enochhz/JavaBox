import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class CommonSources {

    // Amazon S3 information
    public static String ACCESS_KEY_ID = "AKIAID6PVG6VZ5K2WY6A";
    public static String ACCESS_SEC_KEY = "Za49uL0EfiWQ2IX2BtFhEuJJSgjKA3WyuoCgrbO6";
    public static String BUCKET_NAME = "enochtestbucket";
    // Amazon s3 instance
    final static AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY_ID, ACCESS_SEC_KEY);
    final static AmazonS3 s3 = AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.US_WEST_1)
            .build();

    // local folders information
    public static String SOURCE_FOLDER_NAME = "TestFolder";
    public static String DESTINATION_FOLDER_NAME = "TestFolderCopy";


}
