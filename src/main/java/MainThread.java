import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * Created by kris on 05.07.17.
 */
public class MainThread {
    private static ArrayList<File> sourcePaths = new ArrayList<>();
    private static ArrayList<File> targetPaths = new ArrayList<>();
    private static ScheduledExecutorService scheduledExecutorService =
            Executors.newScheduledThreadPool(2);
    private static ExecutorService executor =
            Executors.newSingleThreadExecutor();

    public static void  main (String[] args) {
        WorkDirs dirs = new WorkDirs();
        dirs.findDirs();
        dirs.validateDirs();

        ScheduledFuture scheduledFuture = null;
        Queue<ScheduledFuture> listThreads = new LinkedList<>();

        while(true) {

            listThreads.add(scheduledExecutorService.schedule(new ScanDir(dirs.getSourcePath(),
                    sourcePaths), 5, TimeUnit.SECONDS));


            listThreads.add(scheduledExecutorService.schedule(new ScanDir(dirs.getTargetPath(),
                    targetPaths), 5,TimeUnit.SECONDS));

            boolean allDone = true; // Will change to false if state below is false

            try {
//                for (ScheduledFuture thread : listThreads) {
//                    thread.get();
//                }

                while (listThreads.iterator().hasNext()) {
                    ScheduledFuture thread = listThreads.poll();
                    boolean state = (boolean) thread.get();
                    allDone &= state;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if (allDone) {
                executor.execute(new CompareDirs(sourcePaths, targetPaths, dirs.getSourcePath()
                    , dirs.getTargetPath()));
            }





//            for (File file : sourcePaths) {
//                System.out.println(file.getAbsolutePath());
//            }
//
//            for (File file : targetPaths) {
//                System.out.println(file.getAbsolutePath());
//            }
        }







    }
}
