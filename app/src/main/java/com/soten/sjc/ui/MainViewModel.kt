package com.soten.sjc.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ApiException
import com.soten.sjc.domain.model.congestion.Category
import com.soten.sjc.domain.model.congestion.CongestionFilter
import com.soten.sjc.domain.model.congestion.CongestionInfos
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

    private val _congestionInfos = MutableStateFlow(CongestionInfos.EMPTY)
    private val congestionInfos = _congestionInfos.asStateFlow()

    private val _congestionFilter = MutableStateFlow(CongestionFilter())
    private val congestionFilter = _congestionFilter.asStateFlow()

    val filteredCongestionInfos = congestionInfos.combine(congestionFilter) { infos, filter ->
        infos.search(filter)
    }

    private val _categories = MutableStateFlow(emptyList<Category>())
    val categories = _categories.asStateFlow()

    private val _error = MutableSharedFlow<Throwable>()
    val error = _error.asSharedFlow()

    init {
        fetchCongestionInfos()
    }

    fun fetchCongestionInfos() {
        viewModelScope.launch {
            congestRepository.fetchCongests()
                .onSuccess { congestionInfos ->
                    _categories.value = congestionInfos.getCategories()
                    _congestionInfos.value = congestionInfos
                }.onFailure {
                    _error.emit(it)
                }
        }
    }

    fun inputKeyword(input: String) {
        _congestionFilter.update {
            it.copy(input)
        }
    }
}