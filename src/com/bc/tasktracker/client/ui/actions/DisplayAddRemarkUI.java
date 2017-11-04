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

package com.bc.tasktracker.client.ui.actions;

import com.bc.appcore.exceptions.TaskExecutionException;
import javax.swing.JComboBox;
import com.bc.tasktracker.client.TasktrackerApp;
import com.bc.tasktracker.client.ui.MessageFrame;
import com.bc.tasktracker.client.ui.TaskResponsePanel;
import com.bc.tasktracker.jpa.entities.master.Appointment;

/**
 * @author Chinomso Bassey Ikwuagwu on Mar 11, 2017 2:31:48 PM
 */
public class DisplayAddRemarkUI extends DisplayAddResponseUI {

    @Override
    public void beforeDisplay(TasktrackerApp app, final MessageFrame<TaskResponsePanel> frame) 
            throws TaskExecutionException {
        super.beforeDisplay(app, frame);
        final JComboBox cb = frame.getMessageComponent().getAuthorCombobox();
        cb.setEnabled(false);
    }

    @Override
    public Appointment getAppointment(TasktrackerApp app) {
        return app.getApexAppointment();
    }

    @Override
    public String getTitle() {
        return "Add Remark";
    }
}
