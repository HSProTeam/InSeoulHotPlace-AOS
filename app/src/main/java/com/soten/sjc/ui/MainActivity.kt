package com.soten.sjc.ui

import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.soten.sjc.R
import com.soten.sjc.base.BaseActivity
import com.soten.sjc.base.BaseAdapter
import com.soten.sjc.databinding.ActivityMainBinding
import com.soten.sjc.extensions.repeatOnStart
import com.soten.sjc.util.KeyboardVisibilityUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel by viewModels<MainViewModel>()

    private val areaAdapter = BaseAdapter(R.layout.item_area)

    private val keyboardVisibilityUtil by lazy {
        KeyboardVisibilityUtil(window) {
            binding.searchEditText.clearFocus()
        }
    }

    override fun initViews() {
        super.initViews()
        binding.recyclerView.adapter = areaAdapter

        keyboardVisibilityUtil.init()
    }

    override fun bindViews() {
        super.bindViews()
        binding.searchEditText.addTextChangedListener {
            mainViewModel.inputKeyword(it.toString())
            binding.recyclerView.scrollToPosition(0)
        }
    }

    override fun observeData() {
        super.observeData()

        repeatOnStart {
            mainViewModel.filteredCongestionInfos
                .distinctUntilChanged()
                .collect {
                    areaAdapter.replaceAll(it.value)
                }
        }
    }
}