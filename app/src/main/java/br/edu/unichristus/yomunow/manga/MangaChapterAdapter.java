package br.edu.unichristus.yomunow.manga;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.edu.unichristus.yomunow.R;

public class MangaChapterAdapter extends RecyclerView.Adapter<MangaChapterAdapter.PageViewHolder> {
    private final Context context;
    private final List<String> paginas;

    public MangaChapterAdapter(Context context, List<String> paginas) {
        this.context = context;
        this.paginas = paginas;
    }

    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chapter_page_view, parent, false);
        return new PageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PageViewHolder holder, int position) {
        String url = paginas.get(position);
        Glide.with(context)
                .load(url)
                .into(holder.imagemPagina);
    }

    @Override
    public int getItemCount() {
        return paginas.size();
    }

    static class PageViewHolder extends RecyclerView.ViewHolder {
        ImageView imagemPagina;

        public PageViewHolder(@NonNull View itemView) {
            super(itemView);
            imagemPagina = itemView.findViewById(R.id.imagemPagina);
        }
    }
}
