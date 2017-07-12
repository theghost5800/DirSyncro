package FileActions;

import ProgramManage.Menu;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class CopyFiles implements Runnable {
    private ArrayList<Path> filesToCp;
    private Path destRootPath;
    private Path sourceRootPath;

    public CopyFiles(ArrayList<Path> filesToCp, Path destPath, Path sourcePath) {
        this.filesToCp = filesToCp;
        this.destRootPath = destPath;
        this.sourceRootPath = sourcePath;
    }

    private static String getFileChecksum(MessageDigest digest, File file) throws IOException
    {
        //Get file input stream for reading the file content
        FileInputStream fis = new FileInputStream(file);

        //Create byte array to read data in chunks
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

        //Read file data and update in message digest
        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        }

        //close the stream; We don't need it now.
        fis.close();

        //Get the hash's bytes
        byte[] bytes = digest.digest();

        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        //return complete hash
        return sb.toString();
    }
    @Override
    public void run() {
        //Use SHA-256 algorithm
        MessageDigest shaDigest = null;
        try {
            shaDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        for (Path file : filesToCp) {
            try {
                // Construct destination path from root source path
                Path destPath = destRootPath.resolve(sourceRootPath.relativize(file));
                Files.copy(file, destPath, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
                // Skip checksum if given path is directory
                if (!Files.isDirectory(destPath)) {
                    System.out.println("File " + file.getFileName() + " was copied to " + destRootPath);
                    String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss")
                            .format(Calendar.getInstance().getTime());



                    //SHA-256 checksum
                    String shaSourceChecksum = getFileChecksum(shaDigest, file.toFile());

                    String shaTargetChecksum = getFileChecksum(shaDigest, destPath.toFile());

                    if (shaSourceChecksum.equals(shaTargetChecksum)) {
                        System.out.println("File " + file.getFileName() + " was correctly copied!");
                        Menu.textArea.append("File " + file.getFileName() +
                                " was copied correctly to " + destRootPath +" "+timeStamp+"\n");
                    } else {
                        System.out.println("File " + file.getFileName() + " was not correctly copied!");
                        Files.delete(destPath);
                        System.out.println("File " + destPath.getFileName() +
                                " it was deleted! It will try again on next scan!");

                        Menu.textArea.append("File " + file.getFileName() +
                                " was not copied correctly " + destRootPath +" "+timeStamp+"\n");
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }
}
