package com.tkap11.spendingrecord.state.lihatlaporan;

import com.linecorp.bot.model.message.TextMessage;
import com.tkap11.spendingrecord.model.Spending;
import com.tkap11.spendingrecord.repository.SpendingDatabase;
import com.tkap11.spendingrecord.service.BotTemplate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LihatCategoryLaporanState extends LihatLaporanState {
  @Autowired
  private BotTemplate botTemplate;
  @Autowired
  private SpendingDatabase spendingService;

  @Override
  public LihatLaporanState userCancelOperation() {
    messageToUser = "Proses dibatalkan";
    this.message = new TextMessage(messageToUser);
    return null;
  }

  @Override
  public LihatLaporanState unknownMessage() {
    messageToUser = "Kategori laporan tidak sesuai! Pilih salah satu dari kategori yang tersedia. ";
    this.message = new TextMessage(messageToUser);
    return this;
  }

  @Override
  public LihatLaporanState otherServiceMessage() {
    messageToUser = "Untuk beralih ke fitur lain, klik tombol 'Batal' terlebih dahulu";
    this.message = new TextMessage(messageToUser);
    return this;
  }

  @Override
  public LihatLaporanState userChooseCategory(String userMessage) {
    List<Spending> laporan = spendingService.getByUserId(userId);

    switch (userMessage) {
      case "makanan":
        List<Integer> laporanMakanan = getFlexLaporan(laporan, "makanan");
        message = botTemplate.createFlexDetailLaporan(urls.get(0), laporanMakanan.get(0),
            laporanMakanan.get(1), laporanMakanan.get(2));
        break;
      case "transportasi":
        List<Integer> laporanTransportasi = getFlexLaporan(laporan, "transportasi");
        message = botTemplate.createFlexDetailLaporan(urls.get(1), laporanTransportasi.get(0),
            laporanTransportasi.get(1), laporanTransportasi.get(2));
        break;
      case "tagihan":
        List<Integer> laporanTagihan = getFlexLaporan(laporan, "tagihan");
        message = botTemplate.createFlexDetailLaporan(urls.get(2), laporanTagihan.get(0),
            laporanTagihan.get(1), laporanTagihan.get(2));
        break;
      case "belanja":
        List<Integer> laporanBelanja = getFlexLaporan(laporan, "belanja");
        message = botTemplate.createFlexDetailLaporan(urls.get(3), laporanBelanja.get(0),
            laporanBelanja.get(1), laporanBelanja.get(2));
        break;
      case "lainnya":
        List<Integer> laporanLainnya = getFlexLaporan(laporan, "lainnya");
        message = botTemplate.createFlexDetailLaporan(urls.get(4), laporanLainnya.get(0),
            laporanLainnya.get(1), laporanLainnya.get(2));
        break;
      case "persentase":
        int makanan = getFlexLaporan(laporan, "makanan").get(2);
        int transportasi = getFlexLaporan(laporan, "transportasi").get(2);
        int tagihan = getFlexLaporan(laporan, "tagihan").get(2);
        int belanja = getFlexLaporan(laporan, "belanja").get(2);
        int lainnya = getFlexLaporan(laporan, "lainnya").get(2);
        Double total = Double.valueOf(makanan + transportasi + tagihan + belanja + lainnya);
        total = (total == 0 ? 1.0 : total);
        message = botTemplate.createFlexDetailPersentase(
            String.format("%.2f", Double.valueOf(makanan) * 100 / total),
            String.format("%.2f", Double.valueOf(transportasi) * 100 / total),
            String.format("%.2f", Double.valueOf(tagihan) * 100 / total),
            String.format("%.2f", Double.valueOf(belanja) * 100 / total),
            String.format("%.2f", Double.valueOf(lainnya) * 100 / total)
        );
        break;
      case "semua":
        List<Integer> makananSM = getFlexLaporan(laporan, "makanan");
        List<Integer> transportasiSM = getFlexLaporan(laporan, "transportasi");
        List<Integer> tagihanSM = getFlexLaporan(laporan, "tagihan");
        List<Integer> belanjaSM = getFlexLaporan(laporan, "belanja");
        List<Integer> lainnyaSM = getFlexLaporan(laporan, "lainnya");
        int totalDay = makananSM.get(0) + transportasiSM.get(0) + tagihanSM.get(0)
            + belanjaSM.get(0) + lainnyaSM.get(0);
        int totalWeek = makananSM.get(1) + transportasiSM.get(1) + tagihanSM.get(1)
            + belanjaSM.get(1) + lainnyaSM.get(1);
        int totalMonth = makananSM.get(2) + transportasiSM.get(2) + tagihanSM.get(2)
            + belanjaSM.get(2) + lainnyaSM.get(2);
        message = botTemplate.createFlexDetailLaporan(urls.get(5), totalDay, totalWeek, totalMonth);
        break;
      default:
    }
    return null;
  }

  /**
   * Return flex laporan.
   */
  public List<Integer> getFlexLaporan(List<Spending> laporan, String userMessageCategory) {
    LocalDateTime localDateTime = LocalDateTime.now();
    localDateTime = localDateTime.plusHours(7);
    int currentDayValue = localDateTime.getDayOfMonth();
    int currentMonthValue = localDateTime.getMonthValue();

    int dayLaporan = 0;
    int weekLaporan = 0;
    int monthLaporan = 0;
    for (int i = 0; i < laporan.size(); i++) {
      Spending spending = laporan.get(i);
      String category = spending.getCategory();
      String timestamp = (spending.getTimestamp().split("T"))[0];

      if (category.equals(userMessageCategory)) {
        int databaseDayValue = Integer.parseInt(timestamp.split("-")[2]);
        int databaseMonthValue = Integer.parseInt(timestamp.split("-")[1]);

        if (databaseMonthValue == currentMonthValue) {
          monthLaporan += Integer.parseInt(spending.getNominal());

          if (databaseDayValue == currentDayValue) {
            dayLaporan += Integer.parseInt(spending.getNominal());
          }
          if ((currentDayValue - 7) < databaseDayValue && databaseDayValue <= currentDayValue) {
            weekLaporan += Integer.parseInt(spending.getNominal());
          }
        }
      }
    }
    return Arrays.asList(dayLaporan, weekLaporan, monthLaporan);
  }
}
