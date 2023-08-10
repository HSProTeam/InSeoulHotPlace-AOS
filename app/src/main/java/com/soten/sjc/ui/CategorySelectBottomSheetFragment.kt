package com.soten.sjc.ui

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.soten.sjc.R
import com.soten.sjc.base.BaseAdapter
import com.soten.sjc.base.BaseBottomSheetFragment
import com.soten.sjc.databinding.BottomSheetCategorySelectBinding
import com.soten.sjc.domain.model.congestion.Category
import com.soten.sjc.extensions.repeatOnStart
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategorySelectBottomSheetFragment : BaseBottomSheetFragment<BottomSheetCategorySelectBinding>(
    R.layout.bottom_sheet_category_select,
), CategoryClicked {

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val categorySelectViewModel by viewModels<CategorySelectViewModel>()

    private val categoryAdapter = BaseAdapter(R.layout.item_category, this)

    override fun initViews() {
        super.initViews()

        initAdapter()

        initCategories()
    }

    override fun bindViews() {
        super.bindViews()

        binding.initContainer.setOnClickListener {
            categorySelectViewModel.initSelected()
        }

        binding.applyContainer.setOnClickListener {
            mainViewModel.setCategoriesFilter(categorySelectViewModel.getSelectedCategories())
            dismiss()
        }
    }

    override fun observeData() {
        super.observeData()

        repeatOnStart {
            categorySelectViewModel.categories.collect {
                categoryAdapter.replaceAll(it)
            }
        }
    }

    override fun onClickCategory(category: Category) {
        categorySelectViewModel.selectCategory(category)
    }

    private fun initAdapter() {
        binding.categoryRecyclerView.adapter = categoryAdapter
    }

    private fun initCategories() {
        val categories = mainViewModel.categories.value
        val filteredCategories = mainViewModel.congestionFilter.value.categories

        categorySelectViewModel.applyCategories(categories, filteredCategories)
    }
}
