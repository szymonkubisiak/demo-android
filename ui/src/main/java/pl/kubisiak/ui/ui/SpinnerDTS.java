package pl.kubisiak.ui.ui;

import org.jetbrains.annotations.NotNull;

import pl.kubisiak.ui.BaseSubViewModel;
import pl.kubisiak.ui.R;
import pl.kubisiak.ui.spinner.DataTemplateSelector;
import pl.kubisiak.ui.main.MainViewModel;

public class SpinnerDTS implements DataTemplateSelector {

    @Override
    public int provideLayoutId(@NotNull BaseSubViewModel viewModel) {
        if (viewModel instanceof MainViewModel.SpinnerItem)
            return R.layout.item_blogname;
        return -1;
    }

    public static SpinnerDTS INSTANCE = new SpinnerDTS();
}
