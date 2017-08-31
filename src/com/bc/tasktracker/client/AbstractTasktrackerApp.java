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

import com.bc.appbase.AbstractApp;
import com.bc.appbase.App;
import com.bc.appcore.AppContext;
import com.bc.appbase.parameter.SelectedRecordsParametersBuilder;
import com.bc.appbase.ui.MainFrame;
import com.bc.appbase.ui.SearchResultsPanel;
import com.bc.appbase.ui.UIContext;
import com.bc.appcore.jpa.model.ResultModel;
import com.bc.appcore.parameter.ParametersBuilder;
import com.bc.util.Util;
import com.bc.tasktracker.client.parameter.AddAppointmentParametersBuilder;
import com.bc.tasktracker.client.parameter.AddResponseParametersBuilder;
import com.bc.tasktracker.client.parameter.AddTaskParametersBuilder;
import com.bc.tasktracker.client.parameter.AddUnitParametersBuilder;
import com.bc.tasktracker.client.parameter.SearchParametersBuilder;
import com.bc.tasktracker.client.ui.AppointmentPanel;
import com.bc.tasktracker.client.ui.TasktrackerMainFrame;
import com.bc.tasktracker.client.ui.DtbUIContextImpl;
import com.bc.tasktracker.client.ui.SearchPanel;
import com.bc.tasktracker.client.ui.TaskFrame;
import com.bc.tasktracker.client.ui.TaskPanel;
import com.bc.tasktracker.client.ui.TaskResponsePanel;
import com.bc.tasktracker.client.ui.UnitPanel;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import java.util.concurrent.TimeUnit;
import com.bc.appcore.ObjectFactory;
import com.bc.tasktracker.client.ui.actions.TasktrackerActionCommands;
import com.bc.tasktracker.jpa.entities.master.Appointment;
import com.bc.tasktracker.jpa.entities.master.Task;
import java.net.URL;
import javax.swing.JFrame;
import com.bc.appcore.User;
import com.bc.appcore.util.BlockingQueueThreadPoolExecutor;
import com.bc.tasktracker.ConfigNames;
import com.bc.tasktracker.client.jpa.model.TasktrackerResultModel;
import com.bc.tasktracker.client.ui.TasktrackerUIContext;
import com.bc.tasktracker.jpa.TasktrackerSearchContext;
import com.bc.tasktracker.jpa.TasktrackerSearchContextImpl;
import com.bc.tasktracker.jpa.entities.master.Task_;
import java.util.ArrayList;

/**
 * @author Chinomso Bassey Ikwuagwu on Feb 7, 2017 11:26:00 PM
 */
public abstract class AbstractTasktrackerApp extends AbstractApp implements TasktrackerApp {
    
    private transient static final Logger logger = Logger.getLogger(AbstractTasktrackerApp.class.getName());
    
    private final ExecutorService updateOutputService;
    
    public AbstractTasktrackerApp(AppContext appContext) {
        this(appContext, new BlockingQueueThreadPoolExecutor("Service_for_writing_excel_data_to_disk_ThreadFactory", 1, 1, 1));
    }

    public AbstractTasktrackerApp(AppContext appContext, ExecutorService dataOutputService) {
        
        super(appContext);
        
        this.updateOutputService = Objects.requireNonNull(dataOutputService);
    }
    
    @Override
    public void init() {
        
        super.init();
        
        this.getUIContext().getTaskFrame().getTaskPanel().init(this);
    }
    
    @Override
    public void shutdown() {
        try{
            super.shutdown();
        }finally{
            Util.shutdownAndAwaitTermination(this.updateOutputService, 1, TimeUnit.SECONDS);
        }
    }

    @Override
    public List<String> getTaskColumnNames() {
        final List<String> temp = new ArrayList(TasktrackerApp.super.getTaskColumnNames());
        temp.remove(Task_.taskid.getName());
        return Collections.unmodifiableList(temp);
    }

    @Override
    public <T> TasktrackerSearchContext<T> getSearchContext(Class<T> entityType) {
        final ResultModel resultModel = this.getResultModel(entityType, null);
        return new TasktrackerSearchContextImpl<>(this, Objects.requireNonNull(resultModel));
    }
    
    @Override
    public Appointment getAppointment(User user, Appointment outputIfNone) {
        final Appointment appt = (Appointment)this.getAction(
                TasktrackerActionCommands.GET_APPOINTMENT_FOR_USER, Level.FINER).executeSilently(
                        this, Collections.singletonMap(User.class.getName(), user), outputIfNone);
        return appt;
    }

    @Override
    protected ObjectFactory createObjectFactory() {
        return new TasktrackerObjectFactory(this);
    }
    
    @Override
    protected MainFrame createMainFrame() {
        return new TasktrackerMainFrame();
    }
    
    @Override
    protected UIContext createUIContext(App app, ImageIcon imageIcon, JFrame mainFrame) {
        final TaskFrame taskFrame = new TaskFrame();
        return new DtbUIContextImpl(this, imageIcon, mainFrame, taskFrame);
    }
    
    @Override
    protected String getImageIconDescription(URL url) {
        return this.getConfig().getString(ConfigNames.LOGO_DESCRIPTION);
    }
    
    @Override
    protected URL getIconURL() {
        return this.getClassLoader().getResource(this.getConfig().getString(ConfigNames.LOGO_FILENAME));
    }
    
    @Override
    public ResultModel<Task> createDefaultResultModel() {
        return new TasktrackerResultModel(
                this, Task.class, this.getTaskColumnNames(), 0    
        );
    }

    @Override
    public TasktrackerUIContext getUIContext() {
        return (TasktrackerUIContext)super.getUIContext();
    }
    
    @Override
    public void updateReports(List<Appointment> appointmentList, boolean refreshDisplay) {
        
        final Callable<List<File>> updateOutputTask = this.getUpdateOutputTask(appointmentList, refreshDisplay);

        this.updateOutputService.submit(updateOutputTask);
    }
    
    @Override
    public Callable<List<File>> getUpdateOutputTask(List<Appointment> appointmentList, boolean refreshDisplay) {
        return () -> { return Collections.EMPTY_LIST; };
    }
    
    @Override
    public <T> ParametersBuilder<T> getParametersBuilder(T source, String actionCommand) {
        
        final ParametersBuilder builder;
        
        if(source instanceof TaskPanel && 
                (TasktrackerActionCommands.ADD_TASK_AND_DOC.equals(actionCommand) ||
                TasktrackerActionCommands.ADD_TASK_TO_DOC.equals(actionCommand))) {
            builder = new AddTaskParametersBuilder();
        }else if(source instanceof TaskResponsePanel && 
                TasktrackerActionCommands.ADD_TASKRESPONSE.equals(actionCommand)) {
            builder = new AddResponseParametersBuilder();
        }else if(source instanceof SearchPanel && TasktrackerActionCommands.SEARCH_AND_DISPLAY_RESULTS_UI.equals(actionCommand)) {    
            builder = new SearchParametersBuilder();
        }else if(source instanceof SearchResultsPanel && TasktrackerActionCommands.DISPLAY_TASKEDITORPANE.equals(actionCommand)) {    
            builder = new SelectedRecordsParametersBuilder();
        }else if(source instanceof SearchResultsPanel && 
                (TasktrackerActionCommands.CLOSE_TASK.equals(actionCommand) || TasktrackerActionCommands.OPEN_TASK.equals(actionCommand))) {    
            builder = new SelectedRecordsParametersBuilder();
        }else if(source instanceof SearchResultsPanel && TasktrackerActionCommands.DELETE_TASK.equals(actionCommand)) {    
            builder = new SelectedRecordsParametersBuilder();
        }else if(source instanceof SearchResultsPanel && 
                (TasktrackerActionCommands.DISPLAY_ADD_RESPONSE_UI.equals(actionCommand) ||
                TasktrackerActionCommands.DISPLAY_ADD_REMARK_UI.equals(actionCommand))) {    
            builder = new SelectedRecordsParametersBuilder();
        }else if(source instanceof AppointmentPanel && TasktrackerActionCommands.ADD_APPOINTMENT.equals(actionCommand)) {
            builder = new AddAppointmentParametersBuilder();
        }else if(source instanceof UnitPanel && TasktrackerActionCommands.ADD_UNIT.equals(actionCommand)) {
            builder = new AddUnitParametersBuilder();
        }else {
            builder = super.getParametersBuilder(source, actionCommand);
        }
        
        builder.context(this).with(source);

        if(logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Container: {0}, actionCommand: {1}, ParametersBuilder: {2}", 
                    new Object[]{source.getClass().getSimpleName(), actionCommand, builder.getClass().getSimpleName()});
        }
        
        return builder;
    }
}
