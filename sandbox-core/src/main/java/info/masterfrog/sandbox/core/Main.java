package info.masterfrog.sandbox.core;

import java.text.NumberFormat;
import java.util.HashMap;

public class Main {
    private long initTimer;
    private long heartbeatCounter = 0;

    public static void main(String[] args) {
        Main main = new Main();
        int size = 1000000;
        main.testHashMap(size);
        main.testArray(size);
    }

    private void testHashMap(int size) {
        // init
        System.gc();
        System.out.println("HashMap:");
        initTimer();

        // setup
        HashMap map = new HashMap();

        // work
        for (int i = 0; i < size; i++) {
            map.put("Test", Math.random());
            logHeartbeat();
        }

        // result
        logResult("HashMap");
    }

    private void testArray(int size) {
        // init
        System.gc();
        System.out.println("Array:");
        initTimer();

        // setup
        double[] array = new double[0];

        // work
        for (int i = 0; i < size; i++) {
            double[] tmp = new double[array.length + 1];
            System.arraycopy(array, 0, tmp, 0, array.length);
            array = tmp;
            array[array.length - 1] = Math.random();
            logHeartbeat();
        }

        // result
        logResult("Array");
    }

    private void logHeartbeat() {
        heartbeatCounter++;

        if (heartbeatCounter % 100000 == 0) {
            System.out.print(" x\n.");
        } else if (heartbeatCounter % 10000 == 0) {
            System.out.print("\n.");
        } else if (heartbeatCounter % 1000 == 0) {
            System.out.print(" .");
        } else if (heartbeatCounter % 100 == 0) {
            System.out.print(".");
        }
    }

    private void logResult(String context) {
        System.out.println(context + ":");
        logTimer();
        logMemoryPulse();
        System.out.println();
    }

    private void initTimer() {
        initTimer = System.nanoTime();
    }

    private void logTimer() {
        long timer = System.nanoTime() - initTimer;

        System.out.println("   ===Timer===\n" + (timer/1000000.0) + "ms");
    }

    private String getMemoryPulseString() {
        // setup
        Runtime runtime = Runtime.getRuntime();
        NumberFormat format = NumberFormat.getInstance();

        // lookup memory details
        Long maxMemory = runtime.maxMemory();
        Long allocatedMemory = runtime.totalMemory();
        Long freeMemory = runtime.freeMemory();

        // generate memory pulse string
        String memoryPulseString = String.format(
            "    ===Memory Pulse===\n%12s: %10s K\n%12s: %10s K\n%12s: %10s K\n%12s: %10s K",
            "Free", format.format(freeMemory / 1024),
            "Allocated", format.format(allocatedMemory / 1024),
            "Max", format.format(maxMemory / 1024),
            "Total Free", format.format((freeMemory + (maxMemory - allocatedMemory))/ 1024)
        );

        return memoryPulseString;
    }

    private void logMemoryPulse() {
        System.out.println(getMemoryPulseString());
    }
}
