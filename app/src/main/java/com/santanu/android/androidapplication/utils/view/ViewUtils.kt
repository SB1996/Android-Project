package com.santanu.android.androidapplication.utils.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View


class ViewUtils(private val mContext: Context) {

    internal fun getBitmapFromView(view: View): Bitmap? {
        // Define a bitmap with the same size as the view
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        // Bind a canvas to it
        val canvas = Canvas(returnedBitmap)
        // Get the view's background
        val bgDrawable: Drawable? = view.background
        if (bgDrawable != null) {
            // has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas)
        }
        else {
            // does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE)
        }
        // draw the view on the canvas
        view.draw(canvas)
        // return the bitmap
        return returnedBitmap
    }
}