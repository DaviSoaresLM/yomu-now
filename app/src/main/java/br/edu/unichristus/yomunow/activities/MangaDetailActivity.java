package br.edu.unichristus.yomunow.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import br.edu.unichristus.yomunow.R;
import br.edu.unichristus.yomunow.data.AppDatabase;
import br.edu.unichristus.yomunow.data.AppDatabaseSingleton;
import br.edu.unichristus.yomunow.manga.Manga;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class MangaDetailActivity extends AppCompatActivity {
    private ImageView mangaCover, imageBackground;
    private TextView mangaTitle, synopsisText;
    private Button buttonStartReading;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_details);

        imageBackground = findViewById(R.id.imageBackground);
        mangaCover = findViewById(R.id.mangaCover);
        mangaTitle = findViewById(R.id.mangaTitle);
        synopsisText = findViewById(R.id.sinopseText);
        buttonStartReading = findViewById(R.id.buttonStartReading);

        appDatabase = AppDatabaseSingleton.getInstance(getApplicationContext());

        int mangaId = getIntent().getIntExtra("manga_id", -1);

        if (mangaId != -1) {
            loadMangaDetails(mangaId);
        } else {
            Toast.makeText(this, "Erro: Manga ID inválido!", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadMangaDetails(int mangaId) {
        Manga manga = appDatabase.mangaDAO().getMangaById(mangaId);

        if (manga != null) {
            mangaTitle.setText(manga.getName());
            synopsisText.setText(manga.getSynopsis());

            buttonStartReading.setBackgroundColor(android.graphics.Color.parseColor(manga.getButtonHex()));
            buttonStartReading.setTextColor(android.graphics.Color.parseColor(manga.getButtonTextHex()));

            Glide.with(this)
                    .load(manga.getCoverUrl())
                    .into(mangaCover);

            Glide.with(this)
                    .load(manga.getCoverUrl())
                    .transform(new MultiTransformation<>(new CenterCrop(), new BlurTransformation(15, 3)))
                    .into(imageBackground);
        } else {
            Toast.makeText(this, "Mangá não encontrado!", Toast.LENGTH_SHORT).show();
        }
    }
}
