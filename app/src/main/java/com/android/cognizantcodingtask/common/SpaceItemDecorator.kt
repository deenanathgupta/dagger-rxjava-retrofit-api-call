package com.android.cognizantcodingtask.common

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.annotation.DimenRes
import androidx.annotation.NonNull

class SpaceItemDecorator(private val mItemOffset: Int) : RecyclerView.ItemDecoration() {

    constructor(@NonNull context: Context, @DimenRes itemOffsetId: Int) : this(context.getResources().getDimensionPixelSize(itemOffsetId)) {}

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val isLast = position === state.getItemCount() - 1
        outRect.left = 0;
        outRect.right = 0;
        outRect.bottom = mItemOffset;

        //Removing top margin for first item
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = 0;
        }
        //Removing bottom margin for last element
        if (isLast) {
            outRect.bottom = 0
        }
    }
}