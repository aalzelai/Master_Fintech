package com.cnebrera.uc3.tech.lesson1;

import com.cnebrera.uc3.tech.lesson1.simulator.BaseSyncOpSimulator;
import com.cnebrera.uc3.tech.lesson1.simulator.SyncOpSimulRndPark;
import org.HdrHistogram.Histogram;

import java.util.concurrent.TimeUnit;

/**
 * First practice, measure latency on a simple operation
 */
public class PracticeLatency1
{

    private static long LOW  = TimeUnit.NANOSECONDS.toNanos(120);
    private static long HIGH = TimeUnit.MICROSECONDS.toNanos(4200);
    private static int SIGNIFICATIONS   = 2;
    private static double SCALE_RATIO = 1000d;

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
        long start, total;
        /* Create histogram */
        Histogram hg = new Histogram(LOW, HIGH, SIGNIFICATIONS);

        // Create a random park time simulator
        BaseSyncOpSimulator syncOpSimulator = new SyncOpSimulRndPark(TimeUnit.NANOSECONDS.toNanos(100), TimeUnit.MICROSECONDS.toNanos(100));

        total = System.currentTimeMillis();
        // Execute the operation lot of times
        for(int i = 0; i < 100000; i++)
        {
            start = System.nanoTime();

            syncOpSimulator.executeOp();
            hg.recordValue(System.nanoTime() - start);
            hg.outputPercentileDistribution(System.out, SCALE_RATIO);
        }

        // Total time
        total = System.currentTimeMillis() - total;

        hg.outputPercentileDistribution(System.out, SCALE_RATIO);

        /* Print time with the rest of measures format */
        System.out.println("#[Min     =       " + (hg.getMinValue()/SCALE_RATIO) +
                ", Time           =        " + (total/SCALE_RATIO) + "]");
        System.out.println("99 percentile = " + hg.getValueAtPercentile(99));
        System.out.println("99.9 percentile = " + hg.getValueAtPercentile(99.9));
    }
}
