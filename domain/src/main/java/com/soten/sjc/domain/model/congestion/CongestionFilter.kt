package com.soten.sjc.domain.model.congestion

import com.soten.sjc.domain.SearchUtil.isMatchByKeyword

data class CongestionFilter(
    val keyword: String = "",
    val categories: List<Category> = emptyList()
) {

    fun isMatch(
        targetKeyword: String,
        targetCategories: Category
    ): Boolean {
        return isMatchKeyword(targetKeyword) && isMatchCategories(targetCategories)
    }

    private fun isMatchKeyword(target: String): Boolean {
        return isMatchByKeyword(target, keyword)
    }

    private fun isMatchCategories(target: Category): Boolean {
        if (categories.isEmpty()) {
            return true
        }

        return target.value in categories.map { it.value }
    }
}
