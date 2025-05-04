package br.edu.unichristus.yomunow.manga;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MangaDAO {
    @Insert
    void insertManga(Manga manga);

    @Query("SELECT COUNT(*) FROM Manga")
    int getCount();

    @Query("SELECT * FROM Manga")
    List<Manga> getAllMangas();
}
