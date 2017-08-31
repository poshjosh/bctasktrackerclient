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

package com.bc.tasktracker.client;

import com.bc.tasktracker.jpa.entities.master.Appointment;
import java.util.List;
import com.bc.appbase.App;
import com.bc.tasktracker.TasktrackerAppCore;
import com.bc.tasktracker.client.ui.TasktrackerUIContext;

/**
 * @author Chinomso Bassey Ikwuagwu on Feb 7, 2017 11:10:58 PM
 */
public interface TasktrackerApp extends TasktrackerAppCore, App {
    
    @Override
    public TasktrackerUIContext getUIContext();
    
    default void updateReports(boolean refreshDisplay) {
        final List<Appointment> branchChiefs = this.getTopAppointments();
        this.updateReports(branchChiefs, refreshDisplay);
    }
    
    void updateReports(List<Appointment> appointmentList, boolean refreshDisplay);
}
