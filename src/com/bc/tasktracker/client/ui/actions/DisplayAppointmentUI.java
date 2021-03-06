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
import com.bc.tasktracker.client.ui.AppointmentPanel;
import java.util.Map;
import javax.swing.JFrame;
import com.bc.appcore.actions.Action;
import com.bc.appbase.App;
import com.bc.tasktracker.client.TasktrackerApp;

/**
 * @author Chinomso Bassey Ikwuagwu on Feb 26, 2017 10:23:56 PM
 */
public class DisplayAppointmentUI implements Action<App,AppointmentPanel> {
    
    @Override
    public AppointmentPanel execute(final App app, final Map<String, Object> params) throws TaskExecutionException {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        AppointmentPanel apptPanel = ((TasktrackerApp)app).getUIContext().createAppointmentPanel();
        frame.getContentPane().add(apptPanel);
        
        frame.pack();
        frame.setVisible(true);
        
        app.getAttributes().put(AppointmentPanel.class.getName(), apptPanel);

        return apptPanel;
    }
}
