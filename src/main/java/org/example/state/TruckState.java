package org.example.state;

public enum TruckState {
    AVAILABLE {
        @Override
        public void handle(String truckName) {
            System.out.println(truckName + " is now available.");
        }
    },
    IN_USE {
        @Override
        public void handle(String truckName) {
            System.out.println(truckName + " is in use.");
        }
    };

    public abstract void handle(String truckName);
}
