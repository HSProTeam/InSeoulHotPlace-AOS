package com.soten.sjc.ui

import android.graphics.Color
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.soten.sjc.R
import com.soten.sjc.base.BaseActivity
import com.soten.sjc.base.BaseAdapter
import com.soten.sjc.databinding.ActivityMainBinding
import com.soten.sjc.domain.model.congestion.CongestionFilter
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
            mainViewModel.initFilter()
        }

        binding.nameChip.setOnClickListener {
            mainViewModel.setSorter(isName = true)
        }

        binding.congestChip.setOnClickListener {
            mainViewModel.setSorter(isCongestNumber = true)
        }

        binding.categoryChip.setOnClickListener {
            CategorySelectBottomSheetFragment().show(supportFragmentManager, null)
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

                    binding.countText.text = it.value.size.toString()
                }
        }

        repeatOnStart {
            mainViewModel.congestionSorter.collect { sorter ->
                setCheckingChip(binding.nameChip, sorter.isAreaName)
                setCheckingChip(binding.congestChip, sorter.isCongestNumber)

                val sortingBackgroundResource = getSortingImageResource(sorter.isAscend)

                binding.sortingImage.setBackgroundResource(sortingBackgroundResource)
            }
        }

        repeatOnStart {
            mainViewModel.congestionFilter.collect { filter ->
                if (filter.categories.isNotEmpty()) {
                    val isMany = filter.categories.size > 1

                    binding.categoryChip.text = getCategoryText(isMany, filter)
                    setCheckingChip(binding.categoryChip, true)
                    return@collect
                }

                binding.categoryChip.text = TEXT_CATEGORY
                setCheckingChip(binding.categoryChip, false)
            }
        }

        repeatOnStart {
            mainViewModel.error.collect {
                Toast.makeText(baseContext, TEXT_ERROR, Toast.LENGTH_SHORT).show()
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

        val dividerItemDecoration =
            ItemDecoration(R.color.lightGray, baseContext, LinearLayoutManager.VERTICAL)

        binding.areaRecyclerView.addItemDecoration(dividerItemDecoration)
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

    private fun getCategoryText(
        isMany: Boolean,
        filter: CongestionFilter
    ) = if (isMany) "$TEXT_CATEGORY ${filter.categories.size}"
    else filter.categories.firstOrNull()?.value

    private fun getSortingImageResource(isAscend: Boolean): Int = if (isAscend)
        R.drawable.ic_sort_asc
    else R.drawable.ic_sort_desc

    companion object {
        private const val POSITION_FIRST = 0

        private const val TEXT_CATEGORY = "카테고리"
        private const val TEXT_ERROR = "오류가 발생했습니다. 잠시 후 새로고침 버튼을 눌러주세요."
    }
}
