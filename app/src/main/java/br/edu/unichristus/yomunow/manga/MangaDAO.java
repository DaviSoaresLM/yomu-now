package br.edu.unichristus.yomunow.manga;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MangaDAO {
    @Insert
    long insert(Manga manga);

    @Insert
    List<Long> insertAll(List<Manga> mangas);

    @Query("SELECT * FROM mangas")
    List<Manga> getAllMangas();

    @Query("SELECT * FROM mangas WHERE id = :id")
    Manga getMangaById(int id);

    @Query("DELETE FROM mangas")
    void deleteAll();
}
