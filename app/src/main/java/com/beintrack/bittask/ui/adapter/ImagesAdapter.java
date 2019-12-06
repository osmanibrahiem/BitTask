package com.beintrack.bittask.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beintrack.bittask.databinding.ItemImageBinding;
import com.beintrack.bittask.model.Image;
import com.beintrack.bittask.ui.adapter.viewHolder.ImageViewHolder;
import com.beintrack.bittask.ui.listener.OnItemClickedListener;

import java.util.ArrayList;
import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImageViewHolder> {

    private List<Image> imageList;
    private OnItemClickedListener<Image> listener;

    public ImagesAdapter() {
        this.imageList = new ArrayList<>();
    }

    public void setImageList(List<Image> imageList) {
        this.imageList.clear();
        this.imageList.addAll(imageList);
        notifyDataSetChanged();
    }

    public void setOnItemClickedListener(OnItemClickedListener<Image> listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemImageBinding binding = ItemImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.bindTo(imageList.get(position));
        holder.itemView.setOnClickListener(v -> {
            if (listener != null)
                listener.onClick(imageList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return imageList == null ? 0 : imageList.size();
    }
}
