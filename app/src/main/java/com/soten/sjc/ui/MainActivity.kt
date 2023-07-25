package com.soten.sjc.ui

import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.soten.sjc.R
import com.soten.sjc.base.BaseActivity
import com.soten.sjc.base.BaseAdapter
import com.soten.sjc.databinding.ActivityMainBinding
import com.soten.sjc.util.KeyboardVisibilityUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

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
        }
    }

    override fun observeData() {
        super.observeData()

        lifecycleScope.launch {
            mainViewModel.searchCongestionInfos
                .distinctUntilChanged()
                .collect {
                    areaAdapter.replaceAll(it)
                }
        }
    }
}