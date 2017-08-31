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
import com.bc.jpa.dao.SelectDao;
import com.bc.jpa.search.SearchResults;
import java.util.Date;
import java.util.Map;
import com.bc.tasktracker.jpa.SelectDaoBuilder;
import com.bc.appcore.actions.Action;
import com.bc.tasktracker.client.TasktrackerApp;
import com.bc.tasktracker.jpa.entities.master.Task;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.bc.tasktracker.jpa.SearchParameters;
import com.bc.tasktracker.jpa.TasktrackerSearchContext;

/**
 * @author Chinomso Bassey Ikwuagwu on Mar 6, 2017 10:49:16 PM
 */
public class Search implements Action<TasktrackerApp, SearchResults> {
    
    @Override
    public SearchResults execute(TasktrackerApp app, Map<String, Object> params) 
            throws TaskExecutionException {
        
        Logger.getLogger(this.getClass().getName()).log(Level.FINE, "Parameters: {0}", params);
        
        final Class resultType = (Class)params.get(SearchParameters.PARAM_RESULT_TYPE);
        final String query = (String)params.get(SearchParameters.PARAM_QUERY);
        final Date from = (Date)params.get(SearchParameters.PARAM_FROM);
        final Date to = (Date)params.get(SearchParameters.PARAM_TO);
        final Boolean opened = (Boolean)params.get(SearchParameters.PARAM_OPENED);
        final Boolean closed = (Boolean)params.get(SearchParameters.PARAM_CLOSED);
        final String who = (String)params.get(SearchParameters.PARAM_WHO);
        final Date deadlineFrom = (Date)params.get(SearchParameters.PARAM_DEADLINE_FROM);
        final Date deadlineTo = (Date)params.get(SearchParameters.PARAM_DEADLINE_TO);
        
        final TasktrackerSearchContext searchContext = app.getSearchContext(resultType);
                    
        final SelectDaoBuilder selectionBuilder = searchContext.getSelectDaoBuilder();
        
        final SelectDao selectDao = selectionBuilder
                .jpaContext(app.getJpaContext())
                .resultType(resultType==null?Task.class:resultType)
                .textToFind(query==null || query.isEmpty() ? null : query)
                .deadlineFrom(deadlineFrom)
                .deadlineTo(deadlineTo)
                .from(from)
                .to(to)
                .opened(opened==null?Boolean.TRUE:opened)
                .closed(closed==null?Boolean.FALSE:closed)
                .who(who)
                .build();
        
        final SearchResults searchResults = searchContext.getSearchResults(selectDao);

        return searchResults;
    }
}

