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
import java.util.Map;
import com.bc.appbase.App;
import com.bc.appbase.ui.actions.ActionCommands;
import com.bc.appbase.ui.actions.DeleteSelectedRecords;
import com.bc.appbase.ui.actions.ParamNames;
import com.bc.appcore.parameter.ParameterException;
import com.bc.tasktracker.jpa.entities.master.Task;
import java.util.HashMap;
import com.bc.tasktracker.client.TasktrackerApp;

/**
 * @author Chinomso Bassey Ikwuagwu on Feb 11, 2017 3:29:19 PM
 */
public class DeleteTask extends DeleteSelectedRecords {

    @Override
    public Boolean execute(App app, Map<String, Object> params) 
            throws ParameterException, TaskExecutionException {
        
        params = new HashMap(params);
        params.put(ParamNames.ENTITY_TYPE, Task.class);
        
        final Boolean success = super.execute(app, params);
        
        if(success) {
            
            app.getAction(ActionCommands.REFRESH_ALL_RESULTS).executeSilently(app);
            
            ((TasktrackerApp)app).updateReports();
        }
        
        return success;
    }
}
