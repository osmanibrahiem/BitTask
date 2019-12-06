package com.beintrack.bittask.ui.adapter.viewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beintrack.bittask.databinding.ItemImageBinding;
import com.beintrack.bittask.model.Image;

public class ImageViewHolder extends RecyclerView.ViewHolder {

    private ItemImageBinding binding;

    public ImageViewHolder(@NonNull ItemImageBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindTo(Image image) {
        binding.setImage(image);
    }
}
