/*-
 * Copyright (C) 2007-2008 Erik Larsson
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catacombae.hfsexplorer.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.io.PrintStream;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;
import org.catacombae.csjc.PrintableStruct;
import org.catacombae.csjc.StructElements;
import org.catacombae.csjc.structelements.Dictionary;
import org.catacombae.hfsexplorer.FileSystemBrowser.NoLeafMutableTreeNode;
import org.catacombae.hfsexplorer.io.JTextAreaOutputStream;
import org.catacombae.hfs.types.hfscommon.CommonHFSExtentIndexNode;
import org.catacombae.hfs.types.hfscommon.CommonHFSExtentKey;
import org.catacombae.hfs.types.hfscommon.CommonHFSExtentLeafNode;
import org.catacombae.hfs.types.hfscommon.CommonHFSExtentLeafRecord;
import org.catacombae.hfs.types.hfscommon.CommonBTIndexRecord;
import org.catacombae.hfs.types.hfscommon.CommonBTNode;
import org.catacombae.hfs.types.hfscommon.CommonBTNodeDescriptor;
import org.catacombae.hfs.HFSVolume;

/**
 *
 * @author  Erik
 */
public class ExtentsInfoPanel extends javax.swing.JPanel {

    private static final int UNIT_INCREMENT = 10;

    /** Creates new form CatalogInfoPanel */
    public ExtentsInfoPanel(final HFSVolume fsView) {

        initComponents();

        JTree dirTree = catalogTree;
        // Populate the root
	/*
         * What we need is a method that gets us the children of the "current" node.
         * A B-tree starts with a header node,
         */
        CommonBTNode iNode = fsView.getExtentsOverflowFile().getExtentsOverflowNode(-1); // Get root index node.

        if(iNode == null) {
            DefaultTreeModel model = new DefaultTreeModel(new NoLeafMutableTreeNode("<empty>"));
            dirTree.setModel(model);
            return;
        }

        DefaultMutableTreeNode rootNode = new NoLeafMutableTreeNode(new BTNodeStorage(iNode, "Extents overflow root"));
        expandNode(rootNode, iNode, fsView);

        DefaultTreeModel model = new DefaultTreeModel(rootNode);
        dirTree.setModel(model);

        dirTree.addTreeWillExpandListener(new TreeWillExpandListener() {

            /* @Override */
            public void treeWillExpand(TreeExpansionEvent e)
                    throws ExpandVetoException {

                TreePath tp = e.getPath();
                Object obj = tp.getLastPathComponent();
                if(obj instanceof DefaultMutableTreeNode) {
                    DefaultMutableTreeNode dmtn = ((DefaultMutableTreeNode) obj);
                    Object obj2 = dmtn.getUserObject();
                    if(obj2 instanceof BTNodeStorage) {
                        CommonBTNode node = ((BTNodeStorage) obj2).getNode();
                        expandNode(dmtn, node, fsView);
                    }
                    else
                        throw new RuntimeException("Wrong user object type in expandable node!");
                }
                else
                    throw new RuntimeException("Wrong node type in tree!");
            }

            /* @Override */
            public void treeWillCollapse(TreeExpansionEvent e) {
            }
        });

        final String INDEX_NAME = "index";
        final String LEAF_NAME = "leaf";
        final String PRINT_FIELDS_AREA_NAME = "printfieldsarea";
        final String OTHER_NAME = "other";
        final String FILE_NAME = "file";
        final String FOLDER_NAME = "folder";
        final String STRUCT_VIEW_PANEL_NAME = "structview";

        final CardLayout clRoot = new CardLayout();
        final JPanel leafPanel = new JPanel();

        final CardLayout clLeaf = new CardLayout();
        leafPanel.setLayout(clLeaf);

        leafPanel.add(new JLabel("INTERNAL ERROR!", SwingConstants.CENTER), OTHER_NAME);

        final JScrollPane structViewPanelScroller = new JScrollPane();
        structViewPanelScroller.getVerticalScrollBar().setUnitIncrement(UNIT_INCREMENT);
        structViewPanelScroller.getHorizontalScrollBar().setUnitIncrement(UNIT_INCREMENT);
        leafPanel.add(structViewPanelScroller, STRUCT_VIEW_PANEL_NAME);

        final LeafInfoPanel fileInfoPanelHeader = new LeafInfoPanel();
        final FileInfoPanel fileInfoPanel = new FileInfoPanel();
        final JPanel fileInfoPanelContainer = new JPanel(new BorderLayout());
        fileInfoPanelContainer.add(fileInfoPanelHeader, BorderLayout.NORTH);
        fileInfoPanelContainer.add(fileInfoPanel, BorderLayout.CENTER);

        JScrollPane fileInfoPanelScroller =
                new JScrollPane(fileInfoPanelContainer);
        fileInfoPanelScroller.getVerticalScrollBar().setUnitIncrement(UNIT_INCREMENT);
        fileInfoPanelScroller.getHorizontalScrollBar().setUnitIncrement(UNIT_INCREMENT);
        leafPanel.add(fileInfoPanelScroller, FILE_NAME);

        final LeafInfoPanel folderInfoPanelHeader = new LeafInfoPanel();
        final FolderInfoPanel folderInfoPanel = new FolderInfoPanel();
        final JPanel folderInfoPanelContainer = new JPanel(new BorderLayout());
        folderInfoPanelContainer.add(folderInfoPanelHeader, BorderLayout.NORTH);
        folderInfoPanelContainer.add(folderInfoPanel, BorderLayout.CENTER);

        JScrollPane folderInfoPanelScroller =
                new JScrollPane(folderInfoPanelContainer);
        folderInfoPanelScroller.getVerticalScrollBar().setUnitIncrement(UNIT_INCREMENT);
        folderInfoPanelScroller.getHorizontalScrollBar().setUnitIncrement(UNIT_INCREMENT);
        leafPanel.add(folderInfoPanelScroller, FOLDER_NAME);

        final LeafInfoPanel printFieldsTextAreaHeader = new LeafInfoPanel();
        final JTextArea printFieldsTextArea = new JTextArea(0, 0);
        printFieldsTextArea.setEditable(false);
        printFieldsTextArea.setLineWrap(false);
        final JPanel printFieldsTextAreaContainer =
                new JPanel(new BorderLayout());
        printFieldsTextAreaContainer.add(printFieldsTextAreaHeader,
                BorderLayout.NORTH);
        printFieldsTextAreaContainer.add(printFieldsTextArea,
                BorderLayout.CENTER);

        JScrollPane printFieldsTextAreaScroller =
                new JScrollPane(printFieldsTextAreaContainer);
        printFieldsTextAreaScroller.getVerticalScrollBar().setUnitIncrement(UNIT_INCREMENT);
        printFieldsTextAreaScroller.getHorizontalScrollBar().setUnitIncrement(UNIT_INCREMENT);
        leafPanel.add(printFieldsTextAreaScroller, PRINT_FIELDS_AREA_NAME);

        infoPanel.setLayout(clRoot);
        final JLabel indexNodeLabel = new JLabel("No selection.", SwingConstants.CENTER);
        infoPanel.add(indexNodeLabel, INDEX_NAME);
        infoPanel.add(leafPanel, LEAF_NAME);
        //infoScroller.setViewportView(infoPanel);

        catalogTree.addTreeSelectionListener(new TreeSelectionListener() {

            /* @Override */
            public void valueChanged(TreeSelectionEvent te) {
                //System.err.println("Tree selection");
                Object o = te.getPath().getLastPathComponent();
                if(o instanceof DefaultMutableTreeNode) {
                    Object o2 = ((DefaultMutableTreeNode) o).getUserObject();
                    if(o2 instanceof BTNodeStorage) {
                        CommonBTNode btn = ((BTNodeStorage) o2).getNode();
                        CommonBTNodeDescriptor btnd = btn.getNodeDescriptor();
                        switch(btnd.getNodeType()) {
                            case INDEX:
                                indexNodeLabel.setText("Index node with " +
                                        btnd.getNumberOfRecords() + " records.");
                                break;
                            case LEAF:
                                indexNodeLabel.setText("Leaf node with " +
                                        btnd.getNumberOfRecords() + " records.");
                                break;
                            default:
                                indexNodeLabel.setText("Unknown error!");
                        }

                        clRoot.show(infoPanel, INDEX_NAME);
                    }
                    else if(o2 instanceof ExtentLeafStorage) {
                        ExtentLeafStorage leafStorage = (ExtentLeafStorage) o2;
                        CommonHFSExtentLeafRecord rec = leafStorage.getRecord();
                        //HFSPlusCatalogLeafRecordData data = rec.getData();
                        if(rec instanceof StructElements) {
                            Dictionary dict = ((StructElements) rec).getStructElements();
                            String label = dict.getTypeDescription();
                            if(label == null)
                                label = dict.getTypeName();

                            final LeafInfoPanel leafInfoPanel =
                                    new LeafInfoPanel();
                            leafInfoPanel.setRecordNumber(
                                    leafStorage.getRecordNumber());
                            leafInfoPanel.setRecordOffset(
                                    leafStorage.getRecordOffset());
                            leafInfoPanel.setRecordSize(
                                    leafStorage.getRecordSize());

                            final JPanel containerPanel =
                                    new JPanel(new BorderLayout());
                            containerPanel.add(leafInfoPanel, BorderLayout.NORTH);
                            containerPanel.add(
                                    new StructViewPanel(label + ":", dict),
                                    BorderLayout.CENTER);

                            structViewPanelScroller.setViewportView(
                                    containerPanel);
                            clLeaf.show(leafPanel, STRUCT_VIEW_PANEL_NAME);
                        }
                        else if(rec instanceof PrintableStruct) {
                            PrintStream ps = new PrintStream(new JTextAreaOutputStream(System.err, printFieldsTextArea));
                            printFieldsTextAreaHeader.setRecordNumber(
                                    leafStorage.getRecordNumber());
                            printFieldsTextAreaHeader.setRecordOffset(
                                    leafStorage.getRecordOffset());
                            printFieldsTextAreaHeader.setRecordSize(
                                    leafStorage.getRecordSize());
                            printFieldsTextArea.setText("");
                            ((PrintableStruct) rec).print(ps, "");
                            ps.close();
                            printFieldsTextArea.setCaretPosition(0);
                            clLeaf.show(leafPanel, PRINT_FIELDS_AREA_NAME);

                        }
                        else {
                            System.err.println("CatalogInfoPanel: Could not show record type " + rec.getClass());
                            clLeaf.show(leafPanel, OTHER_NAME);
                        }
                        clRoot.show(infoPanel, LEAF_NAME);

                    }
                    else
                        System.err.println("WARNING: unknown type in catalog tree user object - " + o2.getClass().toString());
                }
                else
                    System.err.println("WARNING: unknown type in catalog tree - " + o.getClass().toString());
            }
        });
    }

    public void expandNode(DefaultMutableTreeNode dmtn, CommonBTNode node, HFSVolume fsView) {
        if(node instanceof CommonHFSExtentIndexNode) {
            List<CommonBTIndexRecord<CommonHFSExtentKey>> recs = ((CommonHFSExtentIndexNode) node).getBTRecords();
            for(CommonBTIndexRecord<CommonHFSExtentKey> rec : recs) {

                CommonBTNode curNode = fsView.getExtentsOverflowFile().getExtentsOverflowNode(rec.getIndex());
                CommonHFSExtentKey key = rec.getKey();
                dmtn.add(new NoLeafMutableTreeNode(new BTNodeStorage(curNode,
                        key.getFileID().toLong() + ":" + key.getForkType() +
                        ":" + key.getStartBlock())));
            }
        }
        else if(node instanceof CommonHFSExtentLeafNode) {
            CommonHFSExtentLeafNode leafNode = (CommonHFSExtentLeafNode) node;
            CommonHFSExtentLeafRecord[] recs = leafNode.getLeafRecords();
            int[] recordOffsets = leafNode.getRecordOffsets();

            for(int i = 0; i < recs.length; ++i) {
                final CommonHFSExtentLeafRecord rec = recs[i];
                CommonHFSExtentKey key = rec.getKey();
                dmtn.add(new DefaultMutableTreeNode(new ExtentLeafStorage(i,
                        recordOffsets[i],
                        recordOffsets[i + 1] - recordOffsets[i], rec,
                        key.getFileID().toLong() + ":" + key.getForkType() +
                        ":" + key.getStartBlock())));
            }
        }
        else
            throw new RuntimeException("Invalid node type in tree: " + node);
    }

    private static class BTNodeStorage {

        private CommonBTNode node;
        private String text;

        public BTNodeStorage(CommonBTNode node, String text) {
            this.node = node;
            this.text = text;
        }

        public CommonBTNode getNode() {
            return node;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    private static class ExtentLeafStorage {

        private final int recordNumber;
        private final int recordOffset;
        private final int recordSize;
        private CommonHFSExtentLeafRecord rec;
        private String text;

        public ExtentLeafStorage(int recordNumber, int recordOffset,
                int recordSize, CommonHFSExtentLeafRecord rec, String text)
        {
            this.recordNumber = recordNumber;
            this.recordOffset = recordOffset;
            this.recordSize = recordSize;
            this.rec = rec;
            this.text = text;
        }

        public int getRecordNumber() {
            return recordNumber;
        }

        public int getRecordOffset() {
            return recordOffset;
        }

        public int getRecordSize() {
            return recordSize;
        }

        public CommonHFSExtentLeafRecord getRecord() {
            return rec;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        catalogTreeScroller = new javax.swing.JScrollPane();
        catalogTree = new javax.swing.JTree();
        infoPanel = new javax.swing.JPanel();
        descriptionLabel = new javax.swing.JLabel();

        jSplitPane1.setDividerLocation(330);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(1.0);

        catalogTreeScroller.setViewportView(catalogTree);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(catalogTreeScroller, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, catalogTreeScroller, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(jPanel1);

        infoPanel.setPreferredSize(new java.awt.Dimension(100, 140));

        org.jdesktop.layout.GroupLayout infoPanelLayout = new org.jdesktop.layout.GroupLayout(infoPanel);
        infoPanel.setLayout(infoPanelLayout);
        infoPanelLayout.setHorizontalGroup(
            infoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 359, Short.MAX_VALUE)
        );
        infoPanelLayout.setVerticalGroup(
            infoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 101, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(infoPanel);

        descriptionLabel.setText("View of the extent overflow file's B*-tree:");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                    .add(descriptionLabel))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(descriptionLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTree catalogTree;
    private javax.swing.JScrollPane catalogTreeScroller;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSplitPane jSplitPane1;
    // End of variables declaration//GEN-END:variables
}
