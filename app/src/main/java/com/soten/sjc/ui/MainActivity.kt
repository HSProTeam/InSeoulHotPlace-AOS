package com.soten.sjc.ui

import androidx.activity.viewModels
import androidx.core.view.isVisible
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
        binding.areaRecyclerView.adapter = areaAdapter

        keyboardVisibilityUtil.init()
    }

    override fun bindViews() {
        super.bindViews()
        binding.searchEditText.addTextChangedListener {
            binding.deleteKeywordImage.isVisible = it.toString().isNotBlank()
            mainViewModel.inputKeyword(it.toString())
            binding.areaRecyclerView.scrollToPosition(POSITION_FIRST)
        }

        binding.deleteKeywordImage.setOnClickListener {
            binding.searchEditText.text.clear()
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

    companion object {
        private const val POSITION_FIRST = 0
    }
}