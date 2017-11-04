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

package com.bc.tasktracker.client.functions;

import com.bc.tasktracker.jpa.entities.master.Appointment;
import java.util.function.BiConsumer;
import javax.swing.ComboBoxModel;

/**
 * @author Chinomso Bassey Ikwuagwu on Sep 16, 2017 10:50:20 PM
 */
public class SetSelectedAppointment implements BiConsumer<ComboBoxModel<String>, Appointment> {

    @Override
    public void accept(ComboBoxModel<String> model, Appointment appt) {
        final int size = model.getSize();
        String target = null;
        for(int i=0; i<size; i++) {
            final String elem = model.getElementAt(i);
            if(appt.getAppointment().equals(elem) || 
                    appt.getAbbreviation().equals(elem)) {
                target = elem;
                break;
            }
        }
        model.setSelectedItem(target);
    }
}
