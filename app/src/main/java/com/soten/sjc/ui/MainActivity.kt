package com.soten.sjc.ui

import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
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

    override fun initViews() {
        super.initViews()

        initAdMob()

        initAdapter()

        initKeyboardUtil()
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
                    binding.emptyInfoText.isVisible = it.value.isEmpty()
                    areaAdapter.replaceAll(it.value)
                }
        }
    }

    private fun initAdMob() {
        MobileAds.initialize(this)

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    private fun initAdapter() {
        binding.areaRecyclerView.adapter = areaAdapter
    }

    private fun initKeyboardUtil() {
        val keyboardVisibilityUtil = KeyboardVisibilityUtil(window) {
                binding.searchEditText.clearFocus()
            }

        keyboardVisibilityUtil.init()
    }

    companion object {
        private const val POSITION_FIRST = 0
    }
}