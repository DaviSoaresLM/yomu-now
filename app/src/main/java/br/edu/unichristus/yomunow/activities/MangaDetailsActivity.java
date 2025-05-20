package br.edu.unichristus.yomunow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private LinearLayout buttonRead;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_details);

        tvTitle = findViewById(R.id.manga_title);
        tvAuthor = findViewById(R.id.manga_author);
        tvDescription = findViewById(R.id.manga_description);
        ivCover = findViewById(R.id.manga_cover);
        ivBanner = findViewById(R.id.cover_background);
        buttonRead = findViewById(R.id.btn_read);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

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
