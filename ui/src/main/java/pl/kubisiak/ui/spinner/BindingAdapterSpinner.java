package pl.kubisiak.ui.spinner;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import java.util.List;

import pl.kubisiak.ui.BaseSubViewModel;

public class BindingAdapterSpinner {

    @BindingAdapter({"items", "dataTemplateSelector"})
    public static void bindItemsThroughViewModelAdapter(@NonNull Spinner view, @Nullable List<BaseSubViewModel> items, @Nullable DataTemplateSelector selector) {
        if (items != null) {
            view.setAdapter(new SpinnerAdapter(items, selector));
        } else {
            view.setAdapter(null);
        }
    }

    @BindingAdapter({"currentItem"})
    public static void bindCurrentItem(@NonNull Spinner view, BaseSubViewModel oldValue, BaseSubViewModel newValue) {
        if (newValue != null) {
            SpinnerAdapter adapter = ((SpinnerAdapter) view.getAdapter());
            if(adapter == null) {
                return;
            }
            int pos = adapter.getPosition(newValue);
            if (pos == -1) {
                return;
            }
            view.setSelection(pos, true);
        }
    }

    @BindingAdapter("currentItemAttrChanged")
    public static void bindCurrentItem(@NonNull Spinner view, @Nullable final InverseBindingListener bindingListener) {
        view.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (bindingListener != null) {
                    bindingListener.onChange();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //empty
            }
        });
    }

    @InverseBindingAdapter(attribute = "currentItem", event = "currentItemAttrChanged")
    public static Object getSelectedValue(AdapterView view) {
        Object retval = view.getSelectedItem();
        return retval;
    }

//    @BindingAdapter({"items"})
//    public static void bindItemsThroughViewModelAdapter(@NonNull RecyclerView recyclerView, @Nullable ObservableList<BaseViewModel> items) {
//        ViewModelObserverAdapter oldAdapter = (recyclerView.getAdapter() instanceof ViewModelObserverAdapter) ? (ViewModelObserverAdapter)recyclerView.getAdapter() : null;
//        if(oldAdapter != null && oldAdapter.isListSame(items)) {
//            //for some reason, binding engine calls change on instance of the list even though only the contents change, not the instance
//            //if the adapter is observing this list, it's already handling content changes so we have to ignore the "change" of the list here
//            return;
//        }
//        if (items != null ) {
//            recyclerView.setAdapter(new ViewModelObserverAdapter(items));
//        } else {
//            recyclerView.setAdapter(null);
//        }
//    }


}
