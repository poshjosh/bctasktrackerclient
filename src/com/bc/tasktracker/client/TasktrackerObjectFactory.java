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

import com.bc.appbase.ObjectFactoryBase;
import com.bc.appcore.ObjectFactory;
import com.bc.appcore.exceptions.ObjectFactoryException;
import com.bc.tasktracker.TasktrackerCoreObjectFactory;

/**
 * @author Chinomso Bassey Ikwuagwu on Mar 31, 2017 5:24:29 PM
 */
public class TasktrackerObjectFactory extends TasktrackerCoreObjectFactory {

    private final ObjectFactory core;
    
    public TasktrackerObjectFactory(TasktrackerApp app) {
        super(app);
        this.core = new ObjectFactoryBase(app);
    }

    @Override
    public <T> T getOrException(Class<T> type) throws ObjectFactoryException {
        T output;
        try{
            output = (T)super.getOrException(type);
        }catch(Exception e0) {
            try{
                output = (T)this.core.getOrException(type);
            }catch(Exception e1) {
                throw e0;
            }
        }
        return output;
    }

    @Override
    public TasktrackerApp getApp() {
        return (TasktrackerApp)super.getApp(); //To change body of generated methods, choose Tools | Templates.
    }
}
