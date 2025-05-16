package br.edu.unichristus.yomunow.activities;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import br.edu.unichristus.yomunow.R;
import br.edu.unichristus.yomunow.manga.MangaChapter;
import br.edu.unichristus.yomunow.manga.MangaChapterAdapter;
import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MangaChapterActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private ImageView imageBackground;
    private TextView mangaTitle, mangaChapterTitle, pageCounter;
    private LinearLayout topMenu, bottomMenu;
    private ImageButton previousButton, nextButton;
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    private boolean isFullscreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manga_chapter_layout);

        viewPager = findViewById(R.id.viewPages);
        imageBackground = findViewById(R.id.imageBackground);
        mangaTitle = findViewById(R.id.tituloManga);
        mangaChapterTitle = findViewById(R.id.tituloCapitulo);
        pageCounter = findViewById(R.id.contadorPaginas);
        topMenu = findViewById(R.id.menuSuperior);
        bottomMenu = findViewById(R.id.menuInferior);
        previousButton = findViewById(R.id.btnAnterior);
        nextButton = findViewById(R.id.btnProxima);

        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        String jsonLink = getIntent().getStringExtra("jsonLink");
        String mangaTitleStr = getIntent().getStringExtra("manga_title");

        mangaTitle.setText(mangaTitleStr);

        // IMAGEM DE FUNDO
        Glide.with(this)
                .load(getIntent().getStringExtra("manga_cover"))
                .transform(new MultiTransformation<>(new CenterCrop(), new BlurTransformation(15, 3)))
                .into(imageBackground);

        viewPager = findViewById(R.id.viewPages);

        fetchJson(jsonLink);
    }

    private void fetchJson(String jsonLink) {
        Request request = new Request.Builder().url(jsonLink).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String json = response.body().string();

                    List<String> links = gson.fromJson(json, new TypeToken<List<String>>() {
                    }.getType());

                    if (links != null && !links.isEmpty()) {
                        String chapterUrl = links.get(0);

                        fetchChapterJson(chapterUrl);
                    }
                }
            }
        });
    }

    private void fetchChapterJson(String chapterUrl) {
        Request request = new Request.Builder().url(chapterUrl).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String json = response.body().string();

                    MangaChapter chapter = gson.fromJson(json, MangaChapter.class);

                    runOnUiThread(() -> {
                        mangaChapterTitle.setText(chapter.getTitle());

                        MangaChapterAdapter adapter = new MangaChapterAdapter(chapter.getPaginas());
                        viewPager.setAdapter(adapter);

                        // contador de páginas
                        pageCounter.setText("1/" + chapter.getPaginas().size());
                        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                            @Override
                            public void onPageSelected(int position) {
                                super.onPageSelected(position);
                                pageCounter.setText((position + 1) + "/" + chapter.getPaginas().size());
                            }
                        });

                        // toques
                        View leftTap = findViewById(R.id.leftTap);
                        View rightTap = findViewById(R.id.rightTap);
                        View centerTap = findViewById(R.id.centerTap);

                        LinearLayout menuSuperior = findViewById(R.id.menuSuperior);
                        LinearLayout menuInferior = findViewById(R.id.menuInferior);

                        // fullscreen
                        GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
                            @Override
                            public boolean onDoubleTap(MotionEvent e) {
                                toggleFullscreen();
                                return true;
                            }
                        });
                        centerTap.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));

                        //Listeners
                        leftTap.setOnClickListener(v -> {
                            int current = viewPager.getCurrentItem();
                            if (current > 0) {
                                viewPager.setCurrentItem(current - 1, true);
                            }
                        });

                        rightTap.setOnClickListener(v -> {
                            int current = viewPager.getCurrentItem();
                            if (current < chapter.getPaginas().size() - 1) {
                                viewPager.setCurrentItem(current + 1, true);
                            }
                        });

                        centerTap.setOnClickListener(v -> {
                            toggleMenusWithAnimation(menuSuperior, menuInferior);
                        });
                    });
                }
            }
        });
    }
    private void toggleFullscreen() {
        isFullscreen = !isFullscreen;

        if (isFullscreen) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }
    private void toggleMenusWithAnimation(View topMenu, View bottomMenu) {
        boolean isVisible = topMenu.getVisibility() == View.VISIBLE;

        View[] menus = {topMenu, bottomMenu};

        for (View menu : menus) {
            if (isVisible) {
                menu.animate()
                        .alpha(0f)
                        .setDuration(300)
                        .withEndAction(() -> menu.setVisibility(View.GONE));
            } else {
                menu.setAlpha(0f);
                menu.setVisibility(View.VISIBLE);
                menu.animate()
                        .alpha(1f)
                        .setDuration(300)
                        .start();
            }
        }
    }
}
