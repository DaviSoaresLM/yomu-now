package br.edu.unichristus.yomunow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import br.edu.unichristus.yomunow.R;
import br.edu.unichristus.yomunow.data.AppDatabase;
import br.edu.unichristus.yomunow.data.AppDatabaseSingleton;
import br.edu.unichristus.yomunow.manga.Manga;

public class MangaDetailsActivity extends AppCompatActivity {
    private TextView tvTitle, tvAuthor, tvDescription;
    private ImageView ivCover, ivBanner;
    private Button buttonRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manga_details_layout);

        tvTitle = findViewById(R.id.tvMangaTitle);
        tvAuthor = findViewById(R.id.tvAuthor);
        tvDescription = findViewById(R.id.tvDescription);
        ivCover = findViewById(R.id.ivCover);
        ivBanner = findViewById(R.id.bannerImage);
        buttonRead = findViewById(R.id.btnContinueReading);

        int mangaId = getIntent().getIntExtra("manga_id", -1);

        if (mangaId == -1) {
            Toast.makeText(this, "Erro ao carregar mangá", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        AppDatabase db = AppDatabaseSingleton.getInstance(getApplicationContext());
        Manga manga = db.mangaDAO().getMangaById(mangaId);

        if (manga == null) {
            Toast.makeText(this, "Mangá não encontrado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tvTitle.setText(manga.getTitle());
        tvAuthor.setText(manga.getAuthor());
        tvDescription.setText(manga.getSynopsis());

        Glide.with(this).load(manga.getCoverUrl()).into(ivCover);
        Glide.with(this).load(manga.getBannerUrl()).into(ivBanner);


        buttonRead.setOnClickListener(v -> {
            Toast.makeText(this, "Botão clicado", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MangaDetailsActivity.this, MangaReaderActivity.class);

            intent.putExtra("manga_id", manga.getId());
            intent.putExtra("manga_title", manga.getTitle());
            intent.putExtra("chapters_jsonlink", manga.getChaptersJsonLink());

            startActivity(intent);
        });
    }
}
