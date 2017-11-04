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

import com.bc.appbase.ui.DatePanel;
import com.bc.appbase.ui.DateUIUpdater;
import com.bc.tasktracker.ConfigNames;
import java.util.Calendar;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import com.bc.tasktracker.client.ui.actions.TasktrackerActionCommands;
import com.bc.tasktracker.client.TasktrackerApp;

/**
 * @author Josh
 */
public class TaskResponsePanel extends javax.swing.JPanel {

    public TaskResponsePanel() {
        this(null);
    }
    
    public TaskResponsePanel(TasktrackerApp app) {
        initComponents();
        if(app != null) {
            this.init(app);
        }
    }

    public void reset(TasktrackerApp app) {
        this.setToDefaults(app);
    }
    
    public void init(TasktrackerApp app) {
        
        final String [] values = app.getAppointmentNames();
        
        this.getAuthorCombobox().setModel(new DefaultComboBoxModel<>(values));
        
        this.getAuthorCombobox().setEnabled(app.getApexAppointment().equals(app.getUserAppointment(null)));
        
        this.getAddresponseButton().setActionCommand(TasktrackerActionCommands.ADD_TASKRESPONSE);
        
        app.getUIContext().addActionListeners(
                this,
                this.getAddresponseButton());
        
        this.setToDefaults(app);
    }
    
    public void setToDefaults(TasktrackerApp app) {

        this.getTaskidLabel().setText(null);
        
        this.getResponseTextArea().setText(null);
        
        this.getAuthorCombobox().setSelectedIndex(0);
        
        final DateUIUpdater updater = app.getOrException(DateUIUpdater.class);
        
        final Calendar cal = app.getCalendar();
        
        final int deadlineHours = app.getConfig().getInt(ConfigNames.DEFAULT_DEADLINE_EXTENSION_HOURS, 6);
        cal.add(Calendar.HOUR_OF_DAY, deadlineHours);
        
        final DatePanel deadlinePanel = this.getDeadlinePanel();
        
//        updater.updateField(deadlinePanel.getDayTextfield(), cal, Calendar.DAY_OF_MONTH);
        updater.updateMonth(deadlinePanel.getMonthCombobox(), cal);
        updater.updateYear(deadlinePanel.getYearCombobox(), cal);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        formNoteLabel = new javax.swing.JLabel();
        authorCombobox = new javax.swing.JComboBox<>();
        responseLabel = new javax.swing.JLabel();
        authorLabel = new javax.swing.JLabel();
        deadlineLabel = new javax.swing.JLabel();
        addresponseButton = new javax.swing.JButton();
        taskidLabel = new javax.swing.JLabel();
        responseTextAreaScrollPane = new javax.swing.JScrollPane();
        responseTextArea = new javax.swing.JTextArea();
        deadlinePanel = new com.bc.appbase.ui.DatePanel();

        setPreferredSize(new java.awt.Dimension(600, 400));

        formNoteLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        formNoteLabel.setText("<html>Asterixed (<span style=\"color:red\">*</span>) fields are mandatory</html>");

        authorCombobox.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        authorCombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        responseLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        responseLabel.setText("<html><span style=\"color:red\">Response *</span></html>");

        authorLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        authorLabel.setText("<html><span style=\"color:red\">Author *</span></html>");

        deadlineLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        deadlineLabel.setText("Deadline");

        addresponseButton.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        addresponseButton.setText("Add Response");
        addresponseButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        taskidLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        taskidLabel.setText("ID");

        responseTextArea.setColumns(20);
        responseTextArea.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        responseTextArea.setRows(5);
        responseTextAreaScrollPane.setViewportView(responseTextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(formNoteLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(taskidLabel)
                        .addGap(13, 13, 13))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(responseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(responseTextAreaScrollPane))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(authorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(authorCombobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addresponseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(deadlineLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(deadlinePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 180, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(formNoteLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(taskidLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(responseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(responseTextAreaScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(authorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(authorCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(deadlineLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deadlinePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(addresponseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addresponseButton;
    private javax.swing.JComboBox<String> authorCombobox;
    private javax.swing.JLabel authorLabel;
    private javax.swing.JLabel deadlineLabel;
    private com.bc.appbase.ui.DatePanel deadlinePanel;
    private javax.swing.JLabel formNoteLabel;
    private javax.swing.JLabel responseLabel;
    private javax.swing.JTextArea responseTextArea;
    private javax.swing.JScrollPane responseTextAreaScrollPane;
    private javax.swing.JLabel taskidLabel;
    // End of variables declaration//GEN-END:variables

    public JButton getAddresponseButton() {
        return addresponseButton;
    }

    public JComboBox<String> getAuthorCombobox() {
        return authorCombobox;
    }

    public JLabel getAuthorLabel() {
        return authorLabel;
    }

    public DatePanel getDeadlinePanel() {
        return deadlinePanel;
    }

    public JLabel getDeadlineLabel() {
        return deadlineLabel;
    }

    public JLabel getFormNoteLabel() {
        return formNoteLabel;
    }

    public JLabel getResponseLabel() {
        return responseLabel;
    }

    public JTextArea getResponseTextArea() {
        return responseTextArea;
    }

    public JScrollPane getResponseTextAreaScrollPane() {
        return responseTextAreaScrollPane;
    }

    public JLabel getTaskidLabel() {
        return taskidLabel;
    }
}
