package br.edu.unichristus.yomunow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.stream.Collectors;

import br.edu.unichristus.yomunow.R;
import br.edu.unichristus.yomunow.adapter.MangaContinueAdapter;
import br.edu.unichristus.yomunow.adapter.MangaSimpleAdapter;
import br.edu.unichristus.yomunow.data.AppDatabase;
import br.edu.unichristus.yomunow.data.AppDatabaseSingleton;
import br.edu.unichristus.yomunow.manga.Manga;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView rvPopulares, rvAtualizacoes, rvContinueLendo;

    private List<Manga> listaPopulares;
    private List<Manga> listaAtualizacoes;
    private List<Manga> listaContinue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        AppDatabase appDatabase = AppDatabaseSingleton.getInstance(getApplicationContext());

        List<Manga> mangaList = appDatabase.mangaDAO().getAllMangas();

        listaPopulares = carregarPopulares(mangaList);
        listaAtualizacoes = carregarAtualizacoes(mangaList);
        listaContinue = carregarContinuar(mangaList);

        rvPopulares = findViewById(R.id.popularRecycler);
        rvAtualizacoes = findViewById(R.id.updateRecycler);
        rvContinueLendo = findViewById(R.id.continueRecycler);

        setupRecyclerViews();
    }

    private void setupRecyclerViews() {
        LinearLayoutManager horizontalLayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        rvPopulares.setLayoutManager(horizontalLayout);
        rvPopulares.setAdapter(new MangaSimpleAdapter(listaPopulares, this::onMangaSelecionado));

        rvAtualizacoes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvAtualizacoes.setAdapter(new MangaSimpleAdapter(listaAtualizacoes, this::onMangaSelecionado));

        rvContinueLendo.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvContinueLendo.setAdapter(new MangaContinueAdapter(listaContinue, this::onMangaSelecionado));
    }

    private void onMangaSelecionado(Manga manga) {
        Toast.makeText(this, "Selecionado: " + manga.getTitle(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MangaDetailsActivity.class);
        intent.putExtra("manga_id", manga.getId());
        startActivity(intent);
    }

    private List<Manga> carregarPopulares(List<Manga> mangaList) {
        return mangaList.stream()
                .filter(manga -> manga.getCategory().equals("POPULAR"))
                .collect(Collectors.toList());
    }

    private List<Manga> carregarAtualizacoes(List<Manga> mangaList) {
        return mangaList.stream()
                .filter(manga -> manga.getCategory().equals("UPDATING"))
                .collect(Collectors.toList());
    }

    private List<Manga> carregarContinuar(List<Manga> mangaList) {
        return mangaList.stream()
                .filter(manga -> manga.getIsReading() == 1)
                .collect(Collectors.toList());
    }
}
