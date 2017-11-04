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
import com.bc.tasktracker.jpa.entities.master.Doc_;
import com.bc.tasktracker.jpa.entities.master.Task_;
import com.bc.appbase.ui.DateFromUIBuilder;
import com.bc.tasktracker.client.ui.TaskPanel;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.bc.appcore.AppCore;

/**
 * @author Chinomso Bassey Ikwuagwu on Feb 9, 2017 12:10:53 AM
 */
public class AddTaskParametersBuilder implements ParametersBuilder<TaskPanel> {

    private AppCore app;
    
    private TaskPanel newTaskPanel;
    
    @Override
    public ParametersBuilder<TaskPanel> context(AppCore app) {
        this.app = app;
        return this;
    }

    @Override
    public ParametersBuilder<TaskPanel> with(TaskPanel newTaskPanel) {
        this.newTaskPanel = newTaskPanel;
        return this;
    }
    
    @Override
    public Map<String, Object> build() throws ParameterException {
        
        final Logger logger = Logger.getLogger(this.getClass().getName());
        
        final Map<String, Object> params = new HashMap();
        
        final String text = newTaskPanel.getDocidLabel().getText();
        if(!this.isNullOrEmpty(text)) {
            try{
                logger.log(Level.FINER, "docid: {0}", text);
                params.put(Doc_.docid.getName(), Integer.valueOf(text));
            }catch(NumberFormatException ignored) { }
        }
        
        final String referencenumber = newTaskPanel.getReferencenumberTextfield().getText();
        if(!this.isNullOrEmpty(referencenumber)) {
            params.put(Doc_.referencenumber.getName(), referencenumber);
        }
        
        final String subject = newTaskPanel.getSubjectTextfield().getText();
        this.addRequiredParam(params, Doc_.subject.getName(), subject);
        
        final String description = newTaskPanel.getTaskTextArea().getText();
        this.addRequiredParam(params, Task_.description.getName(), description);

        final Object author = newTaskPanel.getAuthorCombobox().getSelectedItem();
        this.addRequiredParam(params, Task_.author.getName(), author);
        
        final Object responsibility = newTaskPanel.getResponsiblityCombobox().getSelectedItem();
        this.addRequiredParam(params, Task_.reponsibility.getName(), responsibility);
        
        final DateFromUIBuilder builder = app.getOrException(DateFromUIBuilder.class);
        
        final Calendar cal = app.getCalendar();
        
        final Date datesigned = builder.calendar(cal)
                .defaultHousrs(00)
                .hoursTextField(null)
                .defaultMinutes(00)
                .minutesTextField(null)
                .dayTextField(newTaskPanel.getDatesignedDatePanel().getDayTextfield())
                .monthComboBox(newTaskPanel.getDatesignedDatePanel().getMonthCombobox())
                .yearComboBox(newTaskPanel.getDatesignedDatePanel().getYearCombobox())
                .build(null);
        if(datesigned != null) {
            params.put(Doc_.datesigned.getName(), datesigned);
        }
        
        
        final Date timeopened = builder.calendar(cal)
                .defaultHousrs(00)
                .hoursTextField(null)
                .defaultMinutes(00)
                .minutesTextField(null)
                .dayTextField(newTaskPanel.getTimeopenedDatePanel().getDayTextfield())
                .monthComboBox(newTaskPanel.getTimeopenedDatePanel().getMonthCombobox())
                .yearComboBox(newTaskPanel.getTimeopenedDatePanel().getYearCombobox())
                .build(null);
        if(timeopened != null) {
            params.put(Task_.timeopened.getName(), timeopened);
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
    
    private boolean isNullOrEmpty(Object obj) {
        return obj == null || "".equals(obj);
    }
}
