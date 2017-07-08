import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Callable;


public class ScanDir implements Callable<Boolean> {
    private ArrayList<File> currPaths;
    private File startPath;
    private ArrayList<File> pathsList;

    public ScanDir(File startPath,ArrayList<File> paths) {
        currPaths = new ArrayList<>();
        this.startPath = startPath;
        pathsList = paths;
    }
    public void displayDirectoryContents(File dir) {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                currPaths.add(file);
                displayDirectoryContents(file);
            } else {
                currPaths.add(file);
            }
        }
    }


//    @Override
//    public void run() {
//        currPaths.clear();
//        displayDirectoryContents(startPath);
//        pathsList.addAll(currPaths);
////        for (File file : currPaths) {
////            System.out.println(file.getAbsolutePath());
////        }
//    }

    @Override
    public Boolean call() throws Exception {
        currPaths.clear();
        displayDirectoryContents(startPath);
        pathsList.addAll(currPaths);
//        if (startPath.getParent().equals("/home/kris/intelliJ_projects/DirSynchro"))
//            return false;
        return true;
    }
}
