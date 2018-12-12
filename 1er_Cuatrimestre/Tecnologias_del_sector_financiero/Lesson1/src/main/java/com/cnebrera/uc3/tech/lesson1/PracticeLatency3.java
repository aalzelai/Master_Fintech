package com.cnebrera.uc3.tech.lesson1;

import com.cnebrera.uc3.tech.lesson1.simulator.BaseSyncOpSimulator;
import com.cnebrera.uc3.tech.lesson1.simulator.SyncOpSimulSleep;
import org.HdrHistogram.ConcurrentHistogram;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Third practice, measure accumulated latency with multiple threads
 */
public class PracticeLatency3
{
    /** Number of consumer threads to run */
    final static int NUM_THREADS = 8;
    /** Number of executions per thread */
    final static int NUM_EXECUTIONS = 100;
    /** Expected max executions per second */
    final static int MAX_EXPECTED_EXECUTIONS_PER_SECOND = 50;

    private static long LOW  = 1;
    private static long HIGH = 1000;
    private static int SIGNIFICATIONS   = 5;



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
        // Create a sleep time simulator, it will sleep for 10 milliseconds in each call
        BaseSyncOpSimulator syncOpSimulator = new SyncOpSimulSleep(10);
        ConcurrentHistogram histogram = new ConcurrentHistogram(LOW, HIGH, SIGNIFICATIONS),
                            hgCumulated = new ConcurrentHistogram(LOW, HIGH, SIGNIFICATIONS);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        String[] latRes, cumLatRes;
        int size;

        // List of threads
        List<Runner> runners = new LinkedList<>();

        // Create the threads and start them
        for (int i = 0; i < NUM_THREADS; i ++)
        {
            final Runner runner = new Runner(syncOpSimulator, histogram, hgCumulated);
            runners.add(runner);
            new Thread(runner).start();
        }

        // Wait for runners to finish
        runners.forEach(Runner::waitToFinish);

        // Get the outputs into a string array
        histogram.outputPercentileDistribution(ps, 1.0);
        ps.flush();
        latRes = baos.toString().split("[\n]");

        baos.reset();

        hgCumulated.outputPercentileDistribution(ps, 1.0);
        ps.flush();
        cumLatRes = baos.toString().split("[\n]");

        // Print them paired for a easier comparison
        size = Arrays.stream(latRes).max(Comparator.comparingInt(String::length)).get().length();
        IntStream.range(0, Math.min(latRes.length, cumLatRes.length))
                .forEach(i -> System.out.printf("%-" + size + "." + size + "s   |   %-" + size + "." + size + "s\n", latRes[i], cumLatRes[i]));
        // TODO Show the percentile distribution of the latency calculation of each executeOp call for all threads
    }

    /**
     * The runner that represent a thread execution
     */
    private static class Runner implements Runnable
    {
        /** The shared operation simulator */
        final BaseSyncOpSimulator syncOpSimulator;
        ConcurrentHistogram histogram;
        ConcurrentHistogram hgCumulated;

        /** True if finished */
        volatile boolean finished = false;

        /**
         * Create a new runner
         *
         * @param syncOpSimulator shared operation simulator
         */
        private Runner(BaseSyncOpSimulator syncOpSimulator, ConcurrentHistogram histogram, ConcurrentHistogram hgCumulated)
        {
            this.histogram = histogram;
            this.hgCumulated = hgCumulated;
            this.syncOpSimulator = syncOpSimulator;
        }

        @Override
        public void run()
        {
            // Calculate the expected time between consecutive calls, considering the number of executions per second
            final long expectedTimeBetweenCalls = TimeUnit.SECONDS.toMillis(1) / MAX_EXPECTED_EXECUTIONS_PER_SECOND;
            long start, total, extTime = 0;

            // Calculate the next call time, the first time should be immediate
            long nextCallTime = System.currentTimeMillis();

            // Execute the operation the required number of times
            for(int i = 0; i < NUM_EXECUTIONS; i++)
            {
                // Wait until there is the time for the next call
                while(System.currentTimeMillis() < nextCallTime);

                start = System.currentTimeMillis();
                // Execute the operation, it will sleep for 10 milliseconds
                syncOpSimulator.executeOp();
                total = System.currentTimeMillis() - start;

                // Add to extra time the time between expected and real
                extTime = extTime + expectedTimeBetweenCalls - total;

                // Extra time could be negative, in that case, put it as 0
                if (extTime < 0) extTime = 0;
                if(total < 0) total = 0;
                // Put results in histograms

                histogram.recordValue(total);
                hgCumulated.recordValue(total + extTime);

                // Calculate the next time to call execute op
                nextCallTime += expectedTimeBetweenCalls;
            }

            finished = true;
        }

        /** Wait for the runner execution to complete */
        public void waitToFinish()
        {
            while(!this.finished)
            {
                try
                {
                    Thread.sleep(1);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
}
