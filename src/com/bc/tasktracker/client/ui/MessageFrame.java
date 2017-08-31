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

package com.bc.tasktracker.client.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.util.Objects;
import javax.swing.JLabel;

/**
 * @author Chinomso Bassey Ikwuagwu on Jul 25, 2017 2:20:34 PM
 * @param <C> The type of the component which holds/displays this frame's message
 */
public class MessageFrame<C extends Component> extends javax.swing.JFrame {

    private final C messageComponent;
    private final JLabel messageLabel;
    private final JLabel titleLabel;

    /**
     * Creates new MessageFrame
     * @param messageComponent The component which holds/displays the message
     * @param font 
     */
    public MessageFrame(C messageComponent, Font font) {
        
        this.messageComponent = Objects.requireNonNull(messageComponent);

        this.titleLabel = new JLabel();
        this.messageLabel = new JLabel();

        titleLabel.setFont(font); 
        titleLabel.setForeground(new java.awt.Color(0, 0, 255));
        titleLabel.setText("Add Response to Task");

        messageLabel.setFont(font);
        messageLabel.setText("  ");
        
        final Container contentPane = this.getContentPane();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(contentPane);
        contentPane.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(messageLabel)
                    .addComponent(messageComponent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(messageLabel)
                .addGap(15, 15, 15)
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(messageComponent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    public JLabel getMessageLabel() {
        return messageLabel;
    }

    public C getMessageComponent() {
        return messageComponent;
    }

    public JLabel getTitleLabel() {
        return titleLabel;
    }
}
