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

import com.bc.appbase.App;
import com.bc.appbase.ui.SearchResultsPanel;
import com.bc.appcore.exceptions.TaskExecutionException;
import java.util.Map;
import javax.swing.JOptionPane;
import com.bc.appcore.actions.Action;
import com.bc.appcore.exceptions.SearchResultsNotFoundException;
import com.bc.appcore.parameter.ParameterException;
import com.bc.jpa.search.SearchResults;
import com.bc.tasktracker.jpa.entities.master.Task;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * @author Chinomso Bassey Ikwuagwu on Feb 13, 2017 12:09:41 PM
 */
public class CloseTask implements Action<App,Boolean> {

    @Override
    public Boolean execute(App app, Map<String, Object> params) 
            throws ParameterException, TaskExecutionException {
        
        final int selection = JOptionPane.showConfirmDialog(app.getUIContext().getMainFrame(), 
                "Are you sure you want to CLOSE the selected task(s)?", "Confirm Close", 
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if(selection == JOptionPane.YES_OPTION) {
            
            final Optional<SearchResults<Task>> optionalSearchResults = this.getSearchResults(app, params);
            
            if(optionalSearchResults.isPresent()) {
                params = new HashMap(params);
                params.put(SearchResults.class.getName(), optionalSearchResults.get());
            }
            
            final List<Task> closedEntities = (List<Task>)app.getAction(TasktrackerActionCommands.SET_TIMECLOSED).execute(app, params);

            if(!closedEntities.isEmpty()) {
                
                app.getUIContext().showSuccessMessage("Success");
                
                return Boolean.TRUE;
                
            }else{
                
                return Boolean.FALSE;
            }
            
        }else{
        
            return Boolean.FALSE;
        }
    }
    
    public Optional<SearchResults<Task>> getSearchResults(App app, Map params) {
        final SearchResultsPanel panel = (SearchResultsPanel)params.get(SearchResultsPanel.class.getName());
        final SearchResults<Task> searchResults;
        if(panel == null) {
            searchResults = null;
        }else{
            try{
                searchResults = app.getUIContext().getLinkedSearchResults(panel);
            }catch(SearchResultsNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.ofNullable(searchResults);
    }
}
