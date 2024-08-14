package com.example.stockmanager.ext

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

fun Context.saveBitmapTemporarily(bitmap: Bitmap): Uri {
    val (uri, file) = createTempImageFile()
    file.outputStream().use { stream ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.flush()
    }

    return uri
}

fun Context.createTempImageFile(): Pair<Uri, File> {
    val directory = File(cacheDir, "images")
    directory.mkdirIfNeeded()

    val file = File.createTempFile("IMG_", ".png", directory)
    val uri = FileProvider.getUriForFile(this, "$packageName.fileprovider", file)

    return uri to file
}
