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
import com.bc.appcore.properties.PropertiesContext;
import com.bc.appcore.sql.script.SqlScriptImporter;
import com.bc.jpa.JpaContext;
import com.bc.tasktracker.jpa.entities.master.Task;
import com.bc.tasktracker.jpa.entities.master.Unit;
import com.bc.tasktracker.jpa.entities.slave.Unit_;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Level;

/**
 * @author Chinomso Bassey Ikwuagwu on Aug 19, 2017 4:53:25 AM
 */
public class TasktrackerJpaContextManager extends JpaContextManagerWithUserPrompt {

    private final ClassLoader classLoader;
    
    private final Set<String> sqlInsertResources;

    public TasktrackerJpaContextManager(
            ClassLoader classLoader, UIContext uiContext, 
            PropertiesContext filepaths, Set<String> sqlInsertResources) {
        this(classLoader, uiContext, filepaths, (persistenceUnit) -> true, sqlInsertResources);
    }

    public TasktrackerJpaContextManager(
            ClassLoader classLoader, UIContext uiContext, 
            PropertiesContext filepaths, Predicate<String> persistenceUnitTest,
            Set<String> sqlInsertResources) {
        super(uiContext, filepaths, persistenceUnitTest);
        this.classLoader = Objects.requireNonNull(classLoader);
        this.sqlInsertResources = Objects.requireNonNull(sqlInsertResources);
    }

    @Override
    public void importInitialData(JpaContext jpaContext) throws IOException {
        
        for(String resource : sqlInsertResources) {
        
            final Enumeration<URL> urls = classLoader.getResources(resource);

            new SqlScriptImporter("utf-8", Level.INFO).executeSqlScripts(jpaContext, Task.class, urls);
        }
    }

    @Override
    public void validateJpaContext(JpaContext jpaContext) {
        jpaContext.getBuilderForSelect(Unit.class, Long.class)
                .from(Unit.class)
                .count(Unit_.unitid.getName())
                .getSingleResultAndClose();
    }
}
