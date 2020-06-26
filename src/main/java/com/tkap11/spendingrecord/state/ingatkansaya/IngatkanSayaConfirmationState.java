package com.tkap11.spendingrecord.state.ingatkansaya;

import com.linecorp.bot.model.message.TextMessage;
import com.tkap11.spendingrecord.model.User;
import com.tkap11.spendingrecord.repository.UserDatabase;
import com.tkap11.spendingrecord.service.BotTemplate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngatkanSayaConfirmationState extends IngatkanSayaState {
  @Autowired
  private BotTemplate botTemplate;
  @Autowired
  private UserDatabase userDatabase;

  @Override
  public IngatkanSayaState userConfirmation(String userMessage) {
    List<User> user = userDatabase.getStatusIngatkanbyUserId(userId);
    boolean statusIngatkan = Boolean.parseBoolean(user.get(0).getIngatkan());
    if (statusIngatkan) {
      userDatabase.setStatusIngatkan("false", userId);
      messageToUser = "Fitur ingatkan dinonaktifkan";
    } else {
      userDatabase.setStatusIngatkan("true", userId);
      messageToUser = "Fitur ingatkan berhasil diaktifkan";
    }
    this.message = new TextMessage(messageToUser);
    return null;
  }

  @Override
  public IngatkanSayaState userCancelOperation() {
    messageToUser = "Proses dibatalkan";
    this.message = new TextMessage(messageToUser);
    return null;
  }

  @Override
  public IngatkanSayaState unknownMessage() {
    messageToUser = "Permintaan tidak dikenali. Jawab pesan ini dengan 'ya' atau 'tidak'.";
    this.message = new TextMessage(messageToUser);
    return this;
  }

  @Override
  public IngatkanSayaState otherServiceMessage() {
    messageToUser = "Untuk beralih ke fitur lain, ketik 'ya' atau 'tidak' terlebih dahulu";
    this.message = new TextMessage(messageToUser);
    return this;
  }

  @Override
  public void getUserIngatkanResponse() {
    List<User> user = userDatabase.getStatusIngatkanbyUserId(userId);
    boolean statusIngatkan = Boolean.parseBoolean(user.get(0).getIngatkan());
    if (statusIngatkan) {
      message = new TextMessage(
          "Fitur ingatkan saat ini sedang aktif, apakah kamu ingin menonaktifkan fitur ini?\n"
              + "Balas dengan 'ya' atau 'tidak'"
      );
    } else {
      message = new TextMessage(
          "Saat ini fitur ingatkan tidak aktif. Ingin mengaktifkan fitur ini?\n"
              + "Balas dengan 'ya' atau 'tidak'\n"
              + "(Kamu akan mendapatkan notifikasi pukul 13:00 dan 21:00 setiap harinya)"
      );
    }
  }
}
