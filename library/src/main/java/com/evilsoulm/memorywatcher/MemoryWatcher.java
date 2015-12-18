package com.evilsoulm.memorywatcher;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Author by EvilsoulM
 * Data:2015-12-18 10:35
 * Project:aimovie
 * Detail:
 */
public class MemoryWatcher extends Thread {
    private static final String TAG = "MemoryWatcher";
    private Unit unit;
    private int interval;
    private InterruptionListener interruptionListener;
    private MemoryListener listener;
    private final Handler uiHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.obj instanceof Float) {
                if (listener != null) {
                    listener.onMemorySize((Float) msg.obj);
                }
            }
        }
    };

    private MemoryWatcher(Builder builder) {
        this.unit = builder.unit;

        if (this.unit == null) {
            this.unit = Unit.MB;
        }
        this.interval = builder.interval;

        if (this.interval == 0) {
            this.interval = Const.DEFAULT_INTERVAL;
        }

        this.listener = builder.listener;
        this.interruptionListener = builder.interruptionListener;
    }

    @Override
    public void run() {
        setName("|MemoryWatcher|");

        while (!isInterrupted()) {

            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                if (interruptionListener != null) {
                    interruptionListener.onInterrupted(e);
                }
                return;
            }

            float memorySize = Utils.getSize();

            if (unit == Unit.MB) {
                memorySize = Utils.getFormatMbSize(memorySize);
            } else if (unit == Unit.KB) {
                memorySize = Utils.getFormatKbSize(memorySize);
            }

            Message msg = Message.obtain();
            msg.obj = memorySize;
            uiHandler.sendMessage(msg);
        }
    }

    public void stopWatch() {
        interrupt();
        uiHandler.removeCallbacksAndMessages(this);
    }

    public static class Builder {
        private Unit unit;
        private int interval;
        private InterruptionListener interruptionListener;
        private MemoryListener listener;


        public Builder setUnit(Unit unit) {
            this.unit = unit;
            return this;
        }

        public Builder setInterruptionListener(InterruptionListener interruptionListener) {
            this.interruptionListener = interruptionListener;
            return this;
        }

        public Builder setListener(MemoryListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder setInterval(int interval) {
            this.interval = interval;
            return this;
        }

        public MemoryWatcher build() {
            return new MemoryWatcher(this);
        }
    }
}
