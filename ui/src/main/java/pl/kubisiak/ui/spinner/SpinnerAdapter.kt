package pl.kubisiak.ui.spinner


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LifecycleOwner
import pl.kubisiak.ui.BaseSubViewModel


class SpinnerAdapter(
    private val items: List<BaseSubViewModel>,
    private val selector: DataTemplateSelector,
) : BaseAdapter() {

    override fun getCount(): Int {
        val retval = items.count()
        return retval
    }

    override fun getItem(position: Int): Any {
        val retval = items[position]
        return retval
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(parent!!.context)
        val viewModel = items[position]
        val layoutResId = selector.provideLayoutId(viewModel)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutResId, parent, false)
        //TODO: set appropriate lifecycle owner URGENT
        val lifecycleOwner = parent.context as? LifecycleOwner
        lifecycleOwner?.also {
            binding.lifecycleOwner = it
        }
        binding.setVariable(BR.vm, viewModel)
        binding.executePendingBindings()
        val view = binding.root
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getView(position, convertView, parent)
    }

    fun getPosition(viewModel: BaseSubViewModel?): Int {
        return items.indexOf(viewModel)
    }
}