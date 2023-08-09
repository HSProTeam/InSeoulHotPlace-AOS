package com.soten.sjc.base

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

open class BaseAdapter(
    @LayoutRes private val layoutResId: Int,
    private val clickedCallback: ItemClicked? = null
) : RecyclerView.Adapter<BaseViewHolder>() {

    protected val items = mutableListOf<Any>()

    open fun replaceAll(items: List<Any>?) {
        items ?: return
        this.items.run {
            clear()
            addAll(items)
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BaseViewHolder.create(layoutResId, parent)

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(items[position], clickedCallback)
    }

    override fun getItemCount(): Int = items.size
}
