package ProgramManage;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainThread {


    public static void  main (String[] args) {
        Scanner input = new Scanner(System.in);
        String console;
        ExecutorService manageThread = Executors.newSingleThreadExecutor();
        Manager manager = new Manager();

        manageThread.execute(manager);

        while (true) {
            console = input.nextLine();

            if (console.equals("stop")) {
                break;
            }
        }

        manager.setShutdown(true);
        manageThread.shutdown();

    }
}
