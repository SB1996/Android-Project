package com.santanu.android.androidapplication.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.santanu.android.androidapplication.R

class IconUtils {
    private val TAG: String = IconUtils::class.java.simpleName

    companion object {
        @SuppressLint("InflateParams")
        internal fun convertBadgeLayout(mContext : Context, count : Int, drawableId : Int) : Drawable {
            val inflater = LayoutInflater.from(mContext)
            val view: View = inflater.inflate(R.layout.badge_icon_layout, null)

            (view.findViewById<View>(R.id.imageView) as ImageView).setImageResource(drawableId)

            if (count == 0) {
                val counterTextPanel: View = view.findViewById<View>(R.id.counterValuePanel)
                counterTextPanel.visibility = View.GONE
            } else {
                val textView: TextView = view.findViewById<View>(R.id.tvBadgeCounter) as TextView
                textView.text = count.toString()
            }

            view.measure(
                View.MeasureSpec.makeMeasureSpec(
                    0,
                    View.MeasureSpec.UNSPECIFIED
                ),
                View.MeasureSpec.makeMeasureSpec(
                    0,
                    View.MeasureSpec.UNSPECIFIED
                )
            )
            view.layout(0, 0, view.measuredWidth, view.measuredHeight)

            view.isDrawingCacheEnabled = true
            view.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
            val bitmap = Bitmap.createBitmap(view.drawingCache)
            view.isDrawingCacheEnabled = false

            return BitmapDrawable(mContext.resources, bitmap)

        }
    }
}