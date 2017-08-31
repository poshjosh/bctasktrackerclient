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

import com.bc.appbase.ui.actions.ActionCommands;
import com.bc.appcore.AppCore;
import com.bc.appcore.exceptions.TaskExecutionException;
import com.bc.appcore.parameter.ParameterException;
import com.bc.tasktracker.jpa.entities.master.Appointment;
import java.util.Map;
import com.bc.appcore.User;

/**
 * @author Chinomso Bassey Ikwuagwu on Jul 31, 2017 7:54:24 PM
 */
public class GetAppointmentForUser extends com.bc.tasktracker.actions.GetAppointmentForUser {

    @Override
    public Appointment execute(AppCore app, Map<String, Object> params) 
            throws ParameterException, TaskExecutionException {

        final User user = app.getUser();
        
        if(!user.isLoggedIn()) {
            
            app.getAction(ActionCommands.LOGIN_VIA_USER_PROMPT).execute(app);
            
            if(!user.isLoggedIn()) {
                
                return null;
            }
        }
        
        return super.execute(app, params);
    }
}
