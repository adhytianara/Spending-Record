package com.tkap11.spendingrecord.service;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.doNothing;
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
import com.tkap11.spendingrecord.model.Budget;
import com.tkap11.spendingrecord.repository.UserDatabase;
import com.tkap11.spendingrecord.state.State;
import com.tkap11.spendingrecord.state.aturbudget.AturCategoryState;
import com.tkap11.spendingrecord.state.catatpengeluaran.ChooseCategoryState;
import com.tkap11.spendingrecord.state.ingatkansaya.IngatkanSayaConfirmationState;
import com.tkap11.spendingrecord.state.lihatlaporan.LihatCategoryLaporanState;
import com.tkap11.spendingrecord.state.sisabudget.SisaCategoryState;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
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
  private UserDatabase userService;

  @Mock
  private Source source;

  @Mock
  private LineMessagingClient lineMessagingClient;

  @Mock
  private LihatCategoryLaporanState lihatCategoryLaporanState;

  @Mock
  private IngatkanSayaConfirmationState ingatkanSayaConfirmationState;

  @Mock
  private HashMap<String, State> currentHandler = new HashMap<>();

  @Test
  void greetingMessageTest() {
    when(lineMessagingClient.getProfile(null))
        .thenReturn(CompletableFuture.completedFuture(
            new UserProfileResponse("displayName", "userId", "", "")
        ));
    String senderId = source.getSenderId();
    UserProfileResponse sender = botService.getProfile(senderId);
    FlexMessage flexMessage = botTemplate.createFlexMenu();
    List<Message> messageList = new ArrayList<>();
    messageList.add(new TextMessage("Hi " + sender.getDisplayName()
        + ", apa yang ingin kamu lakukan ?"));
    messageList.add(flexMessage);
    when(lineMessagingClient.replyMessage(new ReplyMessage(
        "replyToken", messageList
    ))).thenReturn(CompletableFuture.completedFuture(
        new BotApiResponse("ok", Collections.emptyList())
    ));
    botService.greetingMessage("replyToken");
    verify(lineMessagingClient).replyMessage(new ReplyMessage(
        "replyToken", messageList));
    when(lineMessagingClient.getProfile(null))
        .thenThrow(new RuntimeException());
    botService.getProfile(senderId);
  }

  @Test
  void handleMessageEventWhenUserSendMenuMessage() {
    final MessageEvent request = new MessageEvent<>(
        "replyToken",
        new UserSource("userId"),
        new TextMessageContent("id", "menu"),
        Instant.now()
    );
    when(lineMessagingClient.getProfile(null))
        .thenReturn(CompletableFuture.completedFuture(
            new UserProfileResponse("displayName", "userId", "", "")
        ));
    String senderId = source.getSenderId();
    UserProfileResponse sender = botService.getProfile(senderId);
    FlexMessage flexMessage = botTemplate.createFlexMenu();
    List<Message> messageList = new ArrayList<>();
    messageList.add(new TextMessage("Hi " + sender.getDisplayName()
        + ", apa yang ingin kamu lakukan ?"));
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
  void handleMessageEventWhenUserSendAturMessage() {
    final MessageEvent request = new MessageEvent<>(
        "replyToken",
        new UserSource("userId"),
        new TextMessageContent("id", "atur"),
        Instant.now()
    );
    when(lineMessagingClient.replyMessage(new ReplyMessage(
        "replyToken", singletonList(null))))
        .thenReturn(CompletableFuture.completedFuture(
            new BotApiResponse("ok", Collections.emptyList())
        ));
    botService.handleMessageEvent(request);
    verify(botTemplate, times(1)).createFlexBudgetCategory();
    when(currentHandler.get(null))
        .thenReturn(new AturCategoryState());
    botService.handleMessageEvent(request);
  }

  @Test
  void handleMessageEventWhenUserSendSisaCategoryMessage() {
    final MessageEvent request = new MessageEvent<>(
        "replyToken",
        new UserSource("userId"),
        new TextMessageContent("id", "sisa"),
        Instant.now()
    );
    when(lineMessagingClient.replyMessage(new ReplyMessage(
        "replyToken", singletonList(null))))
        .thenReturn(CompletableFuture.completedFuture(
            new BotApiResponse("ok", Collections.emptyList())
        ));
    botService.handleMessageEvent(request);
    verify(botTemplate, times(1)).createFlexSisaCategory();
    when(currentHandler.get(null))
        .thenReturn(new SisaCategoryState("userId"));
    botService.handleMessageEvent(request);
  }

  @Test
  void handleMessageEventWhenUserSendSisaMessage() {
    final MessageEvent request = new MessageEvent<>(
        "replyToken",
        new UserSource("userId"),
        new TextMessageContent("id", "makanan"),
        Instant.now()
    );
    FlexMessage flexMessage = botTemplate.createFlexSisa("category", "budget", "sisa");
    List<Message> messageList = new ArrayList<>();
    messageList.add(new TextMessage("Berikut adalah Sisa Budget-mu pada kategori : category"));
    messageList.add(flexMessage);

    when(lineMessagingClient.replyMessage(new ReplyMessage(
        "replyToken", messageList)))
        .thenReturn(CompletableFuture.completedFuture(
            new BotApiResponse("ok", Collections.emptyList())
        ));
    botService.replyFlexSisa("replyToken", "category", "budget", "sisa");
    verify(lineMessagingClient).replyMessage(new ReplyMessage(
        "replyToken", messageList));
  }

  @Test
  void handleMessageEventWhenUserSendSisaBackupMessage() {
    final MessageEvent request = new MessageEvent<>(
        "replyToken",
        new UserSource("userId"),
        new TextMessageContent("id", "makanan"),
        Instant.now()
    );

    when(lineMessagingClient.replyMessage(new ReplyMessage(
        "replyToken", singletonList(null))))
        .thenReturn(CompletableFuture.completedFuture(
            new BotApiResponse("ok", Collections.emptyList())
        ));
    botService.replyFlexSisaBackup("replyToken", "category");
    verify(botTemplate, times(1)).createFlexSisaBackup("CATEGORY");
  }

  @Test
  void handleMessageEventWhenUserSendIngatkanMessage() {
    TextMessage message = new TextMessage("ingatkan");
    doNothing().when(ingatkanSayaConfirmationState).setUserId(null);
    doNothing().when(ingatkanSayaConfirmationState).getUserIngatkanResponse();
    when(ingatkanSayaConfirmationState.getMessagetoUser())
        .thenReturn(message);
    when(lineMessagingClient.replyMessage(new ReplyMessage(
        "replyToken", Arrays.asList(message))))
        .thenReturn(CompletableFuture.completedFuture(
            new BotApiResponse("ok", Collections.emptyList())
        ));
    MessageEvent request = new MessageEvent<>(
        "replyToken",
        new UserSource("userId"),
        new TextMessageContent("id", "ingatkan"),
        Instant.now()
    );
    botService.handleMessageEvent(request);
    verify(ingatkanSayaConfirmationState, times(1)).setUserId(null);
    verify(ingatkanSayaConfirmationState, times(1)).getUserIngatkanResponse();
    verify(ingatkanSayaConfirmationState, times(1)).getMessagetoUser();

    when(currentHandler.get(null))
        .thenReturn(new IngatkanSayaConfirmationState());
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
        new TextMessageContent("id", "random message"),
        Instant.now()
    );
    when(lineMessagingClient.replyMessage(new ReplyMessage(
        "replyToken", singletonList(new TextMessage("Permintaan tidak dikenali. "
        + "Ketik 'menu' untuk melihat daftar tindakan yang bisa dilakukan."))
    ))).thenReturn(CompletableFuture.completedFuture(
        new BotApiResponse("ok", Collections.emptyList())
    ));
    botService.handleMessageEvent(request);
    verify(lineMessagingClient).replyMessage(new ReplyMessage(
        "replyToken", singletonList(new TextMessage("Permintaan tidak dikenali. "
        + "Ketik 'menu' untuk melihat daftar tindakan yang bisa dilakukan."))
    ));
  }

  @Test
  void catatPengeluaranState() {
    MessageEvent request = new MessageEvent<>(
        "replyToken",
        new UserSource("userId"),
        new TextMessageContent("id", "makanan"),
        Instant.now()
    );
    TextMessage textMessage = new TextMessage("Kategori berhasil dipilih. "
        + "Berapa uang yang kamu habiskan untuk makanan ?");
    when(currentHandler.get(null))
        .thenReturn(new ChooseCategoryState("senderId", "displayName"));
    when(lineMessagingClient.replyMessage(new ReplyMessage(
        "replyToken", Arrays.asList(textMessage))))
        .thenReturn(CompletableFuture.completedFuture(
            new BotApiResponse("ok", Collections.emptyList())
        ));
    botService.handleMessageEvent(request);
  }

  @Test
  void lihatLaporan() {
    FlexMessage flexMessage = botTemplate.createFlexDetailLaporan("url", 1, 2, 3);
    doNothing().when(lihatCategoryLaporanState).setUserId(null);
    when(lihatCategoryLaporanState.handleUserRequest("lihat detail makanan"))
        .thenReturn(new LihatCategoryLaporanState());
    when(lihatCategoryLaporanState.getMessagetoUser())
        .thenReturn(flexMessage);
    when(lineMessagingClient.replyMessage(new ReplyMessage(
        "replyToken", singletonList(null))))
        .thenReturn(CompletableFuture.completedFuture(
            new BotApiResponse("ok", Collections.emptyList())
        ));
    MessageEvent request = new MessageEvent<>(
        "replyToken",
        new UserSource("userId"),
        new TextMessageContent("id", "lihat detail makanan"),
        Instant.now()
    );
    botService.handleMessageEvent(request);
    verify(botTemplate, times(1)).createFlexDetailLaporan("url", 1, 2, 3);
    request = new MessageEvent<>(
        "replyToken",
        new UserSource("userId"),
        new TextMessageContent("id", "lihat laporan"),
        Instant.now()
    );
    botService.handleMessageEvent(request);
    when(currentHandler.get(null))
        .thenReturn(new LihatCategoryLaporanState());
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
  void reminderUsers() {
    Set<String> userIdList = new HashSet<>();
    TextMessage textSiang = new TextMessage("Sudah makan siang? "
        + "jangan lupa catat pengeluarannya ya!");
    when(userService.getAllUserIngatkanAktif()).thenReturn(userIdList);
    when(lineMessagingClient.multicast(new Multicast(
        userIdList, textSiang
    ))).thenReturn(CompletableFuture.completedFuture(
        new BotApiResponse("ok", Collections.emptyList())
    ));
    botService.reminderSiang();
    verify(lineMessagingClient).multicast(new Multicast(
        userIdList, textSiang
    ));

    TextMessage textMalam = new TextMessage("Sebelum tidur, catat pengeluaran hari ini dulu yuk!");
    when(userService.getAllUserIngatkanAktif()).thenReturn(userIdList);
    when(lineMessagingClient.multicast(new Multicast(
        userIdList, textMalam
    ))).thenReturn(CompletableFuture.completedFuture(
        new BotApiResponse("ok", Collections.emptyList())
    ));
    botService.reminderMalam();
    verify(lineMessagingClient).multicast(new Multicast(
        userIdList, textMalam
    ));

    TextMessage monthly = new TextMessage("Sudah Awal bulan lho. "
        + "Jangan lupa atur budgetmu untuk bulan ini ya.");
    when(userService.getAllUserIngatkanAktif()).thenReturn(userIdList);
    when(lineMessagingClient.multicast(new Multicast(
        userIdList, monthly
    ))).thenReturn(CompletableFuture.completedFuture(
        new BotApiResponse("ok", Collections.emptyList())
    ));
    botService.monthlyNotification();
    verify(lineMessagingClient).multicast(new Multicast(
        userIdList, monthly
    ));
  }

  @Test
  void condition() {
    botService.condition();
  }

  @Test
  void executeSisa(){
    List<Budget> sisaResult = Arrays.asList(
        new Budget("userId", "makanan", 10000,
            1000, "juni")
    );
    when(lineMessagingClient.replyMessage(new ReplyMessage(
        "replyToken", singletonList(null))))
        .thenReturn(CompletableFuture.completedFuture(
            new BotApiResponse("ok", Collections.emptyList())
        ));
    botService.executeSisa("replyToken", sisaResult, null);
    String[] sisaBackup = new String[]{"makanan", "tagihan"};
    botService.executeSisa("replyToken", null, sisaBackup);
  }
}