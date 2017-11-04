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

import com.bc.tasktracker.actions.TasktrackerCoreActionCommands;

/**
 * @author Chinomso Bassey Ikwuagwu on Mar 24, 2017 4:16:28 PM
 */
public interface TasktrackerActionCommands 
        extends TasktrackerCoreActionCommands, com.bc.appbase.ui.actions.ActionCommands {
    
    String GET_APPOINTMENT_FOR_USER = com.bc.tasktracker.client.ui.actions.GetAppointmentForUser.class.getName();
    
    String SEARCH = Search.class.getName();
    
    String SEARCH_AND_DISPLAY_RESULTS_UI = SearchAndDisplayResults.class.getName();
    
    String BUILD_SEARCHUI_MESSAGE = BuildSearchUIMessage.class.getName();
    
    String DISPLAY_TASKEDITORPANE = DisplayTaskEditorPane.class.getName();

    String DISPLAY_ADD_TASK_UI = DisplayAddtaskUI.class.getName();
    String ADD_TASK = AddTask.class.getName();
    String ADD_TASK_AND_DOC = AddTaskAndDoc.class.getName();
    String ADD_TASK_TO_DOC = AddTaskToDoc.class.getName();
    String CLEAR_TASK = ClearTaskPanel.class.getName();
    
    String DISPLAY_ADD_RESPONSE_UI = DisplayAddResponseUI.class.getName();
    String ADD_TASKRESPONSE = AddTaskresponse.class.getName();
    
    String DISPLAY_ADD_REMARK_UI = DisplayAddRemarkUI.class.getName();
    
    String DISPLAY_APPOINTMENT_UI = DisplayAppointmentUI.class.getName();
    String ADD_APPOINTMENT = AddAppointment.class.getName();
    
    String DISPLAY_UNIT_UI = DisplayUnitUI.class.getName();
    String ADD_UNIT = AddUnit.class.getName();
    
    String CLOSE_TASK = CloseTask.class.getName();
    String OPEN_TASK = OpenTask.class.getName();
    String SET_TIMECLOSED = SetTimeclosed.class.getName();
    
    String DELETE_TASK = DeleteTask.class.getName();
    
    String ABOUT = About.class.getName();
    
    String EXECUTE_SELECT_QUERY = com.bc.appbase.ui.actions.ExecuteSelectQuery.class.getName();
    String EXECUTE_UPDATE_QUERY = ExecuteUpdateQuery.class.getName();
    String EXECUTE_DELETE_QUERY = ExecuteDeleteQuery.class.getName();
}
