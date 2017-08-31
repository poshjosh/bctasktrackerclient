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

package com.bc.tasktracker.client.jpa.model;

import java.util.logging.Level;
import javax.swing.JOptionPane;
import com.bc.tasktracker.client.TasktrackerApp;
import com.bc.tasktracker.jpa.model.TasktrackerCoreResultModel;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 8, 2017 1:17:28 PM
 */
public class TasktrackerResultModel<T> extends TasktrackerCoreResultModel<T> {

    private static final Logger logger = Logger.getLogger(TasktrackerResultModel.class.getName());

    public TasktrackerResultModel(TasktrackerApp app, Class<T> coreEntityType, List<String> columnNames, int serialColumnIndex) {
        super(app, coreEntityType, columnNames, serialColumnIndex);
    }
    
    @Override
    public void update(Object entity, String entityColumn, Object target, String targetColumn, Object targetValue) {

        final int response = JOptionPane.showConfirmDialog(
                this.getApp().getUIContext().getMainFrame(), "Update: "+targetColumn+'?', 
                "Update?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if(response == JOptionPane.YES_OPTION) {
            try{

                super.update(entity, entityColumn, target, targetColumn, targetValue);
                    
                this.getApp().updateReports(true);
                
            }catch(RuntimeException e) {
                final String msg = "Error updating "+targetColumn;
                logger.log(Level.WARNING, msg, e);
                this.getApp().getUIContext().showErrorMessage(e, msg);
            }
        }
    }
    
    @Override
    public TasktrackerApp getApp() {
        return (TasktrackerApp)super.getApp(); 
    }
}