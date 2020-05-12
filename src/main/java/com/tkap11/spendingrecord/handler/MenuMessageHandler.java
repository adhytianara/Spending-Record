package com.tkap11.spendingrecord.handler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.FlexMessage;
import com.tkap11.spendingrecord.service.BotService;
import com.tkap11.spendingrecord.service.BotTemplate;

import org.springframework.beans.factory.annotation.Autowired;

public class MenuMessageHandler extends MessageHandler{
    
    @Autowired
    private BotService botservice;
    
    @Autowired
    private BotTemplate botTemplate;

    @Override
    void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        String replyToken = event.getReplyToken();
        FlexMessage menuFlex = botTemplate.createFlex("Action Menu", "menu.json");
        botservice.reply(replyToken, menuFlex);
    }

    @Override
    boolean canHandleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        return event.getMessage().getText().toLowerCase().equals("menu");
    }
}