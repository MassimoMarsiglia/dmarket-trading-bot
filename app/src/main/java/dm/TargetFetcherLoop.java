package dm;

import dm.Targets.TargetFetcher;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class TargetFetcherLoop {

    
    public static void targetFetcherLoop(TargetFetcher targetFetcher) {
        // Create a ScheduledExecutorService
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        // Schedule a task to run every 5 seconds
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // Call targetFetcher.updateTargetList() here
                try {
                    targetFetcher.updateTargetList("TargetStatusActive", "s");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, 0, 5, TimeUnit.SECONDS); // Initial delay of 0 seconds, repeat every 5 seconds
    }
}
