package org.example.worker;

import org.example.resource.ResourceManager;
import org.example.resource.Truck;

import java.util.concurrent.TimeUnit;

public class WorkerTask implements Runnable {
    private final String name;
    private final ResourceManager resourceManager;

    public WorkerTask(String name, ResourceManager resourceManager) {
        this.name = name;
        this.resourceManager = resourceManager;
    }

    @Override
    public void run() {
        try {
            Truck truck = resourceManager.acquireTruck();
            try {
                truck.loadCargo("Cargo for " + name);
                TimeUnit.SECONDS.sleep(3);
                truck.unloadCargo();
            } finally {
                resourceManager.releaseTruck(truck);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(name + " was interrupted.");
        }
    }
}
