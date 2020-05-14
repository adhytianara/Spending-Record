package com.tkap11.spendingrecord.service;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.tkap11.spendingrecord.repository.UserDatabase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Collections;
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
    private UserDatabase dbService;

    @Mock
    private LineMessagingClient lineMessagingClient;

//    @Test
//    void greetingMessageTest() {
//        when(lineMessagingClient.replyMessage(new ReplyMessage(
//                "replyToken", singletonList(null)
//        ))).thenReturn(CompletableFuture.completedFuture(
//                new BotApiResponse("ok", Collections.emptyList())
//        ));
//        when(source.getSenderId()).thenReturn("dummyId");
//        when(dbService.registerUser("dummyId", "dummyName")).thenReturn(2);
//        botService.greetingMessage("replyToken");
//        verify(botTemplate, times(1)).createFlexMenu();
//    }

    @Test
    void handleMessageEventWhenUserSendMenuMessage() {
        final MessageEvent request = new MessageEvent<>(
                "replyToken",
                new UserSource("userId"),
                new TextMessageContent("id", "menu"),
                Instant.now()
        );
        when(lineMessagingClient.replyMessage(new ReplyMessage(
                "replyToken", singletonList(null)
        ))).thenReturn(CompletableFuture.completedFuture(
                new BotApiResponse("ok", Collections.emptyList())
        ));
        botService.handleMessageEvent(request);
        verify(botTemplate, times(1)).createFlexMenu();
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