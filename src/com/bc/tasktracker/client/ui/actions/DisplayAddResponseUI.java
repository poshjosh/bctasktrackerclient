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
import com.bc.jpa.dao.Dao;
import com.bc.tasktracker.jpa.entities.master.Task;
import com.bc.tasktracker.jpa.entities.master.Task_;
import com.bc.tasktracker.client.ui.TaskResponsePanel;
import java.util.List;
import java.util.Map;
import com.bc.appcore.actions.Action;
import com.bc.appbase.App;
import com.bc.tasktracker.client.TasktrackerApp;
import com.bc.tasktracker.client.functions.SetSelectedAppointment;
import com.bc.tasktracker.client.ui.MessageFrame;
import com.bc.tasktracker.jpa.entities.master.Appointment;
import javax.swing.ComboBoxModel;

/**
 * @author Chinomso Bassey Ikwuagwu on Feb 11, 2017 2:41:04 PM
 */
public class DisplayAddResponseUI implements Action<App,Object> {
    
    @Override
    public Object execute(final App app, final Map<String, Object> params) throws TaskExecutionException {
        
        final List taskidList = (List)params.get(Task_.taskid.getName() + "List");
        try(Dao dao = app.getActivePersistenceUnitContext().getDao()) {
            for(Object taskid : taskidList) {
                final Task task = dao.find(Task.class, taskid);
                this.execute(((TasktrackerApp)app), task);
            }
        }
        return Boolean.TRUE;
    }

    public MessageFrame<TaskResponsePanel> execute(TasktrackerApp app, final Task task) 
            throws TaskExecutionException {
        
        final MessageFrame<TaskResponsePanel> frame = app.getUIContext().createTaskResponseFrame();

        final TaskResponsePanel panel = frame.getMessageComponent();

        panel.getTaskidLabel().setText(String.valueOf(task.getTaskid()));

        StringBuilder html = new StringBuilder();
        html.append("<html>");
        html.append("Subject: ").append(task.getDoc().getSubject()).append("<br/>");
        html.append("Task: ").append(task.getDescription()).append("<br/>");
        html.append("</html>");
        frame.getMessageLabel().setText(html.toString());

        this.beforeDisplay(app, frame);

        frame.pack();

        frame.setVisible(true);
        
        return frame;
    }
    
    public void beforeDisplay(TasktrackerApp app, 
            final MessageFrame<TaskResponsePanel> frame) 
            throws TaskExecutionException {
        frame.getTitleLabel().setText(this.getTitle());
        final Appointment userAppt = this.getAppointment(app);
        if(userAppt != null) {
            final ComboBoxModel<String> model = frame.getMessageComponent().getAuthorCombobox().getModel();
            new SetSelectedAppointment().accept(model, userAppt);
        }
    }
    
    public String getTitle() {
        return "Add Response to Task";
    }
    
    public Appointment getAppointment(TasktrackerApp app) throws TaskExecutionException {
        final Appointment userAppt = app.getUserAppointment(null);
        if(userAppt == null) {
            throw new TaskExecutionException("You must be logged-in to perform the requested operation");
        }
        return userAppt;
    }
}
