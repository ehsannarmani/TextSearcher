package com.ehsannarmani.textsearcher

import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toAndroidRectF
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID
import java.util.regex.Pattern

class TextSearcher(
    val text: String,
    val scrollState: ScrollState,
    val scope: CoroutineScope
) {

    val searchResults = mutableStateListOf<SearchResult>()
    var annotatedText by mutableStateOf(buildAnnotatedString { append(text) })

    lateinit var textLayout: TextLayoutResult

    fun handleTextLayout(layoutResult: TextLayoutResult) {
        textLayout = layoutResult
    }

    fun search(
        query: String,
        queryStyle: SpanStyle = SpanStyle(),
        searchIndex: Int = 0,
        scrollAnimation: AnimationSpec<Float> = tween(300),
        rectOptions: RectOptions? = null
    ): SearchResult {
        require(this::textLayout.isInitialized) {
            "You have to set searcher text layout with handleTextLayout."
        }
        val indexOfQuery = text.indexOf(query, searchIndex = searchIndex)
        if (indexOfQuery == -1) {
            return SearchResult(
                resultsFound = 0,
                query = query,
                searchIndex = searchIndex,
                queryStyle = queryStyle,
                rect = null,
                rectOptions = rectOptions
            )
        }
        val queryLine = textLayout.getLineForOffset(indexOfQuery)
        val lineOffset = textLayout.getLineTop(queryLine)


        var rect: RectF
        textLayout.getBoundingBox(indexOfQuery).also { startRect ->
            textLayout.getBoundingBox(indexOfQuery + query.length - 1).also { endRect ->
                rect = startRect.copy(right = endRect.right).toAndroidRectF()
            }
        }
        scope.launch {
            annotatedText = buildAnnotatedString {
                append(text, start = 0, end = indexOfQuery)
                withStyle(queryStyle) {
                    append(text, start = indexOfQuery, end = indexOfQuery + query.length)
                }
                append(text, start = indexOfQuery + query.length, end = text.length)
            }
            scrollState.animateScrollTo(lineOffset.toInt(), animationSpec = scrollAnimation)
        }
        val searchPattern = Pattern.compile(query).matcher(text)
        var resultsCount = 0
        while (searchPattern.find()) resultsCount++

        val result = SearchResult(
            resultsFound = resultsCount,
            query = query,
            searchIndex = searchIndex,
            queryStyle = queryStyle,
            rect = rect,
            rectOptions = rectOptions
        )
        searchResults.add(result)

        return result
    }

    fun next(
        scrollAnimation: AnimationSpec<Float> = tween(300),
        queryStyle: SpanStyle? = null,
        rectOptions: RectOptions? = null
    ): SearchResult {
        require(searchResults.isNotEmpty()) {
            "No searches detected to go next word"
        }
        val lastSearch = searchResults.last()
        return search(
            query = lastSearch.query,
            searchIndex = lastSearch.searchIndex.plus(1),
            scrollAnimation = scrollAnimation,
            queryStyle = queryStyle ?: lastSearch.queryStyle,
            rectOptions = rectOptions ?: lastSearch.rectOptions
        )
    }

    fun previous(
        scrollAnimation: AnimationSpec<Float> = tween(300),
        queryStyle: SpanStyle? = null,
    ): SearchResult {
        require(searchResults.isNotEmpty()) {
            "No searches detected to go previous word"
        }
        val lastSearch = searchResults.last()
        return search(
            query = lastSearch.query,
            searchIndex = lastSearch.searchIndex.minus(1),
            scrollAnimation = scrollAnimation,
            queryStyle = queryStyle ?: lastSearch.queryStyle
        )
    }
}


@Composable
fun Modifier.searcher(searcher: TextSearcher): Modifier {
    return verticalScroll(searcher.scrollState).drawBehind {
        val lastSearch = searcher.searchResults.lastOrNull()
        if (lastSearch != null) {
            if (lastSearch.rectOptions != null) {
                if (lastSearch.rect != null) {
                    val rect = lastSearch.rect
                    drawIntoCanvas {
                        it.nativeCanvas.apply {
                            drawRect(
                                rect,
                                Paint().apply {
                                    color = lastSearch.rectOptions.color.toArgb()
                                    style = Paint.Style.STROKE
                                },
                            )
                        }
                    }

                }
            }
        }

    }
}

@Composable
fun rememberTextSearcher(text: String): TextSearcher {
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    return remember {
        TextSearcher(
            text = text,
            scope = scope,
            scrollState = scrollState
        )
    }
}

private fun String.indexOf(value: String, searchIndex: Int): Int {
    if (searchIndex < 0) return -1
    var previousIndex = 0
    repeat(searchIndex) {
        previousIndex += indexOf(value, startIndex = previousIndex) + 1
    }
    return indexOf(value, startIndex = if (searchIndex == 0) 0 else previousIndex)
}

data class RectOptions(
    val color: Color = Color.Unspecified,
    val padding: PaddingValues = PaddingValues(0.dp),
)