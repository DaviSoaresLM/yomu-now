package br.edu.unichristus.yomunow.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.List;

import br.edu.unichristus.yomunow.R;
import br.edu.unichristus.yomunow.data.AppDatabase;
import br.edu.unichristus.yomunow.data.AppDatabaseSingleton;
import br.edu.unichristus.yomunow.manga.MangaChapterAdapter;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class MangaChapterActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private ImageView imageBackground;
    private TextView mangaTitle, mangaChapterTitle, pageCounter;
    private LinearLayout topMenu, bottomMenu;
    private ImageButton previousButton, nextButton;

    private List<String> chaptersUrl;
    private int actualPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manga_chapter_layout);

        AppDatabase database = AppDatabaseSingleton.getInstance(getApplicationContext());

        viewPager = findViewById(R.id.viewPagerPaginas);
        imageBackground = findViewById(R.id.imageBackground);
        mangaTitle = findViewById(R.id.tituloManga);
        mangaChapterTitle = findViewById(R.id.tituloCapitulo);
        pageCounter = findViewById(R.id.contadorPaginas);
        topMenu = findViewById(R.id.menuSuperior);
        bottomMenu = findViewById(R.id.menuInferior);
        previousButton = findViewById(R.id.btnAnterior);
        nextButton = findViewById(R.id.btnProxima);

        String mangaTitleStr = getIntent().getStringExtra("manga_title");
        String chapterTitle = getIntent().getStringExtra("chapter_title");
        String chapterDriveFolder = getIntent().getStringExtra("chapter_drive_folder");

        mangaTitle.setText(mangaTitleStr);
        mangaChapterTitle.setText(chapterTitle);

        // IMAGEM DE FUNDO
        Glide.with(this)
                .load(getIntent().getStringExtra("manga_cover"))
                .transform(new MultiTransformation<>(new CenterCrop(), new BlurTransformation(15, 3)))
                .into(imageBackground);

        MangaChapterAdapter adapter = new MangaChapterAdapter(this, chaptersUrl);
        viewPager.setAdapter(adapter);

        pageCounter.setText("1/" + chaptersUrl.size());

        // Atualiza contador ao mudar de página
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                actualPage = position;
                pageCounter.setText((position + 1) + "/" + chaptersUrl.size());
            }
        });

        // botões anterior/próxima
        previousButton.setOnClickListener(v -> {
            if (actualPage > 0) viewPager.setCurrentItem(actualPage - 1);
        });

        nextButton.setOnClickListener(v -> {
            if (actualPage < chaptersUrl.size() - 1) viewPager.setCurrentItem(actualPage + 1);
        });

        // mostrar menus ao tocar na tela
        viewPager.setOnClickListener(v -> alternarMenus());
    }

    private void alternarMenus() {
        int visibilidade = topMenu.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
        topMenu.setVisibility(visibilidade);
        bottomMenu.setVisibility(visibilidade);
    }
}
