package com.tkap11.spendingrecord.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.LineSignatureValidator;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.ReplyEvent;
import com.linecorp.bot.model.objectmapper.ModelObjectMapper;
import com.tkap11.spendingrecord.model.EventsModel;
import com.tkap11.spendingrecord.service.BotService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

  /**
   * Webhook to receive user request.
   */
  @RequestMapping(value = "/webhook", method = RequestMethod.POST)
  public ResponseEntity<String> callback(
        @RequestHeader("X-Line-Signature") String lineSignature,
        @RequestBody String eventsPayload) {
    try {
      if (!lineSignatureValidator.validateSignature(eventsPayload.getBytes(), lineSignature)) {
        throw new RuntimeException("Invalid Signature Validation");
      }
      handleEvent(eventsPayload, lineSignature);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Handle user request.
   */
  public void handleEvent(String eventsPayload, String lineSignature)
      throws JsonProcessingException {
    ObjectMapper objectMapper = ModelObjectMapper.createNewObjectMapper();
    EventsModel eventsModel = objectMapper.readValue(eventsPayload, EventsModel.class);
    eventsModel.getEvents().forEach(event -> {
      if (event instanceof FollowEvent) {
        String replyToken = ((FollowEvent) event).getReplyToken();
        botService.source = event.getSource();
        botService.greetingMessage(replyToken);
      } else if (event instanceof MessageEvent) {
        botService.source = event.getSource();
        botService.handleMessageEvent((MessageEvent) event);
      }
    });
  }
}