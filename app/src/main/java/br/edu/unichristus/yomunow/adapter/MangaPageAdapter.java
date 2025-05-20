package br.edu.unichristus.yomunow.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import br.edu.unichristus.yomunow.R;

public class MangaPageAdapter extends RecyclerView.Adapter<MangaPageAdapter.PageViewHolder> {

    private List<String> imageUrls;
    private Context context;

    public MangaPageAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manga_page, parent, false);
        return new PageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PageViewHolder holder, int position) {
        String imageUrl = imageUrls.get(position);

        holder.pageProgressBar.setVisibility(View.VISIBLE);

        Glide.with(context)
                .load(imageUrl)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        holder.pageProgressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model,
                                                   Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.pageProgressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public static class PageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ProgressBar pageProgressBar;

        public PageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.pageImage);
            pageProgressBar = itemView.findViewById(R.id.pageProgressBar);
        }
    }
}
