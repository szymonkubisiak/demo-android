package pl.kubisiak.demo.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import pl.kubisiak.demo.R
import pl.kubisiak.demo.ui.BaseViewModel
import pl.kubisiak.demo.ui.listtest.SimpleItemViewModel

class ViewModelAdapter(private val items: List<BaseViewModel>) : RecyclerView.Adapter<ViewModelViewHolder>()  {

    override fun getItemCount(): Int =
        items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, viewType, parent, false)
        return ViewModelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewModelViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    private fun getLayoutIdForPosition(position: Int): Int {
        return when (items[position]) {
            is SimpleItemViewModel -> R.layout.item_simpletest
            else -> -1
        }
    }
}