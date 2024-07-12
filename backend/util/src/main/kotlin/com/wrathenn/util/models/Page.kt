package com.wrathenn.util.models

data class Page<out T>(
    val page: Int,
    val pageSize: Int,
    val totalElements: Int,
    val items: List<T>,
) {
    fun<A> map(f: (T) -> A): Page<A> = Page(
        page = page,
        pageSize = pageSize,
        totalElements = totalElements,
        items = items.map(f),
    )

    companion object {
        val EMPTY: Page<Nothing> = Page(
            page = 0,
            pageSize = 0,
            totalElements = 0,
            items = listOf(),
        )
    }
}
