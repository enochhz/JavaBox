import com.amazonaws.services.s3.AmazonS3;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import static java.nio.file.StandardWatchEventKinds.*;

public class FolderWatch {

    AmazonS3 s3;
    String bucketName;
    String sourceFolderName;
    String destinationFolderName;
    WatchService watcher;
    WatchKey key;

    public FolderWatch(AmazonS3 s3, String bucketName, String sourceFolderName, String destinationFolderName)
        throws Exception {
        this.s3 = s3;
        this.bucketName = bucketName;
        this.sourceFolderName = sourceFolderName;
        this.destinationFolderName = destinationFolderName;
        this.watcher = FileSystems.getDefault().newWatchService();
        this.key = Paths.get(sourceFolderName).register(watcher,
                ENTRY_CREATE,
                ENTRY_DELETE,
                ENTRY_MODIFY);
    }

    public void startToWatch(boolean testMode) {
        System.out.println("Starting to watch folder: " + sourceFolderName);
        if (!testMode) {
            while (true) {
                eventProcess();
                key.reset();
            }
        } else {
            long timeStarted = System.nanoTime();
            while ((System.nanoTime() - timeStarted) / 1000 <= 5000000) {
                eventProcess();
                eventProcess();
                eventProcess();
            }
        }
    }

    public boolean eventProcess() {
            for (WatchEvent<?> event : key.pollEvents()) {

                WatchEvent.Kind<?> kind = event.kind();

                // The filename is the context of the event.
                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                Path fileName = ev.context();
                File sourceFile = new File(sourceFolderName + "/" + fileName.toString());
                File destFile = new File(destinationFolderName + "/" + fileName);

                // Print out event information
                switch(kind.toString()) {
                    case "ENTRY_CREATE":
                        System.out.printf("Create: %s/%s\n", sourceFolderName, fileName);
//                            copyFile(sourceFile, destFile);
                        BucketOperation.uploadFile(s3, bucketName, sourceFile, fileName.toString());
                        break;
                    case "ENTRY_DELETE":
                        System.out.printf("Delete: %s/%s\n", sourceFolderName, fileName);
//                            deleteFile(destFile);
                        BucketOperation.deleteFile(s3, bucketName, fileName.toString());
                        break;
                    case "ENTRY_MODIFY":
                        System.out.printf("Modify: %s/%s\n", sourceFolderName, fileName);
//                            modify(sourceFile, destFile);
                        BucketOperation.updateFile(s3, bucketName, sourceFile, fileName.toString());
                        break;
                    default:
                        System.out.printf("Unknown: %s/%s\n", sourceFolderName, fileName);
                        break;
                }
            }
            return true;
    }

//    private void copyFile(File source, File destination) {
//        if (source == null) {
//            return;
//        }
//        if (!source.isDirectory()) {
//            try {
//                InputStream is = new FileInputStream(source);
//                OutputStream os = new FileOutputStream(destination, false);
//                byte[] buffer = new byte[1024];
//                int length;
//                while ((length = is.read(buffer)) > 0) {
//                    os.write(buffer, 0, length);
//                }
//                is.close();
//                os.close();
//            } catch (Exception e) {
//                System.out.println(e.toString());;
//            }
//        } else {
//            copyFolder(source, destination);
//        }
//    }
//
//    private void copyFolder(File source, File destination) {
//        if (source.isDirectory()) {
//            if (!destination.exists()) {
//                destination.mkdir();
//            }
//            String files[] = source.list();
//            for (String file : files) {
//                File srcFile = new File(source, file);
//                File destFile = new File(destination, file);
//                copyFile(srcFile, destFile);
//            }
//        }
//    }
//
//    private void deleteFile(File destination){
//        if (destination.isDirectory()) {
//            if (destination.exists()) {
//                for (File file: destination.listFiles()) {
//                    deleteFile(file);
//                }
//            }
//        }
//        destination.delete();
//        System.out.println("delete: " + destination);
//    }
//
//    private void modify(File source, File destination) {
//        deleteFile(destination);
//        copyFile(source, destination);
//    }

}
