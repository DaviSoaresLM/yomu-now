package br.edu.unichristus.yomunow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.edu.unichristus.yomunow.activities.MangaDetailActivity;

public class Lancamentos extends AppCompatActivity {

    ImageButton iBtChainsaw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lancamentos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.telaLancamentos), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        iBtChainsaw = findViewById(R.id.iBtChainsaw);

        iBtChainsaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentChainsaw = new Intent(Lancamentos.this, MangaDetailActivity.class);
                intentChainsaw.putExtra("image", R.drawable.chainsaw);
                intentChainsaw.putExtra("title", "Chainsaw Mang");
                intentChainsaw.putExtra("description", "\"Denji é um jovem pobre que vira um " +
                        "caçador de demônios para pagar as dívidas de seu pai. Após ser traído e morto, ele faz um pacto com seu cachorro-demônio Pochita e renasce como o poderoso Chainsaw Man.");
                startActivity(intentChainsaw);
            }

        });


    }



}