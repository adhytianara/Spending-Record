package com.tkap11.spendingrecord.handler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.*;

public abstract class MessageHandler {
    void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {

    }

    void handleImageMessageEvent(MessageEvent<ImageMessageContent> event) {
        
    }

    void handleAudioMessageEvent(MessageEvent<AudioMessageContent> event) {

    }

    void handleStickerMessageEvent(MessageEvent<StickerMessageContent> event) {
        
    }

    void handleLocationMessageEvent(MessageEvent<LocationMessageContent> event) {
        
    }

    boolean canHandleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        return false;
    }

    boolean canHandleImageMessageEvent(MessageEvent<ImageMessageContent> event) {
        return false;
    }

    boolean canHandleAudioMessageEvent(MessageEvent<AudioMessageContent> event) {
        return false;
    }

    boolean canHandleStickerMessageEvent(MessageEvent<StickerMessageContent> event) {
        return false;
    }

    boolean canHandleLocationMessageEvent(MessageEvent<LocationMessageContent> event) {
        return false;
    }
}
