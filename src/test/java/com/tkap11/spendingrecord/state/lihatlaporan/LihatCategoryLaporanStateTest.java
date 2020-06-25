package com.tkap11.spendingrecord.state.lihatlaporan;

import com.tkap11.spendingrecord.model.Spending;
import com.tkap11.spendingrecord.repository.SpendingDatabase;
import com.tkap11.spendingrecord.service.BotTemplate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LihatCategoryLaporanStateTest {
  @Mock
  private BotTemplate botTemplate;

  @Mock
  private SpendingDatabase spendingDatabase;

  @InjectMocks
  private LihatCategoryLaporanState state = new LihatCategoryLaporanState();

  @Test
  void userChooseCategory() {
    LocalDateTime localDateTime = LocalDateTime.now();
    localDateTime = localDateTime.plusHours(7);
    String timestamp = localDateTime.toString();
    List<Spending> laporan = Arrays.asList(
        new Spending(Long.parseLong("1"), "userId", "Adhytia",
            "makanan", timestamp, "10000"));
    when(spendingDatabase.getByUserId("userId"))
        .thenReturn(laporan);

    state.setUserId("userId");
    LihatLaporanState newState = state.handleUserRequest("lihat detail makanan");
    assertEquals(newState, null);

    newState = state.handleUserRequest("lihat detail transportasi");
    assertEquals(newState, null);

    newState = state.handleUserRequest("lihat detail tagihan");
    assertEquals(newState, null);

    newState = state.handleUserRequest("lihat detail belanja");
    assertEquals(newState, null);

    newState = state.handleUserRequest("lihat detail lainnya");
    assertEquals(newState, null);

    newState = state.handleUserRequest("lihat detail persentase");
    assertEquals(newState, null);

    newState = state.handleUserRequest("lihat detail semua kategori");
    assertEquals(newState, null);
  }
}