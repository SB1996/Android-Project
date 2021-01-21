package com.santanu.android.androidapplication.utils

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.TextUtils
import androidx.loader.content.CursorLoader
import java.io.*
import java.lang.Long

class FileUtils {

    /** move file one path to another and after delete original file **/
    private fun moveFile(inputPath: String, inputFile: String, outputPath: String, deleteOriginal: Boolean = true) {
        var `in`: InputStream? = null
        var out: OutputStream? = null
        try {
            // create output directory if it doesn't exist
            val dir = File(outputPath)
            if (!dir.exists()) {
                dir.mkdirs()
            }

            `in` = FileInputStream(inputPath + inputFile)
            out = FileOutputStream(outputPath + inputFile)
            val buffer = ByteArray(1024)

            var read: Int
            while (`in`.read(buffer).also { read = it } != -1) {
                out.write(buffer, 0, read)
            }

            `in`.close()
            `in` = null

            // write the output file
            out.flush()
            out.close()
            out = null

            if (deleteOriginal) {
                try {
                    // delete the original file
                    File(inputPath + inputFile).delete()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        } catch (ex: FileNotFoundException) {
            ex.printStackTrace()
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
    }

    private fun copyFile(inputPath: String, inputFile: String, outputPath: String) {
        var `in`: InputStream? = null
        var out: OutputStream? = null
        try {

            //create output directory if it doesn't exist
            val dir = File(outputPath)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            `in` = FileInputStream(inputPath + inputFile)
            out = FileOutputStream(outputPath + inputFile)
            val buffer = ByteArray(1024)
            var read: Int
            while (`in`.read(buffer).also { read = it } != -1) {
                out.write(buffer, 0, read)
            }
            `in`.close()
            `in` = null

            // write the output file (You have now copied the file)
            out.flush()
            out.close()
            out = null
        } catch (ex: FileNotFoundException) {
            ex.printStackTrace()
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
    }

    private fun deleteFile(inputPath: String, inputFile: String) {
        try {
            // delete the original file
            File(inputPath + inputFile).delete()
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
    }

    internal fun getFilePathFromURI(context: Context, contentUri: Uri?): String? {
        //copy file and send new file path
        val fileName = getFileName(contentUri)
        if (!TextUtils.isEmpty(fileName)) {
            val copyFile = File(context.externalCacheDir.toString() + File.separator + fileName)
            copyFile(context, contentUri, copyFile)
            return copyFile.absolutePath
        }
        return null
    }

    internal fun deleteEmptyFolder(rootFolder: File) {
        if (!rootFolder.isDirectory) return
        val childFiles = rootFolder.listFiles() ?: return
        if (childFiles.isEmpty()) {
            rootFolder.delete()
        } else {
            for (childFile in childFiles) {
                deleteEmptyFolder(childFile)
            }
        }
    }

    internal fun getFileName(uri: Uri?): String? {
        if (uri == null) return null
        var fileName: String? = null
        val path = uri.path
        val cut = path!!.lastIndexOf('/')
        if (cut != -1) {
            fileName = path.substring(cut + 1)
        }
        return fileName
    }

    internal fun copyFile(context: Context, srcUri: Uri?, dstFile: File?) {
        try {
            val inputStream: InputStream = context.contentResolver.openInputStream(srcUri!!) ?: return
            val outputStream: OutputStream = FileOutputStream(dstFile)
            //IOUtils.copy(inputStream, outputStream)
            inputStream.close()
            outputStream.close()
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }

    internal fun getFilePath(context: Context, uri: Uri): String? {
        var currentApiVersion: Int
        try {
            currentApiVersion = Build.VERSION.SDK_INT
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            currentApiVersion = 3
        } catch (e: Exception) {
            e.printStackTrace()
            currentApiVersion = 3
        }

        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
            var filePath = ""
            val wholeID = DocumentsContract.getDocumentId(uri)

            val id = wholeID.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]

            val column = arrayOf(MediaStore.Images.Media.DATA)

            val sel = MediaStore.Images.Media._ID + "=?"

            val cursor = context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, arrayOf(id), null
            )!!

            val columnIndex = cursor.getColumnIndex(column[0])

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex)
            }
            cursor.close()
            return filePath
        } else if (currentApiVersion <= Build.VERSION_CODES.HONEYCOMB_MR2 && currentApiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            var result: String? = null

            val cursorLoader = CursorLoader(
                context,
                uri, proj, null, null, null
            )
            val cursor = cursorLoader.loadInBackground()

            if (cursor != null) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                result = cursor.getString(columnIndex)
            }
            return result
        } else {
            val colIndex: String
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = context.contentResolver.query(uri, proj, null, null, null)!!
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            colIndex = cursor.getString(columnIndex)
            cursor.close()
            return colIndex
        }
    }

    internal fun getPath(context: Context, uri: Uri): String? {
        val isKitKatOrAbove = true
        
        if (DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }

                
            } else if (isDownloadsDocument(uri)) {

                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), Long.valueOf(id)
                )

                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                var contentUri: Uri? = null
                when (type) {
                    "image" -> {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    }
                    "video" -> {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    }
                    "audio" -> {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                }

                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])

                return getDataColumn(context, contentUri, selection, selectionArgs)
            }
        } else if ("content".equals(uri.scheme!!, ignoreCase = true)) {
            return getDataColumn(context, uri, null, null)
        } else if ("file".equals(uri.scheme!!, ignoreCase = true)) {
            return uri.path
        }

        return null
    }

    private fun getDataColumn(
        context: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?
    ): String? {

        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)

        try {
            cursor = context.contentResolver.query(
                uri!!,
                projection,
                selection,
                selectionArgs,
                null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(columnIndex)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

}