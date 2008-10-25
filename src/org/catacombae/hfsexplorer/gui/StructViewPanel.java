/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * StructViewPanel.java
 *
 * Created on 2008-okt-25, 02:56:49
 */

package org.catacombae.hfsexplorer.gui;

import org.catacombae.csjc.structelements.Dictionary;
import org.catacombae.csjc.structelements.Array;

/**
 *
 * @author erik
 */
public class StructViewPanel extends javax.swing.JPanel {

    public StructViewPanel(String label, Dictionary dict) {
        this();
        containerPanel.add(new InternalStructViewPanel(label, dict));
    }
    public StructViewPanel(String label, Array array) {
        this();
        containerPanel.add(new InternalStructViewPanel(label, array));
    }
    
    /** Creates new form StructViewPanel */
    private StructViewPanel() {
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

        containerPanel = new javax.swing.JPanel();

        containerPanel.setLayout(new java.awt.BorderLayout());

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(containerPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(containerPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel containerPanel;
    // End of variables declaration//GEN-END:variables

}
