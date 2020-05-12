package com.tkap11.spendingrecord.handler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageHandlerManager {
    private List<MessageHandler> messageHandlers;

    public MessageHandlerManager() {
        messageHandlers = new ArrayList<MessageHandler>();
    }

    public void addMessageHandler(MessageHandler messageHandler) {
        messageHandlers.add(messageHandler);
    }

    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        for (MessageHandler messageHandler : messageHandlers) {
            if (messageHandler.canHandleTextMessageEvent(event)) {
                messageHandler.handleTextMessageEvent(event);
            }
        }
    }
}