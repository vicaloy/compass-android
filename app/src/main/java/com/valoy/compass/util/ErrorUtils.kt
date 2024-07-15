package com.valoy.compass.util

import android.util.Log
import kotlinx.coroutines.CancellationException

suspend fun tryCatch(tryBlock: suspend () -> Unit, catchBlock: () -> Unit) {
    try {
        tryBlock()
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        catchBlock()
        Log.e("Exception", "Error occurred: ${e.message}", e)
    }
}