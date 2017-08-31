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
import java.util.Map;
import com.bc.appbase.App;
import com.bc.appbase.ui.actions.DisplayURL;
import static com.bc.appbase.ui.actions.ParamNames.CONTENT_TYPE;
import com.bc.appcore.parameter.ParameterException;
import com.bc.appcore.parameter.ParameterNotFoundException;
import com.bc.tasktracker.ConfigNames;
import java.util.HashMap;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Chinomso Bassey Ikwuagwu on Feb 11, 2017 4:54:19 AM
 */
public class About extends DisplayURL {

    private static final Logger logger = Logger.getLogger(About.class.getName());
    
    @Override
    public Boolean execute(App app, Map<String, Object> params) 
            throws ParameterException, TaskExecutionException {
        
        final String aboutFilename = app.getConfig().getString(ConfigNames.ABOUT_FILE_NAME, null);
        logger.log(Level.FINE, "About filename: {0}", aboutFilename);
        this.validate(aboutFilename);
        
        final URL url = app.getClassLoader().getResource(aboutFilename);
        logger.log(Level.FINE, "About file URL: {0}", url);
        this.validate(url);
        
        params = new HashMap();
        params.put(CONTENT_TYPE, "text/html");
        params.put(java.net.URL.class.getName(), url);
        
        return super.execute(app, params);
    }
    
    public void validate(Object val) throws ParameterNotFoundException {
        if(val == null || val.toString().isEmpty()) {
            throw new ParameterNotFoundException("About file not available");
        }
    }
}

