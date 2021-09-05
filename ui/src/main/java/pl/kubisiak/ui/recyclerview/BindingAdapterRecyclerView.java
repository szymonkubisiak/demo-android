package pl.kubisiak.ui.recyclerview;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import pl.kubisiak.ui.BaseViewModel;

public class BindingAdapterRecyclerView {

    @BindingAdapter({"items"})
    public static <VM extends BaseViewModel> void bindItemsThroughViewModelAdapter(@NonNull RecyclerView recyclerView, @Nullable List<VM> items) {
        if (items != null) {
            recyclerView.setAdapter(new ViewModelAdapter<>(items));
        } else {
            recyclerView.setAdapter(null);
        }
    }

    @BindingAdapter({"items"})
    public static <VM extends BaseViewModel> void bindItemsThroughViewModelAdapter(@NonNull RecyclerView recyclerView, @Nullable ObservableList<VM> items) {
        ViewModelObserverAdapter<?> oldAdapter = (recyclerView.getAdapter() instanceof ViewModelObserverAdapter) ? (ViewModelObserverAdapter<?>) recyclerView.getAdapter() : null;
        if (oldAdapter != null && oldAdapter.isListSame(items)) {
            //for some reason, binding engine calls change on instance of the list even though only the contents change, not the instance
            //if the adapter is observing this list, it's already handling content changes so we have to ignore the "change" of the list here
            return;
        }
        if (items != null) {
            recyclerView.setAdapter(new ViewModelObserverAdapter<>(items));
        } else {
            recyclerView.setAdapter(null);
        }
    }

    @BindingAdapter({"vertical"})
    public static void setRecyclerViewOrientation(@NonNull RecyclerView recyclerView, @Nullable Boolean isVertical) {
        if (isVertical == null) isVertical = true;
        Context recyclerViewContext = recyclerView.getContext();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerViewContext, isVertical ? RecyclerView.VERTICAL : RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

    }
}
