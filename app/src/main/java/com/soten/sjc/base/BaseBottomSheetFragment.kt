package com.soten.sjc.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BaseBottomSheetFragment<DB : ViewDataBinding>(@LayoutRes val layoutRes: Int) :
    BottomSheetDialogFragment() {

    private var _binding: DB? = null
    protected val binding get() = _binding!!

    private lateinit var behavior: BottomSheetBehavior<View>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        behavior =
            BottomSheetBehavior.from(
                dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet) ?: return
            )

        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit
        })

        behavior.skipCollapsed = true

        initViews()
        bindViews()
        observeData()
    }

    protected fun extend() {
        requireView().post {
            behavior.peekHeight = requireView().measuredHeight
        }
    }

    open fun initViews() = Unit
    open fun bindViews() = Unit
    open fun observeData() = Unit
}
