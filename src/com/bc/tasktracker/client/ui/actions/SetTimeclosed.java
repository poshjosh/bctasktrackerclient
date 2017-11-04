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
import com.bc.appbase.ui.actions.ActionCommands;
import com.bc.appcore.actions.Action;
import com.bc.appcore.exceptions.TaskExecutionException;
import com.bc.jpa.dao.Dao;
import com.bc.jpa.paging.PaginatedList;
import com.bc.jpa.search.SearchResults;
import com.bc.tasktracker.jpa.entities.master.Task;
import com.bc.tasktracker.jpa.entities.master.Task_;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.bc.tasktracker.client.TasktrackerApp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 4, 2017 12:42:16 PM
 */
public class SetTimeclosed implements Action<App, List> {
    
    public static final String PARAMETER_TIMECLOSED = "timeClosed";

    @Override
    public List execute(App app, Map<String, Object> params) throws TaskExecutionException {
        
        final Object oval = params.get(PARAMETER_TIMECLOSED);
        final Date timeClosed;
        if(params.containsKey(PARAMETER_TIMECLOSED)) {
            timeClosed = oval == null ? null : (Date)oval;
        }else{
            timeClosed = oval == null ? new Date() : (Date)oval;
        }
        
        final List taskidList = (List)params.get(Task_.taskid.getName()+"List");
        Objects.requireNonNull(taskidList);
        
        final SearchResults searchResults = (SearchResults)params.get(SearchResults.class.getName());
        
        final PaginatedList taskList = searchResults.getPages();
        Objects.requireNonNull(taskList);
        
        final List output = new ArrayList(taskidList.size());
        
        for(Object taskid : taskidList) {

            try(final Dao dao = app.getActivePersistenceUnitContext().getDao()) {

                final Task managed = dao.find(Task.class, taskid);

                managed.setTimeclosed(timeClosed);
                
                dao.begin();

                final Task refreshed = dao.merge(managed);
                
                final int pos = taskList.indexOf(managed);
                if(pos != -1) {
                    searchResults.load(pos); 
                }
                
                dao.commit();
                
                output.add(refreshed);
            }
        }

        if(!output.isEmpty()) {
            
            app.getAction(ActionCommands.REFRESH_ALL_RESULTS).executeSilently(app, 
                    Collections.singletonMap(Set.class.getName(), output));
            
            ((TasktrackerApp)app).updateReports();
        }

        return output;
    }
}
