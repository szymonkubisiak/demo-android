package pl.kubisiak.ui.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import pl.kubisiak.ui.BaseViewModel
import pl.kubisiak.ui.R
import pl.kubisiak.ui.items.*
import java.lang.ref.WeakReference

open class ViewModelAdapter<VM : BaseViewModel>(protected open val items: List<VM>) : RecyclerView.Adapter<ViewModelViewHolder>() {

    override fun getItemCount(): Int =
        items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, viewType, parent, false)
        binding.lifecycleOwner = ViewTreeLifecycleOwner.get(parent) ?: parent.context as LifecycleOwner
        return ViewModelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewModelViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    //TODO: replace with DataTemplateSelector
    private fun getLayoutIdForPosition(position: Int): Int {
        return when (items[position]) {
            is ImageItemViewModel -> R.layout.item_image
            is SimpleItemViewModel -> R.layout.item_simpletest
            is PostItemViewModel -> R.layout.item_post
            is LoadingItemViewModel -> R.layout.item_loader
            else -> -1
        }
    }
}

class ViewModelObserverAdapter<VM : BaseViewModel>(override val items: ObservableList<VM>) : ViewModelAdapter<VM>(items) {
    private val eventTranslator = ListChangedEventTranslator<VM>(this)

    fun <VM : BaseViewModel> isListSame(newItems: ObservableList<VM>?): Boolean {
        return newItems === items
    }

    init {
        eventTranslator.subscribeTo(items)
    }

//TODO: research using onAttached instead of init
//    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
//        eventTranslator.subscribeTo(items)
//    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        eventTranslator.unsubscribeFrom(items)
    }
}

/**
 * ListChangedEventTranslator
 * This class implements ObservableList.OnListChangedCallback and translates its "content changed" events to corresponding RecyclerView events.
 */
//TODO: Needs more research if the weak observing breaks anything.
internal class ListChangedEventTranslator<T>(strongAdapter: RecyclerView.Adapter<*>) : ObservableList.OnListChangedCallback<ObservableList<T>>() {
    private val weakAdapter = WeakReference(strongAdapter)
    private fun getAdapter(list: ObservableList<T>?): RecyclerView.Adapter<*>? {
        val retval = weakAdapter.get()
        if (retval == null) {
            unsubscribeFrom(list)
        }
        return retval
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onChanged(sender: ObservableList<T>?) {
        getAdapter(sender)?.notifyDataSetChanged()
    }

    override fun onItemRangeChanged(sender: ObservableList<T>?, positionStart: Int, itemCount: Int) {
        getAdapter(sender)?.notifyItemRangeChanged(positionStart, itemCount)
    }

    override fun onItemRangeInserted(sender: ObservableList<T>?, positionStart: Int, itemCount: Int) {
        getAdapter(sender)?.notifyItemRangeInserted(positionStart, itemCount)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onItemRangeMoved(sender: ObservableList<T>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
        if (itemCount == 1)
            getAdapter(sender)?.notifyItemMoved(fromPosition, toPosition)
        else
            getAdapter(sender)?.notifyDataSetChanged()
    }

    override fun onItemRangeRemoved(sender: ObservableList<T>?, positionStart: Int, itemCount: Int) {
        getAdapter(sender)?.notifyItemRangeRemoved(positionStart, itemCount)
    }

    fun subscribeTo(list: ObservableList<T>?) = list?.addOnListChangedCallback(this)
    fun unsubscribeFrom(list: ObservableList<T>?) = list?.removeOnListChangedCallback(this)
}