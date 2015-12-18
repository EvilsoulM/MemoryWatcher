package com.evilsoulm.memorywatcher;

/**
 * Author by EvilsoulM
 * Data:2015-12-18 12:43
 * Project:MemoryWatcher
 * Detail:
 */
public class Utils {
    public static long getSize() {
        Runtime.getRuntime().maxMemory();
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    public static float getFormatMbSize(float size) {
        long l = Math.round(size / 1024f / 1024f * 100);
        return l / 100.0f;
    }

    public static float getFormatKbSize(float size) {
        long l = Math.round(size / 1024f * 100);
        return l / 100.0f;
    }
}
