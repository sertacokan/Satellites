package com.example.satellites.util

import android.widget.SearchView
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow

fun SearchView.queryFlow() = callbackFlow {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            trySendBlocking(query)
            return true
        }

        override fun onQueryTextChange(query: String): Boolean {
            trySendBlocking(query)
            return true
        }
    })
    awaitClose {
        setOnQueryTextListener(null)
    }
}