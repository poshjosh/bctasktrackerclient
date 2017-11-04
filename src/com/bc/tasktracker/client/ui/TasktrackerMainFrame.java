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

import java.awt.Font;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import com.bc.appbase.App;
import com.bc.tasktracker.client.ui.actions.TasktrackerActionCommands;
import com.bc.tasktracker.client.TasktrackerApp;

/**
 * The help menu though created and set-up, is not added by default. Use 
 * {@link #addHelpMenu()} to add the help menu after adding all other menus 
 * to the menu bar.
 * @author Chinomso Bassey Ikwuagwu on Mar 30, 2017 12:33:53 PM
 */
public class TasktrackerMainFrame extends com.bc.appbase.ui.MainFrame {

    private javax.swing.JMenuItem executeDeleteMenuItem;
    private javax.swing.JMenuItem executeSelectMenuItem;
    private javax.swing.JMenuItem executeUpdateMenuItem;
    private javax.swing.JMenuItem importMenuItem;
    private javax.swing.JMenuItem newtaskMenuItem;
    
    private javax.swing.JMenu remoteMenu;
    private javax.swing.JMenu sqlMenu;
    private javax.swing.JMenuItem syncMenuItem;
            
    public TasktrackerMainFrame() {
        this(null);
    }

    public TasktrackerMainFrame(App app) {
        this(app, new SearchPanel(), new java.awt.Font("Segoe UI", 0, 18), TasktrackerActionCommands.ABOUT);
    }
    
    public TasktrackerMainFrame(App app, JPanel topPanel, Font menuFont, String aboutMenuItemActionCommand) {
        super(app, topPanel, menuFont, aboutMenuItemActionCommand);
    }

    @Override
    public void init(App app) {
        
        super.init(app);
        
        this.getNewtaskMenuItem().setActionCommand(TasktrackerActionCommands.DISPLAY_ADD_TASK_UI);
        this.getImportMenuItem().setActionCommand(TasktrackerActionCommands.IMPORT_SHEET_DATA);
        this.getExecuteSelectMenuItem().setActionCommand(TasktrackerActionCommands.EXECUTE_SELECT_QUERY);
        this.getExecuteUpdateMenuItem().setActionCommand(TasktrackerActionCommands.EXECUTE_UPDATE_QUERY);
        this.getExecuteDeleteMenuItem().setActionCommand(TasktrackerActionCommands.EXECUTE_DELETE_QUERY);
        this.getSyncMenuItem().setActionCommand(TasktrackerActionCommands.SYNC_DATABASE_WITH_USER_PROMPT);
        
        app.getUIContext().addActionListeners(this, 
                this.getNewtaskMenuItem(), 
                this.getImportMenuItem(),
                this.getExecuteSelectMenuItem(), 
                this.getExecuteUpdateMenuItem(),
                this.getExecuteDeleteMenuItem(), 
                this.getSyncMenuItem()
        );
    }
    
    @Override
    public void init(App app, JPanel topPanel) { 
        (((SearchPanel)topPanel)).init((TasktrackerApp)app);
    }

    @Override
    public void reset(App app, JPanel topPanel) { 
        (((SearchPanel)topPanel)).reset((TasktrackerApp)app);
    }
    
    @Override
    public void initComponents() {
        
        super.initComponents(); 
        
        newtaskMenuItem = new javax.swing.JMenuItem();
        importMenuItem = new javax.swing.JMenuItem();
        sqlMenu = new javax.swing.JMenu();
        executeSelectMenuItem = new javax.swing.JMenuItem();
        executeUpdateMenuItem = new javax.swing.JMenuItem();
        executeDeleteMenuItem = new javax.swing.JMenuItem();
        remoteMenu = new javax.swing.JMenu();
        syncMenuItem = new javax.swing.JMenuItem();

        final JMenuBar menuBar = this.getJMenuBar();
        final JMenu fileMenu = this.getFileMenu();

        final Font menuFont = this.getMenuFont();
        newtaskMenuItem.setFont(menuFont);
        newtaskMenuItem.setMnemonic('n');
        newtaskMenuItem.setText("New Task");
        fileMenu.add(newtaskMenuItem);

        importMenuItem.setFont(menuFont);
        importMenuItem.setText("Import");
        fileMenu.add(importMenuItem);

        sqlMenu.setText("SQL");
        sqlMenu.setFont(menuFont);

        executeSelectMenuItem.setFont(menuFont);
        executeSelectMenuItem.setText("Execute SELECT");
        sqlMenu.add(executeSelectMenuItem);

        executeUpdateMenuItem.setFont(menuFont);
        executeUpdateMenuItem.setText("Execute UPDATE");
        executeUpdateMenuItem.setEnabled(false);
        sqlMenu.add(executeUpdateMenuItem);

        executeDeleteMenuItem.setFont(menuFont);
        executeDeleteMenuItem.setText("Execute DELETE");
        executeDeleteMenuItem.setEnabled(false);
        sqlMenu.add(executeDeleteMenuItem);

        menuBar.add(sqlMenu);

        remoteMenu.setText("Remote");
        remoteMenu.setFont(menuFont);

        syncMenuItem.setFont(menuFont);
        syncMenuItem.setText("Sync");
        remoteMenu.add(syncMenuItem);

        menuBar.add(remoteMenu);
    }

    public JMenuItem getExecuteDeleteMenuItem() {
        return executeDeleteMenuItem;
    }

    public JMenuItem getExecuteSelectMenuItem() {
        return executeSelectMenuItem;
    }

    public JMenuItem getExecuteUpdateMenuItem() {
        return executeUpdateMenuItem;
    }

    public JMenuItem getImportMenuItem() {
        return importMenuItem;
    }

    public JMenuItem getNewtaskMenuItem() {
        return newtaskMenuItem;
    }

    public JMenu getRemoteMenu() {
        return remoteMenu;
    }

    public JMenu getSqlMenu() {
        return sqlMenu;
    }

    public JMenuItem getSyncMenuItem() {
        return syncMenuItem;
    }
}
