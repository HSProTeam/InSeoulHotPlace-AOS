package com.soten.sjc.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soten.sjc.domain.model.congestion.Category
import com.soten.sjc.domain.model.congestion.CongestionFilter
import com.soten.sjc.domain.model.congestion.CongestionInfo
import com.soten.sjc.domain.model.congestion.CongestionInfos
import com.soten.sjc.domain.model.congestion.CongestionsSorter
import com.soten.sjc.domain.repository.CongestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val congestRepository: CongestRepository
) : ViewModel() {

    private val congestionInfos = MutableStateFlow(CongestionInfos.EMPTY)

    private val _congestionFilter = MutableStateFlow(CongestionFilter())
    val congestionFilter = _congestionFilter.asStateFlow()

    private val _congestionSorter = MutableStateFlow(CongestionsSorter())
    val congestionSorter = _congestionSorter.asStateFlow()

    private val _categories = MutableStateFlow(emptyList<Category>())
    val categories = _categories.asStateFlow()

    private val _error = MutableSharedFlow<Throwable>()
    val error = _error.asSharedFlow()

    val filteredCongestionInfos =
        combine(congestionInfos, _congestionFilter, congestionSorter) { infos, filter, sorter ->
            infos.search(filter, sorter)
        }

    init {
        fetchCongestionInfos()
    }

    fun fetchCongestionInfos() {
        viewModelScope.launch {
            congestRepository.fetchCongests()
                .onSuccess { congests ->
                    _categories.value = congests.getCategories()
                    congestionInfos.value = congests
                }.onFailure {
                    _error.emit(it)
                }
        }
    }

    fun inputKeyword(input: String) {
        _congestionFilter.update { filter ->
            filter.copy(input)
        }
    }

    fun toggleBookmark(congestionInfo: CongestionInfo) {
        viewModelScope.launch {
            if (congestionInfo.isBookmark) {
                congestRepository.deleteBookmark(congestionInfo.areaName)
            } else {
                congestRepository.insertBookmark(congestionInfo.areaName)
            }
        }

        congestionInfos.update {
            it.toggleBookmark(congestionInfo.areaName)
        }
    }

    fun initSorter() {
        _congestionSorter.value = CongestionsSorter()
    }

    fun setSorter() {
        _congestionSorter.update { sorter ->
            sorter.copy(isAscend = sorter.isAscend.not())
        }
    }

    fun setSorter(isName: Boolean = false, isCongestNumber: Boolean = false) {
        _congestionSorter.update { sorter ->
            sorter.copy(isName, isCongestNumber)
        }
    }
}
