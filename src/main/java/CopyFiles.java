import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Created by kris on 08.07.17.
 */
public class CopyFiles implements Runnable {
    private ArrayList<Path> filesToCp;
    private Path destRootPath;
    private Path sourceRootPath;

    public CopyFiles(ArrayList<Path> filesToCp, Path destPath, Path sourcePath) {
        this.filesToCp = filesToCp;
        this.destRootPath = destPath;
        this.sourceRootPath = sourcePath;
    }
    @Override
    public void run() {
        for (Path file : filesToCp) {
            try {
                // Construct destination path from root source path
                Files.copy(file, destRootPath.resolve(sourceRootPath.relativize(file)));
                System.out.println("File " + file.getFileName() + " was copied to " + destRootPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
