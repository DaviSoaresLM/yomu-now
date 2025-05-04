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
        MangaDAO mangaDao = db.mangaDAO();

        List<Manga> mangas = mangaDao.getAllMangas();
        if (mangas.isEmpty()) {
            mangaDao.insertManga(new Manga("One Piece", "Piratas atrás do One Piece.",
                    "https://drive.google.com/uc?export=view&id=19AZQU9fRkeieWd7cy-BS3csHH216kk6M", "#FF5733"));
        }
    }
}
