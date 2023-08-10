package com.soten.sjc.ui

import com.soten.sjc.base.ItemClicked
import com.soten.sjc.domain.model.congestion.Category

interface CategoryClicked : ItemClicked {

    fun onClickCategory(category: Category)
}
