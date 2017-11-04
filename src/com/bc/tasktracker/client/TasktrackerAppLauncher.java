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

import com.bc.appbase.AppLauncher;
import com.bc.appcore.jpa.JpaContextManager;
import com.bc.tasktracker.jpa.SlaveUpdateListenerImpl;
import java.util.logging.Logger;

/**
 * @author Chinomso Bassey Ikwuagwu on May 5, 2017 9:01:02 PM
 */
public class TasktrackerAppLauncher extends AppLauncher<TasktrackerApp> {

    private static final Logger logger = Logger.getLogger(TasktrackerAppLauncher.class.getName());

    public TasktrackerAppLauncher() { }
    
    @Override
    public JpaContextManager getJpaContextManager() {
        return new TasktrackerJpaContextManager(
                this.getClassLoader(), this.getUiContext(),
                this.getPropertiesContext(), this.getMasterPersistenceUnitTest()
        );
    }

    @Override
    public void onLaunchCompleted(TasktrackerApp app) {
 
        SlaveUpdateListenerImpl.setEntityFilter((entity) -> app.isUsingMasterDatabase());
        SlaveUpdateListenerImpl.setPendingUpdatesManager(app.getPendingSlaveUpdatesManager());
        
//        if(this.isProductionMode()) {
            ((TasktrackerApp)app).updateReports();
//        }
    }
}
