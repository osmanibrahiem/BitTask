package com.beintrack.bittask.helper;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.DrawableRes;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;

import com.beintrack.bittask.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by Osman Ibrahiem
 */
public class DataBindingAdapter {

    @BindingAdapter("android:src")
    public static void setImageUri(AppCompatImageView view, String imageUri) {
        if (TextUtils.isEmpty(imageUri)) {
            view.setImageURI(null);
        } else {
            view.setImageURI(Uri.parse(imageUri));
        }
    }

    @BindingAdapter("android:src")
    public static void setImageUri(AppCompatImageView view, Uri imageUri) {
        view.setImageURI(imageUri);
    }

    @BindingAdapter("android:src")
    public static void setImageDrawable(AppCompatImageView view, Drawable drawable) {
        view.setImageDrawable(drawable);
    }

    @BindingAdapter("android:src")
    public static void setImageResource(AppCompatImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

    @BindingAdapter("android:profile")
    public static void setImageProfile(AppCompatImageView imageView, String url) {
        setImageUrl(imageView, url, R.drawable.placeholder_profile);
    }

    @BindingAdapter("android:url")
    public static void setImageUrl(AppCompatImageView imageView, String url) {
        setImageUrl(imageView, url, R.drawable.placeholder_image);
    }

    private static void setImageUrl(AppCompatImageView imageView, String url, @DrawableRes int placeholder) {
        if (url != null) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(placeholder)
                    .placeholder(placeholder)
                    .thumbnail(0.1f)
                    .into(imageView);
        }
    }
}
