package com.soten.sjc.ui.main

import com.soten.sjc.base.ItemClicked
import com.soten.sjc.domain.model.congestion.CongestionInfo

interface AreaClicked : ItemClicked {

    fun onClickArea(congestionInfo: CongestionInfo)
}
