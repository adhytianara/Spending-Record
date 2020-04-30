package com.tkap11.spendingrecord.service;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        FlexMessage flexMessage=botTemplate.createFlexMenu();
        reply(replyToken, flexMessage);
    }

    public void relpyFlexChooseCategory(String replyToken){
        FlexMessage flexMessage=botTemplate.createFlexChooseCategory();
        reply(replyToken, flexMessage);
    }
    
    public void relpyFlexSisa(String replyToken){
        FlexMessage flexMessage=botTemplate.createFlexSisa();
        reply(replyToken, flexMessage);
    }

    public void replyFlexAlarm(String replyToken){
        FlexMessage flexMessage=botTemplate.createFlexAlarm();
        reply(replyToken, flexMessage);
    }

    private void replyUbahAlarm(String replyToken){
        FlexMessage flexMessage=botTemplate.createFlexUbah();
        reply(replyToken, flexMessage);
    }

    private void replyText(String replyToken, String message){
        TextMessage textMessage=new TextMessage(message);
        reply(replyToken, textMessage);
    }

    public void reply(String replyToken, Message message) {
        ReplyMessage replyMessage=new ReplyMessage(replyToken, message);
        reply(replyMessage);
    }

    private void reply(ReplyMessage replyMessage) {
        try {
            lineMessagingClient.replyMessage(replyMessage).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleMessageEvent(MessageEvent messageEvent){
        TextMessageContent textMessageContent=(TextMessageContent) messageEvent.getMessage();
        String replyToken=messageEvent.getReplyToken();
        String userMessage=textMessageContent.getText();
        boolean userMessageIsEqualsToMenu=userMessage.equalsIgnoreCase("menu");
        if (userMessageIsEqualsToMenu){
            replyFlexMenu(replyToken);
        } else if (userMessage.toLowerCase().contains("catat")) {
            relpyFlexChooseCategory(replyToken);
        } else if (textMessageContent.getText().toLowerCase().contains("sisa")){
            relpyFlexSisa(replyToken);
        } else if (textMessageContent.getText().toLowerCase().contains("ingatkan")){
            replyFlexAlarm(replyToken);
        } else if (textMessageContent.getText().toLowerCase().contains("ubah")){
            replyUbahAlarm(replyToken);
        }
        else{
            replyText(replyToken, "Sedang dalam pengembangan");
        }

    }
}
