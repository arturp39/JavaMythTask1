package org.example.resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.state.TruckState;

import java.util.concurrent.TimeUnit;

public class Truck {
    private static final Logger logger = LogManager.getLogger(Truck.class);

    private final String name;
    private TruckState state;

    public Truck(String name) {
        this.name = name;
        this.state = TruckState.AVAILABLE;
    }

    public String getName() {
        return name;
    }

    public TruckState getState() {
        return state;
    }

    public void setState(TruckState state) {
        this.state = state;
        this.state.handle(name);
    }

    public void loadCargo(String cargo) {
        try {
            logger.info("{} is loading cargo: {}", name, cargo);
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            logger.error("Loading interrupted for {}", name, e);
            Thread.currentThread().interrupt();
        }
    }

    public void unloadCargo() {
        try {
            logger.info("{} is unloading cargo.", name);
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            logger.error("Unloading interrupted for {}", name, e);
            Thread.currentThread().interrupt();
        }
    }
}
