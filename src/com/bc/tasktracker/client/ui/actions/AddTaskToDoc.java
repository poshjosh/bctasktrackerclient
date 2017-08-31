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
import com.bc.tasktracker.jpa.entities.master.Task;
import com.bc.tasktracker.client.ui.TaskPanel;
import java.util.Map;
import com.bc.appcore.actions.Action;
import com.bc.appbase.App;
import com.bc.appcore.parameter.ParameterException;
import com.bc.tasktracker.client.TasktrackerApp;

/**
 * @author Chinomso Bassey Ikwuagwu on Feb 12, 2017 4:04:42 PM
 */
public class AddTaskToDoc implements Action<App,Task> {

    @Override
    public Task execute(App app, Map<String, Object> params) 
            throws ParameterException, TaskExecutionException {
        
        final Task task = (Task)app.getAction(TasktrackerActionCommands.ADD_TASK).execute(app, params);
        
        final TaskPanel taskPanel = ((TasktrackerApp)app).getUIContext().getTaskFrame().getTaskPanel();
        
        taskPanel.getTaskTextArea().setText(null);
        taskPanel.getResponsiblityCombobox().setSelectedIndex(0);
        taskPanel.getDocidLabel().setText(String.valueOf(task.getDoc().getDocid()));
        
        return task;
    }
}
