package com.soten.sjc.domain.model.congestion

enum class CongestSortState(
    val sort: (List<CongestionInfo>) -> List<CongestionInfo>
) {

    NAME_ASC({ congestionInfos ->
        congestionInfos.sortedWith(
            compareByDescending<CongestionInfo> { it.isBookmark }
                .thenBy { it.areaName }
        )
    }),

    NAME_DESC({ congestionInfos ->
        congestionInfos.sortedWith(
            compareByDescending<CongestionInfo> { it.isBookmark }
                .thenByDescending { it.areaName }
        )
    }),

    CONGESTION_LEVEL_ASC({ congestionInfos ->
        congestionInfos.sortedWith(
            compareByDescending<CongestionInfo> { it.isBookmark }
                .thenBy { it.areaCongestNumber }
        )
    }),

    CONGESTION_LEVEL_DESC({ congestionInfos ->
        congestionInfos.sortedWith(
            compareByDescending<CongestionInfo> { it.isBookmark }
                .thenByDescending { it.areaCongestNumber }
        )
    });

    companion object {

        fun of(sorter: CongestionsSorter): CongestSortState {
            val isAreaName = sorter.isAreaName
            val isCongestNumber = sorter.isCongestNumber
            val isAscend = sorter.isAscend
            return when {
                isAreaName && isAscend -> NAME_ASC
                isAreaName && isAscend.not() -> NAME_DESC
                isCongestNumber && isAscend -> CONGESTION_LEVEL_ASC
                isCongestNumber && isAscend.not() -> CONGESTION_LEVEL_DESC
                else -> NAME_ASC
            }
        }
    }
}
