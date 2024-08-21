package com.example.stockmanager.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

class Base64Util {
    fun decodeBase64ToBitmap(base64String: String): Bitmap? {
        return try {
            val decodedString = Base64.decode(base64String, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        } catch (e: IllegalArgumentException) {
            null
        }
    }
}