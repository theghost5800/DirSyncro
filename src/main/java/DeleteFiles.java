import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Created by kris on 08.07.17.
 */
public class DeleteFiles implements Runnable {
    private ArrayList<Path> filesToDel;

    public DeleteFiles(ArrayList<Path> filesToDel) {
        this.filesToDel = filesToDel;
    }

    @Override
    public void run() {
        for (int i = filesToDel.size() - 1; i >= 0; i--) {
            Path path = filesToDel.get(i);
            try {
                Files.delete(path);
                System.out.println("File/Dir " + path.getFileName() + " was deleted!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
