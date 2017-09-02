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
import com.bc.appbase.ui.MainFrame;
import com.bc.appbase.ui.SearchResultsPanel;
import com.bc.appbase.ui.UIContext;
import com.bc.appcore.exceptions.TaskExecutionException;
import com.bc.appcore.parameter.ParameterException;
import com.bc.config.Config;
import com.bc.util.Util;
import com.bc.tasktracker.jpa.entities.master.Task;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import com.bc.tasktracker.client.ui.actions.TasktrackerActionCommands;
import com.bc.appcore.jpa.JpaContextManager;
import com.bc.tasktracker.ConfigNames;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author Chinomso Bassey Ikwuagwu on May 5, 2017 9:01:02 PM
 */
public class TasktrackerAppLauncher extends AppLauncher<TasktrackerApp> {

    private static final Logger logger = Logger.getLogger(TasktrackerAppLauncher.class.getName());

    private ExecutorService scheduleDeadlineTasksReminderService;
    
    private Set<String> sqlInsertResources;

    public TasktrackerAppLauncher() {
        this.sqlInsertResources = Collections.EMPTY_SET;
        this.entityType(Task.class).processLogUIForTitle("HQ NAF TASK TRACKER");
    }
    
    public TasktrackerAppLauncher sqlInsertResources(Set<String> resources) {
        this.sqlInsertResources = resources;
        return this;
    }

    @Override
    public JpaContextManager getJpaContextManager() {
        return new TasktrackerJpaContextManager(
                this.getClassLoader(), this.getUiContext(),
                this.getPropertiesContext(), this.getMasterPersistenceUnitTest(),
                this.sqlInsertResources
        );
    }

    @Override
    public void onShutdown(TasktrackerApp app) {
        Util.shutdownAndAwaitTermination(scheduleDeadlineTasksReminderService, 1, TimeUnit.SECONDS);
    }

    @Override
    public void onLaunchCompleted(TasktrackerApp app) {
        
        final Map<String, Object> params = new HashMap<>();
        final Config config = app.getConfig();
        params.put(ConfigNames.DEADLINE_HOURS, config.getInt(ConfigNames.DEADLINE_HOURS));
        params.put(ConfigNames.DEADLINE_REMINDER_INTERVAL_HOURS, config.getInt(ConfigNames.DEADLINE_REMINDER_INTERVAL_HOURS));

        try{
            this.scheduleDeadlineTasksReminderService = 
                    (ExecutorService)app.getAction(TasktrackerActionCommands.SCHEDULE_DEADLINE_TASKS_REMINDER).execute(app, params);
        }catch(ParameterException | TaskExecutionException e) {
            throw new RuntimeException(e);
        }

        if(this.isProductionMode()) {
            ((TasktrackerApp)app).updateReports(false);
        }
    }

    @Override
    public void configureUI(UIContext uiContext, Config config) {
        
        final MainFrame mainFrame = (MainFrame)uiContext.getMainFrame();

        final SearchResultsPanel resultsPanel = mainFrame.getSearchResultsPanel();

        resultsPanel.getAddButton().setActionCommand(TasktrackerActionCommands.DISPLAY_ADD_TASK_UI);
        uiContext.addActionListeners(resultsPanel, resultsPanel.getAddButton());
    }

    public Set<String> getSqlInsertResources() {
        return Collections.unmodifiableSet(sqlInsertResources);
    }

    public ExecutorService getScheduleDeadlineTasksReminderService() {
        return scheduleDeadlineTasksReminderService;
    }
}
