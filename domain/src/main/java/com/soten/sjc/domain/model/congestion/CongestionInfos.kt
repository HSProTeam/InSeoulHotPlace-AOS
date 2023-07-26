package com.soten.sjc.domain.model.congestion

@JvmInline
value class CongestionInfos(val value: List<CongestionInfo>) {

    fun getCategories(): List<Category> {
        return value.groupBy { it.category }.keys.toList()
    }

    fun getByCategory(category: Category): List<CongestionInfo> {
        return value.filter { it.category == category }
    }
}
