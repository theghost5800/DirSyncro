package FileActions;

import ProgramManage.Menu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


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

                String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(Calendar.getInstance().getTime());

                Menu.textArea.append("File/Dir " + path.getFileName() + " was deleted! "+timeStamp+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
