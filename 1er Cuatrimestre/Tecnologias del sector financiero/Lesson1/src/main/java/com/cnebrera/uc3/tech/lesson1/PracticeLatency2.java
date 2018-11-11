package com.cnebrera.uc3.tech.lesson1;

import com.cnebrera.uc3.tech.lesson1.simulator.BaseSyncOpSimulator;
import com.cnebrera.uc3.tech.lesson1.simulator.SyncOpSimulLongOperation;
import org.HdrHistogram.Histogram;

import java.util.concurrent.TimeUnit;

/**
 * Second practice, measure latency with and without warmup
 */
public class PracticeLatency2
{
    private static long LOW  = TimeUnit.NANOSECONDS.toNanos(1000);
    private static long HIGH = TimeUnit.MICROSECONDS.toNanos(1500000);
    private static int SIGNIFICATIONS   = 2;
    private static double REPETITIONS = 30;
    /**
     * Main method to run the practice
     * @param args command line arument
     */
    public static void main(String [] args)
    {
        runCalculations();
    }

    /**
     * Run the practice calculations
     */
    private static void runCalculations()
    {
        long start;
        /* Create histogram */
        Histogram hg = new Histogram(LOW, HIGH, SIGNIFICATIONS);
        // Create a random park time simulator
        BaseSyncOpSimulator syncOpSimulator = new SyncOpSimulLongOperation();

        //run the calculations multiple times to warm the VM
        for(int j = 0; j < REPETITIONS; j ++) {

            // Execute the operation lot of times
            for (int i = 0; i < 200000; i++) {
                start = System.nanoTime();
                syncOpSimulator.executeOp();
                hg.recordValue(System.nanoTime() - start);
            }
            // Print results
            System.out.println("Min = " + hg.getMinValue());
            System.out.println("Max = " + hg.getMaxValue());
            System.out.println("Mean = " +hg.getMean());
            System.out.println("99 percentile = " + hg.getValueAtPercentile(99));
            System.out.println("99.9 percentile = " + hg.getValueAtPercentile(99.9));
            System.out.println("Repetitions = " + (j+1) + "\n");
            System.out.println("--------------------------------------------------------------");

            hg.reset();
        }

        // TODO Show min, max, mean and percentiles 99 and 99,9 with and without warm up
    }
}
