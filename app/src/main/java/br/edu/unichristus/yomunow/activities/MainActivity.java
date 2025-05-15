package br.edu.unichristus.yomunow.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.unichristus.yomunow.R;
import br.edu.unichristus.yomunow.data.AppDatabase;
import br.edu.unichristus.yomunow.data.AppDatabaseSingleton;
import br.edu.unichristus.yomunow.manga.Manga;
import br.edu.unichristus.yomunow.manga.MangaAdapter;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MangaAdapter mangaAdapter;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        appDatabase = AppDatabaseSingleton.getInstance(getApplicationContext());

        List<Manga> mangas = appDatabase.mangaDAO().getAllMangas();

        mangaAdapter = new MangaAdapter(mangas, this);
        recyclerView.setAdapter(mangaAdapter);
    }
}
