package br.edu.unichristus.yomunow;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import java.util.List;

import br.edu.unichristus.yomunow.manga.MangaDAO;
import br.edu.unichristus.yomunow.data.AppDatabase;
import br.edu.unichristus.yomunow.manga.Manga;

public class InitialApp extends Application {
    private static final String DB_NAME = "manga_db";

    public static void initialize(Context context) {
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).allowMainThreadQueries().build();

        insertMangas(db.mangaDAO());
    }

    private static void insertMangas(MangaDAO mangaDAO) {
        Manga m1 = new Manga("Chainsaw Man",
                "Denji, um jovem pobre, faz um contrato com Pochita, o Demônio da Motosserra, e se torna um híbrido humano-demônio com a capacidade de transformar partes do corpo em motosserras.",
                "https://drive.google.com/uc?id=19AZQU9fRkeieWd7cy-BS3csHH216kk6M", "#F2C166", "F23838", "F2F2F2");
        Manga m2 = new Manga("Kagurabachi",
                "Chihiro Rokuhira, um jovem que, após um trágico incidente, decide seguir os passos de seu pai e se tornar um ferreiro de katanas, buscando vingança pelo assassinato do pai e pela perda de outras seis lâminas encantadas.",
                "https://drive.google.com/uc?id=1CjQTIuGrY25OoA4PSET0PSRJ22hMOoqL", "F22D1B", "#F22D1B", "F2F2F2");
        Manga m3 = new Manga("Dandadan",
                "Momo Ayase, uma estudante do ensino médio que acredita em fantasmas, e Ken Takakura (apelidado de Okarun), que é um viciado em alienígenas. Eles decidem provar que suas crenças são reais, e acabam se envolvendo em uma aventura sobrenatural contra yōkai e alienígenas.",
                "https://drive.google.com/uc?id=1cRVjnwBmo8MMPFA-PqtP0tf01pjuBK5A", "F2D8C9", "#D9043D", "F2F2F2");

        mangaDAO.insertAll(List.of(m1, m2, m3));
    }
}
