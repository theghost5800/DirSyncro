package DirManage;

import FileActions.CopyFiles;
import FileActions.DeleteFiles;
import ProgramManage.Manager;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;


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

    public ArrayList<Path> genRelativePaths(ArrayList<File> paths, Path startPoint) {
        ArrayList<Path> relativePaths = new ArrayList<>();

        for (File path : paths) {
            relativePaths.add(startPoint.relativize(path.toPath()));
        }

        return  relativePaths;
    }

    public void comparePaths(ArrayList<Path> source, ArrayList<Path> target) {
        ArrayList<Path> cpFiles = new ArrayList<>();
        ArrayList<Path> rmFiles = new ArrayList<>();

        for (Path path : source) {
            if (!target.contains(path)) {
                  cpFiles.add(sourceStartPath.resolve(path));
            }
        }

        if (!cpFiles.isEmpty()) {
            Manager.executor.execute(new CopyFiles(cpFiles, targetStartPath, sourceStartPath));
        }

        for (Path path : target) {
            if (!source.contains(path)) {
                rmFiles.add(targetStartPath.resolve(path));
            }
        }

        if (!rmFiles.isEmpty()) {
            Manager.executor.execute(new DeleteFiles((rmFiles)));
        }

        sourcePaths.clear();
        targetPaths.clear();

    }

    @Override
    public void run() {
        ArrayList<Path> relativeSourcePaths = genRelativePaths(sourcePaths, sourceStartPath);
        ArrayList<Path> relativeTargetPaths = genRelativePaths(targetPaths, targetStartPath);

        comparePaths(relativeSourcePaths, relativeTargetPaths);

    }
}