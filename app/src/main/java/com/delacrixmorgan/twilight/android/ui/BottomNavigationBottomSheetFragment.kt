package com.delacrixmorgan.twilight.android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delacrixmorgan.twilight.android.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_navigation_bottom_sheet.*

class BottomNavigationBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        fun create(listener: Listener) = BottomNavigationBottomSheetFragment().apply {
            this.listener = listener
        }
    }

    interface Listener {
        fun onNavigationViewSelected(menuItems: MenuItems)
    }

    enum class MenuItems {
        Credits,
        About
    }

    private lateinit var listener: Listener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_navigation_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.creditsMenu -> listener.onNavigationViewSelected(MenuItems.Credits)
                R.id.aboutMenu -> listener.onNavigationViewSelected(MenuItems.About)
            }
            dismiss()
            true
        }
    }
}