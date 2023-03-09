package ig.core.android.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ig.core.android.base.BaseViewHolder

/**
   * @author PiseyChheang
   * Copyright (C) 2022 IGGroup - All Rights Reserved
   * File created on Feb 28, 2023, 2:22:30 PM
   * File name: BaseAdapter.kt
*/

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseAdapter<VB: ViewBinding, T>
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