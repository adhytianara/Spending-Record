package com.tkap11.spendingrecord.service;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
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
    private LineMessagingClient lineMessagingClient;

    @Test
    void greetingMessageTest() {
        // mock line bot api client response
        when(lineMessagingClient.replyMessage(new ReplyMessage(
                "replyToken", singletonList(null)
        ))).thenReturn(CompletableFuture.completedFuture(
                new BotApiResponse("ok", Collections.emptyList())
        ));

        botService.greetingMessage("replyToken");

        // confirm createFlexMenu is called
        verify(botTemplate, times(1)).createFlexMenu();
    }

    @Test
    void handleMessageEventWhenUserSendMenuMessage() {
        // if user send "menu" as text message
        final MessageEvent request = new MessageEvent<>(
                "replyToken",
                new UserSource("userId"),
                new TextMessageContent("id", "menu"),
                Instant.now()
        );

        // mock line bot api client response
        when(lineMessagingClient.replyMessage(new ReplyMessage(
                "replyToken", singletonList(null)
        ))).thenReturn(CompletableFuture.completedFuture(
                new BotApiResponse("ok", Collections.emptyList())
        ));

        botService.handleMessageEvent(request);

        // confirm createFlexMenu is called
        verify(botTemplate, times(1)).createFlexMenu();
    }

    @Test
    void handleMessageEventWhenUserSendOtherThanMenuMessage() {
        final MessageEvent request = new MessageEvent<>(
                "replyToken",
                new UserSource("userId"),
                new TextMessageContent("id", "text"),
                Instant.now()
        );

        // mock line bot api client response
        when(lineMessagingClient.replyMessage(new ReplyMessage(
                "replyToken", singletonList(new TextMessage("Sedang dalam pengembangan"))
        ))).thenReturn(CompletableFuture.completedFuture(
                new BotApiResponse("ok", Collections.emptyList())
        ));

        botService.handleMessageEvent(request);

        // confirm createFlexMenu is called
        verify(lineMessagingClient).replyMessage(new ReplyMessage(
                "replyToken", singletonList(new TextMessage("Sedang dalam pengembangan"))
        ));
    }

    @Test
    void handleMessageEventWhenUserSendSisaMessage(){
        // if the user text message contains "sisa"
        final MessageEvent request = new MessageEvent<>(
                "replyToken",
                new UserSource("userId"),
                new TextMessageContent("id", "sisa"),
                Instant.now()
        );

        // mock line bot api client response
        when(lineMessagingClient.replyMessage(new ReplyMessage(
                "replyToken", singletonList(null)
        ))).thenReturn(CompletableFuture.completedFuture(
                new BotApiResponse("ok", Collections.emptyList())
        ));

        botService.handleMessageEvent(request);

        // confirm createFlexSisa is called
        verify(botTemplate, times(1)).createFlexSisa();
    }


}