package com.soten.sjc.domain.model.congestion

@JvmInline
value class CongestionInfos(val value: List<CongestionInfo>) {

    fun search(filter: CongestionFilter, sorter: CongestionsSorter): CongestionInfos {
        return CongestionInfos(
            sorting(
                value.filter { congestionInfo ->
                    filter.isMatch(congestionInfo.areaName, congestionInfo.category)
                }.sortedBy { it.isBookmark },
                sorter
            )
        )
    }

    fun toggleBookmark(areaName: String): CongestionInfos {
        return CongestionInfos(
            value.map { congestionInfo ->
                if (congestionInfo.areaName == areaName) {
                    congestionInfo.copy(isBookmark = congestionInfo.isBookmark.not())
                } else {
                    congestionInfo
                }
            }
        )
    }

    fun getCategories(): List<Category> {
        return value.groupBy { it.category }.keys.toList()
    }

    private fun sorting(list: List<CongestionInfo>, sorter: CongestionsSorter): List<CongestionInfo> {
        val isAreaName = sorter.isAreaName
        val isCongestNumber = sorter.isCongestNumber
        val isAscend = sorter.isAscend
        return when {
            isAreaName && isAscend -> list.sortedWith(
                compareByDescending<CongestionInfo> { it.isBookmark }
                    .thenBy { it.areaName }
            )

            isAreaName && isAscend.not() -> list.sortedWith(
                compareByDescending<CongestionInfo> { it.isBookmark }
                    .thenByDescending { it.areaName }
            )

            isCongestNumber && isAscend -> list.sortedWith(
                compareByDescending<CongestionInfo> { it.isBookmark }
                    .thenBy { it.areaCongestNumber }
            )

            isCongestNumber && isAscend.not() -> list.sortedWith(
                compareByDescending<CongestionInfo> { it.isBookmark }
                    .thenByDescending { it.areaCongestNumber }
            )

            else -> list.sortedWith(
                compareByDescending<CongestionInfo> { it.isBookmark }
                    .thenBy { it.areaName }
            )
        }
    }

    companion object {
        val EMPTY = CongestionInfos(emptyList())
    }
}
