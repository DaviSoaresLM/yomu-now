package br.edu.unichristus.yomunow;

import android.app.Application;

import java.util.List;

import br.edu.unichristus.yomunow.data.AppDatabase;
import br.edu.unichristus.yomunow.data.AppDatabaseSingleton;
import br.edu.unichristus.yomunow.manga.Manga;

public class InitialApp extends Application {
    // private static final String DB_NAME = "manga_db";
    private AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        appDatabase = AppDatabaseSingleton.getInstance(getApplicationContext());

        appDatabase.mangaDAO().deleteAll();

        insertMangas(appDatabase);
    }

    private void insertMangas(AppDatabase database) {
        Manga m1 = new Manga("Chainsaw Man", getString(R.string.chainsawman_synopsis),
                "https://drive.google.com/uc?export=download&id=19AZQU9fRkeieWd7cy-BS3csHH216kk6M", "#F23838", "#F2F2F2", null);
        Manga m2 = new Manga("Kagurabachi", getString(R.string.kagurabachi_synopsis),
                "https://drive.google.com/uc?export=download&id=1CjQTIuGrY25OoA4PSET0PSRJ22hMOoqL", "#F22D1B", "#F2F2F2", null);

        Manga m3 = new Manga("Dandadan", getString(R.string.dandadan_synopsis),
                "https://drive.google.com/uc?export=download&id=1cRVjnwBmo8MMPFA-PqtP0tf01pjuBK5A", "#D9043D", "#F2F2F2",
                "https://drive.google.com/uc?export=download&id=1XKK9aZkFOrWIxnmtsNxvbI_OOs7N8ec-");

        Manga m4 = new Manga("Vagabond", getString(R.string.vagabond_synopsis),
                "https://drive.google.com/uc?export=download&id=1bO6Ch558hla4rBBS86ng7LwqsEu0QEMF", "#D9A38F", "#0D0D0D", null);

        Manga m5 = new Manga("Hell’s Paradise: Jigokuraku", getString(R.string.hells_paradise_synopsis),
                "https://drive.google.com/uc?export=download&id=1iIhhA_lSresGye6oLWwQ3ad6pH3Lv6LK", "#0CC8F2", "#0D0D0D", null);

        Manga m6 = new Manga("Solo Leveling", getString(R.string.solo_leveling_synopsis),
                "https://drive.google.com/uc?export=download&id=1RZKR6sh1CO7dZnld0xtx4dTT9laXQiuE", "#88DFF2", "#0D0D0D", null);

        Manga m7 = new Manga("Boruto: Two Blue Vortex", getString(R.string.boruto_synopsis),
                "https://drive.google.com/uc?export=download&id=12R_Ioak50NRv0q3GuMxesNeQdrJkv-yp", "#04B2D9", "#F2F2F2", null);

        Manga m8 = new Manga("Vinland Saga", getString(R.string.vinland_saga_synopsis),
                "https://drive.google.com/uc?export=download&id=1m_zPbMRDQqLDynW9whgl9gy8-1bUcEXx", "#0388A6", "#F2F2F2", null);

        Manga m9 = new Manga("Berserk", getString(R.string.berserk_synopsis),
                "https://drive.google.com/uc?export=download&id=1urFZ0MJsIxpGshhnAX4GQTSwtKlWW6fU", "#F2B988", "#0D0D0D", null);

        database.mangaDAO().insertAll(List.of(m1, m2, m3, m4, m5, m6, m7, m8, m9));
    }
}
