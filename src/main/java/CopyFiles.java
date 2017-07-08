import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Created by kris on 08.07.17.
 */
public class CopyFiles implements Runnable {
    private ArrayList<Path> filesToCp;
    private Path destPath;

    public CopyFiles(ArrayList<Path> filesToCp, Path destPath) {
        this.filesToCp = filesToCp;
        this.destPath = destPath;
    }
    @Override
    public void run() {
        for (Path file : filesToCp) {
            try {
                Files.copy(file,destPath.resolve(file.getFileName()));
                System.out.println("File " + file.getFileName() + " was copied to " + destPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
