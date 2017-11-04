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

import com.bc.appbase.jpa.JpaContextManagerWithUserPrompt;
import com.bc.appbase.ui.UIContext;
import com.bc.appcore.functions.ExecuteSqlFromScriptFile;
import com.bc.appcore.properties.PropertiesContext;
import com.bc.jpa.context.PersistenceUnitContext;
import com.bc.tasktracker.jpa.nodequery.RenameUppercaseSlaveColumnsThenTables;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * @author Chinomso Bassey Ikwuagwu on Aug 19, 2017 4:53:25 AM
 */
public class TasktrackerJpaContextManager extends JpaContextManagerWithUserPrompt {

    private static final Logger logger = Logger.getLogger(TasktrackerJpaContextManager.class.getName());

    private final ClassLoader classLoader;
    
    public TasktrackerJpaContextManager(
            ClassLoader classLoader, 
            UIContext uiContext, 
            PropertiesContext filepaths, 
            Predicate<String> persistenceUnitRequiresAuthenticationTest) {
        super(uiContext, filepaths, persistenceUnitRequiresAuthenticationTest);
        this.classLoader = Objects.requireNonNull(classLoader);
    }

    @Override
    public void initDatabaseData(PersistenceUnitContext puContext) throws IOException {
        
        logger.entering(this.getClass().getName(), "initDatabaseData(PersistenceUnitContext)", puContext.getName());
        
        final String [] suffixes = {"_create_tables.sql", "_insert_into_tables.sql"};
        
        final String persistenceUnit = puContext.getPersistenceUnitName();
        
        for(String suffix : suffixes) {
            
            final URL url = classLoader.getResource(
                    "META-INF/sql/" + persistenceUnit + suffix);

            if(url != null) {
                
                final List<Integer> output = new ExecuteSqlFromScriptFile<URL>().apply(puContext, url);
                
                if(output.isEmpty()) {
                    throw new RuntimeException("Failed to execute SQL script: " + url);
                }
            }
        }
        
        new RenameUppercaseSlaveColumnsThenTables(puContext).apply(
                com.bc.tasktracker.jpa.entities.master.Appointment.class, 
                com.bc.tasktracker.jpa.entities.slave.Appointment.class);
    }
}
