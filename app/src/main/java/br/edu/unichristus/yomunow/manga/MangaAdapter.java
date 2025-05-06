package br.edu.unichristus.yomunow.manga;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import br.edu.unichristus.yomunow.R;
import br.edu.unichristus.yomunow.activities.MangaDetailActivity;

public class MangaAdapter extends RecyclerView.Adapter<MangaAdapter.ViewHolder> {

    private List<Manga> mangaList;
    private Context context;

    public MangaAdapter(List<Manga> mangaList, Context context) {
        this.mangaList = mangaList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_manga, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Manga manga = mangaList.get(position);
        holder.textNome.setText(manga.getName());
        //holder.textNome.setTextColor(android.graphics.Color.parseColor(manga.getTitleHex()));

        Glide.with(context)
                .load(manga.getCoverUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageCapa);

        holder.itemView.setOnClickListener(v -> {
            int mangaId = manga.getId();

            Intent intent = new Intent(context, MangaDetailActivity.class);
            intent.putExtra("manga_id", mangaId);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mangaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageCapa;
        TextView textNome;

        public ViewHolder(View itemView) {
            super(itemView);
            imageCapa = itemView.findViewById(R.id.imageCapa);
            textNome = itemView.findViewById(R.id.textNome);

            itemView.setOnClickListener(v -> {
                int mangaId = (int) v.getTag();
                Toast.makeText(context, "Manga ID: " + mangaId, Toast.LENGTH_SHORT).show();
                // Aqui você pode abrir detalhes, passar para outra Activity, etc.
            });
        }
    }
}
