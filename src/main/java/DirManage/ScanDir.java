package DirManage;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Callable;


public class ScanDir implements Callable<Boolean> {
    private File startPath;
    private ArrayList<File> pathsList;

    public ScanDir(File startPath,ArrayList<File> paths) {
        this.startPath = startPath;
        pathsList = paths;
    }
    public void displayDirectoryContents(File dir) {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                pathsList.add(file);
                displayDirectoryContents(file);
            } else {
                pathsList.add(file);
            }
        }
    }



    @Override
    public Boolean call() throws Exception {

        displayDirectoryContents(startPath);

        return true;
    }
}
