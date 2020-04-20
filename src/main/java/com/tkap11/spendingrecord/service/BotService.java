package com.tkap11.spendingrecord.service;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class BotService {

    @Autowired
    private LineMessagingClient lineMessagingClient;

    @Autowired
    private BotTemplate botTemplate;

    public void greetingMessage(String replyToken) {
        replyFlexMenu(replyToken);
    }

    public void replyFlexMenu(String replyToken){
        FlexMessage flexMessage = botTemplate.createFlexMenu();
        ReplyMessage replyMessage = new ReplyMessage(replyToken, flexMessage);
        reply(replyMessage);
    }

    public void handleMessageEvent(MessageEvent messageEvent){
        TextMessageContent textMessageContent = (TextMessageContent) messageEvent.getMessage();
        String replyToken = messageEvent.getReplyToken();
        String userMessage = textMessageContent.getText();
        if (userMessage.toLowerCase().equals("menu")){
            replyFlexMenu(replyToken);
        } else{
            replyText(replyToken, "Sedang dalam pengembangan");
        }
    }

    private void replyText(String replyToken, String message){
        TextMessage textMessage = new TextMessage(message);
        ReplyMessage replyMessage = new ReplyMessage(replyToken, textMessage);
        reply(replyMessage);
    }

    private void reply(ReplyMessage replyMessage) {
        try {
            lineMessagingClient.replyMessage(replyMessage).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
