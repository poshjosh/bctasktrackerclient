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
import com.bc.tasktracker.jpa.entities.master.Taskresponse;
import java.util.Map;
import java.util.logging.Logger;
import com.bc.appcore.AppCore;
import com.bc.tasktracker.client.TasktrackerApp;

/**
 * @author Chinomso Bassey Ikwuagwu on Feb 11, 2017 2:15:30 PM
 */
public class AddTaskresponse extends com.bc.tasktracker.actions.AddTaskresponse {
    
    private transient static final Logger logger = Logger.getLogger(AddTaskresponse.class.getName());

    @Override
    public Taskresponse execute(AppCore app, Map<String, Object> params) 
            throws ParameterException, TaskExecutionException {

        final Taskresponse response = super.execute(app, params);
        
        ((TasktrackerApp)app).updateReports(true);
            
        ((TasktrackerApp)app).getUIContext().showSuccessMessage("Success");
        
        return response;
    }
}