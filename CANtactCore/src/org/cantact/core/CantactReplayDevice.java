/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cantact.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.Timer;

/**
 *
 * @author Avatar-X
 */
public class CantactReplayDevice extends CantactDevice {

    // This class provides fake CAN data for when you are testing stuff and don't have a car connected.
    private Timer timer;
    private int fRand = 0;
    private int fType = 0; // The Type of the last frame we generated.
    private int fLast = 0;
    private FileReader fr;
    private BufferedReader br;
    private Pattern pPat = Pattern.compile("\\((.*)\\) can0 ([0-9A-F]*)#(.*)");
    private boolean playOrigSpeed;
    private boolean playLoopAtEnd;
    private long startTime; // The time in millseconds when this playback started.

    private float pb_timeIndex;
    private int pb_id;
    private int pb_dlc;
    private int[] pb_data;
    private boolean pb_wait = false;
    private String pb_fileName; // The file name to playback.

    public CantactReplayDevice(String deviceName, String filePath, boolean playOriginalSpeed, boolean loopAtEnd) {
        super(deviceName);
        // Open this file for playback.
        playOrigSpeed = playOriginalSpeed;
        playLoopAtEnd = loopAtEnd;
        pb_wait = false;
        pb_fileName = filePath;

        ActionListener taskPerformer = (ActionEvent evt) -> {
            // The timer fires.  We replay the next line in the file.            
            replayFrame();
        };

        timer = new Timer(100, taskPerformer);
        timer.setInitialDelay(0);
    }

    private void startPlaybackOfFile() {
        startTime = System.currentTimeMillis();
        timer.setDelay(1); // Make it as fast as possible.
        pb_wait = false;

        try {
            fr = new FileReader(pb_fileName);
            br = new BufferedReader(fr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        timer.start();

    }

    @Override
    public boolean start() {
        try {
            // TODO: Start sending junk data
            super.start();
            startPlaybackOfFile();
            return true;
        } catch (Exception ex) {
            // TODO: error handling
            System.out.println(ex);
            return false;
        }
    }

    @Override
    public boolean stop() {
        // Stop sending data.
        timer.stop();
        return super.stop();
    }

    private void replayFrame() {
        // Are we waiting for a frame?
        // If not, read the next one
        if (br == null) {
            return;
        }

        float currentTime = (float) (System.currentTimeMillis() - startTime) / 1000;

        if (pb_wait) {
            if (pb_timeIndex <= currentTime) {
                goReplayFrame();
                pb_wait = false;
            }
        } else {
            // Read the next line from the file.

            String line;
            try {
                if ((line = br.readLine()) != null) {
                    // process the line.
                    // Data format is as follows:
                    //    TIME    can0 ID #Data bytes here
                    // (5.114000) can0 063#062D2C0206171707

                    // Parse into the constituent elements.
                    String dataStr;
                    int[] data;
                    int dlc; // Data length

                    Matcher m = pPat.matcher(line);

                    if (m.find()) {
                        pb_timeIndex = Float.parseFloat(m.group(1));
                        pb_id = Integer.parseInt(m.group(2), 16);
                        dataStr = m.group(3);

                        // Convert the data string to a byte array.
                        pb_dlc = dataStr.length() / 2;
                        pb_data = new int[pb_dlc];

                        for (int i = 0; i < pb_dlc; i++) {
                            pb_data[i] = Integer.parseInt(dataStr.substring(i * 2, i * 2 + 2), 16);
                        }

                        // Is it time to play this one back? Or, do this if we are configured to go as fast as we can.
                        // System.out.println("time index: " + pb_timeIndex + " , current time: " + currentTime);
                        if (pb_timeIndex <= currentTime || !playOrigSpeed) {
                            goReplayFrame();
                            pb_wait = false;
                        } else {
                            // The frame might be played on the next cycle.  It is too soon right now.
                            pb_wait = true;
                        }
                    }

                } else {
                    System.out.println("Done");
                    timer.stop();

                    if (playLoopAtEnd) {
                        startPlaybackOfFile();
                    }
                }
            } catch (Exception e) {
                // Problem reading from the file.
                e.printStackTrace();
            }
        }
    }

    private void goReplayFrame() {
        // Create the can frame.
        CanFrame f = new CanFrame();
        f.setId(pb_id);
        f.setDlc(pb_dlc);
        f.setData(pb_data);
        f.setTimestamp(pb_timeIndex);
        DeviceManager.transmit(f);
    }
}
