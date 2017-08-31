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
import com.bc.appcore.parameter.ParameterException;
import com.bc.tasktracker.jpa.entities.master.Task;
import java.util.Map;
import com.bc.appcore.AppCore;
import com.bc.tasktracker.client.TasktrackerApp;

/**
 * @author Chinomso Bassey Ikwuagwu on Feb 12, 2017 4:07:23 PM
 */
public class AddTask extends com.bc.tasktracker.actions.AddTask {

    @Override
    public Task execute(AppCore appBase, Map<String, Object> params) 
            throws ParameterException, TaskExecutionException {

        final TasktrackerApp app = (TasktrackerApp)appBase;
        
        app.getUIContext().getTaskFrame().getMessageLabel().setText(null);
        
        final Task task = super.execute(appBase, params);
        
        app.updateReports(true);

        app.getUIContext().getTaskFrame().getMessageLabel().setText("Success");
        
        return task;
    }
}    