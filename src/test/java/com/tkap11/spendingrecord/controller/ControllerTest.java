package com.tkap11.spendingrecord.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.client.LineSignatureValidator;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.ReplyEvent;
import com.linecorp.bot.model.objectmapper.ModelObjectMapper;
import com.tkap11.spendingrecord.model.EventsModel;
import com.tkap11.spendingrecord.service.BotService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ControllerTest {
  @InjectMocks
  private Controller controller;

  @Mock
  private BotService botService;

  @Mock
  private LineSignatureValidator lineSignatureValidator;

  @Test
  void handleEvent() throws JsonProcessingException {
    String eventsPayload = "{\"events\":[{\"type\":\"message\",\"replyToken\""
        + ":\"c44827c4d26249d4be7b4e7da3a2c8f9\",\"source\":{\"userId\""
        + ":\"Ue170feb3994bf25ad7adc3640564f75b\",\"type\":\"user\"},\"timestamp\""
        + ":1593088271679,\"mode\":\"active\",\"message\":{\"type\":\"text\",\"id\""
        + ":\"12207939161791\",\"text\":\"ASJDBJ\"}}],\"destination\""
        + ":\"Udc4eaf8c8e5981d0fedfbf27fb1f1d51\"}";
    String lineSignature = "BnSbFgSAlKyUWgPPcTGoqPnOTsy5x5YE4a2BHQqAVys=";
    ObjectMapper objectMapper = ModelObjectMapper.createNewObjectMapper();
    EventsModel eventsModel = objectMapper.readValue(eventsPayload, EventsModel.class);
    Event event = eventsModel.getEvents().get(0);

    doNothing().when(botService).handleMessageEvent((MessageEvent) event);
    controller.handleEvent(eventsPayload, lineSignature);

    eventsPayload = "{\"events\":[{\"type\":\"follow\",\"replyToken\""
        + ":\"408f3a41d18e4ccdbcf61dde3708187c\",\"source\":{\"userId\""
        + ":\"Ue170feb3994bf25ad7adc3640564f75b\",\"type\":\"user\"},\"timestamp\""
        + ":1593090013579,\"mode\":\"active\"}],\"destination\""
        + ":\"Udc4eaf8c8e5981d0fedfbf27fb1f1d51\"}";
    lineSignature = "dEVWOiJPCb4aIUmLc1UxWANBQnS4u+ltX2p/1Hi/X5E=";
    objectMapper = ModelObjectMapper.createNewObjectMapper();
    eventsModel = objectMapper.readValue(eventsPayload, EventsModel.class);
    event = eventsModel.getEvents().get(0);
    String replyToken = ((ReplyEvent) event).getReplyToken();
    doNothing().when(botService).greetingMessage(replyToken);
    controller.handleEvent(eventsPayload, lineSignature);

    verify(botService).greetingMessage(replyToken);
    verify(botService).handleMessageEvent((MessageEvent) event);
  }

  @Test
  void callback() throws JsonProcessingException {
    String eventsPayload = "{\"events\":[{\"type\":\"message\",\"replyToken\""
        + ":\"c44827c4d26249d4be7b4e7da3a2c8f9\",\"source\":{\"userId\""
        + ":\"Ue170feb3994bf25ad7adc3640564f75b\",\"type\":\"user\"},\"timestamp\""
        + ":1593088271679,\"mode\":\"active\",\"message\":{\"type\":\"text\",\"id\""
        + ":\"12207939161791\",\"text\":\"ASJDBJ\"}}],\"destination\""
        + ":\"Udc4eaf8c8e5981d0fedfbf27fb1f1d51\"}";
    String lineSignature = "BnSbFgSAlKyUWgPPcTGoqPnOTsy5x5YE4a2BHQqAVys=";

    when(lineSignatureValidator.validateSignature(eventsPayload.getBytes(),
        lineSignature)).thenReturn(false);
    controller.callback(lineSignature, eventsPayload);

    when(lineSignatureValidator.validateSignature(eventsPayload.getBytes(),
        lineSignature)).thenReturn(true);

    ObjectMapper objectMapper = ModelObjectMapper.createNewObjectMapper();
    EventsModel eventsModel = objectMapper.readValue(eventsPayload, EventsModel.class);
    Event event = eventsModel.getEvents().get(0);

    doNothing().when(botService).handleMessageEvent((MessageEvent) event);
    controller.callback(lineSignature, eventsPayload);

    verify(lineSignatureValidator, times(2)).validateSignature(eventsPayload.getBytes(),
        lineSignature);
  }
}