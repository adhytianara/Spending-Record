package com.tkap11.spendingrecord.state.lihatlaporan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.tkap11.spendingrecord.model.Spending;
import com.tkap11.spendingrecord.repository.SpendingDatabase;
import com.tkap11.spendingrecord.service.BotTemplate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LihatLaporanStateTest {
  @Mock
  private BotTemplate botTemplate;

  @Mock
  private SpendingDatabase spendingDatabase;

  @InjectMocks
  private LihatLaporanState state = new LihatCategoryLaporanState();

  @Test
  void handleUserRequest() {
    List<Spending> laporan = Arrays.asList(new Spending(Long.parseLong("1"), "userId",
        "Adhytia", "category", "2020-06-1T11:51:53.028", "10000"));
    when(spendingDatabase.getByUserId("userId"))
        .thenReturn(laporan);

    state.setUserId("userId");
    LihatLaporanState newState = state.handleUserRequest("lihat detail makanan");
    assertEquals(newState, null);

    newState = state.handleUserRequest("menu");
    assertTrue(newState instanceof LihatLaporanState);

    newState = state.handleUserRequest("batal");
    assertEquals(newState, null);

    newState = state.handleUserRequest("unknown message");
    assertTrue(newState instanceof LihatLaporanState);

    Message message = state.getMessagetoUser();
    assertTrue(message instanceof TextMessage);
  }
}