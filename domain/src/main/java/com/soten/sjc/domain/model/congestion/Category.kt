package com.soten.sjc.domain.model.congestion

data class Category(val value: String, val isChecked: Boolean = false) {

    companion object {
        val ALL = Category("전체", true)
    }
}
