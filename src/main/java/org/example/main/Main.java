package org.example.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.resource.ResourceManager;
import org.example.worker.WorkerTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            ResourceManager resourceManager = ResourceManager.getInstance();

            ExecutorService executorService = Executors.newFixedThreadPool(10);

            for (int i = 1; i <= 10; i++) {
                executorService.submit(new WorkerTask("Worker" + i, resourceManager));
            }

            executorService.shutdown();
            if (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
                logger.warn("Executor did not terminate in the specified time.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while running the application", e);
        }
    }
}
