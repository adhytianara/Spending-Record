package com.tkap11.spendingrecord.service;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.Multicast;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.profile.UserProfileResponse;
import com.tkap11.spendingrecord.catatpengeluaran.CatatPengeluaranHandler;
import com.tkap11.spendingrecord.catatpengeluaran.ChooseCategoryHandler;
import com.tkap11.spendingrecord.repository.SpendingDatabase;
import com.tkap11.spendingrecord.repository.UserDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import java.util.Set;

@Service
public class BotService {

    @Autowired
    private LineMessagingClient lineMessagingClient;

    @Autowired
    private BotTemplate botTemplate;

    @Autowired
    private UserDatabase userService;

    @Autowired
    private SpendingDatabase spendingService;

    public Source source;

    private HashMap<String, CatatPengeluaranHandler> currentHandler =new HashMap<>();

    public void greetingMessage(String replyToken) {
        registerUser(source);
        replyFlexMenu(replyToken);
    }

    private void registerUser(Source source) {
        String senderId = source.getSenderId();
        UserProfileResponse sender = getProfile(senderId);
        userService.registerUser(sender.getUserId(), sender.getDisplayName());
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

    public UserProfileResponse getProfile(String userId){
        try {
            return lineMessagingClient.getProfile(userId).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private void push(PushMessage pushMessage){
        try {
            lineMessagingClient.pushMessage(pushMessage).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private void pushAlarm(String to, Message message){
        PushMessage pushMessage = new PushMessage(to, message);
        push(pushMessage);
    }

    public void multicast(Set<String> to, Message message) {
        try {
            Multicast multicast = new Multicast(to, message);
            lineMessagingClient.multicast(multicast).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMulticast(Set<String> sourceUsers, String txtMessage){
        TextMessage message = new TextMessage(txtMessage);
        Multicast multicast = new Multicast(sourceUsers, message);

        try {
            lineMessagingClient.multicast(multicast).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleMessageEvent(MessageEvent messageEvent){
        TextMessageContent textMessageContent=(TextMessageContent) messageEvent.getMessage();
        String replyToken=messageEvent.getReplyToken();
        String userMessage=textMessageContent.getText();
        String senderId = source.getSenderId();
        CatatPengeluaranHandler oldHandler = currentHandler.get(senderId);
        if (oldHandler instanceof CatatPengeluaranHandler){
            CatatPengeluaranHandler newHandler = oldHandler.handleUserRequest(userMessage.toLowerCase());
            currentHandler.put(senderId, newHandler);
            if (oldHandler.getMessageToUser().contains("berhasil dicatat")){
                spendingService.saveRecord(oldHandler.getDescription());
            }
            replyText(replyToken, oldHandler.getMessageToUser());
        } else if (userMessage.toLowerCase().contains("menu")){
            replyFlexMenu(replyToken);
        } else if (userMessage.toLowerCase().contains("catat")) {
            UserProfileResponse sender = getProfile(senderId);
            CatatPengeluaranHandler categoryHandler = new ChooseCategoryHandler(senderId, sender.getDisplayName());
            currentHandler.put(senderId, categoryHandler);
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
