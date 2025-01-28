package org.example.reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class TruckFileReader {
    private static final Logger logger = LogManager.getLogger(TruckFileReader.class);
    private static final String FILE_PATH = "src/main/resources/trucks.txt";

    private TruckFileReader() {
    }

    public static List<String> readTruckNames() {
        List<String> truckNames = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                truckNames.add(line.trim());
            }
            logger.info("Successfully read truck names from file: {}", FILE_PATH);
        } catch (Exception e) {
            logger.error("Failed to read truck names from file: {}", FILE_PATH, e);
        }
        return truckNames;
    }
}
