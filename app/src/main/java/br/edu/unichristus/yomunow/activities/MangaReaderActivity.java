package br.edu.unichristus.yomunow.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.edu.unichristus.yomunow.R;
import br.edu.unichristus.yomunow.adapter.MangaPageAdapter;
import br.edu.unichristus.yomunow.models.ChapterData;
import br.edu.unichristus.yomunow.models.ChapterIndex;

public class MangaReaderActivity extends AppCompatActivity {
    private static final String PREF_NAME = "MangaPrefs";
    private static final String LAST_READ_CHAPTER = "last_read_chapter";
    private static final String PREF_LAST_PAGE_PREFIX = "last_read_page_chapter_";

    private ViewPager2.OnPageChangeCallback dotCallback;

    private ViewPager2 viewPager;

    private HorizontalScrollView scrollViewDots;
    private LinearLayout dotContainer;
    private TextView mangaTitle, chapterTitle;
    private ProgressBar progressBar;
    private ImageButton btnBack, btnPrevious, btnNext;
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
        btnBack = findViewById(R.id.btnBack);
        scrollViewDots = findViewById(R.id.pageDotsScroll);
        dotContainer = findViewById(R.id.pageProgressDots);

        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
        setupChapterNavigation();

        btnBack.setOnClickListener(v -> finish());

        prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        mangaTitle.setText(getIntent().getStringExtra("manga_title"));
        progressBar.setVisibility(View.VISIBLE);

        setupTapNavigation(viewPager);

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
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

                loadChapter(currentChapterIndex, false);
            } catch (Exception e) {
                e.printStackTrace();
                showError("Erro ao carregar dados do mangá");
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            private int previousPosition = -1;

            @Override
            public void onPageSelected(int position) {
                if (viewPager.getAdapter() == null) return;


                previousPosition = position;
            }
        });
    }

    private void loadChapter(int index, boolean startFromFirstPage) {
        if (index < 0 || index >= chapterLinks.size()) {
            runOnUiThread(() -> showError("Capítulo inválido"));
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                String chapterJsonUrl = chapterLinks.get(index);
                URL chapterUrl = new URL(chapterJsonUrl);
                HttpURLConnection chapterConnection = (HttpURLConnection) chapterUrl.openConnection();
                chapterConnection.connect();

                InputStreamReader chapterReader = new InputStreamReader(chapterConnection.getInputStream());
                ChapterData chapter = gson.fromJson(chapterReader, ChapterData.class);
                chapterReader.close();

                if (chapter == null || chapter.getPages() == null || chapter.getPages().isEmpty()) {
                    runOnUiThread(() -> showError("Erro ao carregar o capítulo"));
                    return;
                }

                runOnUiThread(() -> {
                    chapterTitle.setText(chapter.getChapterTitle());

                    List<String> pages = new ArrayList<>(chapter.getPages());
                    Collections.reverse(pages);

                    MangaPageAdapter adapter = new MangaPageAdapter(this, pages);
                    if (dotCallback != null) {
                        viewPager.unregisterOnPageChangeCallback(dotCallback);
                    }

                    viewPager.setAdapter(adapter);

                    int savedPage = prefs.getInt(PREF_LAST_PAGE_PREFIX + index, 0);
                    int startPage = startFromFirstPage ? pages.size() - 1 : pages.size() - 1 - savedPage;

                    if (startPage < 0 || startPage >= pages.size()) {
                        startPage = 0;
                    }

                    viewPager.setCurrentItem(startPage, false);

                    int pageCount = adapter.getItemCount();
                    setupPageDots(pageCount);
                    updateDotProgress(startPage, pageCount);

                    dotCallback = new ViewPager2.OnPageChangeCallback() {
                        @Override
                        public void onPageSelected(int position) {
                            updateDotProgress(position, pageCount);

                            prefs.edit()
                                    .putInt(PREF_LAST_PAGE_PREFIX + index, pageCount - 1 - position)
                                    .apply();

                        }
                    };
                    viewPager.registerOnPageChangeCallback(dotCallback);

                    progressBar.setVisibility(View.GONE);
                    prefs.edit().putInt(LAST_READ_CHAPTER, index).apply();

                    currentChapterIndex = index; // Atualiza o índice atual
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    showError("Erro ao carregar capítulo");
                    progressBar.setVisibility(View.GONE);
                });
            }
        });
    }

    private void loadRightToLeftNextChapter() {
        if (currentChapterIndex + 1 < chapterLinks.size()) {
            currentChapterIndex++;
            loadChapter(currentChapterIndex, true);
        } else {
            Toast.makeText(this, "Último capítulo!", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadRightToLeftPreviousChapter() {
        if (currentChapterIndex - 1 >= 0) {
            currentChapterIndex--;
            loadChapter(currentChapterIndex, true);
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

    private void setupTapNavigation(ViewPager2 viewPager) {
        View tapArea = findViewById(R.id.tapArea);

        tapArea.setOnTouchListener((v, event) -> {
            if (event.getAction() != MotionEvent.ACTION_DOWN) return false;

            float x = event.getX(), width = v.getWidth();
            int page = viewPager.getCurrentItem();
            RecyclerView.Adapter adapter = viewPager.getAdapter();
            if (adapter == null) return false;

            int max = adapter.getItemCount() - 1;

            if (x < width * 0.33f) {
                if (page < max) {
                    viewPager.setCurrentItem(page + 1, true);
                } else {
                    loadRightToLeftNextChapter();
                }
                v.performClick();
            } else if (x > width * 0.66f) {
                if (page > 0) {
                    viewPager.setCurrentItem(page - 1, true);
                } else {
                    loadRightToLeftPreviousChapter();
                }
                v.performClick();
            }
            return true;
        });
        tapArea.setOnClickListener(v -> {});
    }

    private void setupPageDots(int totalPages) {
        dotContainer.removeAllViews();

        for (int i = 0; i < totalPages; i++) {
            ImageView dot = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            int margin = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    4,
                    getResources().getDisplayMetrics()
            );
            params.setMargins(margin, 0, margin, 0);
            dot.setLayoutParams(params);
            dot.setImageResource(R.drawable.dot_inactive);

            dotContainer.addView(dot);
        }
    }

    private void updateDotProgress(int currentPosition, int totalPages) {
        int visualPosition = totalPages - 1 - currentPosition;

        for (int i = 0; i < totalPages; i++) {
            ImageView dot = (ImageView) dotContainer.getChildAt(i);
            if (dot != null) {
                if (i == visualPosition) {
                    dot.setImageResource(R.drawable.dot_active);
                } else {
                    dot.setImageResource(R.drawable.dot_inactive);
                }
            }
        }

        View selectedDot = dotContainer.getChildAt(visualPosition);
        if (selectedDot != null) {
            int scrollX = selectedDot.getLeft() - (scrollViewDots.getWidth() / 2) + (selectedDot.getWidth() / 2);
            scrollViewDots.smoothScrollTo(scrollX, 0);
        }
    }

    private void setupChapterNavigation() {
        btnPrevious.setOnClickListener(v -> {
            if (currentChapterIndex > 0) {
                currentChapterIndex--;
                loadChapter(currentChapterIndex, true);
            } else {
                Toast.makeText(this, "Primeiro capítulo!", Toast.LENGTH_SHORT).show();
            }
        });

        btnNext.setOnClickListener(v -> {
            if (currentChapterIndex + 1 < chapterLinks.size()) {
                currentChapterIndex++;
                loadChapter(currentChapterIndex, true);
            } else {
                Toast.makeText(this, "Último capítulo!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
