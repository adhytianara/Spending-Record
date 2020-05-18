package com.tkap11.spendingrecord.service;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.profile.UserProfileResponse;
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

    private UserProfileResponse sender = null;

    public Source source;

    public void greetingMessage(String replyToken) {
        replyFlexMenu(replyToken);
    }

    public UserProfileResponse getProfile(String userId){
        try {
            return lineMessagingClient.getProfile(userId).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void replyFlexMenu(String replyToken){
        if(sender == null){
            String senderId = source.getSenderId();
            sender = getProfile(senderId);
        }

        FlexMessage flexMessage=botTemplate.createFlexMenu();
        List<Message> messageList = new ArrayList<>();
        messageList.add(new TextMessage("Hi apa yang ingin kamu lakukan ?"));
        messageList.add(flexMessage);
        reply(replyToken, messageList);
    }

    public void relpyFlexSisa(String replyToken){
        FlexMessage flexMessage=botTemplate.createFlexSisa();
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

    public void reply(String replyToken, List<Message> message) {
        ReplyMessage replyMessage = new ReplyMessage(replyToken, message);
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
        } else if (textMessageContent.getText().toLowerCase().contains("sisa")){
            relpyFlexSisa(replyToken);
        }
        else{
            replyText(replyToken, "Sedang dalam pengembangan");
        }

    }
}
