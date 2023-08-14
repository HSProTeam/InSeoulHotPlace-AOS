package com.soten.sjc.ui.main

import android.content.Context
import android.graphics.Canvas
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

class ItemDecoration(@ColorRes color: Int,context: Context, orientation: Int) :
    DividerItemDecoration(context, orientation) {

    init {
        val attributes = context.obtainStyledAttributes(intArrayOf(android.R.attr.listDivider))
        val divider = attributes.getDrawable(0)
        attributes.recycle()

        val wrappedDivider = divider?.let { DrawableCompat.wrap(it) }
        wrappedDivider?.let {
            DrawableCompat.setTint(
                it,
                ContextCompat.getColor(context, color)
            )
        }
        wrappedDivider?.let { setDrawable(it) }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        val drawable = drawable ?: return

        for (i in 0 until childCount - REMOVE_COUNT) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + drawable.intrinsicHeight

            drawable.setBounds(left, top, right, bottom)
            drawable.draw(c)
        }
    }

    companion object {
        private const val REMOVE_COUNT = 1
    }
}