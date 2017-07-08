import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Created by kris on 06.07.17.
 */
public class CompareDirs implements Runnable {
    private ArrayList<File> sourcePaths;
    private ArrayList<File> targetPaths;
    private Path sourceStartPath;
    private Path targetStartPath;

    public CompareDirs(ArrayList<File> sourcePaths, ArrayList<File> targetPaths,
                       File sourceStartPath, File targetStartPath) {
        this.sourcePaths = sourcePaths;
        this.targetPaths = targetPaths;
        this.sourceStartPath = sourceStartPath.toPath();
        this.targetStartPath = targetStartPath.toPath();
    }

    @Override
    public void run() {
        System.out.println(sourceStartPath + " \n" + targetStartPath);
        for (File path : sourcePaths) {
            Path relativePath = sourceStartPath.relativize(path.toPath());
            System.out.println(relativePath);
        }

        for (File path : targetPaths) {
            Path relativePath = targetStartPath.relativize(path.toPath());
            System.out.println(relativePath);
        }
    }
}
