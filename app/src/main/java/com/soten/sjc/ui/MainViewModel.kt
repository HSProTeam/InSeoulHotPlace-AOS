package com.soten.sjc.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soten.sjc.domain.model.congestion.Category
import com.soten.sjc.domain.model.congestion.CongestionInfo
import com.soten.sjc.domain.repository.CongestRepository
import com.soten.sjc.util.SearchUtil.search
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val congestRepository: CongestRepository
) : ViewModel() {

    private val _congestionInfos = MutableStateFlow<List<CongestionInfo>>(emptyList())
    val congestionInfos = _congestionInfos.asStateFlow()

    private val _keyword = MutableStateFlow("")
    val keyword = _keyword.asStateFlow()

    val searchCongestionInfos = congestionInfos.combine(keyword) { infos, keyword ->
        infos.filter {
            search(it.areaName, keyword)
        }
    }

    private val _categories = MutableStateFlow(emptyList<Category>())
    val categories = _categories.asStateFlow()

    init {
        fetchCongestionInfos()
    }

    fun fetchCongestionInfos() {
        viewModelScope.launch {
            congestRepository.fetchCongests()
                .onSuccess { congestionInfos ->
                    _categories.value = congestionInfos.getCategories()
                    _congestionInfos.value = congestionInfos.value
                }.onFailure {
                    Log.e("ASD", "asd", it)
                }
        }
    }

    fun inputKeyword(input: String) {
        _keyword.value = input
    }
}