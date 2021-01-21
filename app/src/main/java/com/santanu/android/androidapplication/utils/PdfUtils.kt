package com.santanu.android.androidapplication.utils

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import com.itextpdf.text.BadElementException
import com.itextpdf.text.Document
import com.itextpdf.text.DocumentException
import com.itextpdf.text.Image
import com.itextpdf.text.pdf.PdfWriter
import com.santanu.android.androidapplication.utils.view.ViewUtils
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.MalformedURLException


class PdfUtils(private val mContext: Context) {
    private val TAG: String = PdfUtils::class.java.simpleName
    private lateinit var mViewUtils: ViewUtils
    private lateinit var screen: Bitmap

    internal fun setViewScreen(rootView: View) {
        mViewUtils = ViewUtils(mContext)
        mViewUtils.getBitmapFromView(rootView).let {
            if (it != null) {
                screen = it
            }
        }
    }

    internal fun generatePdfFile(filePath: String) {
        try {
            val document = Document()
            PdfWriter.getInstance(document, FileOutputStream(filePath))
            document.open()
            val stream = ByteArrayOutputStream()
            screen.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray: ByteArray = stream.toByteArray()
            addImage(document, byteArray)
            document.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun addImage(document: Document, byteArray: ByteArray) {
        val image: Image
        try {
            image = Image.getInstance(byteArray)
            try {
                document.add(image)
            } catch (e: DocumentException) {
                e.printStackTrace()
            }
        } catch (e: BadElementException) {
            e.printStackTrace()
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}