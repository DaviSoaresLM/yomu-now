package br.edu.unichristus.yomunow;

import android.app.Application;

import java.util.List;

import br.edu.unichristus.yomunow.data.AppDatabase;
import br.edu.unichristus.yomunow.data.AppDatabaseSingleton;
import br.edu.unichristus.yomunow.manga.Manga;

public class InitialApp extends Application {
    private final String DRIVEURL = "https://drive.google.com/uc?export=download&id=";

    @Override
    public void onCreate() {
        super.onCreate();

        AppDatabase database = AppDatabaseSingleton.getInstance(getApplicationContext());

        database.mangaDAO().deleteAll();

        insertMangas(database);
    }

    private void insertMangas(AppDatabase database) {
        // POPULARES
        Manga m1 = new Manga();
        Manga m2 = new Manga();
        Manga m3 = new Manga();

        m1.setTitle("Boruto: Two Blue Vortex");
        m1.setAuthor("Masashi Kishimoto");
        m1.setSynopsis(getString(R.string.boruto_synopsis));
        m1.setCategory("POPULAR");
        m1.setStatus("ONGOING");
        m1.setIsReading(0);
        m1.setCoverUrl(DRIVEURL.concat("1WxKTHsGEX6exnrDDBhvsfvqJMh8xsI1e"));

        m2.setTitle("Jujutsu Kaisen");
        //m2.setSynopsis(getString(R.string.jujutsu_synopsis));
        m2.setAuthor("Gege Akutami");
        m2.setCategory("POPULAR");
        m2.setStatus("COMPLETED");
        m2.setIsReading(0);
        m2.setCoverUrl(DRIVEURL.concat("1MH3ZV2sMYGEWYiuS-WGvBBS1jb3lDiXc"));

        m3.setTitle("Chainsaw Man");
        m3.setSynopsis(getString(R.string.chainsawman_synopsis));
        m3.setAuthor("Tatsuki Fujimoto");
        m3.setCategory("POPULAR");
        m3.setStatus("ONGOING");
        m3.setIsReading(0);
        m3.setCoverUrl(DRIVEURL.concat("1RWs6kFKRbJIhuydXLRHKaRHbsL0JivHd"));

        Manga m4 = new Manga();
        Manga m5 = new Manga();

        m4.setTitle("Dandadan");
        m4.setAuthor("Yukinobu Tatsu");
        m4.setSynopsis(getString(R.string.dandadan_synopsis));
        m4.setStatus("ONGOING");
        m4.setCategory("POPULAR");
        m4.setIsReading(1);
        m4.setCoverUrl(DRIVEURL.concat("19M4Ay3nYH6QSLX_mVesAsAzA2I58yv-U"));
        m4.setBannerUrl(DRIVEURL.concat("1bb1M978jY6FLcc6uw2zKvKjY0sfn9_qB"));
        m4.setChaptersJsonLink(DRIVEURL.concat("1XKK9aZkFOrWIxnmtsNxvbI_OOs7N8ec-"));
        m5.setTitle("Kagurabachi");
        m5.setAuthor("Takeru Hokazono");
        m5.setSynopsis(getString(R.string.kagurabachi_synopsis));
        m5.setStatus("ONGOING");
        m5.setCategory("UPDATING");
        m5.setIsReading(1);
        m5.setCoverUrl(DRIVEURL.concat("1lG7bcRFA-1ZhAAAqH2xmYa750BwGHfi1"));
        m5.setBannerUrl(DRIVEURL.concat("1PrG1xKtnrTeMIriKqwVDn4j6bM4zc4AC"));

        Manga m6 = new Manga();
        Manga m7 = new Manga();

        m6.setTitle("Sakamoto Days");
        m6.setAuthor("Yuto Suzuki");
        ///m6.setSynopsis(getString(R.string.dandadan_synopsis));
        m6.setStatus("ONGOING");
        m6.setCategory("UPDATING");
        m6.setIsReading(0);
        m6.setCoverUrl(DRIVEURL.concat("1GX_s5BOhYpKUcq2O3G-wqfwSBenIzmC-"));

        m7.setTitle("Spy X Family");
        m7.setAuthor("Tatsuya Endo");
        ///m7.setSynopsis(getString(R.string.dandadan_synopsis));
        m7.setStatus("ONGOING");
        m7.setCategory("UPDATING");
        m7.setIsReading(0);
        m7.setCoverUrl(DRIVEURL.concat("1ytlJcSWPJ9k4hJjYf6jvN016HY43Q68K"));

        database.mangaDAO().insertAll(List.of(m1, m2, m3, m4, m5, m6, m7));
    }
}
