package pl.kubisiak.ui.ui;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class BindngAdapter {

    @BindingAdapter(value = {"imageUrl"})
    public static void loadImage(ImageView view, String url) {
        if(url != null) {
            Context context = view.getContext();
            Glide.with(context).load(url).into(view);
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }
}
