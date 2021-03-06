/*
 * Copyright 2017 NUROX Ltd.
 *
 * Licensed under the NUROX Ltd Software License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.looseboxes.com/legal/licenses/software.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bc.tasktracker.client.ui;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import com.bc.tasktracker.client.ui.actions.TasktrackerActionCommands;
import com.bc.tasktracker.client.TasktrackerApp;

/**
 *
 * @author Josh
 */
public class UnitPanel extends javax.swing.JPanel {

    /**
     * Creates new form UnitPanel
     */
    public UnitPanel() {
        this(null);
    }

    /**
     * Creates new form UnitPanel and initializes it from
     * @param app
     */
    public UnitPanel(TasktrackerApp app) {
        initComponents();
        if(app != null) {
            this.parentUnitComboBox.setModel(new DefaultComboBoxModel(
                    app.getUnitNames()
            ));
            this.addUnitButton.setActionCommand(TasktrackerActionCommands.ADD_UNIT);
            this.addUnitButton.addActionListener(app.getUIContext().getActionListener(UnitPanel.this, TasktrackerActionCommands.ADD_UNIT));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        abbreviationLabel = new javax.swing.JLabel();
        parentUnitLabel = new javax.swing.JLabel();
        parentUnitExamplesLabel = new javax.swing.JLabel();
        parentUnitComboBox = new javax.swing.JComboBox<>();
        unitNameTextField = new javax.swing.JTextField();
        abbreviationTextField = new javax.swing.JTextField();
        addUnitButton = new javax.swing.JButton();
        unitNameLabel = new javax.swing.JLabel();

        abbreviationLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        abbreviationLabel.setText("Abbreviation");

        parentUnitLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        parentUnitLabel.setText("Parent Unit");

        parentUnitExamplesLabel.setText("CAS Office is parent unit to all Branches, Commands and DRUs. A Branch is parent to all directorates under that Branch");

        parentUnitComboBox.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        parentUnitComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        unitNameTextField.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        abbreviationTextField.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        addUnitButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        addUnitButton.setText("Add Unit");

        unitNameLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        unitNameLabel.setText("Unit Name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(parentUnitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(abbreviationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(unitNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(27, 27, 27)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(unitNameTextField)
                                .addComponent(parentUnitComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(abbreviationTextField)))
                        .addComponent(parentUnitExamplesLabel))
                    .addComponent(addUnitButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(unitNameLabel)
                    .addComponent(unitNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(abbreviationLabel)
                    .addComponent(abbreviationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(parentUnitLabel)
                    .addComponent(parentUnitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(parentUnitExamplesLabel)
                .addGap(18, 18, 18)
                .addComponent(addUnitButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel abbreviationLabel;
    private javax.swing.JTextField abbreviationTextField;
    private javax.swing.JButton addUnitButton;
    private javax.swing.JComboBox<String> parentUnitComboBox;
    private javax.swing.JLabel parentUnitExamplesLabel;
    private javax.swing.JLabel parentUnitLabel;
    private javax.swing.JLabel unitNameLabel;
    private javax.swing.JTextField unitNameTextField;
    // End of variables declaration//GEN-END:variables

    public JLabel getAbbreviationLabel() {
        return abbreviationLabel;
    }

    public JTextField getAbbreviationTextField() {
        return abbreviationTextField;
    }

    public JButton getAddUnitButton() {
        return addUnitButton;
    }

    public JComboBox<String> getParentUnitComboBox() {
        return parentUnitComboBox;
    }

    public JLabel getParentUnitExamplesLabel() {
        return parentUnitExamplesLabel;
    }

    public JLabel getParentUnitLabel() {
        return parentUnitLabel;
    }

    public JLabel getUnitNameLabel() {
        return unitNameLabel;
    }

    public JTextField getUnitNameTextField() {
        return unitNameTextField;
    }
}
