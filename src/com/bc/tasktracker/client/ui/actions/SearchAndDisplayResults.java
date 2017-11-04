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
import com.bc.jpa.search.SearchResults;
import java.util.Map;
import java.util.logging.Logger;
import com.bc.appcore.actions.Action;
import com.bc.appbase.App;
import com.bc.appbase.ui.SearchResultsFrame;
import com.bc.appbase.ui.SearchResultsPanel;
import com.bc.appbase.ui.UIContext;
import com.bc.appcore.jpa.SearchContext;
import com.bc.appcore.parameter.ParameterException;
import com.bc.functions.FindExceptionInChain;
import com.bc.tasktracker.jpa.SearchParameters;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;
import javax.persistence.Query;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 * @author Chinomso Bassey Ikwuagwu on Feb 10, 2017 3:10:11 PM
 */
public class SearchAndDisplayResults implements Action<App,String> {

    private static final Logger logger = Logger.getLogger(SearchAndDisplayResults.class.getName());
    
    @Override
    public String execute(App app, Map<String, Object> params) 
            throws ParameterException, TaskExecutionException {
        
        String key;
        try{
            
            key = this.createAndShowSearchResults(app, params);
            
        }catch(Exception e) {
            
            key = null;
            
            final FindExceptionInChain findException = new FindExceptionInChain();
            
            final Optional<Throwable> optThrowable;
            
            if((optThrowable = findException.apply(e, (t) -> t instanceof UnknownHostException)).isPresent()) {

                final Throwable targetException = optThrowable.get();
                
                logger.warning(() -> 
                        "Fetching local data. Reason: Failed to connect to remote server.\nCaused by: " + 
                                targetException);
            
                final Function<Query, Query> queryFormatter = (query) -> query
                        .setHint(QueryHints.READ_ONLY, HintValues.TRUE);
//                        .setHint(QueryHints.CACHE_USAGE, CacheUsage.CheckCacheOnly);
                
                params = new HashMap(params);
                params.put("QueryFormatter", queryFormatter);
                
                app.switchToBackupDatabase();
                
                key = this.createAndShowSearchResults(app, params);
                
            }else{
                
                throw e;
            }
        }
        
        return key;
    }
    
    public String createAndShowSearchResults(final App app, final Map<String, Object> params) 
            throws ParameterException, TaskExecutionException {
        
        final SearchResults searchResults = 
                (SearchResults)app.getAction(TasktrackerActionCommands.SEARCH).execute(app, params);
        
        final String KEY = app.getRandomId();
        
        final Object msg = app.getAction(TasktrackerActionCommands.BUILD_SEARCHUI_MESSAGE).execute(app, params);
                
        final Class resultType = (Class)params.getOrDefault(
                SearchParameters.PARAM_RESULT_TYPE, app.getDefaultEntityType());
        
        createAndShowSearchResultsFrame(app, resultType, searchResults, KEY, msg);
        
        return KEY;
    }

    private void createAndShowSearchResultsFrame(App app, Class resultType, 
            SearchResults searchResults, String KEY, Object msg) {

        final SearchResultsFrame frame = new SearchResultsFrame();
        
        final UIContext uiContext = app.getUIContext();
        
        frame.init(uiContext, msg==null?null:msg.toString(), true);
        
        final SearchContext searchContext = app.getSearchContext(resultType);
        
        frame.loadSearchResults(searchContext, searchResults, KEY, true);
        
        final SearchResultsPanel panel = frame.getSearchResultsPanel();
        
        panel.getSearchResultsTable().addMouseListener(app.getUIContext().getMouseListener(panel));
        
        frame.setVisible(true);
    }
}

