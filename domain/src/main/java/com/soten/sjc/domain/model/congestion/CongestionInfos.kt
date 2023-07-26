package com.soten.sjc.domain.model.congestion


@JvmInline
value class CongestionInfos(val value: List<CongestionInfo>) {

    fun search(filter: CongestionFilter): CongestionInfos {
        return CongestionInfos(
            value.filter { congestionInfo ->
                filter.isMatch(congestionInfo.areaName, congestionInfo.category)
            }
        )
    }

    fun getCategories(): List<Category> {
        return value.groupBy { it.category }.keys.toList()
    }

    companion object {
        val EMPTY = CongestionInfos(emptyList())
    }
}
