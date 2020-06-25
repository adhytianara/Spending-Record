package com.tkap11.spendingrecord.service;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.Multicast;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.profile.UserProfileResponse;
import com.linecorp.bot.model.response.BotApiResponse;
import com.tkap11.spendingrecord.catatpengeluaran.CatatPengeluaranState;
import com.tkap11.spendingrecord.catatpengeluaran.ChooseCategoryState;
import com.tkap11.spendingrecord.catatpengeluaran.ConfirmationState;
import com.tkap11.spendingrecord.catatpengeluaran.InsertMoneyState;
import com.tkap11.spendingrecord.repository.SpendingDatabase;
import com.tkap11.spendingrecord.repository.UserDatabase;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BotServiceTest {

  @Mock
  private BotTemplate botTemplate;

  @InjectMocks
  private BotService botService;

  @Mock
  UserDatabase userDatabase;

  @Mock
  private Source source;

  @Mock
  private LineMessagingClient lineMessagingClient;

  @Mock
  private SpendingDatabase spendingService;

  @Spy
  private HashMap<String, CatatPengeluaranState> currentHandler = new HashMap<>();

  @Test
  void greetingMessageTest() {
    FlexMessage flexMessage = botTemplate.createFlexMenu();
    List<Message> messageList = new ArrayList<>();
    messageList.add(new TextMessage("Hi apa yang ingin kamu lakukan ?"));
    messageList.add(flexMessage);
    when(lineMessagingClient.replyMessage(new ReplyMessage(
        "replyToken", messageList
    ))).thenReturn(CompletableFuture.completedFuture(
        new BotApiResponse("ok", Collections.emptyList())
    ));
    when(lineMessagingClient.getProfile(null))
        .thenReturn(CompletableFuture.completedFuture(
            new UserProfileResponse("displayName", "userId", "", "")
        ));
    when(userDatabase.registerUser("userId", "displayName"))
        .thenReturn(2);
    botService.greetingMessage("replyToken");
    verify(lineMessagingClient).replyMessage(new ReplyMessage(
        "replyToken", messageList));
    when(lineMessagingClient.getProfile(null))
        .thenThrow(new RuntimeException());
    botService.getProfile(null);
  }

  @Test
  void handleMessageEventWhenUserSendMenuMessage() {
    final MessageEvent request = new MessageEvent<>(
        "replyToken",
        new UserSource("userId"),
        new TextMessageContent("id", "menu"),
        Instant.now()
    );
    FlexMessage flexMessage = botTemplate.createFlexMenu();
    List<Message> messageList = new ArrayList<>();
    messageList.add(new TextMessage("Hi apa yang ingin kamu lakukan ?"));
    messageList.add(flexMessage);
    when(lineMessagingClient.replyMessage(new ReplyMessage(
        "replyToken", messageList
    ))).thenReturn(CompletableFuture.completedFuture(
        new BotApiResponse("ok", Collections.emptyList())
    ));
    botService.handleMessageEvent(request);
    verify(lineMessagingClient).replyMessage(new ReplyMessage(
        "replyToken", messageList));
  }

  @Test
  void handleMessageEventWhenUserSendCatatMessage() {
    final MessageEvent request = new MessageEvent<>(
        "replyToken",
        new UserSource("userId"),
        new TextMessageContent("id", "catat"),
        Instant.now()
    );
    when(lineMessagingClient.replyMessage(new ReplyMessage(
        "replyToken", singletonList(null))))
        .thenReturn(CompletableFuture.completedFuture(
            new BotApiResponse("ok", Collections.emptyList())
        ));
    when(lineMessagingClient.getProfile(null))
        .thenReturn(CompletableFuture.completedFuture(
            new UserProfileResponse("name", "id", "", "")
        ));
    botService.handleMessageEvent(request);
    verify(botTemplate, times(1)).createFlexChooseCategory();
  }

  @Test
  void handleMessageEventWhenUserSendSisaMessage() {
    final MessageEvent request = new MessageEvent<>(
        "replyToken",
        new UserSource("userId"),
        new TextMessageContent("id", "sisa"),
        Instant.now()
    );
    when(lineMessagingClient.replyMessage(new ReplyMessage(
        "replyToken", singletonList(null)
    ))).thenReturn(CompletableFuture.completedFuture(
        new BotApiResponse("ok", Collections.emptyList())
    ));
    botService.handleMessageEvent(request);
    verify(botTemplate, times(1)).createFlexSisa();
  }

  @Test
  void handleMessageEventWhenUserSendIngatkanMessage() {
    final MessageEvent request = new MessageEvent<>(
        "replyToken",
        new UserSource("userId"),
        new TextMessageContent("id", "ingatkan"),
        Instant.now()
    );
    when(lineMessagingClient.replyMessage(new ReplyMessage(
        "replyToken", singletonList(null)
    ))).thenReturn(CompletableFuture.completedFuture(
        new BotApiResponse("ok", Collections.emptyList())
    ));
    botService.handleMessageEvent(request);
    verify(botTemplate, times(1)).createFlexAlarm();
    when(lineMessagingClient.replyMessage(new ReplyMessage(
        "replyToken", singletonList(null)
    ))).thenThrow(new RuntimeException());
    botService.handleMessageEvent(request);
  }

  @Test
  void handleMessageEventWhenUserSendUbahMessage() {
    final MessageEvent request = new MessageEvent<>(
        "replyToken",
        new UserSource("userId"),
        new TextMessageContent("id", "ubah"),
        Instant.now()
    );
    when(lineMessagingClient.replyMessage(new ReplyMessage(
        "replyToken", singletonList(null)
    ))).thenReturn(CompletableFuture.completedFuture(
        new BotApiResponse("ok", Collections.emptyList())
    ));
    botService.handleMessageEvent(request);
    verify(botTemplate, times(1)).createFlexUbah();
  }

  @Test
  void handleMessageEventWhenUserSendRandomMessage() {
    final MessageEvent request = new MessageEvent<>(
        "replyToken",
        new UserSource("userId"),
        new TextMessageContent("id", "ds"),
        Instant.now()
    );
    when(lineMessagingClient.replyMessage(new ReplyMessage(
        "replyToken", singletonList(new TextMessage("Sedang dalam pengembangan"))
    ))).thenReturn(CompletableFuture.completedFuture(
        new BotApiResponse("ok", Collections.emptyList())
    ));
    botService.handleMessageEvent(request);
    verify(lineMessagingClient).replyMessage(new ReplyMessage(
        "replyToken", singletonList(new TextMessage("Sedang dalam pengembangan"))
    ));
  }

  @Test
  void catatPengeluaranState() {
    MessageEvent request = new MessageEvent<>(
        "replyToken",
        new UserSource("userId"),
        new TextMessageContent("id", "ya"),
        Instant.now()
    );
    CatatPengeluaranState state = new ConfirmationState(
        new InsertMoneyState(
            new ChooseCategoryState(
                "userId", "displayName")));
    when(currentHandler.get(null))
        .thenReturn(state);
    when(lineMessagingClient.replyMessage(new ReplyMessage(
        "replyToken", singletonList(null))))
        .thenReturn(CompletableFuture.completedFuture(
            new BotApiResponse("ok", Collections.emptyList())
        ));
    when(spendingService.saveRecord("userId;displayName;null;")).thenReturn(1);
    botService.handleMessageEvent(request);
  }


  @Test
  void pushAlarm() {
    TextMessage textMessage = new TextMessage("halo");
    when(lineMessagingClient.pushMessage(new PushMessage(
        "user", textMessage
    ))).thenReturn(CompletableFuture.completedFuture(
        new BotApiResponse("ok", Collections.emptyList())
    ));
    PushMessage pushMessage = new PushMessage("user", textMessage);
    botService.push(pushMessage);
    verify(lineMessagingClient).pushMessage(new PushMessage(
        "user", textMessage
    ));
    when(lineMessagingClient.pushMessage(new PushMessage(
        "user", textMessage
    ))).thenThrow(new RuntimeException());
    pushMessage = new PushMessage("user", textMessage);
    botService.push(pushMessage);
  }

  @Test
  void multiCast() {
    TextMessage textMessage = new TextMessage("halo");
    Set<String> users = new HashSet<>();
    users.add("user");
    when(lineMessagingClient.multicast(new Multicast(
        users, textMessage
    ))).thenReturn(CompletableFuture.completedFuture(
        new BotApiResponse("ok", Collections.emptyList())
    ));
    botService.multicast(users, textMessage);
    verify(lineMessagingClient).multicast(new Multicast(
        users, textMessage
    ));
    when(lineMessagingClient.multicast(new Multicast(
        users, textMessage
    ))).thenThrow(new RuntimeException());
    botService.multicast(users, textMessage);
  }

  @Test
  void condition() {
    botService.condition();
  }
}