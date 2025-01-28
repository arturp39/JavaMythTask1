package org.example.resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.state.TruckState;
import org.example.reader.TruckFileReader;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ResourceManager {
    private static final Logger logger = LogManager.getLogger(ResourceManager.class);
    private static ResourceManager instance;
    private final Queue<Truck> availableTrucks = new LinkedList<>();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition truckAvailableCondition = lock.newCondition();

    private ResourceManager() {
        initializeTrucks();
    }

    public static synchronized ResourceManager getInstance() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }

    private void initializeTrucks() {
        List<String> truckNames = TruckFileReader.readTruckNames();
        for (String truckName : truckNames) {
            availableTrucks.add(new Truck(truckName));
        }
        logger.info("Successfully initialized {} trucks.", truckNames.size());
    }

    public Truck acquireTruck() throws InterruptedException {
        lock.lock();
        try {
            while (availableTrucks.isEmpty()) {
                logger.info(Thread.currentThread().getName() + " is waiting for a truck...");
                truckAvailableCondition.await();
            }
            Truck truck = availableTrucks.poll();
            truck.setState(TruckState.IN_USE);
            logger.info(Thread.currentThread().getName() + " acquired " + truck.getName());
            return truck;
        } finally {
            lock.unlock();
        }
    }

    public void releaseTruck(Truck truck) {
        lock.lock();
        try {
            truck.setState(TruckState.AVAILABLE);
            availableTrucks.offer(truck);
            logger.info(Thread.currentThread().getName() + " released " + truck.getName());
            truckAvailableCondition.signal();
        } finally {
            lock.unlock();
        }
    }
}
