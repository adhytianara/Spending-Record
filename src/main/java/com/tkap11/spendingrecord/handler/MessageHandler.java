package com.tkap11.spendingrecord.handler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.*;
import com.linecorp.bot.model.message.Message;

import java.util.Collections;
import java.util.List;

public abstract class MessageHandler {
    List<Message> handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        return Collections.emptyList();
    }

    List<Message> handleImageMessageEvent(MessageEvent<ImageMessageContent> event) {
        return Collections.emptyList();
    }

    List<Message> handleAudioMessageEvent(MessageEvent<AudioMessageContent> event) {
        return Collections.emptyList();
    }

    List<Message> handleStickerMessageEvent(MessageEvent<StickerMessageContent> event) {
        return Collections.emptyList();
    }

    List<Message> handleLocationMessageEvent(MessageEvent<LocationMessageContent> event) {
        return Collections.emptyList();
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
