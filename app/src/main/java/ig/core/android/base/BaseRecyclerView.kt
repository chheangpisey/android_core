package ig.core.android.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * @author piseychheang
 * Created on 11/23/22 at 11:00 AM
 * Modified By piseychheang on 11/23/22 at 11:00 AM
 * File name: BaseRecyclerView.kt
 */

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseRecyclerView<VB: ViewBinding, T>
    : RecyclerView.Adapter<BaseViewHolder<T>>() {

    abstract fun layout(inflater: LayoutInflater, parent: ViewGroup): VB
    abstract fun bindView(context: Context, binding: VB, item: T, position: Int)

    internal val items = ArrayList<T>()

    protected lateinit var context: Context

    fun setItems(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)
        notifyItemRangeChanged(0, this.items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        context = parent.context
        return ItemListHolder(layout(LayoutInflater.from(context), parent))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        val item = this.items[position]
        holder.bind(context, item, position)
    }

    override fun getItemCount(): Int = this.items.size

    inner class ItemListHolder(private val binding: VB): BaseViewHolder<T>(binding.root){
        override fun bind(context: Context, item: T, position: Int) {
            bindView(context, binding, item, position)
        }
    }

}