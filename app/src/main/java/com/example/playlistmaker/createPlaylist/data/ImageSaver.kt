package com.example.playlistmaker.createPlaylist.data

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.core.net.toUri
import java.io.File

class ImageSaver(private val context: Application) {

    fun saveToInternal(uri: Uri, fileName: String): Uri {
        val source = ImageDecoder.createSource(context.contentResolver, uri)
        val bitmap = ImageDecoder.decodeBitmap(source)
        val directory = context.getDir("playlist_covers", Context.MODE_PRIVATE)
        val file = File(directory, "$fileName.jpeg")
        val fileOutputStream = file.outputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
        fileOutputStream.flush()
        fileOutputStream.close()
        return file.toUri()
    }
}