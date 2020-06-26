package com.tkap11.spendingrecord.state.ingatkansaya;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.tkap11.spendingrecord.model.User;
import com.tkap11.spendingrecord.repository.UserDatabase;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IngatkanSayaStateTest {

  @Mock
  private UserDatabase userDatabase;

  @InjectMocks
  private IngatkanSayaState state = new IngatkanSayaConfirmationState();

  @Test
  void userConfirmation() {
  }

  @Test
  void userCancelOperation() {
  }

  @Test
  void unknownMessage() {
  }

  @Test
  void otherServiceMessage() {
  }

  @Test
  void getMessagetoUser() {
  }

  @Test
  void setUserId() {
  }

  @Test
  void handleUserRequest() {
    List<User> users = Arrays.asList(
        new User(Long.parseLong("1"), "userId", "displayName", "true")
    );
    when(userDatabase.getStatusIngatkanbyUserId("userId"))
        .thenReturn(users);
    when(userDatabase.setStatusIngatkan("false", "userId"))
        .thenReturn(1);

    state.setUserId("userId");
    IngatkanSayaState newState = state.handleUserRequest("ya");
    assertEquals(newState, null);

    users = Arrays.asList(
        new User(Long.parseLong("1"), "userId", "displayName", "false")
    );
    when(userDatabase.getStatusIngatkanbyUserId("userId"))
        .thenReturn(users);
    when(userDatabase.setStatusIngatkan("true", "userId"))
        .thenReturn(1);

    newState = state.handleUserRequest("ya");
    assertEquals(newState, null);

    newState = state.handleUserRequest("tidak");
    assertEquals(newState, null);

    newState = state.handleUserRequest("batal");
    assertTrue(newState instanceof IngatkanSayaState);


    newState = state.handleUserRequest("unknown message");
    assertTrue(newState instanceof IngatkanSayaState);

    newState = state.handleUserRequest("menu");
    assertTrue(newState instanceof IngatkanSayaState);

    Message message = state.getMessagetoUser();
    assertTrue(message instanceof TextMessage);
  }

  @Test
  void getUserIngatkanResponse() {
    List<User> users = Arrays.asList(
        new User(Long.parseLong("1"), "userId", "displayName", "true")
    );
    when(userDatabase.getStatusIngatkanbyUserId("userId"))
        .thenReturn(users);

    state.setUserId("userId");
    state.getUserIngatkanResponse();

    users = Arrays.asList(
        new User(Long.parseLong("1"), "userId", "displayName", "false")
    );
    when(userDatabase.getStatusIngatkanbyUserId("userId"))
        .thenReturn(users);
    state.getUserIngatkanResponse();
    assertTrue(state.getMessagetoUser() instanceof TextMessage);
  }
}