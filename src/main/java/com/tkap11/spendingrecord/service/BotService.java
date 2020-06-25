package com.tkap11.spendingrecord.service;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.Multicast;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.profile.UserProfileResponse;
import com.tkap11.spendingrecord.catatpengeluaran.CatatPengeluaranState;
import com.tkap11.spendingrecord.catatpengeluaran.ChooseCategoryState;
import com.tkap11.spendingrecord.repository.SpendingDatabase;
import com.tkap11.spendingrecord.repository.UserDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  private HashMap<String, CatatPengeluaranState> currentHandler = new HashMap<>();

  public void greetingMessage(String replyToken) {
    registerUser(source);
    replyFlexMenu(replyToken);
  }

  private void registerUser(Source source) {
    String senderId = source.getSenderId();
    UserProfileResponse sender = getProfile(senderId);
    userService.registerUser(sender.getUserId(), sender.getDisplayName());
  }

  /**
   * Reply menu flex.
   */
  public void replyFlexMenu(String replyToken) {
    FlexMessage flexMessage = botTemplate.createFlexMenu();
    List<Message> messageList = new ArrayList<>();
    messageList.add(new TextMessage("Hi apa yang ingin kamu lakukan ?"));
    messageList.add(flexMessage);
    reply(replyToken, messageList);
  }

  public void relpyFlexChooseCategory(String replyToken) {
    FlexMessage flexMessage = botTemplate.createFlexChooseCategory();
    reply(replyToken, flexMessage);
  }

  public void relpyFlexSisa(String replyToken) {
    FlexMessage flexMessage = botTemplate.createFlexSisa();
    reply(replyToken, flexMessage);
  }

  public void replyFlexAlarm(String replyToken) {
    FlexMessage flexMessage = botTemplate.createFlexAlarm();
    reply(replyToken, flexMessage);
  }

  public void replyFlexUbah(String replyToken) {
    FlexMessage flexMessage = botTemplate.createFlexUbah();
    reply(replyToken, flexMessage);
  }

  public boolean condition() {
    return false;
  }

  private void replyText(String replyToken, String message) {
    TextMessage textMessage = new TextMessage(message);
    reply(replyToken, textMessage);
  }

  public void reply(String replyToken, Message message) {
    ReplyMessage replyMessage = new ReplyMessage(replyToken, message);
    reply(replyMessage);
  }

  public void reply(String replyToken, List<Message> message) {
    ReplyMessage replyMessage = new ReplyMessage(replyToken, message);
    reply(replyMessage);
  }

  private void reply(ReplyMessage replyMessage) {
    try {
      lineMessagingClient.replyMessage(replyMessage).get();
    } catch (InterruptedException | ExecutionException | RuntimeException e) {
      e.printStackTrace();
    }
  }

  /**
   * Get user data.
   */
  public UserProfileResponse getProfile(String userId) {
    try {
      return lineMessagingClient.getProfile(userId).get();
    } catch (InterruptedException | ExecutionException | RuntimeException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Send message to user without request.
   */
  public void push(PushMessage pushMessage) {
    try {
      lineMessagingClient.pushMessage(pushMessage).get();
    } catch (InterruptedException | ExecutionException | RuntimeException e) {
      e.printStackTrace();
    }
  }

  public void pushAlarm(String to, Message message) {
    PushMessage pushMessage = new PushMessage(to, message);
    push(pushMessage);
  }

  /**
   * Send message to multiple user.
   */
  public void multicast(Set<String> to, Message message) {
    try {
      Multicast multicast = new Multicast(to, message);
      lineMessagingClient.multicast(multicast).get();
    } catch (InterruptedException | ExecutionException | RuntimeException e) {
      e.printStackTrace();
    }
  }

  /**
   * Handle user request.
   */
  public void handleMessageEvent(MessageEvent messageEvent) {
    TextMessageContent textMessageContent = (TextMessageContent) messageEvent.getMessage();
    String replyToken = messageEvent.getReplyToken();
    String userMessage = textMessageContent.getText();
    String senderId = source.getSenderId();
    CatatPengeluaranState oldHandler = currentHandler.get(senderId);
    if (oldHandler instanceof CatatPengeluaranState) {
      CatatPengeluaranState newHandler = oldHandler.handleUserRequest(userMessage.toLowerCase());
      currentHandler.put(senderId, newHandler);
      if (oldHandler.getMessageToUser().contains("berhasil dicatat")) {
        spendingService.saveRecord(oldHandler.getDescription());
      }
      replyText(replyToken, oldHandler.getMessageToUser());
    } else if (userMessage.toLowerCase().contains("menu")) {
      replyFlexMenu(replyToken);
    } else if (userMessage.toLowerCase().contains("catat")) {
      UserProfileResponse sender = getProfile(senderId);
      CatatPengeluaranState categoryHandler =
          new ChooseCategoryState(senderId, sender.getDisplayName());
      currentHandler.put(senderId, categoryHandler);
      relpyFlexChooseCategory(replyToken);
    } else if (textMessageContent.getText().toLowerCase().contains("sisa")) {
      relpyFlexSisa(replyToken);
    } else if (textMessageContent.getText().toLowerCase().contains("ingatkan")) {
      replyFlexAlarm(replyToken);
    } else if (textMessageContent.getText().toLowerCase().contains("ubah")) {
      replyFlexUbah(replyToken);
    } else {
      replyText(replyToken, "Sedang dalam pengembangan");
    }
  }
}
