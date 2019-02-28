import com.amazonaws.services.s3.AmazonS3;
import org.mortbay.util.IO;

import java.io.*;
import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.*;

public class FolderWatcher extends Thread{

    private String sourceFolderName;
    private WatchKey key;
    private int eventCounter;

    private AmazonS3 s3;
    private String bucketName;
    private WatchService watcher;

    public FolderWatcher(AmazonS3 s3, String bucketName, String sourceFolderName)
        throws IOException {
        this.watcher = FileSystems.getDefault().newWatchService();
        this.key = Paths.get(sourceFolderName).register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        this.eventCounter = 0;
        this.s3 = s3;
        this.bucketName = bucketName;
        this.sourceFolderName = sourceFolderName;
    }

    public int getEventCounter() {
        return eventCounter;
    }

    public void run() {
        System.out.println("Starting to watch folder: " + sourceFolderName);
        while (true) {
            eventProcess();
            key.reset();
        }
    }

    public boolean eventProcess() {
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                Path fileName = ev.context();
                File sourceFile = new File(sourceFolderName + "/" + fileName.toString());
                switch(kind.toString()) {
                    case "ENTRY_CREATE":
                        System.out.printf("Create: %s/%s\n", sourceFolderName, fileName);
                        if (s3 != null && bucketName != null) {
                            BucketHelper.uploadFile(s3, bucketName, sourceFile, fileName.toString());
                        }
                        eventCounter++;
                        break;
                    case "ENTRY_DELETE":
                        System.out.printf("Delete: %s/%s\n", sourceFolderName, fileName);
                        if (s3 != null && bucketName != null) {
                            BucketHelper.deleteFile(s3, bucketName, fileName.toString());
                        }
                        eventCounter++;
                        break;
                    case "ENTRY_MODIFY":
                        System.out.printf("Modify: %s/%s\n", sourceFolderName, fileName);
                        if (s3 != null && bucketName != null) {
                            BucketHelper.updateFile(s3, bucketName, sourceFile, fileName.toString());
                        }
                        eventCounter++;
                        break;
                    default:
                        eventCounter++;
                        break;
                }
            }
            return true;
    }
}
