package org.cantact.ui;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.cantact.core.CanFrame;
import org.cantact.core.CanListener;
import org.cantact.core.DeviceManager;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;

class LiveTableDataCell {
    private String current = "";
    private String previous = "";
    
    public String getCurrent() {
        return current;
    }
    public void setCurrent(String value) {
         current = value;
    }
    public String getPrevious() {
        return previous;
    }
    public void getCurrent(String value) {
        previous = value;
    }
    public void swap() {
        previous = current;
        current = "";
    }
    @Override
    public String toString() {
        return current;
    }
}

class LiveTableRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        Component c;
        if (value instanceof LiveTableDataCell) {
            LiveTableDataCell dataCell = (LiveTableDataCell)value;
            c = super.getTableCellRendererComponent(table, 
                dataCell.getCurrent(), isSelected, hasFocus, row, 
                column);
            
            
            // Determine if any of the bytes are set to IGNORE
            String ignore = (String)table.getValueAt(row, 6);
            
            // Are there any bytes that want to be converted to a larger number?
            // Example syntax might be:
            // 1-2,4-8
            
            // byte coloring
            // get the new byte values and old byte values
            String[] currentBytes = dataCell.getCurrent().split(" ");
            String[] prevBytes = dataCell.getPrevious().split(" ");
            String result = "<html>";
            boolean ignoreMe = false;

            if (column == 4) // 'Numeric' column.  At some point the columns should be defined based on their names.
            {
                String numberize = (String)table.getValueAt(row, 7); 
                if (numberize != null)
                {
                    // We allow defining multiple groups.
                    String[] nGroups = numberize.split(",");
                    String sResult = "";

                    // For each group, split by the dash.
                    for (int i = 0; i < nGroups.length; i++)
                    {
                        String[] gSplit = nGroups[i].split("-");
                        if (gSplit != null && gSplit.length == 2)
                        {
                            // Build the result out of the specified numbers.
                            long lResult = 0;
                            try
                            {
                                int startByte = Integer.parseInt(gSplit[0]);
                                int endByte = Integer.parseInt(gSplit[1]);

                                for (int j = startByte; j <= endByte; j++)
                                {
                                    // Shift the previous result's value left by 8 bits
                                    lResult = (lResult << 8) + Integer.parseInt(currentBytes[j]);
                                }
                            }
                            catch (Exception ecc)
                            {
                                // Probably a number format problem
                            }
                            
                            sResult += lResult + (i < nGroups.length ? ", " : "");
                        }

                    }
                    
                    table.setValueAt(sResult, row, 8);

                }
            }
            
            
            for (int i = 0; i < currentBytes.length; i++) {
                // out of bytes in previous data, all other bytes are new
                
                // If ignoreMe is set on this byte, don't make it red.
                ignoreMe = false;
                if (ignore != null) ignoreMe = (ignore.indexOf("" + i) > -1);
                
                if (i >= prevBytes.length) {
                    result = result + ("<font color='red'>" + 
                                       currentBytes[i] +
                                       "</font> ");
                    
                } else {
                    // check if the byte has changed
                    if (currentBytes[i].equals(prevBytes[i]) || ignoreMe) {
                        // byte has not changed
                        result = result + ("<font color='black'>" + 
                                           currentBytes[i] +
                                           "</font> ");
                    } else {
                        // byte changed
                        result = result + ("<font color='red'>" + 
                                           currentBytes[i] +
                                           "</font> ");
                    }
                }
            }
            result = result + "</html>";
            setText(result);
        } else {
            c = super.getTableCellRendererComponent(table, 
                value, isSelected, hasFocus, row, 
                column);
        }
        return c;
    }
}


/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//org.cantact.ui//Live//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "LiveTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "org.cantact.ui.LiveTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_LiveAction",
        preferredID = "LiveTopComponent"
)
@Messages({
    "CTL_LiveAction=Live",
    "CTL_LiveTopComponent=Live Window",
    "HINT_LiveTopComponent=This is a Live window"
})

public final class LiveTopComponent extends TopComponent implements CanListener {

    public LiveTopComponent() {
        initComponents();
        setName(Bundle.CTL_LiveTopComponent());
        setToolTipText(Bundle.HINT_LiveTopComponent());
        liveTable.setDefaultRenderer(Object.class, new LiveTableRenderer());
        DeviceManager.addListener(this);
    }
    class LiveUpdater implements Runnable {
        private CanFrame frame;
        public LiveUpdater(CanFrame f) {
            frame = f;
        }
        public void run() {
            String dataString = "";
            String numString = "";
            String textString = "";
            String nowDate = "";
            nowDate = new SimpleDateFormat("hh:mm:ss").format(new Date());
            
            for (int i = 0; i < frame.getDlc(); i++) {
                int b = frame.getData()[i]; // retrieve the byte for this value
                dataString = dataString + String.format("%02X ", b);
                numString = numString + String.format("%03d ", b);
                
                if (b >= 32 && b <= 127)
                {
                    textString += Character.toString((char)b);
                } else {
                    textString += ".";
                }
            }
            
            DefaultTableModel liveModel = (DefaultTableModel) liveTable.getModel();
            boolean inserted = false;
                   
            for (int i = 0; i < liveModel.getRowCount(); i++) {
                if ((int)liveModel.getValueAt(i, 0) == frame.getId()) {
                    liveModel.setValueAt((Object)frame.getDlc(), i, 1);
                    
                    // Update the text string on the 5th column
                    liveModel.setValueAt(textString, i, 5);
                    liveModel.setValueAt(nowDate, i, 2);
                    
                    // get the existing cell data
                    try {
                        LiveTableDataCell dataCell = (LiveTableDataCell)liveModel.getValueAt(i, 3);
                        dataCell.swap();                  
                        // set current value to new data
                        dataCell.setCurrent(dataString);
                        // push to the table
                        liveModel.setValueAt(dataCell, i, 3);    
                        inserted = true;
                    } catch (ClassCastException e) {
                        // dataCell has been edited and is now a string
                        // remove that row
                        liveModel.removeRow(i);
                    }

                    // NUMERIC: get the existing cell data
                    
                    if (liveModel != null)
                    {
                        try {
                            LiveTableDataCell dataCell = null;
                            dataCell = (LiveTableDataCell)liveModel.getValueAt(i, 4);

                            dataCell.swap();                  
                            dataCell.setCurrent(numString); // set current value to new data
                            liveModel.setValueAt(dataCell, i, 4);     // push to the table
                            inserted = true;
                        } 
                        catch (ClassCastException e) {
                            // dataCell has been edited and is now a string
                            // remove that row
                            liveModel.removeRow(i);
                        }
                        catch (Exception e2) {
                            e2.printStackTrace();                        
                        }
                    }
                    
                }
            }
            
            if (!inserted) {
                LiveTableDataCell dataCell1 = new LiveTableDataCell();
                LiveTableDataCell dataCell2 = new LiveTableDataCell();
                dataCell1.setCurrent(dataString);
                dataCell2.setCurrent(numString);
                
                //        Indexes:          0                    1                  2          3          4           5
                Object[] rowData = {(Object)frame.getId(), (Object)frame.getDlc(), nowDate, dataCell1, dataCell2, textString};
                liveModel.addRow(rowData);
            
            }
        }
    }
    @Override
    public void canReceived(CanFrame f) {
        java.awt.EventQueue.invokeLater(new LiveUpdater(f));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        liveTable = new javax.swing.JTable();
        jToolBar2 = new javax.swing.JToolBar();
        clearButton = new javax.swing.JButton();

        liveTable.setAutoCreateRowSorter(true);
        liveTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DLC", "Last Seen", "Data", "Numeric", "Text", "Ignore", "Numberize", "Result"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        liveTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                liveTableMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(liveTable);
        if (liveTable.getColumnModel().getColumnCount() > 0) {
            liveTable.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(LiveTopComponent.class, "LiveTopComponent.liveTable.columnModel.title0")); // NOI18N
            liveTable.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(LiveTopComponent.class, "LiveTopComponent.liveTable.columnModel.title1")); // NOI18N
            liveTable.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(LiveTopComponent.class, "LiveTopComponent.liveTable.columnModel.title5")); // NOI18N
            liveTable.getColumnModel().getColumn(3).setHeaderValue(org.openide.util.NbBundle.getMessage(LiveTopComponent.class, "LiveTopComponent.liveTable.columnModel.title2")); // NOI18N
            liveTable.getColumnModel().getColumn(4).setHeaderValue(org.openide.util.NbBundle.getMessage(LiveTopComponent.class, "LiveTopComponent.liveTable.columnModel.title3")); // NOI18N
            liveTable.getColumnModel().getColumn(5).setHeaderValue(org.openide.util.NbBundle.getMessage(LiveTopComponent.class, "LiveTopComponent.liveTable.columnModel.title4")); // NOI18N
            liveTable.getColumnModel().getColumn(6).setHeaderValue(org.openide.util.NbBundle.getMessage(LiveTopComponent.class, "LiveTopComponent.liveTable.columnModel.title7")); // NOI18N
            liveTable.getColumnModel().getColumn(7).setHeaderValue(org.openide.util.NbBundle.getMessage(LiveTopComponent.class, "LiveTopComponent.liveTable.columnModel.title6")); // NOI18N
            liveTable.getColumnModel().getColumn(8).setHeaderValue(org.openide.util.NbBundle.getMessage(LiveTopComponent.class, "LiveTopComponent.liveTable.columnModel.title8")); // NOI18N
        }

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);
        jToolBar2.setMaximumSize(new java.awt.Dimension(98, 34));
        jToolBar2.setMinimumSize(new java.awt.Dimension(98, 34));
        jToolBar2.setPreferredSize(new java.awt.Dimension(110, 54));

        org.openide.awt.Mnemonics.setLocalizedText(clearButton, org.openide.util.NbBundle.getMessage(LiveTopComponent.class, "LiveTopComponent.clearButton.text")); // NOI18N
        clearButton.setFocusable(false);
        clearButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        clearButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });
        jToolBar2.add(clearButton);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 715, Short.MAX_VALUE)
            .addComponent(jToolBar2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        DefaultTableModel liveModel = (DefaultTableModel) liveTable.getModel();
        while (liveModel.getRowCount() > 0) {
            for (int i = 0; i < liveModel.getRowCount(); i++) {
                liveModel.removeRow(i);
            }
        }
    }//GEN-LAST:event_clearButtonActionPerformed

    private void liveTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_liveTableMousePressed
        
        System.out.println(evt.getButton());
        if (evt.getButton() == 3) // 3 is right click, for some idiot reason.
        {
            // Show a pop up menu with the right-click options.
            LiveMenu menu = new LiveMenu();

            // Try figure out which row was clicked on.
            int row = liveTable.rowAtPoint(evt.getPoint());
            int col = liveTable.columnAtPoint(evt.getPoint());        
            menu.setRowCol(row, col);
            menu.build();

            menu.show(evt.getComponent(), evt.getX(), evt.getY());        
        }
    }//GEN-LAST:event_liveTableMousePressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearButton;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JTable liveTable;
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
    
    class LiveMenu extends JPopupMenu {
        JMenuItem anItem;
        int CellRow;
        int CellCol;
        ActionListener aListen; // This listens for actions from the menu.
        
        public LiveMenu() {
            aListen = new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    // Which menu item was pressed?
                    LiveMenuItem nItem = (LiveMenuItem)evt.getSource();
                    
                    pressed(nItem.ID);
                }
            };
        }
        public void build()
        {
            // Build the menu 
            
            int ID = (Integer)liveTable.getValueAt(CellRow, 0);
            addItem("Trace ID #" + ID, "trace");
            addItem("Ignore Changed Bytes", "ignore");
            
        }
        
        public void pressed(String ID)
        {
            switch (ID)
            {
                case "trace": // Jump to the Trace window with this ID filtered only, and start tracing.
                    int canID = (Integer)liveTable.getValueAt(CellRow, 0);
                    
                    // switch over to the Trace tab and input this as the filters.
                    TopComponent tc = WindowManager.getDefault().findTopComponent("TraceTopComponent");
                    TraceTopComponent ttc = (TraceTopComponent)tc;

                    // Tell this window to filter only this stuff.
                    ttc.FilterOnly(canID);
                    ttc.requestActive(); // Bring this window to the front.
                    
                    break;
                    
                case "ignore":  // This takes the items that have changed (the items that are red now) and adds them to the ignore list.
                    
                    LiveTableDataCell dataCell = (LiveTableDataCell)liveTable.getValueAt(CellRow, 3);
                    
                    String[] currentBytes = dataCell.getCurrent().split(" ");
                    String[] prevBytes = dataCell.getPrevious().split(" ");
                    String ignoreString = "";
                    
                    for (int i = 0; i < currentBytes.length; i++) {
                        // out of bytes in previous data, all other bytes are new

                        // If ignoreMe is set on this byte, don't make it red.
                        if (i >= prevBytes.length) {
                            ignoreString += "" + i;
                        } else {
                            // check if the byte has changed
                            if (currentBytes[i].equals(prevBytes[i])) {
                                // byte has not changed
                            } else {
                                // byte changed
                                ignoreString += "" + i;
                            }
                        }
                    }
                    
                    liveTable.setValueAt(ignoreString, CellRow, 6);
                    break;
            }
        }
        
        public void setRowCol(int row, int col)
        {
            // This is the row/col that was clicked.
            CellRow = row;
            CellCol = col;
        }
        
        // Create a menu item and add it
        public void addItem(String itemText, String itemID)
        {
            LiveMenuItem nItem = new LiveMenuItem(itemText);
            nItem.ID = itemID;
            nItem.addActionListener(aListen);            
            add(nItem);
        }
        
        
        
    }
    
    class LiveMenuItem extends JMenuItem
    {
        public String ID;
        public LiveMenuItem(String menuText)
        {
            super(menuText);
        }
    }
    
}
