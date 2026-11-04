package pt.pa.patterns;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        StudentRecord record = new StudentRecord("2018", "João Meireles");
        try {
            record.importFromFile(args.length == 0 ? "record.csv" : args[0]);
        } catch (IOException e) {
            System.err.println("Could not import the record: " + e.getMessage());
            return;
        }

        /* Print record */
        System.out.println(record);

        // TODO: After refactoring, select and change the strategy here.
        /* Compute average */
        double average = record.computeAverage();
        System.out.println(String.format("Course average: %.2f", average));
    }
}

