package pl.kubisiak.demo.ui;

import org.jetbrains.annotations.NotNull;

import pl.kubisiak.demo.R;
import pl.kubisiak.demo.spinner.DataTemplateSelector;
import pl.kubisiak.demo.ui.main.MainViewModel;

public class SpinnerDTS implements DataTemplateSelector {

    @Override
    public int provideLayoutId(@NotNull BaseViewModel viewModel) {
        if (viewModel instanceof MainViewModel.SpinnerItem)
            return R.layout.item_blogname;
        return -1;
    }

    public static SpinnerDTS INSTANCE = new SpinnerDTS();
}
