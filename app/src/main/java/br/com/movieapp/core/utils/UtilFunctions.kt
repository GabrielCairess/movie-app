package br.com.movieapp.core.utils

import timber.log.Timber

object UtilFunctions {

    fun logError(tag: String, message: String) {
        Timber.tag(tag).e("ERROR => $message")
    }

    fun logInfo(tag: String, message: String) {
        Timber.tag(tag).e("INFO => $message")
    }
}