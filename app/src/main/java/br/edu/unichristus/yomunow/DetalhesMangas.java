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

public class DetalhesMangas extends AppCompatActivity {

    ImageView imgDetalhe;
    TextView txtTitulo, txtDescricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalhes_mangas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bgDetalhes), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imgDetalhe = findViewById(R.id.imgDetalhe);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDescricao);

        Intent intent = getIntent();
        int imageRes = intent.getIntExtra("image", 0);
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");


        imgDetalhe.setImageResource(imageRes);
        txtTitulo.setText(title);
        txtDescricao.setText(description);

    }
}