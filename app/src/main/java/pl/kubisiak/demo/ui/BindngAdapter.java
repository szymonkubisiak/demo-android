package pl.kubisiak.demo.ui;

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
        } else {
            view.setVisibility(View.GONE);
        }
    }
}
