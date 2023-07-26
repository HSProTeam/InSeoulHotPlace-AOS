package com.soten.sjc.domain.model.congestion

import com.soten.sjc.domain.SearchUtil.isMatchByKeyword

data class CongestionFilter(
    val keyword: String = "",
    val category: Category? = null,
) {

    fun isMatch(
        targetKeyword: String,
        targetCategory: Category?
    ): Boolean {
        return isMatchKeyword(targetKeyword) && isMatchCategory(targetCategory)
    }

    private fun isMatchKeyword(target: String): Boolean {
        return isMatchByKeyword(target, keyword)
    }

    private fun isMatchCategory(target: Category?): Boolean {
        if (category == null) {
            return true
        }

        return target == category
    }
}
