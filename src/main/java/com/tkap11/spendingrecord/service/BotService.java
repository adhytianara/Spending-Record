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
import com.tkap11.spendingrecord.sisabudget.SisaBudgetState;
import com.tkap11.spendingrecord.sisabudget.SisaCategoryState;
import com.tkap11.spendingrecord.repository.SpendingDatabase;
import com.tkap11.spendingrecord.repository.SisaDatabase;
import com.tkap11.spendingrecord.repository.UserDatabase;
import com.tkap11.spendingrecord.model.Spending;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BotService {

  public Source source;
  @Autowired
  private LineMessagingClient lineMessagingClient;
  @Autowired
  private BotTemplate botTemplate;
  @Autowired
  private UserDatabase userService;
  @Autowired
  private SpendingDatabase spendingService;
  @Autowired
  private SisaDatabase sisaService;

  private HashMap<String, CatatPengeluaranState> currentHandler = new HashMap<>();
  private HashMap<String, SisaBudgetState> currentHandlerSisa =new HashMap<>();


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
    String senderId = source.getSenderId();
    UserProfileResponse sender = getProfile(senderId);

    FlexMessage flexMessage=botTemplate.createFlexMenu();
    List<Message> messageList = new ArrayList<>();
    messageList.add(new TextMessage("Hi " + sender.getDisplayName() + ", apa yang ingin kamu lakukan ?"));
    messageList.add(flexMessage);
    reply(replyToken, messageList);
  }

  public void relpyFlexChooseCategory(String replyToken) {
    FlexMessage flexMessage = botTemplate.createFlexChooseCategory();
    reply(replyToken, flexMessage);
  }

  /**
   * Reply sisa kategori flex.
   */
  public void relpyFlexSisaCategory(String replyToken) {
    FlexMessage flexMessage = botTemplate.createFlexSisaCategory();
    reply(replyToken, flexMessage);
  }

  /**
   * Reply sisa flex.
   */
  public void relpyFlexSisa(String replyToken, String category, String nominal) {
    FlexMessage flexMessage = botTemplate.createFlexSisa(category.toUpperCase(), nominal);
    List<Message> messageList = new ArrayList<>();
    messageList.add(new TextMessage("Berikut adalah Sisa Budget-mu pada kategori : " + category));
    messageList.add(flexMessage);
    reply(replyToken, messageList);
  }

  public void relpyFlexSisaBackup(String replyToken, String category) {
    FlexMessage flexMessage = botTemplate.createFlexSisaBackup(category.toUpperCase());
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
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Get user data.
   */
  public UserProfileResponse getProfile(String userId) {
    try {
      return lineMessagingClient.getProfile(userId).get();
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  private void push(PushMessage pushMessage) {
    try {
      lineMessagingClient.pushMessage(pushMessage).get();
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  private void pushAlarm(String to, Message message) {
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
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  private void sendMulticast(Set<String> sourceUsers, String txtMessage) {
    TextMessage message = new TextMessage(txtMessage);
    Multicast multicast = new Multicast(sourceUsers, message);

    try {
      lineMessagingClient.multicast(multicast).get();
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  private void executeSisa(String replyToken, List<Spending> sisaResult, String[] sisaBackup) {
    try {
      String category = sisaResult.get(0).getCategory();
      String nominal = sisaResult.get(0).getNominal();
      NumberFormat formatter = new DecimalFormat("#,###");
      String nominalFormatted = formatter.format(Double.parseDouble(nominal));
      relpyFlexSisa(replyToken, category, nominalFormatted);
    } catch (Exception e){
      String category = sisaBackup[1];
      relpyFlexSisaBackup(replyToken, category);
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
    SisaBudgetState oldHandlerSisa = currentHandlerSisa.get(senderId);
    List<String> categories = Arrays.asList("makanan", "transportasi", "tagihan", "belanja", "lainnya");
    if (oldHandler instanceof CatatPengeluaranState) {
      CatatPengeluaranState newHandler = oldHandler.handleUserRequest(userMessage.toLowerCase());
      currentHandler.put(senderId, newHandler);
      if (oldHandler.getMessageToUser().contains("berhasil dicatat")) {
        spendingService.saveRecord(oldHandler.getDescription());
      }
      replyText(replyToken, oldHandler.getMessageToUser());
    } else if (oldHandlerSisa instanceof SisaBudgetState) {
      SisaBudgetState newHandlerSisa = oldHandlerSisa.handleUserRequest(userMessage.toLowerCase());
      currentHandlerSisa.put(senderId, newHandlerSisa);
      if (categories.contains(userMessage.toLowerCase())) {
        executeSisa(replyToken, sisaService.sisaBudget(oldHandlerSisa.getDescription()), oldHandlerSisa.getDescription().split(";"));
      } else {
        replyText(replyToken, oldHandlerSisa.getMessageToUser());
      }
    } else if (userMessage.toLowerCase().contains("menu")) {
      replyFlexMenu(replyToken);
    } else if (userMessage.toLowerCase().contains("catat")) {
      UserProfileResponse sender = getProfile(senderId);
      CatatPengeluaranState categoryHandler =
          new ChooseCategoryState(senderId, sender.getDisplayName());
      currentHandler.put(senderId, categoryHandler);
      relpyFlexChooseCategory(replyToken);
    } else if (textMessageContent.getText().toLowerCase().contains("sisa")) {
      SisaBudgetState categoryHandlerSisa = new SisaCategoryState(senderId);
      currentHandlerSisa.put(senderId, categoryHandlerSisa);
      relpyFlexSisaCategory(replyToken);
    } else if (textMessageContent.getText().toLowerCase().contains("ingatkan")) {
      replyFlexAlarm(replyToken);
    } else if (textMessageContent.getText().toLowerCase().contains("ubah")) {
      replyFlexUbah(replyToken);
    } else {
      replyText(replyToken, "Sedang dalam pengembangan");
    }

  }
}
