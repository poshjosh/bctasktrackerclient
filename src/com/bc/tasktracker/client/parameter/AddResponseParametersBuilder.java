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

package com.bc.tasktracker.client.parameter;

import com.bc.appcore.parameter.ParameterException;
import com.bc.appcore.parameter.ParameterNotFoundException;
import com.bc.appcore.parameter.ParametersBuilder;
import com.bc.tasktracker.jpa.entities.master.Task_;
import com.bc.tasktracker.jpa.entities.master.Taskresponse_;
import com.bc.appbase.ui.DateFromUIBuilder;
import com.bc.tasktracker.client.ui.TaskResponsePanel;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.bc.appcore.AppCore;

/**
 * @author Chinomso Bassey Ikwuagwu on Feb 11, 2017 1:34:17 PM
 */
public class AddResponseParametersBuilder implements ParametersBuilder<TaskResponsePanel> {

    private AppCore app;
    
    private TaskResponsePanel taskResponsePanel;
    
    @Override
    public ParametersBuilder<TaskResponsePanel> context(AppCore app) {
        this.app = app;
        return this;
    }

    @Override
    public ParametersBuilder<TaskResponsePanel> with(TaskResponsePanel newTaskResponsePanel) {
        this.taskResponsePanel = newTaskResponsePanel;
        return this;
    }
    
    @Override
    public Map<String, Object> build() throws ParameterException {
        
        final Map<String, Object> params = new HashMap();
        
        final String taskidStr = taskResponsePanel.getTaskidLabel().getText();
        params.put(Task_.taskid.getName(), Integer.parseInt(taskidStr));
        
        final String response = taskResponsePanel.getResponseTextArea().getText();
        this.addRequiredParam(params, Taskresponse_.response.getName(), response);
        
        final String author = (String)taskResponsePanel.getAuthorCombobox().getSelectedItem();
        this.addRequiredParam(params, Taskresponse_.author.getName(), author);
        
        final Calendar cal = app.getCalendar();
        
        final DateFromUIBuilder builder = app.getOrException(DateFromUIBuilder.class);
        final Date deadline = builder.calendar(cal)
                .ui(taskResponsePanel.getDeadlinePanel())
                .build(null);
        if(deadline != null) {
            params.put(Taskresponse_.deadline.getName(), deadline);
        }
                
        return params;
    }
    
    private void addRequiredParam(Map<String, Object> params, String name, Object value) 
            throws ParameterNotFoundException {
        if(this.isNullOrEmpty(value)) {
            throw new ParameterNotFoundException(name);
        }else{
            params.put(name, value);
        }
    }
    
    private boolean isNullOrEmpty(Object text) {
        return text == null || "".equals(text);
    }
}

