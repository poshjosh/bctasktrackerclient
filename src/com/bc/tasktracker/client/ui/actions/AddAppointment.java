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
import com.bc.appcore.parameter.ParameterNotFoundException;
import com.bc.tasktracker.jpa.entities.master.Appointment;
import com.bc.tasktracker.jpa.entities.master.Appointment_;
import com.bc.tasktracker.jpa.entities.master.Unit;
import com.bc.tasktracker.jpa.entities.master.Unit_;
import com.bc.tasktracker.client.ui.AppointmentPanel;
import java.awt.Window;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.bc.appcore.actions.Action;
import com.bc.appbase.App;
import com.bc.appcore.parameter.ParameterException;
import com.bc.jpa.dao.Select;

/**
 * @author Chinomso Bassey Ikwuagwu on Feb 26, 2017 8:53:37 PM
 */
public class AddAppointment implements Action<App,Appointment> {
    
    private final static Logger logger = Logger.getLogger(AddAppointment.class.getName());

    @Override
    public Appointment execute(App app, Map<String, Object> params) 
            throws ParameterException, TaskExecutionException {

        Appointment found = this.get(app, params, Appointment_.appointment.getName(), Appointment_.appointment.getName(), Appointment.class);

        final Appointment appt;

        if(found != null) {

            appt = found;

        }else {   

            final String apptStr = (String)params.get(Appointment_.appointment.getName());
            if(this.isNullOrEmpty(apptStr)) {
                throw new ParameterNotFoundException(Appointment_.appointment.getName());
            }

            final String abbrevStr = (String)params.get(Appointment_.abbreviation.getName());
            if(this.isNullOrEmpty(abbrevStr)) {
                throw new ParameterNotFoundException(Appointment_.abbreviation.getName());
            }

            final Appointment parent = this.get(app, params, Appointment_.parentappointment.getName(), Appointment_.appointment.getName(), Appointment.class);

            Unit unit = this.get(app, params, Unit_.unit.getName(), Unit_.unit.getName(),Unit.class);
            if(unit == null) {
                final String name = AddUnit.class.getName()+'#'+Unit.class.getName();
                unit = (Unit)app.getAttributes().get(name);

                logger.log(Level.FINE, "Unit from app attributes: {0}", unit);

                if(unit != null) {
                    app.getAttributes().remove(name);
                }
            }

            appt = new Appointment();
            appt.setAbbreviation(abbrevStr);
            appt.setAppointment(apptStr);
            appt.setParentappointment(parent);
            appt.setUnit(unit);
            app.getActivePersistenceUnitContext().getDao().begin().persistAndClose(appt);
        }

        final AppointmentPanel forDisposal = (AppointmentPanel)params.get(AppointmentPanel.class.getName());
        if(forDisposal != null) {
            Window window = (Window)forDisposal.getTopLevelAncestor();
            if(window != null) {
                window.setVisible(false);
                window.dispose();
            }
        }

        return appt;
    }
    
    private <T> T get(App app, Map<String, Object> params, String key, String name, Class<T> entityType) throws ParameterNotFoundException {
        final String value = (String)params.get(key);
        if(this.isNullOrEmpty(value)) {
            throw new ParameterNotFoundException(key);
        }
        try{
            return this.getDao(app, entityType)
                    .where(entityType, name, value)
                    .getSingleResultAndClose();
        }catch(javax.persistence.NoResultException ignored) {
            return null;
        } 
    }
    
    private <T> Select<T> getDao(App app, Class<T> entityType) {
        final Select<T> dao = app.getActivePersistenceUnitContext()
                .getDao().forSelect(entityType).from(entityType);
        return dao;
    }
    
    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
