package br.edu.unichristus.yomunow.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import br.edu.unichristus.yomunow.manga.MangaDAO;
import br.edu.unichristus.yomunow.manga.Manga;

@Database(entities = Manga.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MangaDAO mangaDAO();
}
