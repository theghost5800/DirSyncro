package ProgramManage;

import DirManage.CompareDirs;
import DirManage.ScanDir;
import DirManage.WorkDirs;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

public class Manager implements Runnable {
    private ArrayList<File> sourcePaths;
    private ArrayList<File> targetPaths;
    private static ScheduledExecutorService scheduledExecutorService;
    private boolean shutdown;
    public static ExecutorService executor;

    public Manager() {
        sourcePaths  = new ArrayList<>();
        targetPaths = new ArrayList<>();
        scheduledExecutorService =
                Executors.newScheduledThreadPool(2);
        executor = Executors.newFixedThreadPool(3);
        shutdown = false;
    }

    public void setShutdown(boolean shutdown) {
        this.shutdown = shutdown;
    }

    @Override
    public void run() {
        WorkDirs dirs = new WorkDirs();
        dirs.findDirs();
        dirs.validateDirs();

        Queue<ScheduledFuture> listThreads = new LinkedList<>();

        while(!shutdown) {

            listThreads.add(scheduledExecutorService.schedule(new ScanDir(dirs.getSourcePath(),
                    sourcePaths), 20, TimeUnit.SECONDS));


            listThreads.add(scheduledExecutorService.schedule(new ScanDir(dirs.getTargetPath(),
                    targetPaths), 20,TimeUnit.SECONDS));

            boolean allDone = true; // Will change to false if state below is false

            try {

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

        }

        scheduledExecutorService.shutdown();
        executor.shutdown();
    }
}
