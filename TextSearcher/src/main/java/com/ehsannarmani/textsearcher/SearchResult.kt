package com.ehsannarmani.textsearcher

import android.graphics.RectF
import androidx.compose.ui.text.SpanStyle
import java.util.UUID

data class SearchResult(
    val id:String = UUID.randomUUID().toString(),
    val query:String,
    val queryStyle: SpanStyle,
    val resultsFound:Int,
    val searchIndex:Int,
    val rect:RectF?,
    val rectOptions: RectOptions?
)