package br.edu.unichristus.yomunow.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import br.edu.unichristus.yomunow.R;
import br.edu.unichristus.yomunow.adapter.MangaPageAdapter;
import br.edu.unichristus.yomunow.models.ChapterData;
import br.edu.unichristus.yomunow.models.ChapterIndex;

public class MangaReaderActivity extends AppCompatActivity {
    private static final String PREF_NAME = "MangaPrefs";
    private static final String LAST_READ_CHAPTER = "last_read_chapter";

    private ViewPager2 viewPager;
    private TextView mangaTitle, chapterTitle;
    private ProgressBar progressBar;

    private List<String> chapterLinks;
    private int currentChapterIndex = 0;
    private Gson gson = new Gson();
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manga_reading_layout);

        viewPager = findViewById(R.id.viewPagerManga);
        mangaTitle = findViewById(R.id.mangaTitle);
        chapterTitle = findViewById(R.id.chapterTitle);
        progressBar = findViewById(R.id.loadingProgressBar);
        prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        mangaTitle.setText(getIntent().getStringExtra("manga_title"));
        progressBar.setVisibility(View.VISIBLE);

        new Thread(() -> {
            try {
                String jsonLink = getIntent().getStringExtra("chapters_jsonlink");
                if (jsonLink == null || jsonLink.isEmpty()) {
                    showError("Erro ao carregar o JSON");
                    return;
                }

                URL url = new URL(jsonLink);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                ChapterIndex index = gson.fromJson(reader, ChapterIndex.class);
                reader.close();

                if (index == null || index.getChapters() == null || index.getChapters().isEmpty()) {
                    showError("Nenhum capítulo disponível");
                    return;
                }

                chapterLinks = index.getChapters();
                currentChapterIndex = prefs.getInt(LAST_READ_CHAPTER, 0);

                if (currentChapterIndex < 0 || currentChapterIndex >= chapterLinks.size()) {
                    currentChapterIndex = 0;
                }

                loadChapter(currentChapterIndex);

            } catch (Exception e) {
                e.printStackTrace();
                showError("Erro ao carregar dados do mangá");
            }
        }).start();

        // Listener de troca de páginas
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            private int previousPosition = -1;

            @Override
            public void onPageSelected(int position) {
                if (viewPager.getAdapter() == null) return;

                int lastPage = viewPager.getAdapter().getItemCount() - 1;

                if (position == lastPage && previousPosition < position) {
                    loadNextChapter();
                } else if (position == 0 && previousPosition > position) {
                    loadPreviousChapter();
                }

                previousPosition = position;
            }
        });
    }

    private void loadChapter(int index) {
        progressBar.setVisibility(View.VISIBLE);
        new Thread(() -> {
            try {
                String chapterJsonUrl = chapterLinks.get(index);
                URL chapterUrl = new URL(chapterJsonUrl);
                HttpURLConnection chapterConnection = (HttpURLConnection) chapterUrl.openConnection();
                chapterConnection.connect();

                InputStreamReader chapterReader = new InputStreamReader(chapterConnection.getInputStream());
                ChapterData chapter = gson.fromJson(chapterReader, ChapterData.class);
                chapterReader.close();

                if (chapter == null || chapter.getPages() == null || chapter.getPages().isEmpty()) {
                    showError("Erro ao carregar o capítulo");
                    return;
                }

                runOnUiThread(() -> {
                    chapterTitle.setText(chapter.getChapterTitle());
                    MangaPageAdapter adapter = new MangaPageAdapter(this, chapter.getPages());
                    viewPager.setAdapter(adapter);

                    viewPager.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

                    viewPager.setCurrentItem(0, false);
                    progressBar.setVisibility(View.GONE);

                    // Salvar progresso
                    prefs.edit().putInt(LAST_READ_CHAPTER, index).apply();
                });

            } catch (Exception e) {
                e.printStackTrace();
                showError("Erro ao carregar capítulo");
            }
        }).start();
    }

    private void loadNextChapter() {
        if (currentChapterIndex + 1 < chapterLinks.size()) {
            currentChapterIndex++;
            loadChapter(currentChapterIndex);
        } else {
            Toast.makeText(this, "Último capítulo!", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadPreviousChapter() {
        if (currentChapterIndex - 1 >= 0) {
            currentChapterIndex--;
            loadChapter(currentChapterIndex);
        } else {
            Toast.makeText(this, "Primeiro capítulo!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showError(String message) {
        runOnUiThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}