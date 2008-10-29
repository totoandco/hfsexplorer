/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FSEntrySummaryPanel.java
 *
 * Created on 2008-okt-25, 06:02:01
 */

package org.catacombae.hfsexplorer.gui;

//import javax.swing.JFrame;
//import javax.swing.JScrollPane;
import org.catacombae.jparted.lib.fs.FSAttributes.POSIXFileAttributes;

/**
 *
 * @author erik
 */
public class POSIXAttributesPanel extends javax.swing.JPanel {
    
    public POSIXAttributesPanel(POSIXFileAttributes attrs) {
        this();

        permissionStringField.setText(attrs.getPermissionString());

        userReadBox.setSelected(attrs.canUserRead());
        userWriteBox.setSelected(attrs.canUserWrite());
        userExecuteBox.setSelected(attrs.canUserExecute());
        groupReadBox.setSelected(attrs.canGroupRead());
        groupWriteBox.setSelected(attrs.canGroupWrite());
        groupExecuteBox.setSelected(attrs.canGroupExecute());
        otherReadBox.setSelected(attrs.canOthersRead());
        otherWriteBox.setSelected(attrs.canOthersWrite());
        otherExecuteBox.setSelected(attrs.canOthersExecute());
        
        setUidBox.setSelected(attrs.isSetUID());
        setGidBox.setSelected(attrs.isSetGID());
        stickyBitBox.setSelected(attrs.isStickyBit());

        ownerIDField.setText(attrs.getUserID() + "");
        groupIDField.setText(attrs.getGroupID() + "");


    }

    private POSIXAttributesPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupIDField = new javax.swing.JTextField();
        ownerIDField = new javax.swing.JTextField();
        ownerIDLabel = new javax.swing.JLabel();
        groupIDLabel = new javax.swing.JLabel();
        stickyBitBox = new javax.swing.JCheckBox();
        setGidBox = new javax.swing.JCheckBox();
        setUidBox = new javax.swing.JCheckBox();
        otherLabel = new javax.swing.JLabel();
        otherReadBox = new javax.swing.JCheckBox();
        otherWriteBox = new javax.swing.JCheckBox();
        otherExecuteBox = new javax.swing.JCheckBox();
        groupExecuteBox = new javax.swing.JCheckBox();
        userExecuteBox = new javax.swing.JCheckBox();
        userWriteBox = new javax.swing.JCheckBox();
        groupWriteBox = new javax.swing.JCheckBox();
        groupReadBox = new javax.swing.JCheckBox();
        userReadBox = new javax.swing.JCheckBox();
        groupLabel = new javax.swing.JLabel();
        userLabel = new javax.swing.JLabel();
        permissionStringField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();

        groupIDField.setEditable(false);
        groupIDField.setText("jTextField5");
        groupIDField.setOpaque(false);

        ownerIDField.setEditable(false);
        ownerIDField.setText("jTextField4");
        ownerIDField.setOpaque(false);

        ownerIDLabel.setText("Owner ID:");

        groupIDLabel.setText("Group ID:");

        stickyBitBox.setBorder(null);
        stickyBitBox.setEnabled(false);

        setGidBox.setBorder(null);
        setGidBox.setEnabled(false);

        setUidBox.setBorder(null);
        setUidBox.setEnabled(false);

        otherLabel.setText("Other:");

        otherReadBox.setBorder(null);
        otherReadBox.setEnabled(false);

        otherWriteBox.setBorder(null);
        otherWriteBox.setEnabled(false);

        otherExecuteBox.setBorder(null);
        otherExecuteBox.setEnabled(false);

        groupExecuteBox.setBorder(null);
        groupExecuteBox.setEnabled(false);

        userExecuteBox.setBorder(null);
        userExecuteBox.setEnabled(false);

        userWriteBox.setBorder(null);
        userWriteBox.setEnabled(false);

        groupWriteBox.setBorder(null);
        groupWriteBox.setEnabled(false);

        groupReadBox.setBorder(null);
        groupReadBox.setEnabled(false);

        userReadBox.setBorder(null);
        userReadBox.setEnabled(false);

        groupLabel.setText("Group:");

        userLabel.setText("User:");

        permissionStringField.setEditable(false);
        permissionStringField.setText("jTextField6");
        permissionStringField.setOpaque(false);

        jLabel10.setText("Permission string:");

        jLabel4.setText("POSIX permissions:");

        jLabel1.setText("Execute");

        jLabel2.setText("Write");

        jLabel3.setText("Read");

        jLabel5.setText("Execute");

        jLabel6.setText("Write");

        jLabel7.setText("Read");

        jLabel8.setText("Execute");

        jLabel9.setText("Write");

        jLabel11.setText("Read");

        jLabel12.setText("Set user ID on execution");

        jLabel13.setText("Set group ID on execution");

        jLabel14.setText("Sticky bit");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel4)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(jLabel10)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(permissionStringField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(userLabel)
                                    .add(groupLabel)
                                    .add(otherLabel))
                                .add(7, 7, 7)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(layout.createSequentialGroup()
                                        .add(otherReadBox)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jLabel11)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(otherWriteBox)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jLabel9)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(otherExecuteBox)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jLabel8))
                                    .add(layout.createSequentialGroup()
                                        .add(groupReadBox)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jLabel7)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(groupWriteBox)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jLabel6)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(groupExecuteBox)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jLabel5))
                                    .add(layout.createSequentialGroup()
                                        .add(userReadBox)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jLabel3)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(userWriteBox)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jLabel2)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(userExecuteBox)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jLabel1))))
                            .add(layout.createSequentialGroup()
                                .add(setUidBox)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabel12))
                            .add(layout.createSequentialGroup()
                                .add(setGidBox)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabel13))
                            .add(layout.createSequentialGroup()
                                .add(stickyBitBox)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabel14))
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(ownerIDLabel)
                                    .add(groupIDLabel))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(ownerIDField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                                    .add(groupIDField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE))))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jLabel4)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel10)
                    .add(permissionStringField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(userReadBox)
                    .add(userWriteBox)
                    .add(userExecuteBox)
                    .add(jLabel1)
                    .add(jLabel2)
                    .add(jLabel3)
                    .add(userLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(groupReadBox)
                    .add(groupWriteBox)
                    .add(groupExecuteBox)
                    .add(jLabel5)
                    .add(jLabel6)
                    .add(jLabel7)
                    .add(groupLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(otherReadBox)
                    .add(otherWriteBox)
                    .add(otherExecuteBox)
                    .add(jLabel8)
                    .add(jLabel9)
                    .add(jLabel11)
                    .add(otherLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(setUidBox)
                    .add(jLabel12))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(setGidBox)
                    .add(jLabel13))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(stickyBitBox)
                    .add(jLabel14))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(ownerIDLabel)
                    .add(ownerIDField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(groupIDLabel)
                    .add(groupIDField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox groupExecuteBox;
    private javax.swing.JTextField groupIDField;
    private javax.swing.JLabel groupIDLabel;
    private javax.swing.JLabel groupLabel;
    private javax.swing.JCheckBox groupReadBox;
    private javax.swing.JCheckBox groupWriteBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JCheckBox otherExecuteBox;
    private javax.swing.JLabel otherLabel;
    private javax.swing.JCheckBox otherReadBox;
    private javax.swing.JCheckBox otherWriteBox;
    private javax.swing.JTextField ownerIDField;
    private javax.swing.JLabel ownerIDLabel;
    private javax.swing.JTextField permissionStringField;
    private javax.swing.JCheckBox setGidBox;
    private javax.swing.JCheckBox setUidBox;
    private javax.swing.JCheckBox stickyBitBox;
    private javax.swing.JCheckBox userExecuteBox;
    private javax.swing.JLabel userLabel;
    private javax.swing.JCheckBox userReadBox;
    private javax.swing.JCheckBox userWriteBox;
    // End of variables declaration//GEN-END:variables

    /*
    public static void main(String[] args) {
        JFrame jf = new JFrame("Test");
        jf.add(new JScrollPane(new POSIXAttributesPanel()));
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }
    */
}
