package com.soten.sjc.ui

import android.graphics.Color
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.soten.sjc.R
import com.soten.sjc.base.BaseActivity
import com.soten.sjc.base.BaseAdapter
import com.soten.sjc.databinding.ActivityMainBinding
import com.soten.sjc.domain.model.congestion.CongestionInfo
import com.soten.sjc.extensions.repeatOnStart
import com.soten.sjc.extensions.setBackgroundTint
import com.soten.sjc.util.KeyboardVisibilityUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main), AreaClicked {

    private val mainViewModel by viewModels<MainViewModel>()

    private val areaAdapter = BaseAdapter(R.layout.item_area, this)

    override fun onStart() {
        super.onStart()
        mainViewModel.fetchCongestionInfos()
    }

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

        binding.initChip.setOnClickListener {
            mainViewModel.initSorter()
        }

        binding.nameChip.setOnClickListener {
            mainViewModel.setSorter(isName = true)
        }

        binding.congestChip.setOnClickListener {
            mainViewModel.setSorter(isLevel = true)
        }

        binding.sortingImage.setOnClickListener {
            mainViewModel.setSorter()
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

        repeatOnStart {
            mainViewModel.congestionSorter.collect { sorter ->
                setCheckingChip(binding.nameChip, sorter.isAreaName)
                setCheckingChip(binding.congestChip, sorter.isLevel)

                val sortingBackgroundResource = if (sorter.isAscend) {
                    R.drawable.ic_sort_asc
                } else {
                    R.drawable.ic_sort_desc
                }
                binding.sortingImage.setBackgroundResource(sortingBackgroundResource)
            }
        }

        repeatOnStart {
            mainViewModel.congestionFilter.collect { filter ->
                if (filter.category != null) {
                    binding.categoryChip.text = filter.category?.value
                    setCheckingChip(binding.categoryChip, true)
                }

                binding.categoryChip.text = "카테고리"
                setCheckingChip(binding.categoryChip, false)
                return@collect
            }
        }
    }

    override fun onClickArea(congestionInfo: CongestionInfo) {
        mainViewModel.toggleBookmark(congestionInfo)
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

    private fun setCheckingChip(chip: TextView, isSorting: Boolean) {
        val tint = if (isSorting) R.color.darkGray else null
        chip.setBackgroundTint(tint)

        val textColor = if (isSorting) Color.WHITE else Color.BLACK
        chip.setTextColor(textColor)
    }

    companion object {
        private const val POSITION_FIRST = 0
    }
}
