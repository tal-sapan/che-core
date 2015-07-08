/*******************************************************************************
 * Copyright (c) 2012-2015 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.ide.connection;

import org.eclipse.che.ide.CoreLocalizationConstant;
import org.eclipse.che.ide.api.event.HttpSessionDestroyedEvent;
import org.eclipse.che.ide.ui.dialogs.DialogFactory;
import org.eclipse.che.ide.websocket.MessageBus;
import org.eclipse.che.ide.websocket.events.ConnectionClosedHandler;
import org.eclipse.che.ide.websocket.events.ConnectionOpenedHandler;
import org.eclipse.che.ide.websocket.events.WebSocketClosedEvent;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

/**
 * @author Evgen Vidolob
 */
public class WsConnectionListener implements ConnectionClosedHandler, ConnectionOpenedHandler {


    private EventBus                 eventBus;
    private MessageBus               messageBus;
    private DialogFactory            dialogFactory;
    private CoreLocalizationConstant constant;

    @Inject
    public WsConnectionListener(MessageBus messageBus,
                                EventBus eventBus,
                                DialogFactory dialogFactory,
                                CoreLocalizationConstant constant) {
        this.eventBus = eventBus;
        this.messageBus = messageBus;
        this.dialogFactory = dialogFactory;
        this.constant = constant;
        messageBus.addOnCloseHandler(this);
    }

    @Override
    public void onClose(WebSocketClosedEvent event) {
        messageBus.removeOnCloseHandler(this);

        int code = event.getCode();
        String reason = event.getReason();
        if (code == WebSocketClosedEvent.CLOSE_NORMAL && "Http session destroyed".equals(reason)) {
            eventBus.fireEvent(new HttpSessionDestroyedEvent());
            return;
        }

        if (code == WebSocketClosedEvent.CLOSE_GOING_AWAY && "The web application is stopping".equals(reason)) {
            dialogFactory.createMessageDialog(constant.serverFailureTitle(), constant.messagesServerStopping(), null)
                         .show();
            return;
        }

        if (code == WebSocketClosedEvent.CLOSE_ABNORMAL ||
            code == WebSocketClosedEvent.CLOSE_GOING_AWAY) {
            dialogFactory.createMessageDialog(constant.serverFailureTitle(), constant.messagesServerFailure(), null)
                         .show();
        }
    }

    @Override
    public void onOpen() {
        messageBus.addOnCloseHandler(this);
    }
}
