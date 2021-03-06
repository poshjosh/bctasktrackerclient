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
import com.bc.appcore.actions.Action;
import com.bc.appcore.actions.ActionQueue;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * @author Chinomso Bassey Ikwuagwu on Apr 19, 2017 3:25:50 PM
 */
public class PromptUserProceedIfActionBusy {
    
    private transient static final Logger logger = Logger.getLogger(PromptUserProceedIfActionBusy.class.getName());
    
    private final float fontSizeEm = 1.2f;
    
    private final App app;

    public PromptUserProceedIfActionBusy(App app) {
        this.app = Objects.requireNonNull(app);
    }
    
    public boolean execute(Action action, String msgIfBusy) {
        
        String timeName = "seconds";

        final ActionQueue actionQueue = app.getActionQueue();
        
        long estTimeLeftMillis = actionQueue.getEstimatedTimeLeftMillis(action, -1);
        long estTimeLeft = estTimeLeftMillis == -1 ? -1 : TimeUnit.MILLISECONDS.toSeconds(estTimeLeftMillis);
        
        final boolean proceed = !(estTimeLeft == -1 || estTimeLeft >= 2);
        
        final boolean output;
        
        if(proceed) {
            
            output = true;
            
        }else{    
            
            if(estTimeLeft > 120) {
                timeName = "minutes";
                estTimeLeftMillis = actionQueue.getEstimatedTimeLeftMillis(action, -1);
                estTimeLeft = estTimeLeftMillis == -1 ? -1 : TimeUnit.MILLISECONDS.toMinutes(estTimeLeftMillis);
            }

            if(logger.isLoggable(Level.FINE)) {
                logger.log(Level.FINE, "Estimated time left: {0} {1}", new Object[]{estTimeLeft, timeName});
            }

            final StringBuilder messageHtml = new StringBuilder(200);

            messageHtml.append("<html><p style=\"font-size:"+fontSizeEm+"em;\">");
            messageHtml.append("... ... ...Please wait.");
            messageHtml.append("<br/>");
            messageHtml.append(msgIfBusy);
            if(estTimeLeft > 0) {
                messageHtml.append("<br/>Estimated time left: ").append(estTimeLeft).append(' ').append(timeName);
            }        
            messageHtml.append("<br/>Click OK to view reports anyway."); 
            messageHtml.append("<br/>However, results may be inconsistent!</p></html>"); 

            final JLabel message = new JLabel(messageHtml.toString());

            final int option = JOptionPane.showConfirmDialog(app.getUIContext().getMainFrame(), 
                    message, msgIfBusy, JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

            output = option == JOptionPane.OK_OPTION;
        }

        return output;
    }
}
