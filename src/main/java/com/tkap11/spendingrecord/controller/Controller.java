package com.tkap11.spendingrecord.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.LineSignatureValidator;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.ReplyEvent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.objectmapper.ModelObjectMapper;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.Multicast;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import com.tkap11.spendingrecord.model.EventsModel;
import com.tkap11.spendingrecord.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

@LineMessageHandler
@RestController
public class Controller {

    @Autowired
    private BotService botService;

    @Autowired
    @Qualifier("lineMessagingClient")
    private LineMessagingClient lineMessagingClient;

    @Autowired
    @Qualifier("lineSignatureValidator")
    private LineSignatureValidator lineSignatureValidator;

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        botService.handleMessageEvent(event);
    }

    @EventMapping
    public void handleFollowEvent(FollowEvent event) {
        String replyToken=((ReplyEvent) event).getReplyToken();
        botService.source = event.getSource();
        botService.greetingMessage(replyToken);
    }
}