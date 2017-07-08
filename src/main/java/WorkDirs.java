import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by kris on 05.07.17.
 */
public class WorkDirs {
    private Properties property;
    private File sourcePath, targetPath;

    public WorkDirs() {
        property = new Properties();
        sourcePath = null;
        targetPath = null;
    }

    public File getSourcePath() {
        return sourcePath;
    }

    public File getTargetPath() {
        return targetPath;
    }

    void findDirs() {
        FileReader reader = null;
        try {
            reader = new FileReader("dirs.prop");
            property.load(reader);  // Get dir paths from property file
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public boolean validateDirs() {
        sourcePath = new File(property.getProperty("source"));
        targetPath = new File(property.getProperty("target"));

            if (sourcePath.exists()) {
                if (targetPath.exists()) {
                    System.out.println("These directories exists!");
//                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//
//                    System.out.println(sdf.format(sourcePath.lastModified()));
                    return true;
                }else {
                    System.out.println("Dir2 not exist!");
                    return false;
                }
            }else {
                System.out.println("Dir1 not exist!");
                return false;
            }

    }


}
