import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import static java.nio.file.StandardWatchEventKinds.*;

public class FolderWatch {

    public static void main(String[] args) {
        new FolderWatch().watch("TestFolder", "TestFolderCopy");
    }

    public void watch(String sourceFolder, String targetFolder) {
        try {
            System.out.println("Folder watcher starting...");

            WatchService watcher = FileSystems.getDefault().newWatchService();

            Path dir = Paths.get(sourceFolder);
            WatchKey key = dir.register(watcher,
                    ENTRY_CREATE,
                    ENTRY_DELETE,
                    ENTRY_MODIFY);

            int eventCounter = 0;
            // Event processing loop
            do {

                // wait for key to be signaled
                key = watcher.take();


                for (WatchEvent<?> event : key.pollEvents()) {
                    eventCounter++;

                    WatchEvent.Kind<?> kind = event.kind();

                    // The filename is the context of the event.
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path fileName = ev.context();

                    // Print out event information
                    switch(kind.toString()) {
                        case "ENTRY_CREATE":
                            System.out.printf("%s. Create: %s/%s\n", eventCounter, dir, fileName);
                            try {
                                File sourceFile = new File(sourceFolder + "/" + fileName.toString());
                                File destFile = new File(targetFolder + "/" + fileName);
                                destFile.createNewFile();
                                FileUtils.copyFile(sourceFile, destFile);
                            } catch (IOException e) {
                                System.out.println(e.toString());
                            }
                            // TODO: Handle directory
                            break;
                        case "ENTRY_DELETE":
                            System.out.printf("%s. Delete: %s/%s\n", eventCounter, dir, fileName);
                            break;
                        case "ENTRY_MODIFY":
                            System.out.printf("%s. Modify: %s/%s\n", eventCounter, dir, fileName);
                            break;
                        default:
                            System.out.printf("%s. Unknown: %s/%s\n", eventCounter, dir, fileName);
                            break;
                    }
                }
            } while (key.reset());

        } catch (IOException e) {
            System.err.println(e);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private void copyFile(File source, File dest) {
        try {
            InputStream is = new FileInputStream(source);
            OutputStream os = new FileOutputStream(dest, false);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            is.close();
            os.close();
        } catch (Exception e) {
            System.out.println(e.toString());;
        }
    }

}
