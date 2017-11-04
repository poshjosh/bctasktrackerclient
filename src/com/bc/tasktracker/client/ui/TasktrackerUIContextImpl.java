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

package com.bc.tasktracker.client.ui;

import com.bc.appbase.ui.SearchResultsPanel;
import com.bc.appbase.ui.UIContextBase;
import com.bc.ui.table.cell.ColumnWidths;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.util.Objects;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import com.bc.tasktracker.client.ui.actions.TasktrackerActionCommands;
import com.bc.tasktracker.client.TasktrackerApp;
import java.awt.Font;
import javax.swing.JFrame;

/**
 * @author Chinomso Bassey Ikwuagwu on Feb 10, 2017 4:51:32 PM
 */
public class TasktrackerUIContextImpl extends UIContextBase implements TasktrackerUIContext {
    
    private transient final Logger logger = Logger.getLogger(TasktrackerUIContextImpl.class.getName());

    private final TasktrackerApp app;
    
    private final TaskFrame newTaskFrame;
    
    public TasktrackerUIContextImpl(TasktrackerApp app, ImageIcon icon, JFrame mainFrame, TaskFrame newTaskFrame) {
        super(app, icon, mainFrame);
        this.app = Objects.requireNonNull(app);
        this.newTaskFrame = Objects.requireNonNull(newTaskFrame);
    }
    
    @Override
    public void dispose() {
        super.dispose();
        if(this.newTaskFrame.isVisible()) {
            this.newTaskFrame.setVisible(false);
        }
        this.newTaskFrame.dispose();
    }

    @Override
    public TasktrackerMainFrame getMainFrame() {
        return (TasktrackerMainFrame)super.getMainFrame();
    }

    @Override
    public ColumnWidths getColumnWidths() {
        return new TaskColumnWidths();
    }

    @Override
    public MouseListener getMouseListener(Container container) {
        
        if(container instanceof SearchResultsPanel) {
            
            final MouseListener listener = 
                    new SearchResultsRightClickListener(app, (SearchResultsPanel)container);
            
            return listener;
            
        }else{
            return new MouseAdapter() {};
        }
    }
    
    @Override
    public AppointmentPanel createAppointmentPanel() {
        return new AppointmentPanel(app);
    }

    @Override
    public UnitPanel createUnitPanel() {
        return new UnitPanel(app);
    }

    @Override
    public MessageFrame<TaskResponsePanel> createTaskResponseFrame() {
        final TaskResponsePanel panel = new TaskResponsePanel();
        final Font font = this.getFont(panel.getClass());
        final MessageFrame<TaskResponsePanel> frame = new MessageFrame<>(panel, font);
        frame.getMessageComponent().init(app);
        return frame;
    }

    @Override
    public EditorPaneFrame createEditorPaneFrame(SearchResultsPanel resultsPanel) {
        final EditorPaneFrame frame = new EditorPaneFrame();
        if(this.getImageIcon() != null) {
            frame.setIconImage(this.getImageIcon().getImage());
        }
        frame.getAddResponseButton().setActionCommand(TasktrackerActionCommands.DISPLAY_ADD_RESPONSE_UI);
        frame.getAddRemarkButton().setActionCommand(TasktrackerActionCommands.DISPLAY_ADD_REMARK_UI);
        frame.getCloseTaskButton().setActionCommand(TasktrackerActionCommands.CLOSE_TASK);
        frame.getDeleteTaskButton().setActionCommand(TasktrackerActionCommands.DELETE_TASK);
        this.addActionListeners(resultsPanel, 
                frame.getAddResponseButton(),
                frame.getAddRemarkButton(),
                frame.getCloseTaskButton(),
                frame.getDeleteTaskButton());
        return frame;
    }

    @Override
    public TaskFrame getTaskFrame() {
        return newTaskFrame;
    }
}
