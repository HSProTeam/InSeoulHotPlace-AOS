package com.soten.sjc.domain.model.congestion

@JvmInline
value class CongestionInfos(val value: List<CongestionInfo>) {

    fun search(filter: CongestionFilter, sorter: CongestionsSorter): CongestionInfos {
        return CongestionInfos(sorting(value.filter { congestionInfo ->
            filter.isMatch(congestionInfo.areaName, congestionInfo.category)
        }, sorter))
    }

    fun toggleBookmark(areaName: String): CongestionInfos {
        return CongestionInfos(value.map { congestionInfo ->
            if (congestionInfo.areaName == areaName) {
                return@map congestionInfo.copy(isBookmark = congestionInfo.isBookmark.not())
            }

            congestionInfo
        })
    }

    fun getCategories(): List<Category> {
        return value.groupBy { it.category }.keys.toList()
    }

    private fun sorting(
        congestions: List<CongestionInfo>, sorter: CongestionsSorter
    ): List<CongestionInfo> {
        return CongestSortState.of(sorter).sort(congestions)
    }

    companion object {
        val EMPTY = CongestionInfos(emptyList())
    }
}
