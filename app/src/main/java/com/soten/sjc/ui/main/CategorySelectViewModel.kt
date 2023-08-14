package com.soten.sjc.ui.main

import androidx.lifecycle.ViewModel
import com.soten.sjc.domain.model.congestion.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CategorySelectViewModel @Inject constructor() : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    fun getSelectedCategories(): List<Category> {
        categories.value.firstOrNull() ?: return emptyList()

        if (categories.value.first().isChecked) return emptyList()
        return categories.value.filter { it.isChecked }
    }

    fun initSelected() {
        val initializedCategories = categories.value.mapIndexed { index, category ->
            if (index == INDEX_FIRST) {
                return@mapIndexed category.copy(isChecked = true)
            }

            category.copy(isChecked = false)
        }

        setCategories(initializedCategories)
    }

    fun selectCategory(selectedCategory: Category) {
        if (isCheckFirst(selectedCategory)) return

        val resultCategories = categories.value.mapIndexed { index, category ->
            if (index == INDEX_FIRST) return@mapIndexed category.copy(isChecked = false)

            if (category == selectedCategory) category.copy(isChecked = category.isChecked.not())
            else category
        }

        if (resultCategories.count { it.isChecked } == 0) {
            initSelected()
            return
        }

        if (resultCategories.count { it.isChecked } == categories.value.size - 1) {
            initSelected()
            return
        }

        setCategories(resultCategories)
    }

    fun applyCategories(categories: List<Category>, filteredCategories: List<Category>) {
        setCategories(categories.map { category ->
            if (category.value in filteredCategories.map { it.value }) category.copy(isChecked = true)
            else category.copy(isChecked = false)
        })
    }

    private fun setCategories(categories: List<Category>) {
        _categories.value = categories
    }

    private fun isCheckFirst(selectedCategory: Category): Boolean {
        categories.value.firstOrNull() ?: return true

        val isFirst = selectedCategory == categories.value.first()
        val isFirstChecked = categories.value.first().isChecked

        if (isFirst && isFirstChecked) return true

        if (isFirst) {
            initSelected()
            return true
        }
        return false
    }

    companion object {
        const val INDEX_FIRST = 0
    }
}