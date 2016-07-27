package org.cantact.core;

import java.util.ArrayList;
import org.junit.Test;

public class DeviceManager {

    private static CantactDevice device;
    private final static ArrayList<CanListener> canListeners = new ArrayList<>();

    public static boolean isDeviceOpen(String devName) {
        if (device != null && devName != null) {
            return (device.getDeviceName().
                    equals(devName) && device.isOpened());
        } else {
            return false;
        }
    }

    public static void addListener(CanListener l) {
        canListeners.add(l);
    }

    public static void removeListener(CanListener l) {
        canListeners.remove(l);
    }

    public static String[] getDeviceList() {
        return CantactDevice.getDeviceList();
    }

    public static void openDevice(String deviceName, int speed) {

        // If this is a request for fake data, open a fake device (it inherits from CantactDevice)
        if (deviceName.equals("TESTDATA")) {
            device = new CantactFakeDevice("");
        } else {
            device = new CantactDevice(deviceName);
        }

        device.setSpeedMode(speed);
        device.start();
    }

    public static boolean openDevice(String deviceName, String replayFile, boolean playOriginalSpeed, boolean loopAtEnd, boolean hardwareReplay, int speed) {

        if (hardwareReplay) {
            // If this is a request for hardware replay, open a cantactreplay device (it inherits from CantactDevice)
            device = new CantactReplayDevice(deviceName, replayFile, playOriginalSpeed, loopAtEnd);
            device.setSpeedMode(speed);

        } else {
            // If this is a request for fake data, open a fake device (it inherits from CantactDevice)
            device = new CantactFakeDevice("");

            CantactFakeDevice cfd = (CantactFakeDevice) device;
            cfd.ReplayFile(replayFile, playOriginalSpeed, loopAtEnd); // Tell the fake device to replay this file.
        }

        return device.start();
    }

    @Test(timeout = 500)
    public static boolean transmit(CanFrame txFrame) {
        if (device != null) {
            if (device.sendFrame(txFrame)) {
                for (CanListener l : canListeners) {
                    l.canReceived(txFrame);
                }
                return true;
            }
        }
        return false;
    }

    public static boolean transmitNoCheck(CanFrame txFrame) {
        if (device != null) {
            if (device.sendFrame(txFrame)) {
                return true;
            }
        }
        return false;
    }

    public static boolean closeDevice(String portName) {
        return device.stop();
    }

    static void giveFrame(CanFrame f) {
        for (CanListener l : canListeners) {
            l.canReceived(f);
        }
    }
}
