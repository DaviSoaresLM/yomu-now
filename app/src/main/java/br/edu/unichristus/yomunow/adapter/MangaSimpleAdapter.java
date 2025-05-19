package br.edu.unichristus.yomunow.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.edu.unichristus.yomunow.R;
import br.edu.unichristus.yomunow.manga.Manga;
import br.edu.unichristus.yomunow.utils.OnMangaClickListener;

public class MangaSimpleAdapter extends RecyclerView.Adapter<MangaSimpleAdapter.MangaViewHolder> {
    private List<Manga> mangaList;
    private OnMangaClickListener listener;

    public MangaSimpleAdapter(List<Manga> mangaList, OnMangaClickListener listener) {
        this.mangaList = mangaList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MangaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_popular, parent, false);
        return new MangaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MangaViewHolder holder, int position) {
        Manga manga = mangaList.get(position);
        holder.bind(manga);
    }

    @Override
    public int getItemCount() {
        return mangaList.size();
    }

    class MangaViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCapa;
        TextView txtTitulo;

        public MangaViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCapa = itemView.findViewById(R.id.imgCapa);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
        }

        void bind(final Manga manga) {
            txtTitulo.setText(manga.getTitle());

            Glide.with(imgCapa.getContext())
                    .load(manga.getCoverUrl())
                    .into(imgCapa);

            itemView.setOnClickListener(v -> listener.onMangaClick(manga));
        }
    }
}
