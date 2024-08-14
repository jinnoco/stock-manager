package com.example.stockmanager.ext

import java.io.File

fun File.mkdirIfNeeded(): Boolean {
    return if (exists()) {
        true
    } else {
        mkdir()
    }
}