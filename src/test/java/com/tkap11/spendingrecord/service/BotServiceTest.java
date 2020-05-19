package com.tkap11.spendingrecord.service;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BotServiceTest {

    @Mock
    private BotTemplate botTemplate;

    @InjectMocks
    private BotService botService;

    @Mock
    private Source source;

    @Mock
    private DatabaseService dbService;

    @Mock
    private LineMessagingClient lineMessagingClient;

//    @Test
//    void greetingMessageTest() {
//        FlexMessage flexMessage=botTemplate.createFlexMenu();
//        List<Message> messageList = new ArrayList<>();
//        messageList.add(new TextMessage("Hi apa yang ingin kamu lakukan ?"));
//        messageList.add(flexMessage);
//        when(lineMessagingClient.replyMessage(new ReplyMessage(
//                "replyToken", messageList
//        ))).thenReturn(CompletableFuture.completedFuture(
//                new BotApiResponse("ok", Collections.emptyList())
//        ));
//        botService.greetingMessage("replyToken");
//        verify(lineMessagingClient).replyMessage(new ReplyMessage(
//                "replyToken", messageList));
//    }

    @Test
    void handleMessageEventWhenUserSendMenuMessage() {
        final MessageEvent request = new MessageEvent<>(
                "replyToken",
                new UserSource("userId"),
                new TextMessageContent("id", "menu"),
                Instant.now()
        );
        FlexMessage flexMessage=botTemplate.createFlexMenu();
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
                "replyToken", singletonList(null)
        ))).thenReturn(CompletableFuture.completedFuture(
                new BotApiResponse("ok", Collections.emptyList())
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
}