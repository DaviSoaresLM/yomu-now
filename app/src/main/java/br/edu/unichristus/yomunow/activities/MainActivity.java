package br.edu.unichristus.yomunow.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

import br.edu.unichristus.yomunow.InitialApp;
import br.edu.unichristus.yomunow.R;
import br.edu.unichristus.yomunow.data.AppDatabase;
import br.edu.unichristus.yomunow.manga.Manga;
import br.edu.unichristus.yomunow.manga.MangaAdapter;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MangaAdapter mangaAdapter;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "manga-db").allowMainThreadQueries().build();

        List<Manga> mangas = db.mangaDAO().getAllMangas();

        if (mangas.isEmpty()) {
            db.mangaDAO().insertManga(new Manga("Chainsaw Man", "Piratas atrás do One Piece.",
                    "https://drive.google.com/uc?id=19AZQU9fRkeieWd7cy-BS3csHH216kk6M", "#FF5733"));
            mangas = db.mangaDAO().getAllMangas();
        }

        mangaAdapter = new MangaAdapter(mangas, this);
        recyclerView.setAdapter(mangaAdapter);
    }
}
