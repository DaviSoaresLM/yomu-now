package br.edu.unichristus.yomunow.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.edu.unichristus.yomunow.R;
import br.edu.unichristus.yomunow.data.AppDatabase;
import br.edu.unichristus.yomunow.data.AppDatabaseSingleton;
import br.edu.unichristus.yomunow.manga.Manga;
import br.edu.unichristus.yomunow.manga.MangaAdapter;
import br.edu.unichristus.yomunow.manga.MangaDAO;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MangaAdapter mangaAdapter;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        appDatabase = AppDatabaseSingleton.getInstance(getApplicationContext());

        appDatabase.mangaDAO().deleteAll();

        List<Manga> mangas;

        insertMangas(appDatabase.mangaDAO());
        mangas = appDatabase.mangaDAO().getAllMangas();

        mangaAdapter = new MangaAdapter(mangas, this);
        recyclerView.setAdapter(mangaAdapter);
    }

    private void insertMangas(MangaDAO mangaDAO) {
        Manga m1 = new Manga("Chainsaw Man",
                "Denji, um jovem pobre, faz um contrato com Pochita, o Demônio da Motosserra, e se torna um híbrido humano-demônio com a capacidade de transformar partes do corpo em motosserras.",
                "https://drive.google.com/uc?export=download&id=19AZQU9fRkeieWd7cy-BS3csHH216kk6M", "#F2C166", "#F23838", "#F2F2F2");
        Manga m2 = new Manga("Kagurabachi",
                "Chihiro Rokuhira, um jovem que, após um trágico incidente, decide seguir os passos de seu pai e se tornar um ferreiro de katanas, buscando vingança pelo assassinato do pai e pela perda de outras seis lâminas encantadas.",
                "https://drive.google.com/uc?export=download&id=1CjQTIuGrY25OoA4PSET0PSRJ22hMOoqL", "#F22D1B", "#F22D1B", "#F2F2F2");
        Manga m3 = new Manga("Dandadan",
                "Momo Ayase, uma estudante do ensino médio que acredita em fantasmas, e Ken Takakura (apelidado de Okarun), que é um viciado em alienígenas. Eles decidem provar que suas crenças são reais, e acabam se envolvendo em uma aventura sobrenatural contra yōkai e alienígenas.",
                "https://drive.google.com/uc?export=download&id=1cRVjnwBmo8MMPFA-PqtP0tf01pjuBK5A", "#F2D8C9", "#D9043D", "#F2F2F2");

        mangaDAO.insertAll(List.of(m1, m2, m3));
    }
}
