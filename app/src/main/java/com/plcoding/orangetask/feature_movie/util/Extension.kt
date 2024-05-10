package com.plcoding.orangetask.feature_movie.util

import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import com.plcoding.orangetask.feature_movie.presentation.model.PagingPlaceholderKey
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

@Throws(JSONException::class)
fun JSONObject.toMap(): Map<String, Any> {
    val map = mutableMapOf<String, Any>()
    val keysItr: Iterator<String> = this.keys()
    while (keysItr.hasNext()) {
        val key = keysItr.next()
        var value: Any = this.get(key)
        when (value) {
            is JSONArray -> value = value.toList()
            is JSONObject -> value = value.toMap()
        }
        map[key] = value
    }
    return map
}

@Throws(JSONException::class)
fun JSONArray.toList(): List<Any> {
    val list = mutableListOf<Any>()
    for (i in 0 until this.length()) {
        var value: Any = this[i]
        when (value) {
            is JSONArray -> value = value.toList()
            is JSONObject -> value = value.toMap()
        }
        list.add(value)
    }
    return list
}
fun <T : Any> LazyGridScope.itemsPaging(
    items: LazyPagingItems<T>,
    key: ((item: T) -> Any)? = null,
    itemContent: @Composable LazyGridItemScope.(value: T?) -> Unit
) {
    items(
        count = items.itemCount,
        key = if (key == null) null else { index ->
            val item = items.peek(index)
            if (item == null) {
                PagingPlaceholderKey(index)
            } else {
                key(item)
            }
        }
    ) { index ->
        itemContent(items[index])
    }
}