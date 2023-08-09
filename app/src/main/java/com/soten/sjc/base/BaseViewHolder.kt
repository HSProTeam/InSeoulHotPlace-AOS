package com.soten.sjc.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.soten.sjc.BR

open class BaseViewHolder(
    private val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {

    open fun <T> bind(item: T, clickedCallback: ItemClicked?) {
        binding.setVariable(BR.item, item)

        if (clickedCallback != null) {
            binding.setVariable(BR.callback, clickedCallback)
        }

        binding.executePendingBindings()
    }

    companion object {
        fun create(@LayoutRes layoutResId: Int, parent: ViewGroup) =
            BaseViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    layoutResId,
                    parent,
                    false
                )
            )
    }
}
