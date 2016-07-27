package org.cantact.ui;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.cantact.core.DeviceManager;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//org.cantact.ui//Config//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "ConfigTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "leftSlidingSide", openAtStartup = true)
@ActionID(category = "Window", id = "org.cantact.ui.ConfigTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_ConfigAction",
        preferredID = "ConfigTopComponent"
)
@Messages({
    "CTL_ConfigAction=Config",
    "CTL_ConfigTopComponent=Config Window",
    "HINT_ConfigTopComponent=This is a Config window"
})
public final class ConfigTopComponent extends TopComponent {

    public ConfigTopComponent() {
        initComponents();
        setName(Bundle.CTL_ConfigTopComponent());
        setToolTipText(Bundle.HINT_ConfigTopComponent());
        putClientProperty(TopComponent.PROP_CLOSING_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_MAXIMIZATION_DISABLED, Boolean.TRUE);
        setPortList();
    }

    private void setPortList() {
        String[] deviceList = DeviceManager.getDeviceList();
        if (deviceList.length > 0) {
            this.portComboBox.removeAllItems();
            for (String device : deviceList) {
                this.portComboBox.addItem(device);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        startButton = new javax.swing.JButton();
        portComboBox = new javax.swing.JComboBox<>();
        bitRateComboBox = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        refreshButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        debugDataButton = new javax.swing.JButton();
        debugStop = new javax.swing.JButton();
        replayButton = new javax.swing.JButton();
        replayFileText = new javax.swing.JTextField();
        selFileButton = new javax.swing.JButton();
        replayStopButton = new javax.swing.JButton();
        chkPlayOriginalSpeed = new javax.swing.JCheckBox();
        chkLoopAtEnd = new javax.swing.JCheckBox();
        chkHardwareReplay = new javax.swing.JCheckBox();

        org.openide.awt.Mnemonics.setLocalizedText(startButton, org.openide.util.NbBundle.getMessage(ConfigTopComponent.class, "ConfigTopComponent.startButton.text")); // NOI18N
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        portComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None" }));
        portComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portComboBoxActionPerformed(evt);
            }
        });

        bitRateComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "10 Kbps", "20 Kbps", "50 Kbps", "100 Kbps", "125 Kbps", "250 Kbps", "500 Kbps", "750 Kbps", "1000 Kbps" }));
        bitRateComboBox.setSelectedIndex(4);
        bitRateComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bitRateComboBoxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ConfigTopComponent.class, "ConfigTopComponent.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(ConfigTopComponent.class, "ConfigTopComponent.jLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(refreshButton, org.openide.util.NbBundle.getMessage(ConfigTopComponent.class, "ConfigTopComponent.refreshButton.text")); // NOI18N
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(stopButton, org.openide.util.NbBundle.getMessage(ConfigTopComponent.class, "ConfigTopComponent.stopButton.text")); // NOI18N
        stopButton.setEnabled(false);
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(debugDataButton, org.openide.util.NbBundle.getMessage(ConfigTopComponent.class, "ConfigTopComponent.debugDataButton.text")); // NOI18N
        debugDataButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                debugDataButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(debugStop, org.openide.util.NbBundle.getMessage(ConfigTopComponent.class, "ConfigTopComponent.debugStop.text")); // NOI18N
        debugStop.setEnabled(false);
        debugStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                debugStopActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(replayButton, org.openide.util.NbBundle.getMessage(ConfigTopComponent.class, "ConfigTopComponent.replayButton.text")); // NOI18N
        replayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replayButtonActionPerformed(evt);
            }
        });

        replayFileText.setText(org.openide.util.NbBundle.getMessage(ConfigTopComponent.class, "ConfigTopComponent.replayFileText.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(selFileButton, org.openide.util.NbBundle.getMessage(ConfigTopComponent.class, "ConfigTopComponent.selFileButton.text")); // NOI18N
        selFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selFileButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(replayStopButton, org.openide.util.NbBundle.getMessage(ConfigTopComponent.class, "ConfigTopComponent.replayStopButton.text")); // NOI18N
        replayStopButton.setEnabled(false);
        replayStopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replayStopButtonActionPerformed(evt);
            }
        });

        chkPlayOriginalSpeed.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(chkPlayOriginalSpeed, org.openide.util.NbBundle.getMessage(ConfigTopComponent.class, "ConfigTopComponent.chkPlayOriginalSpeed.text")); // NOI18N
        chkPlayOriginalSpeed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPlayOriginalSpeedActionPerformed(evt);
            }
        });

        chkLoopAtEnd.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(chkLoopAtEnd, org.openide.util.NbBundle.getMessage(ConfigTopComponent.class, "ConfigTopComponent.chkLoopAtEnd.text")); // NOI18N
        chkLoopAtEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkLoopAtEndActionPerformed(evt);
            }
        });

        chkHardwareReplay.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(chkHardwareReplay, org.openide.util.NbBundle.getMessage(ConfigTopComponent.class, "ConfigTopComponent.chkHardwareReplay.text")); // NOI18N
        chkHardwareReplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkHardwareReplayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(portComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(startButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(stopButton)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(bitRateComboBox, 0, 180, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refreshButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(debugStop)
                            .addComponent(debugDataButton)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(replayFileText, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(selFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(chkLoopAtEnd)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(replayButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(replayStopButton))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(chkPlayOriginalSpeed)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(chkHardwareReplay)))))
                .addContainerGap(107, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(portComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(refreshButton)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(bitRateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startButton)
                    .addComponent(stopButton))
                .addGap(35, 35, 35)
                .addComponent(debugDataButton)
                .addGap(1, 1, 1)
                .addComponent(debugStop)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(selFileButton)
                    .addComponent(replayFileText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkPlayOriginalSpeed)
                    .addComponent(chkHardwareReplay))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkLoopAtEnd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(replayButton)
                    .addComponent(replayStopButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        String portName = this.portComboBox.getSelectedItem().toString();
        if (portName.equals("None")) {
            // no device selected, do nothing
            return;
        }
        int speed = this.bitRateComboBox.getSelectedIndex();

        DeviceManager.openDevice(portName, speed);

        setAllButtonStates(false);
        stopButton.setEnabled(true);

    }//GEN-LAST:event_startButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        setPortList();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void portComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portComboBoxActionPerformed
        if (this.portComboBox.getSelectedItem() == null) {
            return;
        }
        String portName = this.portComboBox.getSelectedItem().toString();
        if (DeviceManager.isDeviceOpen(portName)) {
            setAllButtonStates(false);
            stopButton.setEnabled(true);
        } else {
            setAllButtonStates(false);
            turnOnStartButtons(true);
        }
    }//GEN-LAST:event_portComboBoxActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        String portName = this.portComboBox.getSelectedItem().toString();
        if (DeviceManager.isDeviceOpen(portName)) {
            DeviceManager.closeDevice(portName);

            setAllButtonStates(false);
            turnOnStartButtons(true);
        }
    }//GEN-LAST:event_stopButtonActionPerformed

    private void bitRateComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bitRateComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bitRateComboBoxActionPerformed

    private void debugDataButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_debugDataButtonActionPerformed
        // This button will generate test data.
        setAllButtonStates(false);
        debugStop.setEnabled(true);

        // We use a fake device name to do this.
        DeviceManager.openDevice("TESTDATA", 0);


    }//GEN-LAST:event_debugDataButtonActionPerformed

    private void debugStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_debugStopActionPerformed
        // Stop generating test data.
        setAllButtonStates(false);
        turnOnStartButtons(true);

        if (DeviceManager.isDeviceOpen("TESTDATA")) {
            DeviceManager.closeDevice("TESTDATA");
        }

    }//GEN-LAST:event_debugStopActionPerformed

    private void setAllButtonStates(boolean state) {
        // Set the enabled state of all buttons.
        debugStop.setEnabled(state);
        replayStopButton.setEnabled(state);
        turnOnStartButtons(state);
    }

    private void turnOnStartButtons(boolean state) {
        // These buttons all get enabled together as they can start things.
        selFileButton.setEnabled(state);
        replayFileText.setEnabled(state);
        debugDataButton.setEnabled(state);
        startButton.setEnabled(state);
        portComboBox.setEnabled(state);
        replayButton.setEnabled(state);
        bitRateComboBox.setEnabled(state);
        refreshButton.setEnabled(state);
        chkLoopAtEnd.setEnabled(state);
        chkPlayOriginalSpeed.setEnabled(state);
    }

    private void selFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selFileButtonActionPerformed
        // Choose a file.
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("ASCII Logs", "asc");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            replayFileText.setText(chooser.getSelectedFile().getPath());
        }

    }//GEN-LAST:event_selFileButtonActionPerformed

    private void replayStopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_replayStopButtonActionPerformed
        // Stop playing the file.

        if (DeviceManager.isDeviceOpen("TESTDATA")) {
            DeviceManager.closeDevice("TESTDATA");
        }

        String portName = this.portComboBox.getSelectedItem().toString();
        if (DeviceManager.isDeviceOpen(portName)) {
            DeviceManager.closeDevice(portName);
        }

        setAllButtonStates(false);
        turnOnStartButtons(true);

    }//GEN-LAST:event_replayStopButtonActionPerformed

    private void replayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_replayButtonActionPerformed
        // Start playing the file.

        String filePath = replayFileText.getText();
        int speed = this.bitRateComboBox.getSelectedIndex();
        String portName = this.portComboBox.getSelectedItem().toString();

        if (!filePath.equals("")) {
            setAllButtonStates(false);
            replayStopButton.setEnabled(true);

            // We use a fake device name to do this.
            DeviceManager.openDevice(portName, filePath, chkPlayOriginalSpeed.isSelected(), chkLoopAtEnd.isSelected(), chkHardwareReplay.isSelected(), speed);
        }


    }//GEN-LAST:event_replayButtonActionPerformed

    private void chkPlayOriginalSpeedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPlayOriginalSpeedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkPlayOriginalSpeedActionPerformed

    private void chkLoopAtEndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkLoopAtEndActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkLoopAtEndActionPerformed

    private void chkHardwareReplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkHardwareReplayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkHardwareReplayActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> bitRateComboBox;
    private javax.swing.JCheckBox chkHardwareReplay;
    private javax.swing.JCheckBox chkLoopAtEnd;
    private javax.swing.JCheckBox chkPlayOriginalSpeed;
    private javax.swing.JButton debugDataButton;
    private javax.swing.JButton debugStop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JComboBox<String> portComboBox;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton replayButton;
    private javax.swing.JTextField replayFileText;
    private javax.swing.JButton replayStopButton;
    private javax.swing.JButton selFileButton;
    private javax.swing.JButton startButton;
    private javax.swing.JButton stopButton;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
}
