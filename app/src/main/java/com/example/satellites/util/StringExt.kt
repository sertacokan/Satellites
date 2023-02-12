package com.example.satellites.util

import android.text.Html
import android.text.Spanned

fun CharSequence.fromHtml(): Spanned? {
    return Html.fromHtml(this.toString(), Html.FROM_HTML_MODE_COMPACT)
}