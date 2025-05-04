package br.edu.unichristus.yomunow;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MangaActivity extends AppCompatActivity {
    ImageView imageBackground;

    ImageView mangaCover;
    TextView mangaTitle;
    TextView mangaSinopse;
    TextView sinopseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manga_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bgDetalhes), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageBackground = findViewById(R.id.imageBackground);
        mangaCover = findViewById(R.id.mangaCover);
        mangaTitle = findViewById(R.id.mangaTitle);
        mangaSinopse = findViewById(R.id.mangaSinopse);

        Intent intent = getIntent();
        int imageRes = intent.getIntExtra("image", 0);
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");


        mangaCover.setImageResource(imageRes);
        mangaTitle.setText(title);
        mangaSinopse.setText(description);
    }
}